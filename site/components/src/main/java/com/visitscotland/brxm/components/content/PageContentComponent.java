package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.dms.ProductSearchBuilder;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.factory.ImageFactory;
import com.visitscotland.brxm.factory.LinkModulesFactory;
import com.visitscotland.brxm.hippobeans.BaseDocument;
import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.model.FlatImage;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.Module;
import com.visitscotland.brxm.model.megalinks.HorizontalListLinksModule;
import com.visitscotland.brxm.services.CommonUtilsService;
import com.visitscotland.brxm.services.LinkService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class PageContentComponent<TYPE extends Page> extends EssentialsContentComponent {

    private static final Logger logger = LoggerFactory.getLogger(PageContentComponent.class);

    public static final String DOCUMENT = "document";
    public static final String EDIT_PATH = "path";
    public static final String FACILITIES = "keyFacilities";
    public static final String HERO_COORDINATES = "heroCoordinates";

    private LinkService linksService;
    private LocationLoader locationLoader;
    private LinkModulesFactory linksFactory;
    private ImageFactory imageFactory;

    public PageContentComponent(){
        linksService = VsComponentManager.get(LinkService.class);
        locationLoader = VsComponentManager.get(LocationLoader.class);
        linksFactory = VsComponentManager.get(LinkModulesFactory.class);
        imageFactory = VsComponentManager.get(ImageFactory.class);
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addDocumentPath(request);
        addProductSearchBuilder(request);

        initPage(request);
    }

    /**
     * Set the coordinates for Hero images
     *
     * @param request HstRequest
     */
    protected void addHeroCoordinates(HstRequest request) {
        LocationObject location = locationLoader.getLocation(getDocument(request).getHeroImage().getLocation(), request.getLocale());

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
     * Set the OTYML module if present
     *
     * @param page Page
     * @param locale Locale
     */
    protected HorizontalListLinksModule addOTYML(Page page, Locale locale) {
        HorizontalListLinksModule otyml = null;
        if(page.getOtherThings()!=null) {
            otyml = linksFactory.horizontalListLayout(page.getOtherThings(), locale);
        }
        return otyml;
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
    private FlatLink createLink(HstRequest request, HippoCompound item) {
        return linksService.createLink(request.getLocale(), item);
    }

    /**
     * TODO Refactor and remove this method
     */
    private void initPage (HstRequest request){
        final String HERO_IMAGE = "heroImage";
        final String ALERTS = "alerts";
        List<String> alerts = validateDesiredFields(getDocument(request));
        Module introModule = new Module<TYPE>();

        FlatImage heroImage = imageFactory.createImage(getDocument(request).getHeroImage(), introModule, request.getLocale());

        request.setAttribute(HERO_IMAGE, heroImage);

        if (!Contract.isEmpty(introModule.getErrorMessages())){
            request.setAttribute(ALERTS, introModule.getErrorMessages());
        }
    }

    /**
     * TODO Refactor and remove this method
     */
    private List<String> validateDesiredFields (Page item){
        List<String> response =  new ArrayList<>();
        if (item.getTeaser() == null || item.getTeaser().isEmpty()) {
            response.add("Teaser field should be provided");
            logger.warn(CommonUtilsService.contentIssue("The teaser has not been provided for = %s",item.getPath()));
        }
        if (item.getSeoTitle() == null || item.getSeoTitle().isEmpty()) {
            response.add("SEO title field is required");
            logger.warn(CommonUtilsService.contentIssue("The SEO title has not been provided for = %s",item.getPath()));
        }
        if (item.getSeoDescription() == null || item.getSeoDescription().isEmpty()) {
            response.add("SEO description field is required");
            logger.warn(CommonUtilsService.contentIssue("The SEO description has not been provided for = %s",item.getPath()));
        }

        return response;
    }
}
