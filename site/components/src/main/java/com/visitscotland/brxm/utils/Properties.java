package com.visitscotland.brxm.utils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

//TODO Externalize properties
public class Properties {

    private Properties() {

    }

    /**
     * @deprecated Use Language.values() instead
     */
    @Deprecated
    public static final List<Locale> locales = Arrays.asList(null,
            Locale.forLanguageTag("es"),
            Locale.forLanguageTag("fr"),
            Locale.forLanguageTag("nl"),
            Locale.forLanguageTag("de"),
            Locale.forLanguageTag("it"));

    //TODO Calculate local environment
    //TODO USe in the globalNavigation
    public static final String LOCALHOST = "http://localhost:8080/site";

    public static final String INSTAGRAM_API = "https://www.instagram.com/p/";

    public static final String HELPDESK = "helpdesk@visitscotland.com";

    //TODO Calculate environment
    public static final String VS_DMS_SERVICE = "http://172.28.81.65:8089";

    public static Charset DMS_ENCODING = StandardCharsets.UTF_8;
    public static String DMS_TOKEN = "tokenID";
    public static Integer DMS_TIMEOUT = 2000;
    public static Integer DMS_TRIES = 3;
    public static Integer DMS_WAIT_TIME = 60_000;

}
