package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.factory.ImageFactory;
import com.visitscotland.brxm.factory.MegalinkFactory;
import com.visitscotland.brxm.factory.SignpostFactory;
import com.visitscotland.brxm.hippobeans.BaseDocument;
import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.model.FlatImage;
import com.visitscotland.brxm.model.Module;
import com.visitscotland.brxm.model.SignpostModule;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;


public class PageContentComponent<T extends Page> extends EssentialsContentComponent {

    private static final Logger logger = LoggerFactory.getLogger(PageContentComponent.class);

    public static final String DOCUMENT = "document";
    public static final String EDIT_PATH = "path";
    public static final String OTYML = "otyml";
    public static final String NEWSLETTER_SIGNPOST = "newsletterSignpost";
    public static final String PREVIEW_ALERTS = "alerts";

    private MegalinkFactory linksFactory;
    private ImageFactory imageFactory;
    private final SignpostFactory signpostFactory;

    public PageContentComponent() {
        linksFactory = VsComponentManager.get(MegalinkFactory.class);
        imageFactory = VsComponentManager.get(ImageFactory.class);
        signpostFactory = VsComponentManager.get(SignpostFactory.class);
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addHeroImage(request);

        addOTYML(request);
        addNewsletterSignup(request);
    }

    /**
     * - Alerts are only used for issues related with the hero image at the moment
     * - Hero Image is not necessary for all document types. Is it better to add the field in order to keep consistency?
     */
    private void addHeroImage(HstRequest request){
        final String HERO_IMAGE = "heroImage";
        final String ALERTS = "alerts";
        Module<T> introModule = new Module<>();

        FlatImage heroImage = imageFactory.createImage(getDocument(request).getHeroImage(), introModule, request.getLocale());
        request.setAttribute(HERO_IMAGE, heroImage);

        if (!Contract.isEmpty(introModule.getErrorMessages())) {
            request.setAttribute(ALERTS, introModule.getErrorMessages());
        }
    }

    /**
     * Set the OTYML module if present
     */
    protected void addOTYML(HstRequest request) {
        Page page = getDocument(request);
        if (page.getOtherThings() != null) {
            request.setAttribute(OTYML, linksFactory.horizontalListLayout(page.getOtherThings(), request.getLocale()));
        }
    }

    protected void addNewsletterSignup(HstRequest request) {
        Page page = getDocument(request);
        if (!Contract.defaultIfNull(page.getHideNewsletter(), false)) {
            SignpostModule signpost = signpostFactory.createNewsletterSignpostModule(request.getLocale());
            request.setAttribute(NEWSLETTER_SIGNPOST, signpost);
        }
    }

    /**
     * Return the document from the request
     *
     * @param request HstRequest
     * @return the master document of
     */
    protected T getDocument(HstRequest request) {
        if (request.getAttribute(DOCUMENT) instanceof Page) {
            return (T) request.getAttribute(DOCUMENT);
        } else {
            logger.error("The master document is not an instance of Page.", new ClassCastException());
            return null;
        }
    }

    protected void setErrorMessages(HstRequest request, Collection<String> errorMessages) {
        request.setAttribute(PREVIEW_ALERTS, errorMessages);
    }
}
