package com.visitscotland.brmx.beans;

import org.hippoecm.hst.content.beans.standard.HippoBean;

import java.util.List;

public interface TranslationLinkContainer {
    /**
     * Should return an array containing the name of all the translatable child nodes that this node contains.
     * e.g. visitscotland:stops for the Day class.
     * @return
     */
    default String[] getTranslatableLinkNames() {
        return new String[] {};
    };
}
