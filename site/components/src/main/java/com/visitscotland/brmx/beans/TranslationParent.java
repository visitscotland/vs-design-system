package com.visitscotland.brmx.beans;

public interface TranslationParent {
    default String[] getChildJcrTypes() {
        return new String[] {};
    };
}
