package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.model.IModel;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SameNameSiblingProviderTest {
    @Test
    public void constructor_emptyChangeSetList() throws Exception {
        SameNameSiblingProvider provider = new SameNameSiblingProvider(new ArrayList<>());
        assertNotNull(provider.getEntryList());
        assertTrue(provider.getEntryList().isEmpty());
    }

    @Test
    public void constructor_withFolderAndDocuments() throws Exception {
        List<ChangeSet> changeSetList = new ArrayList<>();

        ChangeSet mockChangeSet1 = mock(ChangeSet.class);
        ILocaleProvider.HippoLocale mockLocale1 = mock(ILocaleProvider.HippoLocale.class);
        when(mockChangeSet1.getTargetLocale()).thenReturn(mockLocale1);
        List<FolderTranslation> changeSet1Folders = new ArrayList<>();
        when(mockChangeSet1.getFolders()).thenReturn(changeSet1Folders);
        List<FolderTranslation> changeSet1Documents = new ArrayList<>();
        when(mockChangeSet1.getDocuments()).thenReturn(changeSet1Documents);
        changeSetList.add(mockChangeSet1);

        ChangeSet mockChangeSet2 = mock(ChangeSet.class);
        ILocaleProvider.HippoLocale mockLocale2 = mock(ILocaleProvider.HippoLocale.class);
        when(mockChangeSet2.getTargetLocale()).thenReturn(mockLocale2);
        List<FolderTranslation> changeSet2Folders = new ArrayList<>();
        when(mockChangeSet2.getFolders()).thenReturn(changeSet2Folders);
        List<FolderTranslation> changeSet2Documents = new ArrayList<>();
        when(mockChangeSet2.getDocuments()).thenReturn(changeSet2Documents);
        changeSetList.add(mockChangeSet2);

        FolderTranslation folder1 = new FolderTranslation("folder1");
        FolderTranslation folder2 = new FolderTranslation("folder2");
        folder2.setHasSameNameSibling(true);
        FolderTranslation folder3 = new FolderTranslation("folder3");
        folder3.setHasSameUrlSibling(true);
        FolderTranslation folder4 = new FolderTranslation("folder4");
        folder4.setHasSameNameSibling(true);
        folder4.setHasSameUrlSibling(true);

        changeSet2Folders.add(folder1);
        changeSet2Folders.add(folder2);
        changeSet2Folders.add(folder3);
        changeSet2Folders.add(folder4);

        ChangeSet mockChangeSet3 = mock(ChangeSet.class);
        ILocaleProvider.HippoLocale mockLocale3 = mock(ILocaleProvider.HippoLocale.class);
        when(mockChangeSet3.getTargetLocale()).thenReturn(mockLocale3);
        List<FolderTranslation> changeSet3Folders = new ArrayList<>();
        when(mockChangeSet3.getFolders()).thenReturn(changeSet3Folders);
        List<FolderTranslation> changeSet3Documents = new ArrayList<>();
        when(mockChangeSet3.getDocuments()).thenReturn(changeSet3Documents);
        changeSetList.add(mockChangeSet3);

        FolderTranslation document1 = new FolderTranslation("doc1");
        FolderTranslation document2 = new FolderTranslation("doc2");
        document2.setHasSameNameSibling(true);
        FolderTranslation document3 = new FolderTranslation("doc3");
        document3.setHasSameUrlSibling(true);
        FolderTranslation document4 = new FolderTranslation("doc4");
        document4.setHasSameUrlSibling(true);
        document4.setHasSameNameSibling(true);

        changeSet3Documents.add(document1);
        changeSet3Documents.add(document2);
        changeSet3Documents.add(document3);
        changeSet3Documents.add(document4);

        SameNameSiblingProvider provider = new SameNameSiblingProvider(changeSetList);

        assertNotNull(provider.getEntryList());
        assertEquals(6, provider.getEntryList().size());

        assertTrue(provider.getEntryList().stream()
                .anyMatch((entry) -> entry.getFolderTranslation().getId().equals("folder2")));
        assertTrue(provider.getEntryList().stream()
                .anyMatch((entry) -> entry.getFolderTranslation().getId().equals("folder3")));
        assertTrue(provider.getEntryList().stream()
                .anyMatch((entry) -> entry.getFolderTranslation().getId().equals("folder4")));

        assertTrue(provider.getEntryList().stream()
                .anyMatch((entry) -> entry.getFolderTranslation().getId().equals("doc2")));
        assertTrue(provider.getEntryList().stream()
                .anyMatch((entry) -> entry.getFolderTranslation().getId().equals("doc3")));
        assertTrue(provider.getEntryList().stream()
                .anyMatch((entry) -> entry.getFolderTranslation().getId().equals("doc4")));

        provider.getEntryList().stream()
                .filter((entry) -> entry.getFolderTranslation().getId().startsWith("folder"))
                .forEach((entry) -> assertSame(mockLocale2, entry.getLocale()));

        provider.getEntryList().stream()
                .filter((entry) -> entry.getFolderTranslation().getId().startsWith("doc"))
                .forEach((entry) -> assertSame(mockLocale3, entry.getLocale()));
    }

    @Test
    public void model() throws Exception {
        SameNameSiblingProvider provider = new SameNameSiblingProvider(new ArrayList<>());
        SameNameSiblingProvider.Entry entry = provider.new Entry(mock(ILocaleProvider.HippoLocale.class),
                new FolderTranslation("id"));

        IModel<SameNameSiblingProvider.Entry> model = provider.model(entry);

        assertSame(entry, model.getObject());
    }
}
