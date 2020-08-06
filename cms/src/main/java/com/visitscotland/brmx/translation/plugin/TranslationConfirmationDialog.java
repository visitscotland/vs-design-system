package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.resource.ResourceReference;
import org.hippoecm.addon.workflow.IWorkflowInvoker;
import org.hippoecm.addon.workflow.WorkflowDialog;
import org.hippoecm.frontend.plugins.standards.image.CachingImage;
import org.hippoecm.frontend.service.IconSize;
import org.hippoecm.frontend.translation.ILocaleProvider;

public class TranslationConfirmationDialog extends WorkflowDialog<Void> {
    public static final String ID_IMG = "img";
    public static final String ID_NAME = "name";
    public static final String ID_LANGUAGES = "languages";
    public static final String ID_CHANGE = "change";

    public TranslationConfirmationDialog(IWorkflowInvoker invoker,
                                         IDataProvider<DocumentChangeProvider.Entry> changeSetProvider) {
        super(invoker);

        setTitle(new AbstractReadOnlyModel<String>() {

            @Override
            public String getObject() {
                return "Translation Clone Document";
            }
        });

        add(new DataView<DocumentChangeProvider.Entry>(ID_CHANGE, changeSetProvider) {
            {
                onPopulate();
            }

            @Override
            protected void populateItem(Item<DocumentChangeProvider.Entry> item) {
                DocumentChangeProvider.Entry change = item.getModelObject();
                item.add(new Label(ID_NAME, change.getDocumentName()));
                IDataProvider<ILocaleProvider.HippoLocale> localeProvider = new LocaleListProvider(change.getLocaleList());
                item.add(new DataView<ILocaleProvider.HippoLocale>(ID_LANGUAGES, localeProvider) {
                    {
                        onPopulate();
                    }

                    @Override
                    protected void populateItem(Item<ILocaleProvider.HippoLocale> localeItem) {
                        ILocaleProvider.HippoLocale locale = localeItem.getModelObject();
                        ResourceReference resourceRef = locale.getIcon(IconSize.M, ILocaleProvider.LocaleState.EXISTS);
                        localeItem.add(new CachingImage(ID_IMG, resourceRef));
                    }
                });

            }
        });
    }
}
