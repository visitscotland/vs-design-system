package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.mapping.LocalizedURL;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.brmx.utils.Properties;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.CommonComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class InternalContentComponent extends CommonComponent {

    public static final String PARAM_SSO = "sso";
    public static final String PARAM_ROOT_PATH = "root-path";
    public static final String PARAM_EXTERNAL = "external";

    public static final String PATH_PLACEHOLDER = "[PATH-PLACEHOLDER]";

    public static final String FULLY_QUALIFIED_URLS = "fullyQualified";
    public static final String SSO_URL = "returnUrl";
    public static final String GLOBAL_MENU_URLS = "localizedURLs";


    ResourceBundleService bundle;
    HippoUtilsService utils;

    public InternalContentComponent() {
        bundle = new ResourceBundleService();
        utils = new HippoUtilsService();
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addLocalizedURLs(request);

        processParameters(request);
    }

    private void processParameters(HstRequest request) {
        final StringBuilder returnUrl = new StringBuilder();
        String external = utils.getParameterFromUrl(request, PARAM_EXTERNAL);
        String rootPath = utils.getParameterFromUrl(request, PARAM_ROOT_PATH);
        String sso = utils.getParameterFromUrl(request, PARAM_SSO);

        if (rootPath != null) {
            returnUrl.append(rootPath).append("/").append(PATH_PLACEHOLDER);
            request.getRequestContext().setModel(FULLY_QUALIFIED_URLS, "true");
        } else if (external != null) {
            //sb.append(re);
            returnUrl.append(composeNonExistingURL(request.getLocale(), request));
            request.getRequestContext().setModel(FULLY_QUALIFIED_URLS, "true");
        } else {
            returnUrl.append("/").append(PATH_PLACEHOLDER);
        }

        if (sso != null) {
            returnUrl.append("?id=").append(sso);
        }

        request.setModel(SSO_URL, returnUrl.toString());
    }

    public void addLocalizedURLs(HstRequest request) {
        List<LocalizedURL> translatedURL = new ArrayList<>(Properties.Language.values().length);

        for (Locale locale : Properties.locales) {
            LocalizedURL lan = new LocalizedURL();
            lan.setLocale(locale);
            lan.setLanguage(locale == null ? "en" : locale.getLanguage());
            lan.setDisplayName(bundle.getResourceBundle("universal", lan.getLanguage(), request.getLocale()));

            lan.setUrl(composeNonExistingURL(locale, request));
            lan.setExists(false);
            translatedURL.add(lan);
        }
        request.setModel(GLOBAL_MENU_URLS, translatedURL);
    }

    /**
     * Composes a non existing URL with the Placeholder.
     */
    private String composeNonExistingURL(Locale locale, HstRequest request) {
        String languagePath = "";

        if (locale != null) {
            languagePath += "/" + locale.getLanguage();
        }

        return request.getRequestContext().getBaseURL().getHostName() +
                request.getRequestContext().getBaseURL().getContextPath() +
                languagePath + PATH_PLACEHOLDER;
    }
}
