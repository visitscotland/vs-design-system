package com.visitscotland.brmx.beans;

import java.util.List;

public interface TranslationParent {

    List<BaseDocument> getModules();

    default String[] getChildJcrTypes() {
        return new String[] {};
    }
}
