package com.visitscotland.brxm.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.DMSUtils;
import com.visitscotland.brxm.hippobeans.DMSLink;
import com.visitscotland.brxm.hippobeans.Itinerary;
import com.visitscotland.brxm.hippobeans.ItineraryExternalLink;
import com.visitscotland.brxm.hippobeans.Stop;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.ItineraryStopModule;
import com.visitscotland.brxm.model.LinkType;
import com.visitscotland.brxm.services.CommonUtilsService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.visitscotland.brxm.dms.DMSConstants.DMSProduct.*;

@Component
public class ItineraryFactory {

    private static final Logger logger = LoggerFactory.getLogger(ItineraryFactory.class);

    private static final String BUNDLE_FILE = "itinerary";

    private ResourceBundleService bundle;
    private DMSDataService dmsData;
    private ImageFactory imageFactory;
    private DMSUtils utils;

    public ItineraryFactory(ResourceBundleService bundle, DMSDataService dmsData, ImageFactory imageFactory, DMSUtils utils) {
        this.bundle = bundle;
        this.dmsData = dmsData;
        this.imageFactory = imageFactory;
        this.utils = utils;
    }

    /**
     * Genarates the information forone Stop from the
     * @param locale
     * @param stop
     * @param itinerary
     * @return
     */
    public ItineraryStopModule generateStop(Locale locale, Stop stop, Itinerary itinerary, Integer index){
        ItineraryStopModule module = initializeStop(stop);
        module.setIndex(index);

        if (stop.getImage() != null) {
            //TODO Use imageFactory
            module.setImage(imageFactory.createImage(stop.getImage(), module, locale));
        }

        if (stop.getStopItem() instanceof DMSLink) {
            processDMSStop(locale, itinerary, module, (DMSLink) stop.getStopItem());
        } else if (stop.getStopItem() instanceof ItineraryExternalLink) {
            processExternalStop(locale, module, (ItineraryExternalLink) stop.getStopItem());
        } else {
            module.addErrorMessage("The product's id  was not provided");
            logger.warn(CommonUtilsService.contentIssue("The product's id  was not provided for %s, Stop %s", itinerary.getName(), module.getIndex()));
        }

        //TODO: Check coordinates for externalLinks

        if (module.getImage() == null){
            module.addErrorMessage("An image should be provided for external links");
        }

        //Check with JL this statement was changed
        if (!Contract.isEmpty(stop.getSubtitle())) {
            module.setSubTitle(stop.getSubtitle());
        }

        if (module.getSubTitle() == null){
            module.addErrorMessage(String.format("The stop %s does not have a subtitle.", module.getIndex()));
        }

        return module;
    }

    /**
     * Creates an Stop from the stop Document type
     * @param stop
     * @return
     */
    private ItineraryStopModule initializeStop(Stop stop) {
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

    /**
     * Generates the text for time to explore
     */
    private String generateTimeToExplore(String visitDuration, Locale locale){
        return visitDuration + " " + bundle.getResourceBundle(BUNDLE_FILE, visitDuration.equals("1") ?"stop.hour": "stop.hours", locale);
    }

    /**
     * Extracts all relevant information external link in order to enhance the stop
     */
    public void processExternalStop(Locale locale, ItineraryStopModule module, ItineraryExternalLink externalLink) {
        module.setTimeToExplore(generateTimeToExplore(externalLink.getTimeToExplore(), locale));

        if (externalLink.getExternalLink() != null) {
            FlatLink ctaLink = new FlatLink(bundle.getCtaLabel(externalLink.getExternalLink().getLabel(), locale),
                    externalLink.getExternalLink().getLink(), LinkType.EXTERNAL);
            module.setCtaLink(ctaLink);
        }

        if (externalLink.getCoordinates() != null) {
            module.setCoordinates(new Coordinates(externalLink.getCoordinates().getLatitude(), externalLink.getCoordinates().getLongitude()));
        }
    }

    /**
     * Extracts all relevant information from the Product Card in order to enhance the Stop
     */
    public void processDMSStop(Locale locale, Itinerary itinerary, ItineraryStopModule module, DMSLink dmsLink) {
        JsonNode product = dmsData.productCard(dmsLink.getProduct(), locale);

        if (product == null) {
            module.addErrorMessage("The product id does not match in the DMS");
            logger.warn(CommonUtilsService.contentIssue("The product id does not match in the DMS for %s, Stop %s", itinerary.getName(), module.getIndex()));
            return;
        }

        module.setCtaLink(new FlatLink(bundle.getCtaLabel(dmsLink.getLabel(), locale), product.get(URL).asText(), LinkType.INTERNAL));
        module.setCoordinates(new Coordinates(product.get(LATITUDE).asDouble(), product.get(LONGITUDE).asDouble()));
        module.setFacilities(utils.getKeyFacilities(product));

        if (product.has(TIME_TO_EXPLORE)) {
            module.setTimeToExplore(generateTimeToExplore(product.get(TIME_TO_EXPLORE).asText(), locale));
        }

        if (product.has(PRICE) && product.get(PRICE).has(DISPLAY_PRICE)) {
            module.setPrice(product.get(PRICE).get(DISPLAY_PRICE).asText());
        }

        //TODO: Create a test for this case. At this point the image should have been set
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

        if (product.has(OPENING)){
            module.setOpen(utils.setOpeningTimes(product, locale));
            module.setOpenLink(new FlatLink(bundle.getResourceBundle(BUNDLE_FILE, "stop.opening", locale),
                    module.getCtaLink().getLink() + "#opening", null));
        }
    }
}
