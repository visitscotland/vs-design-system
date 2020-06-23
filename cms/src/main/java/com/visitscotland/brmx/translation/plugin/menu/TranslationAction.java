package com.visitscotland.brmx.translation.plugin.menu;

import com.visitscotland.brmx.translation.plugin.*;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.hippoecm.addon.workflow.StdWorkflow;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.frontend.dialog.IDialogService;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.translation.TranslationWorkflow;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TranslationAction extends StdWorkflow<TranslationWorkflow> {
    private final IModel<ILocaleProvider.HippoLocale> localeModel;
    private TranslationWorkflowPlugin workflowPlugin;
    private DocumentTranslator translator;

    public TranslationAction(TranslationWorkflowPlugin workflowPlugin,
                             String id,
                             IModel<String> name,
                             IModel<ILocaleProvider.HippoLocale> localeModel) {
        this(workflowPlugin, id, name, localeModel, new DocumentTranslator());
    }

    TranslationAction(TranslationWorkflowPlugin workflowPlugin,
                      String id,
                      IModel<String> name,
                      IModel<ILocaleProvider.HippoLocale> localeModel,
                      DocumentTranslator translator) {
        super(id, name, workflowPlugin.getPluginContext(), (WorkflowDescriptorModel) workflowPlugin.getModel());
        this.workflowPlugin = workflowPlugin;
        this.localeModel = localeModel;
        this.translator = translator;
    }

    @Override
    public boolean isVisible() {
        if (super.isVisible() && findPage() != null) {
            return workflowPlugin.canTranslateModel();
        }
        return false;
    }

    @Override
    protected Component getIcon(final String id) {
        return workflowPlugin.getActionIcon(id, localeModel);
    }

    @Override
    protected String execute(TranslationWorkflow workflow)
            throws WorkflowException, RepositoryException, RemoteException, QueryException {
        Session session = getJcrSession();

        // Only want to add languages we do not have a translation for
        List<ILocaleProvider.HippoLocale> untranslatedLocales = new ArrayList<>();
        for (String language : workflowPlugin.getAvailableLanguages()) {
            if (!workflowPlugin.hasLocaleTranslation(language)) {
                untranslatedLocales.add(workflowPlugin.getLocaleProvider().getLocale(language));
            }
        }

        for (ILocaleProvider.HippoLocale targetLocale : untranslatedLocales) {

            Node docNode = ((WorkflowDescriptorModel) workflowPlugin.getDefaultModel()).getNode();

            try {
                translator.cloneDocumentAndFolderStructure(docNode, targetLocale, session, workflow);
            } catch(TranslationException ex) {
                return ex.getMessage();
            }
        }
        return null;
    }

    @Override
    protected IDialogService.Dialog createRequestDialog() {
        return new TranslationConfirmationDialog(this, new UntranslatedLocaleProvider(workflowPlugin, workflowPlugin.getLocaleProvider()));
    }

    protected Session getJcrSession() {
        return UserSession.get().getJcrSession();
    }
}
