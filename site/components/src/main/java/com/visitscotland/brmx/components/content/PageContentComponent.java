package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.HippoUtils;
import com.visitscotland.brmx.utils.ProductSearchBuilder;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PageContentComponent<TYPE extends Page> extends EssentialsContentComponent {

    private static final Logger logger = LoggerFactory.getLogger(PageContentComponent.class);

    public final String DOCUMENT = "document";
    public final String EDIT_PATH = "path";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addDocumentPath(request);
        addProductSearchBuilder(request);
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
     * Returns the default CTA label when the manual CTA  is not defined.     *
     *
     * @param manualCta Manual CTA defined in the CMS
     * @param locale Locale
     *
     * @return the manual CTA if provided otherwise the default CTA
     */
    public String getCtaLabel(String manualCta, Locale locale) {
        if (!CommonUtils.isEmpty(manualCta)) {
            return manualCta;
        } else {
            return HippoUtils.getResourceBundle("button.find-out-more", "essentials.global", locale);
        }
    }


    protected void generateIndexPage(HstRequest request){
        final String HERO_IMAGE = "heroImage";
        final String LISTICLE_ALERTS = "alerts";
        List<String> listicleAlerts = desiredFieldsAlert(getDocument(request));

        FlatImage heroImage = CommonUtils.getTranslatedImage(getDocument(request).getHeroImage(), request.getLocale());
        CommonUtils.checkImageErrors(heroImage,request.getLocale(),logger,listicleAlerts);
        request.setAttribute(HERO_IMAGE, heroImage);

        if (listicleAlerts.size()>0){
            request.setAttribute(LISTICLE_ALERTS, listicleAlerts);
        }
    }

    protected List<String> desiredFieldsAlert (Page item){
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
}
