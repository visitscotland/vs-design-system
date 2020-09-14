package com.visitscotland.brmx.translation;

import com.visitscotland.brmx.translation.plugin.JcrDocument;
import com.visitscotland.brmx.translation.plugin.JcrDocumentFactory;

import javax.jcr.Node;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class MockJcrDocumentBuilder {
    Map<String, Node> documentVariants = new HashMap<>();
    JcrDocumentFactory factory;
    Node rootNode;

    public MockJcrDocumentBuilder withJcrDocumentFactory(JcrDocumentFactory factory) {
        this.factory = factory;
        return this;
    }

    public MockJcrDocumentBuilder createdFromNode(Node rootNode) {
        this.rootNode = rootNode;
        return this;
    }

    public MockJcrDocumentBuilder withVariantNode(String state, Node variantNode) {
        documentVariants.put(state, variantNode);
        return this;
    }

    public JcrDocument build() throws Exception {
        JcrDocument mockJcrDocument = mock(JcrDocument.class);

        if ( null != factory && null != rootNode ) {
            when(factory.createFromNode(same(rootNode))).thenReturn(mockJcrDocument);
        }

        for (Map.Entry<String, Node> variantEntry : documentVariants.entrySet()) {
            when(mockJcrDocument.getVariantNode(eq(variantEntry.getKey()))).thenReturn(variantEntry.getValue());
        }

        return mockJcrDocument;
    }
}
