package com.visitscotland.brmx.translation.plugin;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

public class JcrDocumentFactory {
    public JcrDocument createFromNode(Node toCreate) throws RepositoryException {
        return new JcrDocument(toCreate);
    }
}
