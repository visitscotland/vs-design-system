package com.visitscotland.brxm.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.dms.DMSConstants;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import com.visitscotland.brxm.model.*;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.model.megalinks.EnhancedLink;
import com.visitscotland.brxm.services.CommonUtilsService;
import com.visitscotland.brxm.services.DocumentUtilsService;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LinkFactory {

    private static final Logger logger = LoggerFactory.getLogger(LinkFactory.class);

    private final HippoUtilsService utils;
    private final DMSDataService dmsData;
    private final LinkService linkService;
    private final ResourceBundleService bundle;
    private final LocationLoader locationLoader;
    private final ImageFactory imageFactory;
    private final CommonUtilsService commonUtils;
    private final DocumentUtilsService documentUtilsService;

    @Autowired
    public LinkFactory(HippoUtilsService utils, DMSDataService dmsData, LinkService linkService, ResourceBundleService bundle, LocationLoader locationLoader, ImageFactory imageFactory, CommonUtilsService commonUtils, DocumentUtilsService documentUtilsService) {
        this.utils = utils;
        this.dmsData = dmsData;
        this.linkService = linkService;
        this.bundle = bundle;
        this.locationLoader = locationLoader;
        this.imageFactory = imageFactory;
        this.commonUtils = commonUtils;
        this.documentUtilsService = documentUtilsService;
    }


    /**
     * Create a localized FlatImage from an Image Object
     *
     * @param img    Image Object
     * @param locale User language to localize Image texts such as the caption
     * @return flat image to be consumed by FED team
     *
     * @deprecated Is This method a duplication of imageFactory.populateLocation()
     */
    @Deprecated
    private FlatImage createFlatImage(Image img, Locale locale) {
        FlatImage flatImage = imageFactory.createImage(img, null, locale);
        LocationObject locationObject = locationLoader.getLocation(img.getLocation(), locale);
        if (locationObject != null) {
            flatImage.setCoordinates(new Coordinates(locationObject.getLatitude(), locationObject.getLongitude()));
        }

        return flatImage;
    }


    public EnhancedLink createEnhancedLink(Linkable linkable, Locale locale, Module<?> module, boolean addCategory) {
        EnhancedLink link = new EnhancedLink();
        link.setTeaser(linkable.getTeaser());
        link.setLabel(linkable.getTitle());

        if (linkable.getImage() != null) {
            link.setImage(imageFactory.createImage(linkable.getImage(), module, locale));
        }

        if (linkable instanceof Page) {
            enhancedPageLink(link, (Page) linkable);
        } else if (linkable instanceof SharedLink) {
            enhancedSharedLink(link, (SharedLink) linkable, locale, addCategory);
        } else {
            logger.warn("The type {} was not expected and will be skipped", linkable.getClass().getSimpleName());
            return null;
        }

        if (addCategory && link.getLink()!= null && link.getCategory()==null){
            link.setCategory(linkService.getLinkCategory (link.getLink(),locale));
        }
        if (link.getImage() == null) {
            CommonUtilsService.contentIssue("The link to {} does not have an image but it is expecting one", linkable);
        }

        return link;
    }

    /**
     * Query the DMSDataService and extract the information about the product as a {@code JsonNode}
     *
     * @param link SharedLink where the DMS product (ID) is defined
     * @param locale User language to consume DMS texts such a category, location, facilities...
     * @return JSON with DMS product information to create the card or null if the product does not exist
     */
    private JsonNode getNodeFromSharedLink(SharedLink link, Locale locale) {
        if (link.getLinkType() instanceof DMSLink) {
            return dmsData.productCard(((DMSLink) link.getLinkType()).getProduct(), locale);
        }
        return null;
    }

    private void enhancedPageLink(EnhancedLink link, Page linkable){
        link.setLink(utils.createUrl(linkable));
        link.setType(LinkType.INTERNAL);
        if (linkable instanceof Itinerary) {
            Itinerary itinerary = (Itinerary) linkable;
            link.setItineraryDays(documentUtilsService.getSiblingDocuments(linkable,Day.class, "visitscotland:Day").size());
            if (itinerary.getTransports().length > 0){
                link.setItineraryTransport(itinerary.getTransports()[0]);
            }
        }
    }

    private void enhancedSharedLink(EnhancedLink link, SharedLink linkable, Locale locale, boolean addCategory){
        JsonNode product = getNodeFromSharedLink(linkable, locale);
        link.setLink(linkService.getPlainLink(linkable, product));

        if (link.getImage() == null && product != null && product.has(DMSConstants.DMSProduct.IMAGE)) {
            //TODO Propagate the error messages
            link.setImage(imageFactory.createImage(product, null));
        }
        if (linkable.getLinkType() instanceof ExternalDocument){
            ExternalDocument externalDocument = (ExternalDocument) linkable.getLinkType();
            String size = commonUtils.getExternalDocumentSize(externalDocument.getLink(), locale);
            String downloadLabel = bundle.getResourceBundle("essentials.global", "label.download", locale, true);
            if (size == null) {
                //TODO Create preview warning.
                //TODO Content Issue
                logger.warn("The external document {} might be broken.", link.getLink());
                link.setLabel(linkable.getTitle() + " (" + downloadLabel + ")");
            } else {
                link.setLabel(linkable.getTitle() + " (" + downloadLabel + " " + size + ")");
            }

            link.setType(LinkType.DOWNLOAD);
            if (addCategory) {
                link.setCategory(externalDocument.getCategory());
            }
        }

        if (link.getType() == null) {
            link.setType(linkService.getType(link.getLink()));
        }
    }
}
