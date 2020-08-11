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
    private TranslationWorkflowPlugin workflowPlugin;
    private DocumentTranslator translator;

    public SendForTranslationAction(TranslationWorkflowPlugin workflowPlugin,
                                    String id) {
        this(workflowPlugin, id, new DocumentTranslator());
    }

    SendForTranslationAction(TranslationWorkflowPlugin workflowPlugin,
                             String id,
                             DocumentTranslator translator) {
        super(id, new LoadableDetachableModel<String>() {
            @Override
            protected String load() {
                return "Send for translation";
            }
        }, workflowPlugin.getPluginContext(), (WorkflowDescriptorModel) workflowPlugin.getModel());
        this.workflowPlugin = workflowPlugin;
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
        ResourceReference flagIcon = new PackageResourceReference(TranslationWorkflowPlugin.class, "translations-16.png");
        final HippoIconStack nodeIcon = new HippoIconStack(id, IconSize.M);
        nodeIcon.addFromResource(flagIcon);
        return nodeIcon;
    }

    @Override
    protected String execute(TranslationWorkflow workflow)
            throws WorkflowException, RepositoryException, RemoteException, ObjectBeanManagerException {
        // TODO need to decide what sending for translation actually means
        return null;
    }
}
