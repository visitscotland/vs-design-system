package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.repository.api.HippoNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.RepositoryException;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorAddAllUntranslatedFoldersTest {
    private DocumentTranslator translator;
    @Mock
    private HippoTranslatedNodeFactory mockTranslatedNodeFactory;
    @Mock
    private TranslatedFolderFactory mockFolderFactory;
    @Mock
    private TranslatedFolder mockSourceFolder;
    private List<FolderTranslation> folders;

    @BeforeEach
    public void beforeEach() {
        folders = new LinkedList<>();
        translator = new DocumentTranslator(mockTranslatedNodeFactory, mockFolderFactory);
    }

    @Test
    public void addAllUntranslatedFolders_highestAlreadyTranslated() throws Exception {
        // The first folder is already translated, should return that folder
        when(mockSourceFolder.getSibling(eq("no"))).thenReturn(mock(TranslatedFolder.class));

        translator.addAllUntranslatedFolders("no", folders, mockSourceFolder);
        assertTrue(folders.isEmpty());
    }

    @Test
    public void addAllUntranslatedFolders_parentIsNull() throws Exception {
        // No translated sibling before parent is eventually null
        int numberInChain = 10;
        createUntranslatedFolderChain(mockSourceFolder, numberInChain, "no",false);
        assertThrows(RepositoryException.class, () -> translator.addAllUntranslatedFolders("no", folders, mockSourceFolder));

        // The folders should also be populated, not sure if this intended functionality
        // but I'll test that the values are what they should be
        // Source folder + numberInChain, note the folders are in reverse index order
        assertEquals(numberInChain + 1, folders.size());
        for (int index = 0; index < numberInChain + 1; index++) {
            assertFolderTranslation(index, folders.get(index));
        }
    }

    @Test
    public void addAllUntranslatedFolders_parentIsTranslated() throws Exception {
        // Valid path, walks the untranslated folders adding them to the folders list
        // the names of the folders and their URLs should match the originals
        int numberInChain = 10;
        createUntranslatedFolderChain(mockSourceFolder, numberInChain, "no",true);
        translator.addAllUntranslatedFolders("no", folders, mockSourceFolder);

        // The folders should be populated
        assertEquals(numberInChain, folders.size());
        for (int index = 0; index < numberInChain; index++) {
            assertFolderTranslation(index, folders.get(index));
        }
    }

    private void assertFolderTranslation(int index, FolderTranslation translation) {
        assertTrue(translation.getName().endsWith(Integer.toString(index)));
        assertEquals(translation.getName(), translation.getNamefr());
        assertTrue(translation.getUrl().endsWith(Integer.toString(index)));
        assertEquals(translation.getUrl(), translation.getUrlfr());
    }

    private HippoNode createHippoNode(String url, String displayName) throws Exception {
        HippoNode mockNode = mock(HippoNode.class);
        when(mockNode.getName()).thenReturn(url);
        when(mockNode.getDisplayName()).thenReturn(displayName);
        return mockNode;
    }

    private void createUntranslatedFolderChain(TranslatedFolder sourceFolder, int numberInChain, String targetLanguage, boolean lastFolderTranslated) throws Exception {
        // We have numberInChain + 1 for the source folder. The source folder is number 0
        int nodeNumber = numberInChain;
        TranslatedFolder lastCreated = mock(TranslatedFolder.class);
        if (lastFolderTranslated) {
            when(lastCreated.getSibling(eq(targetLanguage))).thenReturn(mock(TranslatedFolder.class));
        } else {
            HippoNode mockNode = createHippoNode("url" + nodeNumber, "name" + nodeNumber);
            when(lastCreated.getNode()).thenReturn(mockNode);
            when(lastCreated.getSibling(eq(targetLanguage))).thenReturn(null);
            when(lastCreated.getParent()).thenReturn(null);
        }
        nodeNumber--;

        for (int index = 1; index < numberInChain; index++) {
            TranslatedFolder nextFolder = mock(TranslatedFolder.class);
            HippoNode mockNode = createHippoNode("url" + nodeNumber, "name" + nodeNumber);
            nodeNumber--;
            when(nextFolder.getNode()).thenReturn(mockNode);
            when(nextFolder.getSibling(eq(targetLanguage))).thenReturn(null);
            when(nextFolder.getParent()).thenReturn(lastCreated);
            lastCreated = nextFolder;
        }
        HippoNode mockSourceNode = createHippoNode("url" + nodeNumber, "name" + nodeNumber);
        when(sourceFolder.getNode()).thenReturn(mockSourceNode);
        when(sourceFolder.getSibling(eq(targetLanguage))).thenReturn(null);
        when(sourceFolder.getParent()).thenReturn(lastCreated);
    }
}
