package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.Workflow;
import org.hippoecm.repository.api.WorkflowException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface TranslationWorkflow extends Workflow {
    Document addTranslation(String var1, String var2) throws WorkflowException, RepositoryException, RemoteException;

    Document addTranslation(String var1, String var2, Node sourceNode) throws WorkflowException, RepositoryException, RemoteException;

    void addTranslation(String var1, Document var2) throws WorkflowException, RepositoryException, RemoteException;

    SetTranslationRequiredResult setTranslationRequiredFlag() throws WorkflowException, RepositoryException, RemoteException;

    class SetTranslationRequiredResult {
        List<Node> nodesBlockingTranslation = new ArrayList<>();
        List<Node> flaggedNodes = new ArrayList<>();

        public SetTranslationRequiredResult() {}

        public SetTranslationRequiredResult(List<Node> failedCheckoutNodes, List<Node> flaggedNodes) {
            this.nodesBlockingTranslation.addAll(failedCheckoutNodes);
            this.flaggedNodes.addAll(flaggedNodes);
        }

        public List<Node> getNodesBlockingTranslation() {
            return nodesBlockingTranslation;
        }

        public List<Node> getFlaggedNodes() {
            return flaggedNodes;
        }
    }
}
