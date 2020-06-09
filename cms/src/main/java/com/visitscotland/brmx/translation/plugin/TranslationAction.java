package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.hippoecm.addon.workflow.StdWorkflow;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.frontend.dialog.IDialogService;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.frontend.translation.workflow.JcrFolderTranslationFactory;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.translation.TranslationWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

final class TranslationAction extends StdWorkflow<TranslationWorkflow> {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationAction.class);
    public static final String COULD_NOT_CREATE_FOLDERS = "could-not-create-folders";

    private TranslationWorkflowPlugin workflowPlugin;
    private final IModel<ILocaleProvider.HippoLocale> localeModel;

    private String url;

    private List<FolderTranslation> folders;

    private SessionFactory sessionFactory;
    private DocumentTranslator translator;

    TranslationAction(TranslationWorkflowPlugin workflowPlugin,
                      String id,
                      IModel<String> name,
                      IModel<ILocaleProvider.HippoLocale> localeModel) {
        this(workflowPlugin, id, name, localeModel, new SessionFactory(), new DocumentTranslator());
    }

    TranslationAction(TranslationWorkflowPlugin workflowPlugin,
                      String id,
                      IModel<String> name,
                      IModel<ILocaleProvider.HippoLocale> localeModel,
                      SessionFactory sessionFactory,
                      DocumentTranslator translator) {
        super(id, name, workflowPlugin.getPluginContext(), (WorkflowDescriptorModel) workflowPlugin.getModel());
        this.workflowPlugin = workflowPlugin;
        this.localeModel = localeModel;
        this.sessionFactory = sessionFactory;
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
    protected String execute(TranslationWorkflow workflow) throws WorkflowException, RepositoryException, RemoteException {
        Session session = sessionFactory.getJcrSession();

        // Only want to add languages we do not have a translation for
        List<ILocaleProvider.HippoLocale> untranslatedLocales = new ArrayList<>();
        for (String language : workflowPlugin.getAvailableLanguages()) {
            if (!workflowPlugin.hasLocaleTranslation(language)) {
                untranslatedLocales.add(workflowPlugin.getLocaleProvider().getLocale(language));
            }
        }

        for(ILocaleProvider.HippoLocale targetLocale : untranslatedLocales) {

            Node docNode = ((WorkflowDescriptorModel) workflowPlugin.getDefaultModel()).getNode();

            List<FolderTranslation> folders = new LinkedList<>();
            String result = translator.cloneTranslationFolderStructure(docNode, folders, targetLocale, session);
            if (result != null) {
                return result;
            }

            workflow.addTranslation(targetLocale.getName(), translator.getTranslatedDocumentName(folders));

        }
        return null;
    }

    @Override
    protected IDialogService.Dialog createRequestDialog() {
        return new TranslationConfirmationDialog(this, new UntranslatedLocaleProvider(workflowPlugin, workflowPlugin.getLocaleProvider()));
    }
}
