package com.visitscotland.brmx.translation.plugin;

import javax.jcr.Node;

public class TranslatedFolderFactory {
    public TranslatedFolder createFromNode(Node sourceNode) {
        return new TranslatedFolder(sourceNode);
    }
}
