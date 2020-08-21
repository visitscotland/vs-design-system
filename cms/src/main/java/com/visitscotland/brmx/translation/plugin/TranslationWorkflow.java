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
    String VS_TRANSLATABLE = "visitscotland:translatable";

    Document addTranslation(String var1, String var2) throws WorkflowException, RepositoryException, RemoteException;

    Document addTranslation(String var1, String var2, Node sourceNode) throws WorkflowException, RepositoryException, RemoteException;

    void addTranslation(String var1, Document var2) throws WorkflowException, RepositoryException, RemoteException;

    List<JcrDocument> setTranslationRequiredFlag() throws WorkflowException, RepositoryException, RemoteException;

}
