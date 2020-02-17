package com.visitscotland.brmx.utils;

import com.google.common.collect.Sets;

import java.util.*;

//TODO Externalize properties
public class Properties {

    //TODO: Load the list from the list of locations defined in the CMS. Bear in mind that a default location (null) is required
    public static final List<Locale> locales = Arrays.asList(null,
            Locale.forLanguageTag("es-es"),
            Locale.forLanguageTag("fr-fr"),
            Locale.forLanguageTag("nl-nl"),
            Locale.forLanguageTag("de-de"),
            Locale.forLanguageTag("it-it"));

    //TODO Calculate environment
    public static final String VS_DMS_PRODUCTS = "https://test1.visitscotland.com";


}
