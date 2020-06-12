package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.AccessDeniedException;
import javax.jcr.ItemNotFoundException;
import javax.jcr.Node;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorFindHighestTranslatedSourceFolderTest {
    DocumentTranslator translator;
    @Mock
    HippoTranslatedNodeFactory mockTranslatedNodeFactory;
    @Mock
    Node mockSourceNode;
    @Mock
    private TranslatedFolderFactory mockFolderFactory;

    @BeforeEach
    public void beforeEach() {
        translator = new DocumentTranslator(mockTranslatedNodeFactory, mockFolderFactory);
    }

    @Test
    public void findHighestPopulatedSourceFolder_ItemNotFoundException_whenWalkingToRoot() throws Exception {
        // An ItemNotFoundException thrown when trying to find a translated parent
        // Should catch the error and return null
        when(mockSourceNode.isNodeType(eq(HippoTranslationNodeType.NT_TRANSLATED))).thenReturn(false);
        when(mockSourceNode.getParent()).thenThrow(new ItemNotFoundException());

        assertNull(translator.findHighestTranslatedSourceFolder(mockSourceNode));
    }

    @Test
    public void populateFolders_AccessDeniedException_whenWalkingToRoot() throws Exception {
        // An AccessDeniedException thrown when trying to find a translated parent
        // Should be no alterations to the folder list
        when(mockSourceNode.isNodeType(eq(HippoTranslationNodeType.NT_TRANSLATED))).thenReturn(false);
        when(mockSourceNode.getParent()).thenThrow(new AccessDeniedException());

        assertNull(translator.findHighestTranslatedSourceFolder(mockSourceNode));
    }

    @Test
    public void populateFolders_AllTranslatedAlready() throws Exception {
        // If highest translated source folder has already been translated then
        when(mockSourceNode.isNodeType(eq(HippoTranslationNodeType.NT_TRANSLATED))).thenReturn(true);

        assertSame(mockSourceNode, translator.findHighestTranslatedSourceFolder(mockSourceNode));
    }
}
