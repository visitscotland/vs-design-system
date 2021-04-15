package com.visitscotland.brxm.components.content;


import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.factory.ItineraryFactory;
import com.visitscotland.brxm.hippobeans.Itinerary;
import com.visitscotland.brxm.model.ItineraryPage;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItineraryContentComponent extends PageContentComponent<Itinerary> {

    private static final Logger logger = LoggerFactory.getLogger(ItineraryContentComponent.class);

    public static final String STOPS_MAP = "stops";

    //The following constants are to be removed after Refactoring the ftls
    public static final String ITINERARY = "itinerary";
    public static final String DISTANCE = "distance";
    public static final String FIRST_STOP_LOCATION = "firstStopLocation";
    public static final String LAST_STOP_LOCATION = "lastStopLocation";

    private ItineraryFactory itineraryFactory;


    public ItineraryContentComponent() {
        logger.debug("ItineraryContentComponent initialized");

        itineraryFactory = VsComponentManager.get(ItineraryFactory.class);
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
        ItineraryPage page = itineraryFactory.buildItinerary(getDocument(request), request.getLocale());

        request.setAttribute(ITINERARY, page);

        // TODO: The following constants are to be removed after Refactoring the ftls
        request.setAttribute(STOPS_MAP, page.getStops());
        request.setAttribute(DISTANCE, page.getDistance());
        request.setAttribute(FIRST_STOP_LOCATION, page.getFirstStopLocation());
        request.setAttribute(LAST_STOP_LOCATION, page.getLastStopLocation());
    }



}
