package com.visitscotland.brmx.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.brmx.utils.Properties;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;

public class LinkService {

    private static final Logger logger = LoggerFactory.getLogger(LinkService.class);

    final static String URL = "url";

    private final DMSDataService dmsData;
    private final ProductSearchBuilder psBuilder;
    private final ResourceBundleService resourceBundle;
    private final HippoUtilsService utils;

    public LinkService() {
        this(new DMSDataService(), new ProductSearchBuilder(), new ResourceBundleService(), new HippoUtilsService());
    }

    public LinkService(DMSDataService dmsData, ProductSearchBuilder psBuilder, ResourceBundleService resourceBundle, HippoUtilsService utils) {
        this.dmsData = dmsData;
        this.psBuilder = psBuilder;
        this.resourceBundle = resourceBundle;
        this.utils = utils;
    }

    // TODO is the last parameter required?
//    public FlatLink createLink(Locale locale, HippoCompound item){
//        return createLink(locale, item, item);
//    }

    //

    /**
     * TODO comment this method
     *
     * @param locale locale
     * @param item Compound Item
     *
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
                    return new FlatLink(resourceBundle.getCtaLabel(dmsLink.getLabel(), locale), Properties.VS_DMS_SERVICE + product.get(URL).asText());
                }
            } catch (IOException e) {
                logger.error(String.format("Error while querying the DMS for '%s', (%s)",
                        dmsLink.getProduct(), item.getPath()));
            }
        } else if (item instanceof ProductSearchLink) {
            ProductSearchLink productSearchLink = (ProductSearchLink) item;
            ProductSearchBuilder psb = new ProductSearchBuilder().fromHippoBean(productSearchLink.getSearch()).locale(locale);

            return new FlatLink(resourceBundle.getCtaLabel(productSearchLink.getLabel(), locale), psb.build());

        } else if (item instanceof ExternalLink) {
            ExternalLink externalLink = (ExternalLink) item;
            return new FlatLink(resourceBundle.getCtaLabel(externalLink.getLabel(), locale), externalLink.getLink());

        } else if (item instanceof CMSLink) {
            CMSLink cmsLink = (CMSLink) item;
            return new FlatLink(resourceBundle.getCtaLabel(cmsLink.getLabel(), locale), utils.createUrl(cmsLink.getLink()));
        }

        return null;
    }

    /**
     * Extracts the information about the link form a SharedLink and returns it in a URL.
     *
     * @param link SharedLink Object;
     * @param product JsonNode with the data of the product. It is only used when the type of SharedLink is DMSLink.
     *
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
        } else if (link.getLinkType() instanceof ProductsSearch) {
            return psBuilder.fromHippoBean((ProductsSearch) link.getLinkType()).build();
        } else {
            logger.warn(String.format("This class %s is not recognized as a link type and cannot be converted", link.getLinkType() == null ? "null" : link.getClass().getSimpleName()));
        }
        return null;
    }



}
