package com.visitscotland.brxm.components.content;


import com.visitscotland.brxm.dms.DMSUtils;
import com.visitscotland.brxm.factory.ImageFactory;
import com.visitscotland.brxm.factory.ItineraryFactory;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.model.*;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.services.DocumentUtilsService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.dms.LocationLoader;
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
    private ItineraryFactory itineraryFactory;
    private DocumentUtilsService documentUtils;

    public ItineraryContentComponent() {
        logger.debug("ItineraryContentComponent initialized");

        bundle = VsComponentManager.get(ResourceBundleService.class);
        locationLoader = VsComponentManager.get(LocationLoader.class);
        dmsData = VsComponentManager.get(DMSDataService.class);
        imageFactory = VsComponentManager.get(ImageFactory.class);
        utils = VsComponentManager.get(DMSUtils.class);
        itineraryFactory = VsComponentManager.get(ItineraryFactory.class);
        documentUtils = VsComponentManager.get(DocumentUtilsService.class);
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        generateStops(request);
        addHeroCoordinates(request);
    }

    /**
     * @param request HstRequest
     */
    private void generateStops(HstRequest request) {

        final Itinerary itinerary = getDocument(request);


        final Map<String, ItineraryStopModule> products = new LinkedHashMap<>();
        BigDecimal totalDistance = BigDecimal.ZERO;
        Coordinates prevCoordinates = null;

        String firstStopId = null;
        String lastStopId = null;
        int index = 1;

        for (Day day : documentUtils.getAllowedDocuments(itinerary, Day.class)) {
            for (Stop stop : day.getStops()) {
                ItineraryStopModule module = itineraryFactory.generateStop(request.getLocale(), stop, itinerary, index++);

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

        request.setAttribute(STOPS_MAP, products);

        request.setAttribute(DISTANCE, totalDistance.compareTo(BigDecimal.ZERO) == 0 ? itinerary.getDistance() : totalDistance);

        if (products.size() > 0) {
            request.setAttribute(FIRST_STOP_LOCATION, itinerary.getStart().isEmpty() ? products.get(firstStopId).getSubTitle() : itinerary.getStart());
            request.setAttribute(LAST_STOP_LOCATION, itinerary.getFinish().isEmpty() ? products.get(lastStopId).getSubTitle() : itinerary.getFinish());
        } else {
            request.setAttribute(FIRST_STOP_LOCATION, itinerary.getStart());
            request.setAttribute(LAST_STOP_LOCATION, itinerary.getFinish());
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
