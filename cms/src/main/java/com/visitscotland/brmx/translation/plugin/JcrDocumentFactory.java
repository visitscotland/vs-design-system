package com.visitscotland.brmx.translation.plugin;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class JcrDocumentFactory {
    public JcrDocument createJcrDocument(Node sourceNode) throws RepositoryException {
        return new JcrDocument(sourceNode);
    }
}
