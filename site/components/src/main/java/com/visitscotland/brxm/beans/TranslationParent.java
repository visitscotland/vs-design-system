package com.visitscotland.brxm.beans;

import java.util.List;

public interface TranslationParent {

//    List<BaseDocument> getModules();

    default String[] getChildJcrTypes() {
        return new String[] {};
    }
}
