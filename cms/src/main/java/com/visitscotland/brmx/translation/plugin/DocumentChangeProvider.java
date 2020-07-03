package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.hippoecm.frontend.translation.ILocaleProvider;

import java.util.*;
import java.util.stream.Collectors;

public class DocumentChangeProvider implements IDataProvider<DocumentChangeProvider.Entry> {
    protected List<Entry> documentChangeList;

    public DocumentChangeProvider(List<ChangeSet> changeSetList) {
        documentChangeList = buildDocumentChangeMap(changeSetList);
    }

    protected List<Entry> buildDocumentChangeMap(List<ChangeSet> changeSetList) {
        // Need to convert the List<ChangeSet> into a list of unique document names and the languages they will
        // cloned into
        Map<String, List<ILocaleProvider.HippoLocale>> documentLocaleMap = new HashMap<>();
        for (ChangeSet change : changeSetList) {
            ILocaleProvider.HippoLocale targetLocale = change.getTargetLocale();
            for (FolderTranslation document : change.getDocuments()) {
                List<ILocaleProvider.HippoLocale> localeList = documentLocaleMap.get(document.getName());
                if (null == localeList) {
                    localeList = new ArrayList<>();
                    documentLocaleMap.put(document.getName(), localeList);
                }
                localeList.add(targetLocale);
            }
        }
        return documentLocaleMap.entrySet().stream().map((entry) -> new Entry(entry)).collect(Collectors.toList());
    }

    @Override
    public Iterator<? extends Entry> iterator(long first, long count) {
        return documentChangeList.subList((int) first, (int) (first + count)).iterator();
    }

    @Override
    public long size() {
        return documentChangeList.size();
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
        private String documentName;
        private List<ILocaleProvider.HippoLocale> localeList;

        Entry(Map.Entry<String, List<ILocaleProvider.HippoLocale>> entry) {
            this.documentName = entry.getKey();
            this.localeList = entry.getValue();
        }

        public String getDocumentName() {
            return documentName;
        }

        public List<ILocaleProvider.HippoLocale> getLocaleList() {
            return localeList;
        }
    }
}
