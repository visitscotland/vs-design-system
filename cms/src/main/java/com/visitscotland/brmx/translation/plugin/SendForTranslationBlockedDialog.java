package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.util.string.Strings;
import org.hippoecm.frontend.dialog.Dialog;
import org.hippoecm.frontend.dialog.ExceptionDialog;
import org.hippoecm.frontend.plugins.standards.image.CachingImage;
import org.hippoecm.frontend.service.IconSize;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;

import javax.jcr.RepositoryException;
import java.util.Iterator;
import java.util.List;

public class SendForTranslationBlockedDialog extends Dialog {
    public static final String TITLE = "Unable to send for translation";
    public static final String MESSAGE = "The following documents are being edited. Unable to send for translation until all documents are not being edited";
    private List<JcrDocument> documentsBlocking;
    private ILocaleProvider localeProvider;

    public SendForTranslationBlockedDialog(List<JcrDocument> documentsBlocking, ILocaleProvider localeProvider) {
        this.documentsBlocking = documentsBlocking;
        this.localeProvider = localeProvider;
        setTitle(new AbstractReadOnlyModel<String>() {

            @Override
            public String getObject() {
                return TITLE;
            }
        });

        add(new Label("message", new AbstractReadOnlyModel<String>() {

            @Override
            public String getObject() {
                return MESSAGE;
            }

        }));
        setCancelVisible(false);

        add(new DataView<JcrDocument>("change", getDocumentProvider()) {
            {
                onPopulate();
            }

            @Override
            protected void populateItem(Item<JcrDocument> item) {
                try {
                    JcrDocument itemDocument = item.getModelObject();
                    // In Bloomreach we can always cast to a HippoNode without checking the type
                    HippoNode node = (HippoNode)itemDocument.getVariantNode(JcrDocument.VARIANT_DRAFT);
                    item.add(new Label("name", node.getDisplayName()));
                    String localeString = node.getProperty(HippoTranslationNodeType.LOCALE).getString();
                    ILocaleProvider.HippoLocale locale = localeProvider.getLocale(localeString);
                    ResourceReference resourceRef = locale.getIcon(IconSize.M, ILocaleProvider.LocaleState.EXISTS);
                    item.add(new CachingImage("img", resourceRef));
                    item.add(new Label("username", node.getProperty(HippoStdNodeType.HIPPOSTD_HOLDER).getString()));
                } catch (RepositoryException ex) {
                    throw new RuntimeException("Error initalising dialog", ex);
                }
            }
        });
    }

    public IDataProvider<JcrDocument> getDocumentProvider() {
        return new IDataProvider<JcrDocument>() {
            @Override
            public Iterator<? extends JcrDocument> iterator(long first, long count) {
                return documentsBlocking.subList((int) first, (int) (first + count)).iterator();
            }

            @Override
            public long size() {
                return documentsBlocking.size();
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
