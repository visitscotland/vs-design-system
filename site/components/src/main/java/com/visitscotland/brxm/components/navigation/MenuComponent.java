package com.visitscotland.brxm.components.navigation;


import com.visitscotland.brxm.components.navigation.info.MenuComponentInfo;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.factory.NavigationFactory;
import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.model.LocalizedURL;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.Language;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsMenuComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ParametersInfo(
        type = MenuComponentInfo.class
)
public class MenuComponent extends EssentialsMenuComponent {

    private static final Logger logger = LoggerFactory.getLogger(MenuComponent.class);

    public static final String MENU = "menu";

    private ResourceBundleService bundle;
    private HippoUtilsService utils;
    private NavigationFactory factory;

    public MenuComponent() {
        this(VsComponentManager.get(ResourceBundleService.class),
                VsComponentManager.get(HippoUtilsService.class),
                VsComponentManager.get(NavigationFactory.class));
    }

    public MenuComponent(ResourceBundleService bundle, HippoUtilsService utils, NavigationFactory factory) {
        this.bundle = bundle;
        this.utils = utils;
        this.factory = factory;
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        enhanceRequest(request);
        addLocalizedURLs(request);
    }

    protected void enhanceRequest(HstRequest request) {
        request.setModel(MENU, factory.buildMenu(request, request.getModel(MENU)));
    }

    public void addLocalizedURLs(HstRequest request) {
        List<LocalizedURL> translatedURL = new ArrayList<>(Language.values().length);

        HippoBean document = request.getRequestContext().getContentBean();

        HippoBean englishSite = null;
        if (document != null) {
            for (Language language : Language.values()) {
                LocalizedURL lan = new LocalizedURL();
                lan.setLocale(language.getLocale());
                lan.setLanguage(language.getLocale().getLanguage());
                lan.setDisplayName(bundle.getResourceBundle("universal", lan.getLanguage(), request.getLocale()));

                HippoBean translation = document.getAvailableTranslations().getTranslation(lan.getLanguage());

                if (Locale.UK.equals(language.getLocale())) {
                    if (translation == null) {
                        logger.error("The requested page does not exist in English: {}", document.getPath());
                    } else {
                        englishSite = translation;
                    }
                }

                if (translation instanceof Page) {
                    lan.setUrl(utils.createUrl((Page) translation));
                    lan.setExists(true);
                } else {
                    //TODO: Define if the URL is made up, or we use the englishSite link instead
                    //lan.setUrl(utils.createUrl(englishSite));
                    lan.setUrl(composeNonExistingURL(language.getLocale(), request));
                    lan.setExists(false);
                }
                translatedURL.add(lan);
            }
        } else {
            logger.error("Menu functionality is not supported for Channel Manager Pages at the moment");
        }
        request.setModel("localizedURLs", translatedURL);
    }

    /**
     * TODO move to CommonUtilsService
     * Composes the URL from the current request for a non-existing URL.
     */
    private String composeNonExistingURL(Locale locale, HstRequest request){
        String languagePath = "";

        if (locale != null) {
            languagePath += "/" + locale.getLanguage();
        }

        //TODO This might be wrong if the resource is proxied
        return request.getRequestContext().getBaseURL().getHostName() +
                request.getRequestContext().getBaseURL().getContextPath() +
                languagePath + request.getRequestContext().getBaseURL().getPathInfo();
    }
}
