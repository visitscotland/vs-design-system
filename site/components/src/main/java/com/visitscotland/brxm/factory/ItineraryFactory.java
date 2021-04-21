package com.visitscotland.brxm.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.DMSUtils;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.model.*;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.services.CommonUtilsService;
import com.visitscotland.brxm.services.DocumentUtilsService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.utils.Contract;
import com.visitscotland.utils.CoordinateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Locale;

import static com.visitscotland.brxm.dms.DMSConstants.DMSProduct.*;

@Component
public class ItineraryFactory {

    private static final Logger logger = LoggerFactory.getLogger(ItineraryFactory.class);

    private static final String BUNDLE_FILE = "itinerary";

    private final ResourceBundleService bundle;
    private final DMSDataService dmsData;
    private final ImageFactory imageFactory;
    private final DMSUtils utils;
    private final DocumentUtilsService documentUtils;

    public ItineraryFactory(ResourceBundleService bundle, DMSDataService dmsData, ImageFactory imageFactory, DMSUtils utils, DocumentUtilsService documentUtils) {
        this.bundle = bundle;
        this.dmsData = dmsData;
        this.imageFactory = imageFactory;
        this.utils = utils;
        this.documentUtils = documentUtils;
    }

    /**
     * Collects the information about an itinerary and enhances the information in it
     */
    public ItineraryPage buildItinerary (Itinerary itinerary, Locale locale){
        final boolean calculateDistance = (itinerary.getDistance() == 0);

        ItineraryPage page = new ItineraryPage();
        ItineraryStopModule firstStop = null;
        ItineraryStopModule lastStop = null;
        BigDecimal totalDistance = BigDecimal.ZERO;
        Coordinates prevCoordinates = null;
        int index = 1;

        page.setDocument(itinerary);
        page.setDays(documentUtils.getAllowedDocuments(itinerary, Day.class));

        for (Day day : documentUtils.getAllowedDocuments(itinerary, Day.class)) {
            for (Stop stop : day.getStops()) {
                ItineraryStopModule module = generateStop(locale, stop, itinerary, index++);

                lastStop = module;
                if (firstStop == null) {
                    firstStop = lastStop;
                }

                if (calculateDistance) {
                    if (prevCoordinates != null && module.getCoordinates() != null) {
                        BigDecimal distancePrevStop = getDistanceStops(module.getCoordinates(), prevCoordinates);
                        totalDistance = totalDistance.add(distancePrevStop);

                        //TODO Why this is only populated when distance is 0?
                        module.setDistance(distancePrevStop);
                    }
                    prevCoordinates = module.getCoordinates();
                }

                page.addStop(module);
            }
        }

        page.setDistance(calculateDistance ? totalDistance :BigDecimal.valueOf(itinerary.getDistance()));

        populateFirstAndLastStopTexts(page, firstStop, lastStop);

        return page;
    }

    /**
     * Populates the first stop text and the last stop text depending on whether they have been set or not in the
     * Itinerary document
     */
    private void populateFirstAndLastStopTexts(ItineraryPage page, ItineraryStopModule first, ItineraryStopModule last){
        Itinerary itinerary = page.getDocument();

        if (Contract.isEmpty(itinerary.getStart()) && first != null) {
            page.setFirstStopLocation(first.getSubTitle());
        } else {
            page.setFirstStopLocation(itinerary.getStart());
        }

        if(Contract.isEmpty(itinerary.getFinish()) && last != null) {
            page.setLastStopLocation(last.getSubTitle());
        } else {
            page.setLastStopLocation(itinerary.getFinish());
        }
    }

    /**
     * Method to calculate the distance between stops
     */
    private BigDecimal getDistanceStops(Coordinates current, Coordinates previous) {
        return CoordinateUtils.haversineDistance(
                BigDecimal.valueOf(previous.getLatitude()), BigDecimal.valueOf(previous.getLongitude()),
                BigDecimal.valueOf(current.getLatitude()), BigDecimal.valueOf(current.getLongitude()),
                true, "#,###,##0.0");
    }

    /**
     * Transform an Stop document in a ItineraryStopModule and add extra information depending on the type
     */
    public ItineraryStopModule generateStop(Locale locale, Stop stop, Itinerary itinerary, Integer index){
        ItineraryStopModule module = initializeStop(stop);
        module.setIndex(index);

        if (stop.getImage() != null) {
            module.setImage(imageFactory.createImage(stop.getImage(), module, locale));
        }

        if (stop.getStopItem() instanceof DMSLink) {
            processDMSStop(locale, itinerary, module, (DMSLink) stop.getStopItem());
        } else if (stop.getStopItem() instanceof ItineraryExternalLink) {
            processExternalStop(locale, module, (ItineraryExternalLink) stop.getStopItem());
        } else {
            module.addErrorMessage("The product's id  was not provided");
            if (logger.isWarnEnabled()) {
                logger.warn(CommonUtilsService.contentIssue("The product's id  was not provided for %s, Stop %s", itinerary.getName(), module.getIndex()));
            }
        }

        if (module.getImage() == null){
            module.addErrorMessage("An image should be provided for external links");
        }

        if (!Contract.isEmpty(stop.getSubtitle())) {
            module.setSubTitle(stop.getSubtitle());
        }else{
            module.setSubTitle(module.getLocation());
        }

        if (module.getSubTitle() == null){
            module.addErrorMessage(String.format("The stop %s does not have a subtitle.", module.getIndex()));
        }

        return module;
    }

    /**
     * Creates an Stop from the stop Document type
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
     * Extracts all relevant information for an External link in order to enhance the stop
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
            if (logger.isWarnEnabled()) {
                logger.warn(CommonUtilsService.contentIssue("The product id does not match in the DMS for %s, Stop %s", itinerary.getName(), module.getIndex()));
            }
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
            module.setOpening(product.get(OPENING));
            module.setOpenLink(new FlatLink(bundle.getResourceBundle(BUNDLE_FILE, "stop.opening", locale),
                    module.getCtaLink().getLink() + "#opening", null));
        }
    }
}
