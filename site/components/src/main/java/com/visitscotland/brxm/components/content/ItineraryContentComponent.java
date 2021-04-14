package com.visitscotland.brxm.components.content;


import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.dms.DMSUtils;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.factory.ImageFactory;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.model.*;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.services.CommonUtilsService;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.utils.Contract;
import com.visitscotland.utils.CoordinateUtils;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;


public class ItineraryContentComponent extends PageContentComponent<Itinerary> {

    private static final Logger logger = LoggerFactory.getLogger(ItineraryContentComponent.class);

    final String BUNDLE = "itinerary";

    public final String STOPS_MAP = "stops";

    public final String DISTANCE = "distance";
    public final String FIRST_STOP_LOCATION = "firstStopLocation";
    public final String LAST_STOP_LOCATION = "lastStopLocation";

    private ResourceBundleService bundle;
    private LocationLoader locationLoader;
    private DMSDataService dmsData;
    private ImageFactory imageFactory;
    private DMSUtils utils;

    public ItineraryContentComponent() {
        logger.debug("ItineraryContentComponent initialized");

        bundle = VsComponentManager.get(ResourceBundleService.class);
        locationLoader = VsComponentManager.get(LocationLoader.class);
        dmsData = VsComponentManager.get(DMSDataService.class);
        imageFactory = VsComponentManager.get(ImageFactory.class);
        utils = VsComponentManager.get(DMSUtils.class);
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        generateStops(request);
        addHeroCoordinates(request);
    }


    final String ADDRESS = "address";
    final String LOCATION = "city";
    final String URL = "url";
    final String TIME_TO_EXPLORE = "timeToExplore";
    final String PRICE = "price";
    final String DISPLAY_PRICE = "displayPrice";

    final String LAT = "latitude";
    final String LON = "longitude";

    final String IMAGE = "images";



    /**
     * @param request HstRequest
     */
    private void generateStops(HstRequest request) {

        final Itinerary itinerary = getDocument(request);
        final String OTYML = "otyml";


        final Map<String, ItineraryStopModule> products = new LinkedHashMap<>();
        BigDecimal totalDistance = BigDecimal.ZERO;
        Coordinates prevCoordinates = null;

        String firstStopId = null;
        String lastStopId = null;
        int index = 1;

        for (Day day : itinerary.getDays()) {
            for (Stop stop : day.getStops()) {
                ItineraryStopModule module = generateStop(request.getLocale(), stop, itinerary);

                module.setIndex(index++);

                lastStopId = module.getIdentifier();
                if (firstStopId == null) {
                    firstStopId = lastStopId;
                }
                if (itinerary.getDistance() == 0) {
                    if (prevCoordinates != null && module.getCoordinates() != null) {
                        BigDecimal distancePrevStop = getDistanceStops(module, prevCoordinates);
                        totalDistance = totalDistance.add(distancePrevStop);
                        module.setDistance(distancePrevStop);
                    }
                    prevCoordinates = module.getCoordinates();
                }
                products.put(module.getIdentifier(), module);
            }
        }

        request.setAttribute(DISTANCE, totalDistance.compareTo(BigDecimal.ZERO) == 0 ? itinerary.getDistance() : totalDistance);
        request.setAttribute(STOPS_MAP, products);
        request.setAttribute(OTYML, addOTYML(itinerary, request.getLocale()));

        if (products.size() > 0) {
            request.setAttribute(FIRST_STOP_LOCATION, itinerary.getStart().isEmpty() ? products.get(firstStopId).getSubTitle() : itinerary.getStart());
            request.setAttribute(LAST_STOP_LOCATION, itinerary.getFinish().isEmpty() ? products.get(lastStopId).getSubTitle() : itinerary.getFinish());
        } else {
            request.setAttribute(FIRST_STOP_LOCATION, itinerary.getStart());
            request.setAttribute(LAST_STOP_LOCATION, itinerary.getFinish());
        }
    }

    public ItineraryStopModule createStop(Stop stop) {
        ItineraryStopModule module = new ItineraryStopModule();
        module.setIdentifier(stop.getIdentifier());
        module.setTitle(stop.getTitle());
        module.setDescription(stop.getDescription());

        if (stop.getStopTip()!=null){
            module.setTipsTitle(stop.getStopTip().getTitle());
            module.setTipsBody(stop.getStopTip().getCopy());
        }

        return module;
    }

    private ItineraryStopModule generateStop(Locale locale, Stop stop, Itinerary itinerary){
        ItineraryStopModule module = createStop(stop);

        if (stop.getImage() != null) {
            Image cmsImage = stop.getImage();
            if (cmsImage != null) {
                //TODO Use imageFactory
                module.setImage(imageFactory.createImage(cmsImage, module, locale));

                if (!(stop.getStopItem() instanceof DMSLink)) {
                    LocationObject locationObject = locationLoader.getLocation(cmsImage.getLocation(), locale);
                    if (locationObject != null) {
                        module.setLocation(locationObject.getName());
                    }
                }
            }
        }

        if (stop.getStopItem() instanceof DMSLink) {
            processDMSProduct(locale, itinerary, module, (DMSLink) stop.getStopItem());
        } else if (stop.getStopItem() instanceof ItineraryExternalLink) {
            processExternalLink(locale, module, (ItineraryExternalLink) stop.getStopItem());
        } else {
            module.addErrorMessage("The product's id  was not provided");
            logger.warn(CommonUtilsService.contentIssue("The product's id  was not provided for %s, Stop %s", itinerary.getName(), module.getIndex()));
        }

        //TODO: Check coordinates for externalLinks

        if (module.getImage() == null){
            module.addErrorMessage("An image should be provided for external links");
        }

        if (Contract.isEmpty(stop.getSubtitle())) {
            module.setSubTitle(module.getLocation());
        } else {
            module.setSubTitle(stop.getSubtitle());
        }

        return module;
    }



    private String generateTimeToExplore(String visitDuration, Locale locale){
        return visitDuration + " " + bundle.getResourceBundle(BUNDLE, visitDuration.equals("1") ?"stop.hour": "stop.hours", locale);
    }

    public void processExternalLink(Locale locale, ItineraryStopModule module, ItineraryExternalLink externalLink) {
        module.setTimeToExplore(generateTimeToExplore(externalLink.getTimeToExplore(), locale));

        if (externalLink.getExternalLink() != null) {
            //TODO: Link Service?
            FlatLink ctaLink = new FlatLink(bundle.getCtaLabel(externalLink.getExternalLink().getLabel(), locale),
                    externalLink.getExternalLink().getLink(), LinkType.EXTERNAL);
            module.setCtaLink(ctaLink);
        }

        if (externalLink.getCoordinates() != null) {
            module.setCoordinates(new Coordinates(externalLink.getCoordinates().getLatitude(), externalLink.getCoordinates().getLongitude()));
        }
    }

    public void processDMSProduct(Locale locale, Itinerary itinerary, ItineraryStopModule module, DMSLink dmsLink) {
        JsonNode product = dmsData.productCard(dmsLink.getProduct(), locale);

        if (product == null) {
            module.addErrorMessage("The product id does not match in the DMS");
            logger.warn(CommonUtilsService.contentIssue("The product id does not match in the DMS for %s, Stop %s", itinerary.getName(), module.getIndex()));
        } else {
            //TODO: Link Service?
            module.setCtaLink(new FlatLink(bundle.getCtaLabel(dmsLink.getLabel(), locale), product.get(URL).asText(), LinkType.INTERNAL));

            if (product.has(TIME_TO_EXPLORE)) {
                module.setTimeToExplore(generateTimeToExplore(product.get(TIME_TO_EXPLORE).asText(), locale));
            }

            if (product.has(PRICE)) {
                JsonNode price = product.get(PRICE);
                module.setPrice(price.get(DISPLAY_PRICE).asText());
            }

            //TODO At this point the image should have been set
            //TODO: Create a test for this case.
//                if (stop.getImage() == null && product.has(IMAGE)) {
            if (module.getImage() == null && product.has(IMAGE)) {
                //TODO: Check this in Freemarker
                module.setImage(imageFactory.createImage(product, module));
            }

            if (product.has(ADDRESS)) {
                JsonNode address = product.get(ADDRESS);
                module.setAddress(address);
                if (address.has(LOCATION)){
                    module.setLocation(address.get(LOCATION).asText());
                }
            }

            module.setCoordinates(new Coordinates(product.get(LAT).asDouble(), product.get(LON).asDouble()));

            //TODO dmsUtils.getFacilities
            module.setFacilities(utils.getKeyFacilities(product));

            utils.setOpeningTimes(product, module, locale);
        }
    }

    /**
     * Method to calculate the distance between stops
     * y     * @param model the stop
     *
     * @return distance between stops
     */
    private BigDecimal getDistanceStops(ItineraryStopModule model, Coordinates prevCoordinates) {
        BigDecimal distance = CoordinateUtils.haversineDistance(new BigDecimal(prevCoordinates.getLatitude()), new BigDecimal(prevCoordinates.getLongitude()),
                new BigDecimal(model.getCoordinates().getLatitude()), new BigDecimal(model.getCoordinates().getLongitude()), true, "#,###,##0.0");

        if (distance != null) {
            model.setDistance(distance);
        }

        return distance;
    }

}
