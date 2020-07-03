package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.request.resource.ResourceReference;
import org.hippoecm.addon.workflow.IWorkflowInvoker;
import org.hippoecm.addon.workflow.WorkflowDialog;
import org.hippoecm.frontend.plugins.standards.image.CachingImage;
import org.hippoecm.frontend.service.IconSize;
import org.hippoecm.frontend.translation.ILocaleProvider;

public class TranslationConfirmationDialog extends WorkflowDialog<Void> {
    public static final String ID_IMG = "img";
    public static final String ID_NAME = "name";

    public TranslationConfirmationDialog(IWorkflowInvoker invoker,
                                         IDataProvider<ILocaleProvider.HippoLocale> languageList) {
        super(invoker);
        add(new DataView<ILocaleProvider.HippoLocale>(TranslationWorkflowPlugin.ID_LANGUAGES, languageList) {
            {
                onPopulate();
            }

            @Override
            protected void populateItem(Item<ILocaleProvider.HippoLocale> item) {
                ILocaleProvider.HippoLocale locale = item.getModelObject();
                ResourceReference resourceRef = locale.getIcon(IconSize.M, ILocaleProvider.LocaleState.EXISTS);
                item.add(new CachingImage(ID_IMG, resourceRef));
                item.add(new Label(ID_NAME, locale.getDisplayName(getLocale())));
            }
        });
    }
}
