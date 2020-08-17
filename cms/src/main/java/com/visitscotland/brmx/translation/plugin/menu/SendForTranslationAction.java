package com.visitscotland.brmx.translation.plugin.menu;

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
import org.hippoecm.frontend.plugins.standards.icon.HippoIconStack;
import org.hippoecm.frontend.service.IconSize;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.repository.api.WorkflowException;

import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.rmi.RemoteException;
import java.util.List;

public class SendForTranslationAction extends StdWorkflow<TranslationWorkflow> {
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
                    workflowPlugin.currentDocumentHasTranslation() &&
                    workflowPlugin.canTranslateModel() &&
                    workflowPlugin.isChangePending();
        }
        return false;
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
        // Mark the Node as Translation required if it has mixin visitscotland:translatable
        workflow.setTranslationRequiredFlag();
        return null;
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
