package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.repository.api.HippoNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorCloneDocumentAndFolderStructureTest {
    private DocumentTranslator translator;
    private HippoNode mockDocNode;
    private List<FolderTranslation> folders;
    private ILocaleProvider.HippoLocale targetLocale;
    @Mock
    private Session mockSession;

    @BeforeEach
    public void beforeEach() throws Exception {
        // Will use a spy to allow the more complex methods to be mocked.
        // The other methods are covered by other tests.
        translator = Mockito.spy(DocumentTranslator.class);
        folders = new LinkedList<>();

        createMockDocumentNodes();
        mockAvoidSameNameSiblings();
    }

    @Test
    public void cloneDocumentAndFolderStructure_no_untranslated_folders() throws Exception {
        String targetLanguage = "es";
        createMockTargetLocale(targetLanguage);
        mockPopulateFolders(6, 0, folders, targetLanguage);

        assertNull(translator.cloneDocumentAndFolderStructure(mockDocNode, folders, targetLocale, mockSession));

        verify(translator, never()).saveFolder(any(FolderTranslation.class), any(Session.class), anyString());
    }

    @Test
    public void cloneDocumentAndFolderStructure_half_translated() throws Exception {
        String targetLanguage = "fr";
        createMockTargetLocale(targetLanguage);
        mockPopulateFolders(6, 3, folders, targetLanguage);
        mockSaveFolder(true);

        assertNull(translator.cloneDocumentAndFolderStructure(mockDocNode, folders, targetLocale, mockSession));

        // Should have saved the top 3 untranslated folders
        verify(translator, times(1)).saveFolder(same(folders.get(5)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(4)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(3)), any(Session.class), anyString());
    }

    @Test
    public void cloneDocumentAndFolderStructure_all_untranslated() throws Exception {
        String targetLanguage = "it";
        createMockTargetLocale(targetLanguage);
        //Note the root folder is always translated
        mockPopulateFolders(6, 5, folders, targetLanguage);
        mockSaveFolder(true);

        assertNull(translator.cloneDocumentAndFolderStructure(mockDocNode, folders, targetLocale, mockSession));

        // Should have saved the top 3 untranslated folders
        verify(translator, times(1)).saveFolder(same(folders.get(5)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(4)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(3)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(2)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(1)), any(Session.class), anyString());
    }

    @Test
    public void cloneDocumentAndFolderStructure_saveFolder_fails() throws Exception {
        String targetLanguage = "it";
        createMockTargetLocale(targetLanguage);
        mockPopulateFolders(6, 3, folders, targetLanguage);
        mockSaveFolder(false);

        assertEquals(DocumentTranslator.COULD_NOT_CREATE_FOLDERS, translator.cloneDocumentAndFolderStructure(mockDocNode, folders, targetLocale, mockSession));

    }

    private void createMockTargetLocale(String targetLanguage) {
        targetLocale = mock(ILocaleProvider.HippoLocale.class);
        when(targetLocale.getName()).thenReturn(targetLanguage);
    }

    private void createMockDocumentNodes() throws Exception {
        HippoNode mockDocParent = mock(HippoNode.class);
        when(mockDocParent.getName()).thenReturn("url");
        when(mockDocParent.getDisplayName()).thenReturn("name");

        mockDocNode = mock(HippoNode.class);
        when(mockDocNode.getParent()).thenReturn(mockDocParent);
    }

    private void mockSaveFolder(boolean isSaved) {
        doReturn(isSaved).when(translator).saveFolder(any(FolderTranslation.class), any(Session.class), anyString());
    }

    private void mockPopulateFolders(final int numberOfFolders,
                                     final int numberUntranslated,
                                     final List<FolderTranslation> folders,
                                     final String targetLanguage) throws Exception {
        doAnswer(invocation -> {
            // Want to mock the return from the populateFolders function.
            // This should be a chain of FolderTranslation instances,
            // the lower indexes are the translated folders (not editable),
            // the upper indexes are the untranslated folders (editable).

            List<FolderTranslation> folderList = invocation.getArgument(2);
            int untranslatedNeeded = numberUntranslated;
            for (int index = 0; index < numberOfFolders; index++) {
                FolderTranslation mockFolderTranslation = mock(FolderTranslation.class);
                folderList.add(mockFolderTranslation);
                if (untranslatedNeeded > 0) {
                    lenient().when(mockFolderTranslation.isEditable()).thenReturn(true);
                    untranslatedNeeded--;
                } else {
                    lenient().when(mockFolderTranslation.isEditable()).thenReturn(false);
                }
            }
            Collections.reverse(folderList);

            return null;
        }).when(translator).populateFolders(any(Node.class), eq(targetLanguage), same(folders));
    }

    private void mockAvoidSameNameSiblings() throws Exception {
        doNothing().when(translator).avoidSameNameSiblings(any(Session.class), anyInt(), anyString(), anyList());
    }
}
