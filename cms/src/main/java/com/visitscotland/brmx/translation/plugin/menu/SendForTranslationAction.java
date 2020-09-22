package com.visitscotland.brmx.translation.plugin.menu;

import com.visitscotland.brmx.translation.SpringContext;
import com.visitscotland.brmx.translation.difference.DifferenceGenerator;
import com.visitscotland.brmx.translation.plugin.*;
import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.resource.PackageResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;
import org.hippoecm.addon.workflow.StdWorkflow;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.addon.workflow.WorkflowSNSException;
import org.hippoecm.frontend.dialog.ExceptionDialog;
import org.hippoecm.frontend.dialog.IDialogService;
import org.hippoecm.frontend.model.JcrNodeModel;
import org.hippoecm.frontend.plugins.standards.icon.HippoIconStack;
import org.hippoecm.frontend.service.IBrowseService;
import org.hippoecm.frontend.service.IconSize;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.diagnosis.HDC;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.rmi.RemoteException;
import java.util.List;

import static com.visitscotland.brmx.translation.plugin.TranslationWorkflow.VS_TRANSLATABLE;

public class SendForTranslationAction extends StdWorkflow<TranslationWorkflow> {
    private static final Logger logger = LoggerFactory.getLogger(SendForTranslationAction.class);
    public static final String MENU_TEXT = "Send for translation";
    private TranslationWorkflowPlugin workflowPlugin;

    public SendForTranslationAction(TranslationWorkflowPlugin workflowPlugin,
                                       String id) {
        super(id, new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                return MENU_TEXT;
            }
        }, workflowPlugin.getPluginContext(), (WorkflowDescriptorModel) workflowPlugin.getModel());
        this.workflowPlugin = workflowPlugin;
    }

    @Override
    public boolean isVisible() {
        if (super.isVisible() && findPage() != null) {
            return "en".equals(workflowPlugin.getCurrentlySelectedDocumentLocale()) &&
                    isSourceNodeTranslatable(workflowPlugin) &&
                    workflowPlugin.currentDocumentHasTranslation() &&
                    workflowPlugin.canTranslateModel() &&
                    workflowPlugin.isChangePending();
        }
        return false;
    }

    protected boolean isSourceNodeTranslatable(TranslationWorkflowPlugin plugin) {
        try {
            return plugin.getSourceDocumentNode().isNodeType(VS_TRANSLATABLE);
        } catch (RepositoryException ex) {
            return false;
        }
    }

    @Override
    protected Component getIcon(final String id) {
        ResourceReference flagIcon = new PackageResourceReference(TranslationWorkflowPlugin.class, "translations-16.png");
        final HippoIconStack nodeIcon = new HippoIconStack(id, IconSize.M);
        nodeIcon.addFromResource(flagIcon);
        return nodeIcon;
    }

    @Override
    protected String execute(TranslationWorkflow workflow)
            throws WorkflowException, RepositoryException, RemoteException, ObjectBeanManagerException {
        List<JcrDocument> documentsBlockingTranslation = workflow.setTranslationRequiredFlag();
        // If there are any documents blocking we want to show that here
        if (!documentsBlockingTranslation.isEmpty()) {
            workflowPlugin.getPluginContext().getService(IDialogService.class.getName(), IDialogService.class).show(
                    new SendForTranslationBlockedDialog(documentsBlockingTranslation, workflowPlugin.getLocaleProvider()));
        }
        return null;
    }

    @Override
    protected IDialogService.Dialog createRequestDialog() {
        // Get a list of the documents to be sent for translation
        // Or a list of the documents blocking translation
        try {
            List<JcrDocument> documentBlockingTranslation = workflowPlugin.getDocumentsBlockingTranslation();
            if (!documentBlockingTranslation.isEmpty()) {
                // Create transaction blocked dialog
                return new SendForTranslationBlockedDialog(documentBlockingTranslation, workflowPlugin.getLocaleProvider());
            }

            List<JcrDocument> documentTranslations = workflowPlugin.getCurrentDocumentTranslations();
            // Create confirmation dialog
            return new SendForTranslationConfirmationDialog(this, documentTranslations, workflowPlugin.getLocaleProvider());
        } catch(RepositoryException ex) {
            return new ExceptionDialog(ex);
        }
    }

    protected void clearSuperVisible() {
        // This is only intended for testing purposes, clears the parent's default visible flag
        setFlag(0x0010, false);
    }

    protected String getStdWorkflowName() {
        // This is only intended for testing purposes, the getName is protected and not visible from tests
        return getName();
    }
}
