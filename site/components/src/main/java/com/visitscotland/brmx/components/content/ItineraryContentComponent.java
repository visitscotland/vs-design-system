package com.visitscotland.brmx.components.content;


import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.dms.LocationObject;
import com.visitscotland.brmx.beans.mapping.Coordinates;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.FlatStop;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.LocationLoader;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


public class ItineraryContentComponent extends PageContentComponent<Itinerary> {

    private static final Logger logger = LoggerFactory.getLogger(ItineraryContentComponent.class);

    public final String STOPS_MAP = "stops";
    public final String FIRST_STOP_LOCATION = "firstStopLocation";
    public final String LAST_STOP_LOCATION = "lastStopLocation";
    public final String HERO_COORDINATES = "heroCoordinates";
    private final String ROOT_SITE = "/site/";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        generateStops(request);
        setCoordinates(request);
    }

    private void setCoordinates(HstRequest request) {
        LocationObject location = LocationLoader.getLocation(getDocument(request).getHeroImage().getLocation(), request.getLocale());

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
     */
    private void generateStops(HstRequest request)  {

        final Itinerary itinerary = getDocument(request);
        final String ADDRESS = "address";
        final String LOCATION = "city";
        final String URL = "url";
        final String TIME_TO_EXPLORE = "timeToExplore";
        final String PRICE = "price";
        final String LAT = "latitude";
        final String LON = "longitude";
        final String FACILITIES = "keyFacilities";
        final String OPENING = "todayOpeningTime";
        final String START_TIME = "startTime";
        final String END_TIME = "endTime";
        final String IMAGE = "images";
        final String MEDIA = "mediaUrl";
        final String CREDIT = "copyright";
        final String ALT_TEXT = "altText";

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
                    if (stop.getImage()!=null) {
                        img.setCmsImage(stop.getImage());
                    }

                    //CONTENT prefix on error messages could means that the problem can be fixed by altering the content.
                    try {

                        if (dmsLink.getProduct() == null){
                            model.setErrorMessage("The product's id  was not provided");
                            logger.warn(CommonUtils.contentIssue("The product's id was not provided for %s, Stop %s", itinerary.getName(), model.getIndex()));
                        } else {
                            JSONObject product = CommonUtils.getProduct(dmsLink.getProduct(), request.getLocale());
                            if (product == null){
                                model.setErrorMessage("The product id does not exist in the DMS");
                                logger.warn(CommonUtils.contentIssue("The product id does not exist in the DMS for %s, Stop %s", itinerary.getName(), model.getIndex()));
                            } else {

                                FlatLink ctaLink = new FlatLink(this.getCtaLabel(dmsLink.getLabel(),request.getLocale()),product.getString(URL));
                                model.setCtaLink(ctaLink);
                                if (product.has(ADDRESS)){
                                    JSONObject address =product.getJSONObject(ADDRESS);
                                    model.setAddress(address);
                                    model.setLocation( address.has(LOCATION)?address.getString(LOCATION):null);
                                }

                                model.setTimeToexplore(product.has(TIME_TO_EXPLORE)? product.getString(TIME_TO_EXPLORE):null);
                                model.setPrice(product.has(PRICE)? product.getString(PRICE):null);
                                if (stop.getImage() == null){
                                    JSONObject dmsImage = product.getJSONArray(IMAGE).getJSONObject(0);
                                    img.setExternalImage(dmsImage.has(MEDIA)?dmsImage.getString(MEDIA):null);
                                    img.setCredit(dmsImage.has(CREDIT)?dmsImage.getString(CREDIT):null);
                                    img.setDescription(dmsImage.has(ALT_TEXT)?dmsImage.getString(ALT_TEXT):product.getString("name"));
                                    img.setAltText(img.getDescription());
                                }

                                coordinates.setLatitude(product.getDouble(LAT));
                                coordinates.setLongitude(product.getDouble(LON));
                                model.setCoordinates(coordinates);

                                if (product.has(FACILITIES)){
                                    List<JSONObject> facilities = new ArrayList<>();
                                    JSONArray keyFacilitiesList = product.getJSONArray(FACILITIES);
                                    if (keyFacilitiesList!=null){
                                        for (int i = 0; i < keyFacilitiesList.length(); i++) {
                                            facilities.add(keyFacilitiesList.getJSONObject(i));
                                        }
                                    }
                                    model.setFacilities(facilities);
                                }

                                if (product.has(OPENING)){
                                    JSONObject opening = product.getJSONObject(OPENING);
                                    //TODO adjust the message to designs when ready
                                    if ((opening.has(START_TIME)) && (opening.has(END_TIME))) {
                                        model.setOpen(opening.getString("day") + ": " + opening.getString("startTime") + "-" + opening.getString("endTime"));
                                    }
                                    //TODO "* Please check openings times" create this message on the ftl with the anchor
                                }

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

                    if (externalLink.getExternalLink() != null) {
                        FlatLink ctaLink = new FlatLink(this.getCtaLabel(externalLink.getExternalLink().getLabel(), request.getLocale()), externalLink.getExternalLink().getLink());
                        model.setCtaLink(ctaLink);
                    }

                    if (externalLink.getCoordinates() != null) {
                        coordinates.setLatitude(externalLink.getCoordinates().getLatitude());
                        coordinates.setLongitude(externalLink.getCoordinates().getLongitude());
                        model.setCoordinates(coordinates);
                    }

                }else{
                    model.setErrorMessage("The product's id  was not provided");
                    logger.warn(CommonUtils.contentIssue("The product's id  was not provided for %s, Stop %s", itinerary.getName(), model.getIndex()));
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
}
