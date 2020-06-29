package com.visitscotland.brmx.translation.plugin.menu;

import com.visitscotland.brmx.translation.plugin.ChangeSet;
import com.visitscotland.brmx.translation.plugin.DocumentTranslator;
import com.visitscotland.brmx.translation.plugin.TranslationWorkflowPlugin;
import com.visitscotland.brmx.translation.plugin.UntranslatedLocale;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MenuLocaleProvider implements IDataProvider<ILocaleProvider.HippoLocale> {
    private static final Logger LOG = LoggerFactory.getLogger(MenuLocaleProvider.class);
    private final ILocaleProvider localeProvider;
    private TranslationWorkflowPlugin workflowPlugin;
    private transient List<ILocaleProvider.HippoLocale> availableLocales;

    public MenuLocaleProvider(TranslationWorkflowPlugin workflowPlugin) {
        this.workflowPlugin = workflowPlugin;
        this.localeProvider = workflowPlugin.getLocaleProvider();
    }

    List<ILocaleProvider.HippoLocale> getAvailableLocales() {
        return availableLocales;
    }

    void load() {
        availableLocales = new LinkedList<>();
        String documentLocale = workflowPlugin.getCurrentlySelectedDocumentLocale();
        // Only want to add languages we have a translation for
        boolean hasMissingTranslation = false;
        for (String language : workflowPlugin.getAvailableLanguages()) {
            if (workflowPlugin.hasLocaleTranslation(language)) {
                // Don't add the current language to the menu
                if (!documentLocale.equals(language)) {
                    availableLocales.add(localeProvider.getLocale(language));
                }
            } else {
                hasMissingTranslation = true;
            }
        }
        availableLocales.sort(Comparator.comparing(o -> o.getDisplayName(workflowPlugin.getLocale())));
        if (hasMissingTranslation) {
            availableLocales.add(0, new UntranslatedLocale());
        } else {
            try {
                // Build a change set to check if there are any untranslated children
                // A change set is only added to the list if it or one of it's children is missing a translation
                DocumentTranslator translator = new DocumentTranslator();

                final ILocaleProvider localeProvider = workflowPlugin.getLocaleProvider();
                List<ILocaleProvider.HippoLocale> localeList = workflowPlugin.getAvailableLanguages().stream()
                        .map(name -> localeProvider.getLocale(name)).collect(Collectors.toList());

                List<ChangeSet> changeSetList = translator.buildChangeSetList(workflowPlugin.getSourceDocumentNode(), localeList);

                if (!changeSetList.isEmpty()) {
                    availableLocales.add(0, new UntranslatedLocale());
                }
            } catch(ObjectBeanManagerException | RepositoryException ex) {
                LOG.error("Unable to build change set for document children");
            }
        }
    }

    @Override
    public Iterator<? extends ILocaleProvider.HippoLocale> iterator(long first, long count) {
        if (availableLocales == null) {
            load();
        }
        return availableLocales.subList((int) first, (int) (first + count)).iterator();
    }

    @Override
    public IModel<ILocaleProvider.HippoLocale> model(ILocaleProvider.HippoLocale object) {
        return new LoadableDetachableModel<ILocaleProvider.HippoLocale>() {
            private static final long serialVersionUID = 1L;

            @Override
            protected ILocaleProvider.HippoLocale load() {
                return object;
            }

        };
    }

    @Override
    public long size() {
        if (availableLocales == null) {
            load();
        }
        return availableLocales.size();
    }

    public void detach() {
        availableLocales = null;
    }
}
