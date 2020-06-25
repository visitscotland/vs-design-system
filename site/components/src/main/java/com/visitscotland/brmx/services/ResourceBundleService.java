package com.visitscotland.brmx.services;

import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.resourcebundle.ResourceBundleRegistry;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;

public class ResourceBundleService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceBundleService.class.getName());

    final ResourceBundleRegistry resourceBundleRegistry;

    public ResourceBundleService (){
        //Default Hippo Resource bundle Service
        this(HstServices.getComponentManager().getComponent(ResourceBundleRegistry.class.getName()));
    }

    ResourceBundleService (ResourceBundleRegistry rbr){
        resourceBundleRegistry = rbr;
    }


    /**
     * TODO: CREATE J-UNIT TEST
     *
     *
     */
    public String getResourceBundle(String bundleName, String key, Locale locale){
        return getResourceBundle(bundleName, key, locale, false);
    }

    /**
     * Used from FREEMARKER
     *
     * @param bundleName
     * @param key
     * @param locale
     * @return
     */
    public String getResourceBundle(String bundleName, String key, String locale){
        return getResourceBundle(bundleName, key, Locale.forLanguageTag(locale));
    }

    /**
     * Used from FREEMARKER
     *
     * @param bundleName
     * @param key
     * @param locale
     * @param optional
     * @return
     */
    public String getResourceBundle(String bundleName, String key, String locale, boolean optional){
        return getResourceBundle(bundleName, key, Locale.forLanguageTag(locale), false);
    }
    /**
     * TODO: CREATE J-UNIT TEST
     * TODO: DOCUMENT
     */
    public String getResourceBundle(String bundleName, String key, Locale locale, boolean optional){

        ResourceBundle bundle = getResourceBundle(bundleName, locale);

        String value = null;

        if (bundle == null) {
            logIssue(String.format("The resource bundle '%s' does not exist" , bundleName));
        } else {
            if(bundle.containsKey(key)) {
                value = bundle.getString(key);
                if (Contract.isEmpty(value) && bundle.getLocale() != null && !optional) {
                    value = getResourceBundle(bundleName,key, (Locale) null, optional);
                    if (!Contract.isEmpty(value)) {
                        logContentIssue("The label key %s does not exists for the %s channel. Resource Bundle key %s", key, bundle.getLocale(), bundleName);
                    }
                }
            }
            if (Contract.isEmpty(value) && !optional){
                logContentIssue("The label key %s does not exists for the English channel. Resource Bundle key %s", key, bundleName);
            }
        }

        return value;
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

    public boolean existsResourceBundleKey(String key, String bundleName, Locale locale){
        ResourceBundle bundle = getResourceBundle(bundleName, locale);
        return bundle != null && bundle.containsKey(key);
    }



    void logContentIssue(String message, Object... args){
        CommonUtils.contentIssue(message, args);
    }

    void logIssue(String message){
        logger.warn(message);
    }
}
