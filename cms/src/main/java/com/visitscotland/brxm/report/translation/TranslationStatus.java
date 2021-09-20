package com.visitscotland.brxm.report.translation;

public enum TranslationStatus {

    TRANSLATED, NOT_SENT_FOR_TRANSLATION, SEND_FOR_TRANSLATION, CLONED;

    @Override
    public String toString() {
        switch (this) {
            case NOT_SENT_FOR_TRANSLATION:
                return "Untranslated";
            case SEND_FOR_TRANSLATION:
                return "Sent for translation";
            case TRANSLATED:
                return "Translated";
            case CLONED:
                return "Untranslated (Cloned)";
            default:
                return this.name();
        }
    }
}
