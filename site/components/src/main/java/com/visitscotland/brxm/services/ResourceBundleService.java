package com.visitscotland.brxm.services;

import com.visitscotland.brxm.utils.CommonUtils;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.resourcebundle.ResourceBundleRegistry;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;
import java.util.ResourceBundle;


public class ResourceBundleService {

    private static final Logger logger = LoggerFactory.getLogger(ResourceBundleService.class.getName());

    private static final String SERVICE_NAME = "ResourceBundle";

    ResourceBundleRegistry resourceBundleRegistry;
    final CommonUtils common;

    public ResourceBundleService() {
        //Default Hippo Resource bundle Service
        this(new CommonUtils());
    }

    ResourceBundleService (CommonUtils common){
        this.common = common;
    }

    private ResourceBundleRegistry getResourceBundleRegistry(){
        if (resourceBundleRegistry== null){
            resourceBundleRegistry = HstServices.getComponentManager().getComponent(ResourceBundleRegistry.class.getName());
        }
        return  resourceBundleRegistry;
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
        return getResourceBundle(bundleName, key, toLocale(locale));
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
        return getResourceBundle(bundleName, key, toLocale(locale), optional);
    }

    /**
     * when locale is null or empty a {@code null}value is returned. Otherwise, a locale is created according to
     * Locale.forLanguageTag(String) specification
     *
     * @param locale String with the locale information
     *
     * @return a {@code Locale} object version of the {@dode String} or {@code null} when empty String or null
     */
    Locale toLocale(String locale){
        if (locale == null || locale.length() == 0){
            return null;
        } else {
            return Locale.forLanguageTag(locale);
        }
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
        boolean fallback = false;

        if (bundle == null) {
            logger.warn(String.format("The resource bundle '%s' does not exist" , bundleName));
        } else {
            if(bundle.containsKey(key)) {
                value = bundle.getString(key);
                if (Contract.isEmpty(value) && locale != null && !optional) {
                    value = getResourceBundle(bundleName,key, (Locale) null, false);
                    if (!Contract.isEmpty(value)) {
                        logContentIssue("The label key %s does not exists for the %s channel. Resource Bundle key %s", key, bundle.getLocale(), bundleName);
                    }
                    fallback = true;
                }
            }
            if (Contract.isEmpty(value) && !optional){
                logContentIssue("The label key %s does not exists for the English channel. Resource Bundle key %s", key, bundleName);
                logger.warn(String.format("The label key %s does not exists for the English channel. Resource Bundle key %s", key, bundleName));
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
            return getResourceBundleRegistry().getBundle(bundleName);
        } else {
            return getResourceBundleRegistry().getBundle(bundleName, locale);
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
        return bundle != null && bundle.containsKey(key) && !Contract.isEmpty(bundle.getString(key));
    }

    /**
     * Logs a issue that can be solved from the CMS
     *
     * @param message message
     * @param args arguments for the message
     */
    void logContentIssue(String message, Object... args) {
        //TODO Transform into a different Logger
        logger.warn(common.contentIssue(message, args));
    }

    public void registerIn(HstRequest request) {
        if (request.getAttribute(SERVICE_NAME) == null) {
            request.setAttribute(SERVICE_NAME, this);
            logger.debug(SERVICE_NAME + " has been registered on the request");
        } else {
            logger.info(SERVICE_NAME + " has been been already registered on the request");
        }
    }

    /**
     * Returns the default CTA label when the manual CTA  is not defined.     *
     *
     * @param manualCta Manual CTA defined in the CMS
     * @param locale    Locale
     * @return the manual CTA if provided otherwise the default CTA
     */
    public String getCtaLabel(String manualCta, Locale locale) {
        if (!Contract.isEmpty(manualCta)) {
            return manualCta;
        } else {
            return getResourceBundle("essentials.global","button.find-out-more",  locale);
        }
    }

}
