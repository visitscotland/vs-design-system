package com.visitscotland.brmx.translation;

import com.visitscotland.brmx.translation.plugin.JcrDocument;
import org.hippoecm.repository.api.Workflow;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import java.rmi.RemoteException;
import java.util.Locale;

public class NodeLockedOperation {
    private SessionFactory sessionFactory;

    public NodeLockedOperation(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void performOperationAndDispose(JcrDocument jcrDocument, Operation function) throws RepositoryException, RemoteException, WorkflowException {
        if (jcrDocument.isDraftBeingEdited()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Document being edited");
        }

        Workflow editing = sessionFactory.getUserSession().getWorkflowManager().getWorkflow("editing", jcrDocument.getHandle());
        if (editing instanceof EditableWorkflow) {
            EditableWorkflow editableWorkflow = null;
            try {
                editableWorkflow = (EditableWorkflow) editing;
                editableWorkflow.obtainEditableInstance();

                function.performOperation(jcrDocument);

            } catch(WorkflowException ex) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Unable to lock document for edit");
            } finally {
                if (null != editableWorkflow) {
                    editableWorkflow.disposeEditableInstance();
                }
            }
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to get EditableWorkflow");
        }
    }

    @FunctionalInterface
    public interface Operation {
        void performOperation(JcrDocument jcrDocument) throws RemoteException;
    }
}
