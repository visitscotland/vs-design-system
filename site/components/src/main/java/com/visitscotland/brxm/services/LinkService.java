package com.visitscotland.brxm.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.beans.*;
import com.visitscotland.brxm.beans.mapping.FlatLink;
import com.visitscotland.brxm.beans.mapping.LinkType;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.ProductSearchBuilder;
import com.visitscotland.brxm.utils.CommonUtils;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.Properties;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

public class LinkService {


    private static final Logger logger = LoggerFactory.getLogger(LinkService.class);

    final static String URL = "url";

    private final DMSDataService dmsData;
    private final ResourceBundleService resourceBundle;
    private final HippoUtilsService utils;

    public LinkService() {
        this(new DMSDataService(), new ResourceBundleService(), new HippoUtilsService());
    }

    public LinkService(DMSDataService dmsData, ResourceBundleService resourceBundle, HippoUtilsService utils) {
        this.dmsData = dmsData;
        this.resourceBundle = resourceBundle;
        this.utils = utils;
    }

    /**
     * TODO comment this method
     *
     * @param locale locale
     * @param item   Compound Item
     * @return
     */
    public FlatLink createLink(Locale locale, HippoCompound item) {
        final String URL = "url";

        if (item instanceof DMSLink) {
            DMSLink dmsLink = (DMSLink) item;
            try {
                JsonNode product = dmsData.productCard(dmsLink.getProduct(), locale);
                if (product == null) {
                    logger.warn(CommonUtils.contentIssue("There is no product with the id '%s', (%s) ",
                            dmsLink.getProduct(), item.getPath()));
                } else {
                    //TODO build the link for the DMS product properly
                    return new FlatLink(resourceBundle.getCtaLabel(dmsLink.getLabel(), locale), Properties.VS_DMS_SERVICE + product.get(URL).asText(), LinkType.INTERNAL);
                }
            } catch (IOException e) {
                logger.error(String.format("Error while querying the DMS for '%s', (%s)",
                        dmsLink.getProduct(), item.getPath()));
            }
        } else if (item instanceof ProductSearchLink) {
            ProductSearchLink productSearchLink = (ProductSearchLink) item;
            ProductSearchBuilder psb = new ProductSearchBuilder().fromHippoBean(productSearchLink.getSearch()).locale(locale);

            return new FlatLink(resourceBundle.getCtaLabel(productSearchLink.getLabel(), locale), psb.build(), LinkType.INTERNAL);

        } else if (item instanceof ExternalLink) {
            ExternalLink externalLink = (ExternalLink) item;
            LinkType linkType = getType(externalLink.getLink());
            return new FlatLink(resourceBundle.getCtaLabel(externalLink.getLabel(), locale), externalLink.getLink(), linkType);

        } else if (item instanceof CMSLink) {
            CMSLink cmsLink = (CMSLink) item;
            return new FlatLink(resourceBundle.getCtaLabel(cmsLink.getLabel(), locale), utils.createUrl(cmsLink.getLink()), LinkType.INTERNAL);
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
                CommonUtils.contentIssue("The product id '%s' does not exist but is linked ",
                        ((DMSLink) link.getLinkType()).getProduct(), link.getPath());
            } else {
                return Properties.VS_DMS_SERVICE + product.get(URL).asText();
            }
        } else if (link.getLinkType() instanceof ExternalLink) {
            return ((ExternalLink) link.getLinkType()).getLink();
        } else if (link.getLinkType() instanceof ProductSearchLink) {
            return new ProductSearchBuilder().fromHippoBean(((ProductSearchLink) link.getLinkType()).getSearch()).build();
        } else if (link.getLinkType() instanceof ExternalDocument) {
            return ((ExternalDocument) link.getLinkType()).getLink();
        }
        else {
            logger.warn(String.format("This class %s is not recognized as a link type and cannot be converted", link.getLinkType() == null ? "null" : link.getClass().getSimpleName()));
        }
        return null;
    }

    /**
     * Analyzes the URL and identifies what type of link it is.
     *
     * @param url URL to analyse
     * @return
     */
    public LinkType getType(String url) {
        //TODO the following if block requires some refinement
        if (Contract.isEmpty(url)) {
            return null;
        } else if (url.startsWith("/") || url.contains("localhost") || url.contains("visitscotland.com")
                || url.startsWith(Properties.VS_DMS_SERVICE)) {
            return LinkType.INTERNAL;
        }

        return LinkType.EXTERNAL;
    }

    /**
     * Method to assign the right category based on the url/cms structure
     *
     * @param path String path of the document
     * @param locale Locale
     * @return category
     */
    public String getLinkCategory(String path, Locale locale) {
        try {
            if (getType(path)==LinkType.EXTERNAL) {
                java.net.URL url = new URL(path);
                String host = url.getHost();
                String category = host.toUpperCase().startsWith("WWW.") ? host.substring(4) : host;
                return category.toUpperCase();
            }else {
                if (path.contains("ebooks.visitscotland.com")) {
                    return "eBooks";
                } else if (path.contains("blog")) {
                    return resourceBundle.getResourceBundle("navigation.main", "blog", locale, true);
                } else if (path.contains("see-do") || path.contains("events") || path.contains("tours")) {
                    return resourceBundle.getResourceBundle("navigation.main", "see-do", locale, true);
                } else if (path.contains("accommodation")) {
                    return resourceBundle.getResourceBundle("navigation.main", "accommodation", locale, true);
                } else if (path.contains("destination") || path.contains("towns-villages")) {
                    return resourceBundle.getResourceBundle("navigation.main", "destinations-map", locale, true);
                } else if (path.contains("travel") || path.contains("holidays") || path.contains("transport")) {
                    return resourceBundle.getResourceBundle("navigation.main", "travel-planning", locale, true);
                } else if (path.contains("brochures")) {
                    return resourceBundle.getResourceBundle("navigation.main", "inspiration", locale, true);
                } else if (path.contains("about") || path.contains("contact") || path.contains("policies") || path.contains("services")) {
                    return resourceBundle.getResourceBundle("navigation.footer", "footer.visitor-information", locale, true);
                }
            }
            return resourceBundle.getResourceBundle("navigation.main", "see-do", locale ,true);

        } catch (MalformedURLException e) {
            logger.error("The URL "+path+" is not valid", e);
            return null;
        }

    }
}
