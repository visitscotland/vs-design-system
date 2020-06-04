package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.repository.translation.HippoTranslatedNode;

import javax.jcr.RepositoryException;

public class HippoTranslatedNodeFactory {
    public HippoTranslatedNode createFromTranslatedFolder(TranslatedFolder folder) throws RepositoryException {
        return new HippoTranslatedNode(folder.getNode());
    }
}
