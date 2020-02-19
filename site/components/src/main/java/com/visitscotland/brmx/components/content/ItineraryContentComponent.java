package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.dms.LocationObject;
import com.visitscotland.brmx.beans.mapping.Coordinates;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatStop;
import com.visitscotland.brmx.utils.LocationLoader;
import com.visitscotland.brmx.utils.Properties;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;


public class ItineraryContentComponent extends EssentialsContentComponent {

    private static final Logger logger = LoggerFactory.getLogger(ItineraryContentComponent.class);

    public final String STOPS_MAP = "stops";
    public final String FIRST_STOP_LOCATION = "firstStopLocation";
    public final String LAST_STOP_LOCATION = "lastStopLocation";
    public final String HERO_COORDINATES = "heroCoordinates";
    private final String ROOT_SITE = "/site/";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        generateStops(request, (Itinerary) request.getAttribute("document"));
        setCoordinates(request, (Itinerary) request.getAttribute("document"));
        request.setAttribute("path", getDocumentLocation((Itinerary) request.getAttribute("document")));
    }

    private void setCoordinates(HstRequest request, Itinerary itinerary) {
        LocationObject location = LocationLoader.getLocation(itinerary.getHeroImage().getLocation(), request.getLocale());

        if (location != null){
            Coordinates coordinates = new Coordinates();
            coordinates.setLatitude(location.getLatitude());
            coordinates.setLongitude(location.getLongitude());
            request.setAttribute(HERO_COORDINATES, coordinates);
        }
    }

    /**
     *
     * @param request
     * @param itinerary
     */
    private void generateStops(HstRequest request, Itinerary itinerary){

        String path= itinerary.getPath().substring(itinerary.getPath().indexOf(ROOT_SITE),itinerary.getPath().indexOf("/content/content")).replace(ROOT_SITE,"");
        request.setAttribute("path", path);

        final String LOCATION = "locationName";
        final String URL = "url";
        final String IMAGE = "image";
        final String TIME_TO_EXPLORE = "timeToExplore";
        final String LAT = "latitude";
        final String LON = "longitude";
        final String FACILITIES = "facilities";

        final Map<String ,FlatStop> products =  new LinkedHashMap<>();

        String firstStopId = null;
        String lastStopId = null;
        Integer index = 1;

        for (Day day: itinerary.getDays()) {
            for (Stop stop : day.getStops()) {
                FlatStop model = new FlatStop(stop);
                Coordinates coordinates = new Coordinates();
                model.setIndex(index++);
                FlatImage img = new FlatImage();

                if (stop.getStopItem() instanceof DMSLink){
                    DMSLink dmsLink = (DMSLink) stop.getStopItem();
                    List<String> facilities = new ArrayList<>();

                    if (stop.getImage()!=null) {
                        img.setCmsImage(stop.getImage());
                    }

                    //CONTENT prefix on error messages could means that the problem can be fixed by altering the content.
                    try {

                        if (dmsLink.getProduct() == null){
                            model.setErrorMessage("The product's id  wasn't provided");
                            logger.warn("CONTENT The product's id  wasn't provided for " + itinerary.getName() + ", Stop " + model.getIndex());
                        } else {
                            JSONObject product = getProduct(dmsLink.getProduct(), request.getLocale());
                            if (product == null){                                model.setErrorMessage("The product id does not exists in the DMS");
                                logger.warn("CONTENT The product's id  wasn't provided for " + itinerary.getName() + ", Stop " + model.getIndex());
                            } else {

                                model.setCta(product.getString(URL));
                                model.setLocation(product.getString(LOCATION));

                                //TODO: GET TIME TO EXPLORE FROM DMS
//                                model.setTimeToexplore(product.getString(TIME_TO_EXPLORE));
                                if (stop.getImage() == null){
                                    img.setExternalImage(product.getString(IMAGE));
                                    //TODO: SET ALT-TEXT, CREDITS AND DESCRIPTION
                                }

                                coordinates.setLatitude(product.getDouble(LAT));
                                coordinates.setLongitude(product.getDouble(LON));
                                model.setCoordinates(coordinates);

                                for (Object facility:  product.getString(FACILITIES).split(",")) {
                                    facilities.add(facility.toString());
                                }

                                model.setFacilities(facilities);

                            }
                        }
                    } catch (IOException exception) {
                        model.setErrorMessage("Error while querying the DMS: " + exception.getMessage());
                        logger.error("Error while querying the DMS for " + itinerary.getName() + ", Stop " + model.getIndex() + ": " + exception.getMessage());
                    }
                } else if (stop.getStopItem() instanceof ItineraryExternalLink){
                    ItineraryExternalLink externalLink = (ItineraryExternalLink) stop.getStopItem();

                    if (stop.getImage() != null) {
                        img.setCmsImage(stop.getImage());
                        img.setAltText(stop.getImage().getAltText());
                        img.setCredit(stop.getImage().getCredit());
                        img.setDescription(stop.getImage().getDescription());
                    }

                    model.setTimeToexplore(externalLink.getTimeToExplore());
                    model.setCta(externalLink.getExternalLink().getLink());


                    if (externalLink.getCoordinates() != null) {
                        coordinates.setLatitude(externalLink.getCoordinates().getLatitude());
                        coordinates.setLongitude(externalLink.getCoordinates().getLongitude());
                        model.setCoordinates(coordinates);
                    }

                }

                lastStopId = model.getIdentifier();
                if (firstStopId == null){
                    firstStopId = lastStopId;
                }
                model.setImage(img);
                products.put(model.getIdentifier(), model);
            }
        }

        if (products.size() > 0 ) {
            request.setAttribute(FIRST_STOP_LOCATION, products.get(firstStopId).getLocation());
            request.setAttribute(LAST_STOP_LOCATION, products.get(lastStopId).getLocation());

            request.setAttribute(STOPS_MAP, products);
        }
    }


    private JSONObject getProduct(String productId, Locale locale) throws IOException {

        String body = request(Properties.VS_DMS_PRODUCTS + "/data/product-search/map?prod_id=" + productId+ "&locale="+locale.getLanguage());
        JSONObject json = new JSONObject(body);
        JSONArray data = (JSONArray) json.get("data");

        return data.getJSONObject(0);
    }

    /**
     * Request a page and return the body as String
     */
    private static String request(String url) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        final StringBuilder sb = new StringBuilder();
        int cp;

        while ((cp = br.read()) != -1) {
            sb.append((char) cp);
        }

        return sb.toString();
    }

    /**
     *
     * @param itinerary
     */
    private String getDocumentLocation( Itinerary itinerary) {
        return itinerary.getPath().substring(itinerary.getPath().indexOf(ROOT_SITE), itinerary.getPath().indexOf("/content/content")).replace(ROOT_SITE, "");
    }
}