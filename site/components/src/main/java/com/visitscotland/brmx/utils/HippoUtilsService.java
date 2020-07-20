package com.visitscotland.brmx.utils;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleRegistry;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class HippoUtilsService {

    private static final Logger logger = LoggerFactory.getLogger(HippoUtilsService.class.getName());

    final ResourceBundleRegistry resourceBundleRegistry;
    final HstRequestContext requestContext;

    public HippoUtilsService(){
        resourceBundleRegistry = HstServices.getComponentManager().getComponent(ResourceBundleRegistry.class.getName());
        requestContext = RequestContextProvider.get();
    }

    public HippoUtilsService(
            ResourceBundleRegistry resourceBundleRegistry,
            HstRequestContext requestContext){
        this.resourceBundleRegistry = resourceBundleRegistry;
        this.requestContext = requestContext;
    }

    static HippoUtilsService INSTANCE;

    /**
     * This method
     * @return
     */
    @Deprecated
    public static HippoUtilsService getInstance(){
        if (INSTANCE == null){
            INSTANCE = new HippoUtilsService();
        }
        return INSTANCE;
    }

    /**
     * TODO: CREATE J-UNIT TEST
     *
     *
     */
    public String getResourceBundle(String key, String bundleName, Locale locale){
        return getResourceBundle(key, bundleName, locale, false);
    }

    /**
     * TODO: CREATE J-UNIT TEST
     * TODO: DOCUMENT
     */
    public String getResourceBundle(String key, String bundleName, Locale locale, boolean optional){

        ResourceBundle bundle = getResourceBundle(bundleName, locale);

        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getString(key);
        } else if (!optional){
            String localeText = locale==null? Locale.UK.getLanguage():locale.getLanguage();
            logger.warn(String.format("The resource bundle key %s does not exist for %s", key, localeText));
        }

        return "??"+key+"??";
    }

    public boolean existsResourceBundleKey(String key, String bundleName, Locale locale){
        ResourceBundle bundle = getResourceBundle(bundleName, locale);
        return bundle != null && bundle.containsKey(key);
    }

    /**
     * Return a resource bundle for a specific locale
     *
     * @param bundleName id of the Resource Bundle defined in Hippo
     * @param locale locale
     *
     * @return
     */
    private ResourceBundle getResourceBundle(String bundleName, Locale locale){
        if (locale == null) {
            return resourceBundleRegistry.getBundle(bundleName);
        } else {
            return resourceBundleRegistry.getBundle(bundleName, locale);
        }
    }

    /**
     * Convert and HstLink or a HippoBean into a URL String
     *
     * TODO: Verify that the item can be rendered as a page and return null when it doesn't
     *
     * @param document Page document
     *
     * @return URL for the page that renders the document or null when it cannot be rendered as a page.
     */
    public String createUrl(HippoBean document){
        final boolean FULLY_QUALIFIED = true;
        HstRequestContext requestContext = RequestContextProvider.get();

        HstLink link = requestContext.getHstLinkCreator().create(document, requestContext);
        return link.toUrlForm(requestContext, FULLY_QUALIFIED);
    }
}
