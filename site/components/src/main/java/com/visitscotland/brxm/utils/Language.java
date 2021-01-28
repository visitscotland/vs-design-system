package com.visitscotland.brxm.utils;

import java.util.Locale;

public enum Language {
    ENGLISH(Locale.UK),
    SPANISH(Locale.forLanguageTag("es-es")),
    ITALIAN(Locale.forLanguageTag("it-it")),
    GERMAN(Locale.forLanguageTag("de-de")),
    DUTCH(Locale.forLanguageTag("nl-nl")),
    FRENCH(Locale.forLanguageTag("fr-fr"));

    private final Locale locale;

    Language(Locale locale) {
        this.locale = locale;
    }

    public static Language getLanguageForLocale(Locale locale) {
        if (locale != null) {
            for (Language language : values()) {
                if (locale.getLanguage().equals(language.locale.getLanguage())) {
                    return language;
                }
            }
        }
        return ENGLISH;
    }

    public Locale getLocale(){
        return locale;
    }

    public String getCMSPathVariable() {
        if (this == ENGLISH) {
            return "";
        } else {
            return locale.getLanguage();
        }
    }

    public String getDMSPathVariable() {
        if (this == ENGLISH) {
            return null;
        } else {
            return "/" + locale.toLanguageTag();
        }
    }
}
