package com.visitscotland.brxm.components.content;


import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.ProductSearchBuilder;
import com.visitscotland.brxm.factory.ItineraryFactory;
import com.visitscotland.brxm.hippobeans.Itinerary;
import com.visitscotland.brxm.model.ItineraryPage;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ItineraryContentComponent extends PageContentComponent<Itinerary> {

    private static final Logger logger = LoggerFactory.getLogger(ItineraryContentComponent.class);

    public static final String ITINERARY = "itinerary";

    private ItineraryFactory itineraryFactory;

    public ItineraryContentComponent() {
        logger.debug("ItineraryContentComponent initialized");

        itineraryFactory = VsComponentManager.get(ItineraryFactory.class);
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addHeroCoordinates(request);
        addProductSearchBuilder(request);

        request.setAttribute(ITINERARY, itineraryFactory.buildItinerary(getDocument(request), request.getLocale()));

        //TODO: Remove this code after Refactoring the ftls
        generateStops(request);
    }

    /**
     * @deprecated TODO: The following method needs to be removed after Refactoring the ftls
     */
    @Deprecated
    private void generateStops(HstRequest request) {
        final String STOPS_MAP = "stops";
        final String DISTANCE = "distance";
        final String FIRST_STOP_LOCATION = "firstStopLocation";
        final String LAST_STOP_LOCATION = "lastStopLocation";

        ItineraryPage page = itineraryFactory.buildItinerary(getDocument(request), request.getLocale());

        request.setAttribute(STOPS_MAP, page.getStops());
        request.setAttribute(DISTANCE, page.getDistance());
        request.setAttribute(FIRST_STOP_LOCATION, page.getFirstStopLocation());
        request.setAttribute(LAST_STOP_LOCATION, page.getLastStopLocation());
    }

    public void addProductSearchBuilder(HstRequest request) {
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        try {
            TemplateHashModel psb = (TemplateHashModel) staticModels.get(ProductSearchBuilder.class.getCanonicalName());
            request.setAttribute("ProductSearchBuilder", psb);
        } catch (TemplateModelException e) {
            logger.error("Product Search Builder is not available for the Page", e);
        }
    }

}
