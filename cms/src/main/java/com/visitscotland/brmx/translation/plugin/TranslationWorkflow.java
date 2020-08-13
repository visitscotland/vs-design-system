package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.Workflow;
import org.hippoecm.repository.api.WorkflowException;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.rmi.RemoteException;

public interface TranslationWorkflow extends Workflow {
    Document addTranslation(String var1, String var2) throws WorkflowException, RepositoryException, RemoteException;

    Document addTranslation(String var1, String var2, Node sourceNode) throws WorkflowException, RepositoryException, RemoteException;

    void addTranslation(String var1, Document var2) throws WorkflowException, RepositoryException, RemoteException;

    void setTranslationRequiredFlag() throws RepositoryException;
}
