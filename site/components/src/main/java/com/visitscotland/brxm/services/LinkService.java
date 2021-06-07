package com.visitscotland.brxm.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.dms.DMSConstants;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.factory.ImageFactory;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import com.visitscotland.brxm.model.*;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.ProductSearchBuilder;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.model.megalinks.EnhancedLink;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.Properties;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

@Component
public class LinkService {

    private static final Logger logger = LoggerFactory.getLogger(LinkService.class);

    public static final String URL = "url";

    private final DMSDataService dmsData;
    private final ResourceBundleService bundle;
    private final HippoUtilsService utils;
    private final Properties properties;
    private final LocationLoader locationLoader;
    private final ImageFactory imageFactory;
    private final CommonUtilsService commonUtils;
    private final DocumentUtilsService documentUtilsService;

    /**
     * TODO: Remove constructor
     *
     * @deprecated The only use for this constructor is to keep compatibility with some legacy tests
     */
    @Deprecated
    public LinkService(DMSDataService dmsData, ResourceBundleService resourceBundle, HippoUtilsService utils, Properties properties) {
        this.dmsData = dmsData;
        this.bundle = resourceBundle;
        this.utils = utils;
        this.properties = properties;
        this.locationLoader = null;
        this.imageFactory = null;
        this.commonUtils = null;
        this.documentUtilsService = null;
    }

    @Autowired
    public LinkService(DMSDataService dmsData, ResourceBundleService bundle, HippoUtilsService utils, Properties properties, LocationLoader locationLoader, ImageFactory imageFactory, CommonUtilsService commonUtils, DocumentUtilsService documentUtilsService) {
        this.dmsData = dmsData;
        this.bundle = bundle;
        this.utils = utils;
        this.properties = properties;
        this.locationLoader = locationLoader;
        this.imageFactory = imageFactory;
        this.commonUtils = commonUtils;
        this.documentUtilsService = documentUtilsService;
    }

    private ProductSearchBuilder productSearch(){
        return VsComponentManager.get(ProductSearchBuilder.class);
    }

    /**
     * Creates a link from a Compound item of one of the following types:
     * DMSLink, ProductSearchLink, ExternalLink, CMSLink
     *
     * @param locale locale Language for the labels
     * @param item   Compound Item
     */
    public FlatLink createLink(Locale locale, HippoCompound item) {

        if (item instanceof DMSLink) {
            DMSLink dmsLink = (DMSLink) item;
            JsonNode product = dmsData.productCard(dmsLink.getProduct(), locale);

            if (dmsLink.getProduct() == null) {
                String message = CommonUtilsService.contentIssue("There is no product with the id '%s', (%s) ",
                        dmsLink.getProduct(), item.getPath());
                logger.warn(message);
            } else if (product != null) {
                //TODO build the link for the DMS product properly
                return new FlatLink(bundle.getCtaLabel(dmsLink.getLabel(), locale), properties.getDmsHost() + product.get(URL).asText(), LinkType.INTERNAL);
            }
        } else if (item instanceof ProductSearchLink) {
            ProductSearchLink productSearchLink = (ProductSearchLink) item;
            ProductSearchBuilder psb = productSearch().fromHippoBean(productSearchLink.getSearch()).locale(locale);

            return new FlatLink(bundle.getCtaLabel(productSearchLink.getLabel(), locale), psb.build(), LinkType.INTERNAL);

        } else if (item instanceof ExternalLink) {
            ExternalLink externalLink = (ExternalLink) item;
            LinkType linkType = getType(externalLink.getLink());
            return new FlatLink(bundle.getCtaLabel(externalLink.getLabel(), locale), externalLink.getLink(), linkType);

        } else if (item instanceof CMSLink) {
            CMSLink cmsLink = (CMSLink) item;
            return new FlatLink(bundle.getCtaLabel(cmsLink.getLabel(), locale), utils.createUrl(cmsLink.getLink()), LinkType.INTERNAL);
        } else {
            String message = CommonUtilsService.contentIssue("The document %s could not be turned into a link'  ", item.getPath());
            logger.warn(message);
        }

        return null;
    }

    /**
     * Extracts the information about the link form a SharedLink and returns it in a URL.
     *
     * @param link    SharedLink Object;
     * @param product JsonNode with the data of the product. It is only used when the type of SharedLink is DMSLink.
     * @return String URL from the SharedLink
     */
    public String getPlainLink(SharedLink link, JsonNode product) {

        if (link.getLinkType() instanceof DMSLink) {
            if (product == null) {//((DMSLink) link).getDmsData(locale)
                CommonUtilsService.contentIssue("The product id '%s' does not exist but is linked ",
                        ((DMSLink) link.getLinkType()).getProduct(), link.getPath());
            } else {
                return properties.getDmsHost() + product.get(URL).asText();
            }
        } else if (link.getLinkType() instanceof ExternalLink) {
            return ((ExternalLink) link.getLinkType()).getLink();
        } else if (link.getLinkType() instanceof ProductsSearch) {
            return productSearch().fromHippoBean(((ProductsSearch) link.getLinkType())).build();
        } else if (link.getLinkType() instanceof ProductSearchLink) {
            return productSearch().fromHippoBean(((ProductSearchLink) link.getLinkType()).getSearch()).build();
        } else if (link.getLinkType() instanceof ExternalDocument) {
            return ((ExternalDocument) link.getLinkType()).getLink();
        } else {
            String linkType = link.getLinkType() == null ? "null" : link.getLinkType().getClass().getSimpleName();
            logger.warn("This class {} is not recognized as a link type and cannot be converted", linkType);
        }
        return null;
    }

    /**
     * Analyzes the URL and identifies what type of link it is.
     *
     * @param url URL to analyze
     * @return
     */
    public LinkType getType(String url) {
        //TODO the following if block requires some refinement
        if (Contract.isEmpty(url)) {
            return null;
        } else if (url.startsWith("/") || url.contains("localhost") || url.contains("visitscotland.com")
                || url.startsWith(properties.getDmsHost())) {
            return LinkType.INTERNAL;
        }

        return LinkType.EXTERNAL;
    }

    /**
     * Method to assign the right category based on the url/cms structure
     *
     * @param path   String path of the document
     * @param locale Locale
     * @return category
     */
    public String getLinkCategory(String path, Locale locale) {
        try {
            if (getType(path) == LinkType.EXTERNAL) {
                java.net.URL url = new URL(path);
                String host = url.getHost();
                String category = host.toUpperCase().startsWith("WWW.") ? host.substring(4) : host;
                return category.toUpperCase();
            } else if (path.contains("ebooks.visitscotland.com")) {
                return "eBooks";
            } else if (path.contains("blog")) {
                return bundle.getResourceBundle("navigation.main", "Travel-Blog", locale, true);
            } else if (path.contains("see-do") || path.contains("events") || path.contains("tours")) {
                return bundle.getResourceBundle("navigation.main", "see-do", locale, true);
            } else if (path.contains("accommodation")) {
                return bundle.getResourceBundle("navigation.main", "accommodation", locale, true);
            } else if (path.contains("destination") || path.contains("towns-villages")) {
                return bundle.getResourceBundle("navigation.main", "destinations-map", locale, true);
            } else if (path.contains("travel") || path.contains("holidays") || path.contains("transport")) {
                return bundle.getResourceBundle("navigation.main", "travel-planning", locale, true);
            } else if (path.contains("brochures")) {
                return bundle.getResourceBundle("navigation.main", "inspiration", locale, true);
            } else if (path.contains("about") || path.contains("contact") || path.contains("policies") || path.contains("services")) {
                return bundle.getResourceBundle("navigation.footer", "footer.visitor-information", locale, true);
            }

            return bundle.getResourceBundle("navigation.main", "see-do", locale, true);

        } catch (MalformedURLException e) {
            logger.error("The URL " + path + " is not valid", e);
            return null;
        }
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
            link.setCategory(getLinkCategory (link.getLink(),locale));
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
        link.setLink(getPlainLink(linkable, product));

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
            link.setType(getType(link.getLink()));
        }
    }
}
