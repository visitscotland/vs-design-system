package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HippoTranslatedNodeFactoryTest {

    private HippoTranslatedNodeFactory factory;
    @Mock
    private TranslatedFolder mockFolder;
    @Mock
    private Node mockNode;

    @BeforeEach
    public void beforeEach() {
        factory = new HippoTranslatedNodeFactory();
    }

    @Test
    public void fromTranslatedFolder_not_translated_type() throws Exception {
        when(mockFolder.getNode()).thenReturn(mockNode);
        when(mockNode.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)).thenReturn(false);

        assertThrows(RepositoryException.class, () -> factory.createFromTranslatedFolder(mockFolder));
    }

    @Test
    public void fromTranslatedFolder_is_translated_type() throws Exception {
        when(mockFolder.getNode()).thenReturn(mockNode);
        when(mockNode.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)).thenReturn(true);

        HippoTranslatedNode hippoNode = factory.createFromTranslatedFolder(mockFolder);
        assertNotNull(hippoNode);
    }

    @Test
    public void fromNode_not_translated_type() throws Exception {
        when(mockNode.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)).thenReturn(false);

        assertThrows(RepositoryException.class, () -> factory.createFromNode(mockNode));
    }

    @Test
    public void fromNode_is_translated_type() throws Exception {
        when(mockNode.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)).thenReturn(true);

        HippoTranslatedNode hippoNode = factory.createFromNode(mockNode);
        assertNotNull(hippoNode);
    }
}
