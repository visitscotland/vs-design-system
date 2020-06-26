package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorGetTranslatedDocumentNameTest {
    private DocumentTranslator translator;

    @BeforeEach
    public void beforeEach() {
        translator = new DocumentTranslator();
    }

    @Test
    public void getTranslatedDocumentName_null_list() {
        assertThrows(IllegalStateException.class,
                () -> {
                    translator.getTranslatedDocumentName(null);
                });
    }

    @Test
    public void getTranslatedDocumentName_empty_list() {
        assertThrows(IllegalStateException.class,
                () -> {
                    translator.getTranslatedDocumentName(new LinkedList<>());
                });
    }

    @Test
    public void getTranslatedDocumentName_valid_list() {
        String doumentName = "supercally";
        FolderTranslation mockDocTranslation = mock(FolderTranslation.class);
        when(mockDocTranslation.getNamefr()).thenReturn(doumentName);
        FolderTranslation mockFolder = mock(FolderTranslation.class);

        List<FolderTranslation> folders = new LinkedList<>();
        folders.add(mockFolder);
        folders.add(mockDocTranslation);

        assertEquals(doumentName, translator.getTranslatedDocumentName(folders));
    }
}
