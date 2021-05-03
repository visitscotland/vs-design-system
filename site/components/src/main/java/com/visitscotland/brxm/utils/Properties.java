package com.visitscotland.brxm.utils;

import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

@Component
public class Properties {

    private static final Logger logger = LoggerFactory.getLogger(Properties.class.getName());

    public static final String INSTAGRAM_API = "instagram.api";
    public static final String INSTAGRAM_TOKEN ="instagram.accesstoken";
    public static final String INSTAGRAM_URL ="instagram.post-url";
    public static final String LOCALHOST = "localhost";
    public static final String HELPDESK_EMAIL = "helpdesk-email";
    public static final String DMS_HOST = "vs-dms-products.url";
    public static final String DMS_MAP_DEFAULT_DISTANCE = "dms.default-distance";
    public static final String DMS_DATA_HOST = "dms-data.url";
    public static final String DMS_DATA_ENCODING = "dms-data.encoding";
    public static final String DMS_DATA_API_KEY = "dms-data.api-key";
    public static final String DMS_DATA_TIMEOUT = "dms-data.timeout";
    public static final String DMS_DATA_TRIES = "dms-data.tries";
    public static final String DMS_DATA_SLEEP_TIME = "dms-data.sleep-time";
    private static final String CONFIGURATION = "config.cms";

    private final ResourceBundleService bundle;

    public Properties(ResourceBundleService bundle){
        this.bundle = bundle;
    }

    public String getLocalhost() {
        return readString(LOCALHOST);
    }

    public String getInstagramApi() {
        return readString(INSTAGRAM_API);
    }

    public String getInstagramURL() {
        return readString(INSTAGRAM_URL);
    }

    public String getInstagramToken() {
        return readString(INSTAGRAM_TOKEN);
    }

    public String getHelpdeskEmail() {
        return readString(HELPDESK_EMAIL);
    }

    public String getDmsHost() {
        return readString(DMS_HOST);
    }

    public String getDmsDataHost() {
        return readString(DMS_DATA_HOST);
    }

    public String getDmsMapDefaultDistance() {
        return readString(DMS_MAP_DEFAULT_DISTANCE);
    }

    public String getDmsToken() {
        return readString(DMS_DATA_API_KEY);
    }

    public Integer getDmsTimeout() {
        return readInteger(DMS_DATA_TIMEOUT);
    }

    public Integer getDmsTries() {
        return readInteger(DMS_DATA_TRIES);
    }

    public Integer getDmsWaitTime() {
        return readInteger(DMS_DATA_SLEEP_TIME);
    }

    //TODO Test
    public Charset getDmsEncoding() {
        String value = bundle.getResourceBundle(CONFIGURATION, DMS_DATA_ENCODING, Locale.UK);
        try{
            if (!Contract.isEmpty(value)) {
                return Charset.forName(value);
            }
        } catch (Exception e){
            logger.warn("{} is not a valid value for the property {}", value, DMS_DATA_ENCODING);
        }
        return StandardCharsets.UTF_8;
    }

    //TODO Test
    private String readString(String key){
        String value = bundle.getResourceBundle(CONFIGURATION, key, Locale.UK);

        if (Contract.isEmpty(value)) {
            logger.warn("The property {} hasn't been set in the resourceBundle {}", key, CONFIGURATION);
        } else if (value.startsWith("$")){
            String env = System.getenv(value.substring(1));
            if (env != null){
                return env;
            }
        } else  {
            return value;
        }

        return "";
    }

    //TODO test
    private Integer readInteger(String key){
        String value = bundle.getResourceBundle(CONFIGURATION, key, Locale.UK);
        try {
            if (Contract.isEmpty(value)){
                logger.warn("The property {} hasn't been set in the resourceBundle {}", key, CONFIGURATION);
            } else {
                return Integer.valueOf(value);
            }
        } catch (NumberFormatException nfe){
            logger.error("The property value of the property {} cannot be casted to Integer. '{}' is not allowed.", key,value);
        }
        return 0;
    }
}
