package com.visitscotland.brmx.translation.plugin.menu;

import com.visitscotland.brmx.translation.plugin.*;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.hippoecm.addon.workflow.StdWorkflow;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.addon.workflow.WorkflowSNSException;
import org.hippoecm.frontend.dialog.ExceptionDialog;
import org.hippoecm.frontend.dialog.IDialogService;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.repository.api.WorkflowException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.rmi.RemoteException;
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
            throws WorkflowException, RepositoryException, RemoteException, ObjectBeanManagerException {
        Session session = getJcrSession();

        //Build a change set for the folders and documents that are missing
        List<ChangeSet> changeSetList = translator.buildChangeSetList(workflowPlugin.getSourceDocumentNode(),
                workflowPlugin.getAvailableLocales());

        try {
            translator.applyChangeSet(changeSetList, session, workflow);
        } catch (TranslationException ex) {
            return ex.getMessage();
        }
        return null;
    }

    @Override
    protected IDialogService.Dialog createRequestDialog() {
        try {
            List<ChangeSet> changeSetList = translator.buildChangeSetList(workflowPlugin.getSourceDocumentNode(),
                    workflowPlugin.getAvailableLocales());

            boolean haveSameNameSiblings = false;
            for (ChangeSet changeSet : changeSetList) {
                changeSet.markSameNameSiblings(getJcrSession());
                if (changeSet.hasSameNameSiblingConflicts()) {
                    haveSameNameSiblings = true;
                }
            }
            if (haveSameNameSiblings) {
                SameNameSiblingProvider provider = new SameNameSiblingProvider(changeSetList,
                        workflowPlugin.getSession().getJcrSession());
                return new SameNameSiblingDialog(provider);
            }
            return new TranslationConfirmationDialog(this, new DocumentChangeProvider(changeSetList));
        } catch (ObjectBeanManagerException | WorkflowSNSException | RepositoryException ex) {
            return new ExceptionDialog(ex);
        }
    }

    protected Session getJcrSession() {
        return UserSession.get().getJcrSession();
    }
}
