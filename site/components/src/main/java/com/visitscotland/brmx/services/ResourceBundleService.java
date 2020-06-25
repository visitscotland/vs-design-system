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
    final CommonUtils common;

    public ResourceBundleService (){
        //Default Hippo Resource bundle Service
        this(HstServices.getComponentManager().getComponent(ResourceBundleRegistry.class.getName()),
                new CommonUtils());
    }

    ResourceBundleService (ResourceBundleRegistry rbr, CommonUtils common){
        resourceBundleRegistry = rbr;
        this.common = common;
    }


    /**
     * Gets a string for the given key from this resource bundle or one of its parents.
     *
     * @param bundleName id of the Resource Bundle defined in Hippo
     * @param key key
     * @param locale locale
     */
    public String getResourceBundle(String bundleName, String key, Locale locale){
        return getResourceBundle(bundleName, key, locale, false);
    }

    /**
     * Gets a string for the given key from this resource bundle or one of its parents.
     *
     * This method is usually used from FREEMARKER
     *
     * @param bundleName id of the Resource Bundle defined in Hippo
     * @param key key
     * @param locale locale
     */
    public String getResourceBundle(String bundleName, String key, String locale){
        return getResourceBundle(bundleName, key, Locale.forLanguageTag(locale));
    }

    /**
     * Gets a string for the given key from this resource bundle (or one of its parents when is not {@code optional}).
     *
     * This method is usually used from FREEMARKER
     *
     * @param bundleName id of the Resource Bundle defined in Hippo
     * @param key key
     * @param locale locale
     * @param optional when {@code false} if the value does not exist in the language it would fallback to English
     *
     * @return
     */
    public String getResourceBundle(String bundleName, String key, String locale, boolean optional){
        return getResourceBundle(bundleName, key, Locale.forLanguageTag(locale), false);
    }

    /**
     * Gets a string for the given key from this resource bundle (or one of its parents when is not {@code optional}).
     *
     *
     * @param bundleName id of the Resource Bundle defined in Hippo
     * @param key key
     * @param locale locale
     * @param optional when {@code false} if the value does not exist in the language it would fallback to English
     * @return
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

    /**
     * Verify that a value exists for a key in the specified language
     *
     * @param bundleName id of the Resource Bundle defined in Hippo
     * @param key Key
     * @param locale Locale
     *
     * @return {@code true} when the key exists in that language and has a value.
     */
    public boolean existsResourceBundleKey(String bundleName, String key,  Locale locale){
        ResourceBundle bundle = getResourceBundle(bundleName, locale);
        return bundle != null && bundle.getLocale() != null
                && bundle.containsKey(key) && !Contract.isEmpty(bundle.getString(key));
    }

    /**
     * Logs a issue that can be solved from the CMS
     *
     * @param message message
     * @param args arguments for the message
     */
    void logContentIssue(String message, Object... args){
        common.contentIssue(message, args);
    }

    /**
     * Logs an issue with a warning message.
     *
     * This method allows Unit test to verify that the message is logged.
     *
     * @param message
     */
    // TODO: Is unit test for this really important?
    void logIssue(String message){
        logger.warn(message);
    }
}
