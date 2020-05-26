package com.visitscotland.brmx.components.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.dms.LocationObject;
import com.visitscotland.brmx.beans.mapping.Coordinates;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.utils.*;
import com.visitscotland.dataobjects.DataType;
import com.visitscotland.utils.Contract;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PageContentComponent<TYPE extends Page> extends EssentialsContentComponent {

    private static final Logger logger = LoggerFactory.getLogger(PageContentComponent.class);

    public static final String DOCUMENT = "document";
    public static final String EDIT_PATH = "path";
    protected final String FACILITIES = "keyFacilities";
    public final String HERO_COORDINATES = "heroCoordinates";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addDocumentPath(request);
        addProductSearchBuilder(request);

        initPage(request);
    }

    /**
     * TODO Comment
     * @param request
     */
    protected void addHeroCoordinates(HstRequest request) {
        LocationObject location = LocationLoader.getLocation(getDocument(request).getHeroImage().getLocation(), request.getLocale());

        if (location != null){
            Coordinates coordinates = new Coordinates();
            coordinates.setLatitude(location.getLatitude());
            coordinates.setLongitude(location.getLongitude());
            request.setAttribute(HERO_COORDINATES, coordinates);
        }
    }

    public void addProductSearchBuilder(HstRequest request){
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        try {
            TemplateHashModel psb = (TemplateHashModel) staticModels.get(ProductSearchBuilder.class.getCanonicalName());
            request.setAttribute("ProductSearchBuilder", psb);
        } catch (TemplateModelException e) {
            logger.error("Product Search Builder is not available for the Page", e);
        }
    }

    /**
     * Return the document from the request
     *
     * @param request HstRequest
     * @return the master document of
     */
    protected TYPE getDocument(HstRequest request) {
        if (request.getAttribute(DOCUMENT) instanceof Page) {
            return (TYPE) request.getAttribute(DOCUMENT);
        } else {
            logger.error("The master document is not an instance of Page.", new ClassCastException());
            return null;
        }

    }

    /**
     * Add the document path that will be used as path by default when creating documents. So for Example, the prompted
     * path for the days of an itinenrary would be where the itinerary (masted document) lives
     *
     * @param request Request where the master document is defined and where the path will be added
     */
    private void addDocumentPath(HstRequest request) {
        final String ROOT_SITE = "/site/";

        if (request.getAttribute(DOCUMENT) instanceof BaseDocument) {
            BaseDocument document = getDocument(request);
            //Extract the document path for the CMS Editor
            String path = document.getPath().substring(
                    document.getPath().indexOf(ROOT_SITE) + ROOT_SITE.length(),
                    document.getPath().indexOf("/content/content"));

            request.setAttribute(EDIT_PATH, path);
        }
    }

    /**
     * @param request HstRequest
     * @param item Compound for DMSLink, PSRLink , External Link or CMS link
     * @return FlatLink
     */
    protected FlatLink createLink(HstRequest request, HippoCompound item) {
        return createLink(request, item, getDocument(request));
    }


    protected static FlatLink createLink(HstRequest request, HippoCompound item, Page document) {
        final String URL = "url";

        if (item instanceof DMSLink) {
            DMSLink dmsLink = (DMSLink) item;
            try {
                JsonNode product = CommonUtils.getProduct(dmsLink.getProduct(), request.getLocale());
                if (product == null) {
                    logger.warn(CommonUtils.contentIssue("There is no product with the id '%s', (%s) ",
                            dmsLink.getProduct(),document.getPath()));
                } else {
                    //TODO build the link for the DMS product properly
                    return new FlatLink(getCtaLabel(dmsLink.getLabel(), request.getLocale()), Properties.VS_DMS_SERVICE + product.get(URL).asText());
                }
            } catch (IOException e) {
                logger.error(String.format("Error while querying the DMS for '%s', (%s)",
                        dmsLink.getProduct(),document.getPath()));
            }
        } else if (item instanceof ProductSearchLink) {
            ProductSearchLink productSearchLink = (ProductSearchLink) item;
            ProductSearchBuilder psb = new ProductSearchBuilder()
                    .productType(productSearchLink.getSearch()).locale(request.getLocale());

            return new FlatLink(getCtaLabel(productSearchLink.getLabel(), request.getLocale()), psb.build());

        } else if (item instanceof ExternalLink) {
            ExternalLink externalLink = (ExternalLink) item;
            return new FlatLink(getCtaLabel(externalLink.getLabel(), request.getLocale()), externalLink.getLabel());

        } else if (item instanceof CMSLink) {
            //TODO: check this. It is probably wrong!
            CMSLink cmsLink = (CMSLink) item;
            return new FlatLink(getCtaLabel(cmsLink.getLabel(), request.getLocale()), cmsLink.getLabel());
        }

        return null;
    }

    /**
     * Returns the default CTA label when the manual CTA  is not defined.     *
     *
     * @param manualCta Manual CTA defined in the CMS
     * @param locale Locale
     *
     * @return the manual CTA if provided otherwise the default CTA
     */
    protected static String getCtaLabel(String manualCta, Locale locale) {
        if (!Contract.isEmpty(manualCta)) {
            return manualCta;
        } else {
            return HippoUtils.getResourceBundle("button.find-out-more", "essentials.global", locale);
        }
    }


    protected void initPage (HstRequest request){
        final String HERO_IMAGE = "heroImage";
        final String ALERTS = "alerts";
        List<String> alerts = validateDesiredFields(getDocument(request));

        FlatImage heroImage = new FlatImage(getDocument(request).getHeroImage(), request.getLocale());
        checkImageErrors(heroImage,request.getLocale(),alerts);
        request.setAttribute(HERO_IMAGE, heroImage);

        if (alerts.size()>0){
            request.setAttribute(ALERTS, alerts);
        }
    }

    protected List<String> validateDesiredFields (Page item){
        List<String> response =  new ArrayList<>();
        if (item.getTeaser() == null || item.getTeaser().isEmpty()) {
            response.add("Teaser field should be provided");
            logger.warn(CommonUtils.contentIssue("The teaser has not been provided for = %s",item.getPath()));
        }
        if (item.getSeoTitle() == null || item.getSeoTitle().isEmpty()) {
            response.add("SEO title field is required");
            logger.warn(CommonUtils.contentIssue("The SEO title has not been provided for = %s",item.getPath()));
        }
        if (item.getSeoDescription() == null || item.getSeoDescription().isEmpty()) {
            response.add("SEO description field is required");
            logger.warn(CommonUtils.contentIssue("The SEO description has not been provided for = %s",item.getPath()));
        }

        return response;
    }

    protected static void checkImageErrors(FlatImage image, Locale locale, List<String> errors){
        if (image.getAltText() == null || image.getAltText().isEmpty()){
            image.setAltText(image.getCmsImage().getAltText());
            errors.add("Alt text field not provided for " + locale.getDisplayLanguage());
            logger.warn(CommonUtils.contentIssue("Alt text field not provided for %s for the image : %s - %s",
                    locale.getDisplayLanguage() , image.getCmsImage().getName(), image.getCmsImage().getPath()));
        }
        if (image.getDescription() == null || image.getDescription().isEmpty()){
            image.setDescription(image.getCmsImage().getDescription());
            errors.add("Caption field not provided for " + locale.getDisplayLanguage());
            logger.warn(CommonUtils.contentIssue("Caption field not provided for %s for the image : %s - %s",
                    locale.getDisplayLanguage() , image.getCmsImage().getName(), image.getCmsImage().getPath()));
        }
    }

    protected List<DataType> getFacilities (JsonNode product){
        List<DataType> facilities = null;
        if (product.has(FACILITIES)){
            facilities = new ArrayList<>();
            JsonNode keyFacilitiesList = product.get(FACILITIES);

            if (keyFacilitiesList.isArray()) {
                for (JsonNode facility : keyFacilitiesList) {
                    DataType dataType = new DataType(facility.get("id").asText(),facility.get("name").asText());
                    facilities.add(dataType);
                }
            }
        }
        return facilities;
    }

}
