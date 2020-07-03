package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.hippoecm.frontend.translation.ILocaleProvider;

import javax.jcr.RepositoryException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SameNameSiblingProvider implements IDataProvider<SameNameSiblingProvider.Entry> {
    private List<Entry> entryList;

    public SameNameSiblingProvider(List<ChangeSet> changeSetList) throws RepositoryException {
        this.entryList = new ArrayList<>();

        for (ChangeSet changeSet : changeSetList) {
            ILocaleProvider.HippoLocale targetLocale = changeSet.getTargetLocale();
            entryList.addAll(
                changeSet.getFolders().stream()
                        .filter((folder) -> folder.hasSameNameSibling() || folder.hasSameUrlSibling())
                        .map((folder) -> new Entry(targetLocale, folder))
                        .collect(Collectors.toList())
            );
            entryList.addAll(
                changeSet.getDocuments().stream()
                        .filter((document) -> document.hasSameNameSibling() || document.hasSameUrlSibling())
                        .map((document) -> new Entry(targetLocale, document))
                        .collect(Collectors.toList())
            );
        }
    }

    protected List<Entry> getEntryList() {
        return entryList;
    }

    @Override
    public Iterator<? extends Entry> iterator(long first, long count) {
        return entryList.subList((int) first, (int) (first + count)).iterator();
    }

    @Override
    public long size() {
        return entryList.size();
    }

    @Override
    public IModel<Entry> model(Entry object) {
        return new LoadableDetachableModel<Entry>() {
            private static final long serialVersionUID = 1L;

            @Override
            protected Entry load() {
                return object;
            }

        };
    }

    @Override
    public void detach() {

    }

    public class Entry {
        private ILocaleProvider.HippoLocale locale;
        private FolderTranslation folderTranslation;

        protected Entry(ILocaleProvider.HippoLocale locale, FolderTranslation folderTranslation) {
            this.locale = locale;
            this.folderTranslation = folderTranslation;
        }

        public ILocaleProvider.HippoLocale getLocale() {
            return locale;
        }

        public FolderTranslation getFolderTranslation() {
            return folderTranslation;
        }
    }
}
