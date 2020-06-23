package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.translation.TranslationWorkflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorCloneSingleDocumentAndFolderStructureTest {
    private DocumentTranslator translator;
    private HippoNode mockDocNode;
    private List<FolderTranslation> folders;
    private ILocaleProvider.HippoLocale targetLocale;
    @Mock
    private Session mockSession;
    @Mock
    private TranslationWorkflow mockWorkflow;

    @BeforeEach
    public void beforeEach() throws Exception {
        // Will use a spy to allow the more complex methods to be mocked.
        // The other methods are covered by other tests.
        translator = Mockito.spy(DocumentTranslator.class);
        folders = new LinkedList<>();

        mockAvoidSameNameSiblings();
    }

    @Test
    public void cloneSingleDocumentAndFolderStructure_no_untranslated_folders() throws Exception {
        String targetLanguage = "es";
        createMockTargetLocale(targetLanguage);
        populateMockFolders(6, 0, folders, targetLanguage);

        translator.cloneSingleDocumentAndFolderStructure(folders, targetLocale, mockSession, mockWorkflow);

        verify(translator, never()).saveFolder(any(FolderTranslation.class), any(Session.class), anyString());
        verify(mockWorkflow).addTranslation(eq(targetLanguage), eq("name (" + targetLanguage + ")"));
    }

    @Test
    public void cloneSingleDocumentAndFolderStructure_half_translated() throws Exception {
        String targetLanguage = "fr";
        createMockTargetLocale(targetLanguage);
        populateMockFolders(6, 3, folders, targetLanguage);
        mockSaveFolder(true);

        translator.cloneSingleDocumentAndFolderStructure(folders, targetLocale, mockSession, mockWorkflow);

        // Should have saved the top 3 untranslated folders
        verify(translator, times(1)).saveFolder(same(folders.get(5)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(4)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(3)), any(Session.class), anyString());
        verify(mockWorkflow).addTranslation(eq(targetLanguage), eq("name (" + targetLanguage + ")"));
    }

    @Test
    public void cloneSingleDocumentAndFolderStructure_all_untranslated() throws Exception {
        String targetLanguage = "it";
        createMockTargetLocale(targetLanguage);
        //Note the root folder is always translated
        populateMockFolders(6, 5, folders, targetLanguage);
        mockSaveFolder(true);

        translator.cloneSingleDocumentAndFolderStructure(folders, targetLocale, mockSession, mockWorkflow);

        // Should have saved the top 3 untranslated folders
        verify(translator, times(1)).saveFolder(same(folders.get(5)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(4)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(3)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(2)), any(Session.class), anyString());
        verify(translator, times(1)).saveFolder(same(folders.get(1)), any(Session.class), anyString());
        verify(mockWorkflow).addTranslation(eq(targetLanguage), eq("name (" + targetLanguage + ")"));
    }

    @Test
    public void cloneSingleDocumentAndFolderStructure_saveFolder_fails() throws Exception {
        String targetLanguage = "it";
        createMockTargetLocale(targetLanguage);
        populateMockFolders(6, 3, folders, targetLanguage);
        mockSaveFolder(false);

        TranslationException ex = assertThrows(TranslationException.class,
                () -> translator.cloneSingleDocumentAndFolderStructure(folders, targetLocale, mockSession, mockWorkflow));
        assertEquals(DocumentTranslator.COULD_NOT_CREATE_FOLDERS, ex.getMessage());
        verify(mockWorkflow, never()).addTranslation(anyString(), anyString());
    }

    private void createMockTargetLocale(String targetLanguage) {
        targetLocale = mock(ILocaleProvider.HippoLocale.class);
        when(targetLocale.getName()).thenReturn(targetLanguage);
    }

    private void mockSaveFolder(boolean isSaved) {
        doReturn(isSaved).when(translator).saveFolder(any(FolderTranslation.class), any(Session.class), anyString());
    }

    private void populateMockFolders(final int numberOfFolders,
                                     final int numberUntranslated,
                                     final List<FolderTranslation> folders,
                                     final String targetLanguage) throws Exception {
        // Want to mock the return from the populateFolders function.
        // This should be a chain of FolderTranslation instances,
        // the lower indexes are the translated folders (not editable),
        // the upper indexes are the untranslated folders (editable).

        FolderTranslation nodeTranslation = mock(FolderTranslation.class);
        lenient().when(nodeTranslation.getNamefr()).thenReturn("name (" + targetLanguage + ")");
        folders.add(nodeTranslation);
        int untranslatedNeeded = numberUntranslated;
        for (int index = 0; index < numberOfFolders; index++) {
            FolderTranslation mockFolderTranslation = mock(FolderTranslation.class);
            folders.add(mockFolderTranslation);
            if (untranslatedNeeded > 0) {
                lenient().when(mockFolderTranslation.isEditable()).thenReturn(true);
                untranslatedNeeded--;
            } else {
                lenient().when(mockFolderTranslation.isEditable()).thenReturn(false);
            }
        }
        Collections.reverse(folders);
    }

    private void mockAvoidSameNameSiblings() throws Exception {
        doNothing().when(translator).avoidSameNameSiblings(any(Session.class), anyInt(), anyString(), anyList());
    }
}
