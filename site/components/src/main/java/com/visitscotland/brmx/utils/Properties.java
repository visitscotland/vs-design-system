package com.visitscotland.brmx.utils;


import java.util.*;

//TODO Externalize properties
public class Properties {

    //TODO: Load the list from the list of locations defined in the CMS. Bear in mind that a default location (null) is required
    public static final List<Locale> locales = Arrays.asList(null,
            Locale.forLanguageTag("es"),
            Locale.forLanguageTag("fr"),
            Locale.forLanguageTag("nl"),
            Locale.forLanguageTag("de"),
            Locale.forLanguageTag("it"));

    //TODO Calculate environment
    public static final String VS_DMS_SERVICE = "http://172.28.81.65:8089";

    public static final String INSTAGRAM_API = "https://www.instagram.com/p/";


}
