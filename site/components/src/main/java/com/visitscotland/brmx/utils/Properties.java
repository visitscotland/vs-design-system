package com.visitscotland.brmx.utils;


import java.util.*;

//TODO Externalize properties
public class Properties {

    public enum Language {
        ENGLISH(Locale.UK),
        SPANISH(Locale.forLanguageTag("es-es")),
        ITALIAN(Locale.forLanguageTag("it-it")),
        GERMAN( Locale.forLanguageTag("de-de")),
        DUTCH(Locale.forLanguageTag("nl-nl")),
        FRENCH(Locale.forLanguageTag("fr-fr"))
        ;

        private final Locale locale;

        Language(Locale locale) {
            this.locale = locale;
        }

        public static Language getLanguageForLocale(Locale locale){
            if (locale != null) {
                for (Language language : values()) {
                    if (locale.getLanguage().equals(language.locale.getLanguage())){
                        return language;
                    }
                }
            }
            return ENGLISH;
        }

        public String getCMSPathVariable(){
            if (this == ENGLISH){
                return "";
            } else {
                return locale.getLanguage();
            }
        }

        public String getDMSPathVariable(){
            if (this == ENGLISH){
                return null;
            } else {
                return "/" + locale.toLanguageTag();
            }
        }
    }

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
