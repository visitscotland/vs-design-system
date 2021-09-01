package com.visitscotland.brxm.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.DMSConstants;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.ProductSearchBuilder;
import com.visitscotland.brxm.factory.ImageFactory;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.LinkType;
import com.visitscotland.brxm.model.Module;
import com.visitscotland.brxm.model.megalinks.EnhancedLink;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.Properties;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.content.beans.standard.HippoBean;
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
    private static final Logger contentLogger = LoggerFactory.getLogger("content");

    private final DMSDataService dmsData;
    private final ResourceBundleService bundle;
    private final HippoUtilsService utils;
    private final Properties properties;
    private final ImageFactory imageFactory;
    private final CommonUtilsService commonUtils;
    private final DocumentUtilsService documentUtilsService;

    @Autowired
    public LinkService(DMSDataService dmsData, ResourceBundleService bundle, HippoUtilsService utils, Properties properties, ImageFactory imageFactory, CommonUtilsService commonUtils, DocumentUtilsService documentUtilsService) {
        this.dmsData = dmsData;
        this.bundle = bundle;
        this.utils = utils;
        this.properties = properties;

        this.imageFactory = imageFactory;
        this.commonUtils = commonUtils;
        this.documentUtilsService = documentUtilsService;
    }

    /**
     * Fetches a new Product Search Object
     */
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
    public FlatLink createLink(Locale locale, HippoBean item) {

        if (item instanceof DMSLink) {
            DMSLink dmsLink = (DMSLink) item;
            JsonNode product = dmsData.productCard(dmsLink.getProduct(), locale);

            if (dmsLink.getProduct() == null) {
                contentLogger.warn("There is no product with the id '{}', ({}) ", dmsLink.getProduct(), item.getPath());
            } else if (product != null) {
                return createDmsLink(locale, dmsLink, product);
            }
        } else if (item instanceof ProductSearchLink) {
            ProductSearchLink productSearchLink = (ProductSearchLink) item;
            ProductSearchBuilder psb = productSearch().fromHippoBean(productSearchLink.getSearch()).locale(locale);

            return new FlatLink(bundle.getCtaLabel(productSearchLink.getLabel(), locale), psb.build(), LinkType.INTERNAL);

        } else if (item instanceof ExternalLink) {
            ExternalLink externalLink = (ExternalLink) item;
            LinkType linkType = getType(externalLink.getLink());
            return new FlatLink(bundle.getCtaLabel(externalLink.getLabel(), locale), externalLink.getLink(), linkType);

        } else if (item instanceof CMSLink && ((CMSLink) item).getLink() instanceof Page) {
            CMSLink cmsLink = (CMSLink) item;
            return new FlatLink(bundle.getCtaLabel(cmsLink.getLabel(), locale), utils.createUrl((Page) cmsLink.getLink()), LinkType.INTERNAL);
        } else {
            contentLogger.warn("The document {} could not be turned into a link", item.getPath());
        }

        return null;
    }

    public FlatLink createDmsLink(Locale locale, DMSLink dmsLink, JsonNode dmsProductJson) {
        return new FlatLink(bundle.getCtaLabel(dmsLink.getLabel(), locale), properties.getDmsHost() + dmsProductJson.get(DMSConstants.DMSProduct.URL).get(DMSConstants.DMSProduct.URL_LINK).asText(), LinkType.INTERNAL);
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
                contentLogger.warn("The product id '{}' does not exist but is linked - {}", ((DMSLink) link.getLinkType()).getProduct(), link.getPath());
            } else {
                return properties.getDmsHost() + product.get(DMSConstants.DMSProduct.URL).get(DMSConstants.DMSProduct.URL_LINK).asText();
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
     * @return linkType
     *
     */
    public LinkType getType(String url) {
        if (Contract.isEmpty(url)) {
            return null;
        } else if (url.toLowerCase().endsWith(".pdf")) {
            return LinkType.DOWNLOAD;
        } else if (url.startsWith("/") || url.startsWith("#")){
            return LinkType.INTERNAL;
        } else if (isInternalDomain(url)){
            return LinkType.INTERNAL;
        }

        return LinkType.EXTERNAL;
    }

    /**
     * Check if the host of the URL is marked as an internal URL
     *
     * Note: Malformed URLs will be treated as external URLs
     *
     * @param url
     * @return
     */
    private boolean isInternalDomain(String url){
        try {
            String host = new URL(url).getHost();
            return ((!Contract.isEmpty(properties.getInternalSites()) && properties.getInternalSites().contains(host)) ||
                    (!Contract.isEmpty(properties.getDmsHost()) && host.equals(properties.getDmsHost())));
        } catch (MalformedURLException e) {
            logger.info("Malformed URL detected {}",url);
        }
        return false;
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
                return bundle.getResourceBundle("navigation.main", "Travel-Blog", locale);
            } else if (path.contains("see-do") || path.contains("events") || path.contains("tours")) {
                return bundle.getResourceBundle("navigation.main", "see-do", locale);
            } else if (path.contains("accommodation")) {
                return bundle.getResourceBundle("navigation.main", "accommodation", locale);
            } else if (path.contains("destination") || path.contains("towns-villages")) {
                return bundle.getResourceBundle("navigation.main", "destinations-map", locale);
            } else if (path.contains("travel") || path.contains("holidays") || path.contains("transport")) {
                return bundle.getResourceBundle("navigation.main", "travel-planning", locale);
            } else if (path.contains("brochures")) {
                return bundle.getResourceBundle("navigation.main", "inspiration", locale);
            } else if (path.contains("about") || path.contains("contact") || path.contains("policies") || path.contains("services")) {
                return bundle.getResourceBundle("navigation.footer", "footer.visitor-information", locale);
            }

            return bundle.getResourceBundle("navigation.main", "see-do", locale);

        } catch (MalformedURLException e) {
            logger.error("The URL " + path + " is not valid", e);
            return null;
        }
    }

    /**
     * Creates an enhanced link form a {@code Linkable} object
     *
     * @param linkable Page or Shared link that contains the information about the link
     * @param module Module to
     * @param locale
     * @param addCategory
     * @return
     */
    public EnhancedLink createEnhancedLink(Linkable linkable, Module<?> module, Locale locale, boolean addCategory) {
        EnhancedLink link = new EnhancedLink();
        link.setTeaser(linkable.getTeaser());
        link.setLabel(linkable.getTitle());

        if (linkable.getImage() != null) {
            link.setImage(imageFactory.createImage(linkable.getImage(), module, locale));
        }

        if (linkable instanceof Page) {
            enhancedLinkFromPage(link, (Page) linkable);
        } else if (linkable instanceof SharedLink) {
            enhancedLinkFromSharedLink(link, (SharedLink) linkable, module, locale, addCategory);
        } else {
            logger.warn("The type {} was not expected and will be skipped", linkable.getClass().getSimpleName());
            return null;
        }

        if (addCategory && link.getLink()!= null && link.getCategory()==null){
            link.setCategory(getLinkCategory (link.getLink(),locale));
        }
        if (link.getImage() == null) {
            if (module != null) {
                module.addErrorMessage("The link to '" + link.getLink() + "' does not contain an image.");
            } else {
                logger.warn("The error message cannot be displayed in preview");
            }
            contentLogger.warn("The link to {} does not have an image but it is expecting one", ((BaseDocument) linkable).getPath());
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

    /**
     * Populated the information about an enhanced Link from a Page Document.
     *
     * @param link EnhacencedLink with minimum data
     * @param linkable SharedLink document that contains extra information
     */
    private void enhancedLinkFromPage(EnhancedLink link, Page linkable){
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

    /**
     * Populated the information about an enhanced Link from a SharedLink Document.
     *
     * @param link EnhacencedLink with minimum data
     * @param linkable SharedLink document that contains extra information
     * @param module Module to feed with any possible issue found while creating the page.
     * @param locale Language for the label
     * @param addCategory wether or not the category field is populated.
     */
    private void enhancedLinkFromSharedLink(EnhancedLink link, SharedLink linkable, Module<?> module, Locale locale, boolean addCategory){
        JsonNode product = getNodeFromSharedLink(linkable, locale);
        link.setLink(getPlainLink(linkable, product));

        if (link.getImage() == null && product != null && product.has(DMSConstants.DMSProduct.IMAGE)) {
            link.setImage(imageFactory.createImage(product, module));
        }
        if (linkable.getLinkType() instanceof ExternalDocument){
            ExternalDocument externalDocument = (ExternalDocument) linkable.getLinkType();
            link.setLabel(linkable.getTitle() + getDownloadText(link.getLink(),locale, module));
            link.setType(LinkType.DOWNLOAD);

            if (addCategory) {
                link.setCategory(externalDocument.getCategory());
            }
        }

        if (link.getType() == null) {
            link.setType(getType(link.getLink()));
        }
    }

    public String getDownloadText(String link) {
        return getDownloadText(link, utils.getRequestLocale(), null);
    }


    public String getDownloadText(String link, Locale locale, Module<?> module){
        String downloadLabel = bundle.getResourceBundle("essentials.global", "label.download", locale);
        //TODO The following operation is expensive. We should cache the value
        String size = commonUtils.getExternalDocumentSize(link, locale);
        if (size == null) {
            if (module != null) {
                module.addErrorMessage("The Link to the External document might be broken");
            }
            contentLogger.warn("The external document {} might be broken.", link);
            return " (" + downloadLabel + ")";
        } else {
            return " (" + downloadLabel + " " + size + ")";
        }
    }


}
