package com.visitscotland.brxm.utils;


import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

//TODO Externalize properties
public class Properties {

    private Properties(){

    }


    //TODO: Load the list from the list of locations defined in the CMS. Bear in mind that a default location (null) is required
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

    //TODO Calculate environment
    public static final String VS_DMS_SERVICE = "http://172.28.81.65:8089";

    //TODO Calculate local environment
    //TODO USe in the globalNavigation
    public static final String LOCALHOST = "http://localhost:8080/site";

    public static final String INSTAGRAM_API = "https://www.instagram.com/p/";

    public static final String HELPDESK = "helpdesk@visitscotland.com";

    final static Charset VS_DMS_ENCODING = StandardCharsets.UTF_8;


}
