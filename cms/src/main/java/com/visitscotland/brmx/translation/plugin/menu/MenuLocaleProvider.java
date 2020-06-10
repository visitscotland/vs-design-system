package com.visitscotland.brmx.translation.plugin.menu;

import com.visitscotland.brmx.translation.plugin.TranslationWorkflowPlugin;
import com.visitscotland.brmx.translation.plugin.UntranslatedLocale;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.hippoecm.frontend.translation.ILocaleProvider;

import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MenuLocaleProvider implements IDataProvider<ILocaleProvider.HippoLocale> {
    private TranslationWorkflowPlugin workflowPlugin;
    private final ILocaleProvider localeProvider;
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
        }
    }

    @Override
    public Iterator<? extends ILocaleProvider.HippoLocale> iterator(long first, long count) {
        if (availableLocales == null) {
            load();
        }
        return availableLocales.subList((int) first, (int)(first + count)).iterator();
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
