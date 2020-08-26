package com.visitscotland.brmx.translation.plugin;

import com.visitscotland.brmx.beans.Day;
import com.visitscotland.brmx.beans.Itinerary;
import com.visitscotland.brmx.beans.TranslationLinkContainer;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.repository.api.HippoNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChangeSetAddDocumentTest {
    private ChangeSet changeSet;
    @Mock
    private ILocaleProvider.HippoLocale mockLocale;

    @BeforeEach
    public void beforeEach() {
        changeSet = new ChangeSet(mockLocale);
    }

    @Test
    public void addDocument() throws Exception {
        HippoNode mockHandle = mock(HippoNode.class);
        when(mockHandle.getName()).thenReturn("docUrl");
        when(mockHandle.getDisplayName()).thenReturn("docName");
        JcrDocument mockDocument = mock(JcrDocument.class);
        when(mockDocument.getHandle()).thenReturn(mockHandle);
        when(mockDocument.asHippoBean()).thenReturn(mock(HippoBean.class));

        when(mockLocale.getName()).thenReturn("locale");

        changeSet.addDocument(mockDocument);

        assertFalse(changeSet.getDocuments().isEmpty());
        FolderTranslation translation = changeSet.getDocuments().get(0);
        assertEquals("docUrl", translation.getUrl());
        assertEquals("docUrl", translation.getUrlfr());
        assertEquals("docName", translation.getName());
        assertEquals("docName (LOCALE)", translation.getNamefr());
        assertFalse(translation.containsTranslationLinks());
    }

    @Test
    public void addDocument_isTranslationLinkContainer() throws Exception {
        HippoNode mockHandle = mock(HippoNode.class);
        when(mockHandle.getName()).thenReturn("docUrl");
        when(mockHandle.getDisplayName()).thenReturn("docName");
        JcrDocument mockDocument = mock(JcrDocument.class);
        when(mockDocument.getHandle()).thenReturn(mockHandle);
        when(mockDocument.asHippoBean()).thenReturn(mock(Day.class));

        when(mockLocale.getName()).thenReturn("locale");

        changeSet.addDocument(mockDocument);

        assertFalse(changeSet.getDocuments().isEmpty());
        FolderTranslation translation = changeSet.getDocuments().get(0);
        assertEquals("docUrl", translation.getUrl());
        assertEquals("docUrl", translation.getUrlfr());
        assertEquals("docName", translation.getName());
        assertEquals("docName (LOCALE)", translation.getNamefr());
        assertTrue(translation.containsTranslationLinks());
    }
}
