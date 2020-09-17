package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.resource.ResourceReference;
import org.hippoecm.addon.workflow.IWorkflowInvoker;
import org.hippoecm.addon.workflow.WorkflowDialog;
import org.hippoecm.frontend.plugins.standards.image.CachingImage;
import org.hippoecm.frontend.service.IconSize;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;

import javax.jcr.RepositoryException;
import java.util.Iterator;
import java.util.List;

public class SendForTranslationConfirmationDialog extends WorkflowDialog<Void> {
    private List<JcrDocument> documentsToConfirm;
    private ILocaleProvider localeProvider;

    public SendForTranslationConfirmationDialog(IWorkflowInvoker invoker, List<JcrDocument> documentsToConfirm, ILocaleProvider localeProvider) {
        super(invoker);
        this.documentsToConfirm = documentsToConfirm;
        this.localeProvider = localeProvider;

        setTitle("Send for translation");

        add(new DataView<JcrDocument>("change", getDocumentProvider()) {
            {
                onPopulate();
            }

            @Override
            protected void populateItem(Item<JcrDocument> item) {
                try {
                    JcrDocument itemDocument = item.getModelObject();
                    // In Bloomreach we can always cast to a HippoNode without checking the type
                    HippoNode node = (HippoNode)itemDocument.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
                    item.add(new Label("name", node.getDisplayName()));
                    String localeString = node.getProperty(HippoTranslationNodeType.LOCALE).getString();
                    ILocaleProvider.HippoLocale locale = localeProvider.getLocale(localeString);
                    ResourceReference resourceRef = locale.getIcon(IconSize.M, ILocaleProvider.LocaleState.EXISTS);
                    item.add(new CachingImage("img", resourceRef));
                } catch (RepositoryException ex) {
                    throw new RuntimeException("Error initalising dialog", ex);
                }
            }
        });
    }

    public void setTitle(String title) {
        setTitle(
            new AbstractReadOnlyModel<String>() {
                @Override
                public String getObject() {
                    return title;
                }
            });
    }

    public IDataProvider<JcrDocument> getDocumentProvider() {
        return new IDataProvider<JcrDocument>() {
            @Override
            public Iterator<? extends JcrDocument> iterator(long first, long count) {
                return documentsToConfirm.subList((int) first, (int) (first + count)).iterator();
            }

            @Override
            public long size() {
                return documentsToConfirm.size();
            }

            @Override
            public IModel<JcrDocument> model(JcrDocument object) {
                return new LoadableDetachableModel<JcrDocument>() {
                    private static final long serialVersionUID = 1L;

                    @Override
                    protected JcrDocument load() {
                        return object;
                    }

                };
            }

            @Override
            public void detach() { }
        };
    }
}
