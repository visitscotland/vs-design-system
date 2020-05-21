package com.visitscotland.brmx.utils;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleRegistry;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class HippoUtils {

    private static final Logger logger = LoggerFactory.getLogger(HippoUtils.class.getName());

    /**
     * TODO: CREATE J-UNIT TEST
     * TODO: DOCUMENT
     */
    public static String getResourceBundle(String key, String bundleName, Locale locale){
        return getResourceBundle(key, bundleName, locale, false);
    }

    /**
     * TODO: CREATE J-UNIT TEST
     * TODO: DOCUMENT
     */
    public static String getResourceBundle(String key, String bundleName, Locale locale, boolean optional){

        ResourceBundle bundle = getResourceBundle(bundleName, locale);

        if (bundle != null && bundle.containsKey(key)) {
            return bundle.getString(key);
        } else if (!optional){
            String localeText = locale==null? Locale.UK.getLanguage():locale.getLanguage();
            logger.warn(String.format("The resource bundle key %s does not exist for %s", key, localeText));
        }

        return "??"+key+"??";
    }

    public static boolean existsResourceBundleKey(String key, String bundleName, Locale locale){
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
    private static ResourceBundle getResourceBundle(String bundleName, Locale locale){
        final ResourceBundleRegistry resourceBundleRegistry =
                HstServices.getComponentManager().getComponent(ResourceBundleRegistry.class.getName());

        if (locale == null) {
            return resourceBundleRegistry.getBundle(bundleName);
        } else {
            return resourceBundleRegistry.getBundle(bundleName, locale);
        }
    }

    /**
     * TODO comment
     * @param document
     * @return
     */
    public static String createUrl(HippoBean document){
        final boolean FULLY_QUALIFIED = true;
        HstRequestContext context = RequestContextProvider.get();
        HstLink link = context.getHstLinkCreator().create(document, context);
        return link.toUrlForm(context, FULLY_QUALIFIED);
    }
}
