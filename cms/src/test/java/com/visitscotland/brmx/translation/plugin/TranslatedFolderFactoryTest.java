package com.visitscotland.brmx.translation.plugin;

import org.junit.jupiter.api.Test;

import javax.jcr.Node;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;

public class TranslatedFolderFactoryTest {

    @Test
    public void creationTest() {
        TranslatedFolderFactory factory = new TranslatedFolderFactory();
        Node mockNode = mock(Node.class);
        TranslatedFolder result = factory.createFromNode(mockNode);
        assertSame(mockNode, result.getNode());
    }
}
