package com.visitscotland.www;

import com.visitscotland.www.beans.BaseDocument;
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
        final ResourceBundleRegistry resourceBundleRegistry =
                HstServices.getComponentManager().getComponent(ResourceBundleRegistry.class.getName());
        ResourceBundle bundle;

        if (resourceBundleRegistry != null) {
            if (locale == null) {
                bundle = resourceBundleRegistry.getBundle(bundleName);
            } else {
                bundle = resourceBundleRegistry.getBundle(bundleName, locale);
            }
            if (bundle != null && bundle.containsKey(key)) {
                return bundle.getString(key);
            } else if (!optional){
                String localeText = locale==null? Locale.UK.getLanguage():locale.getLanguage();
                logger.warn(String.format("The resource bundle key %s does not exist for %s", key, localeText));
            }
        }

        return "??"+key+"??";
    }
}
