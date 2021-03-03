package com.visitscotland.brxm.beans;


public interface TranslationParent {

//    List<BaseDocument> getModules();

    default String[] getChildJcrTypes() {
        return new String[] {};
    }
}
