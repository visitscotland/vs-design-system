package com.visitscotland.brmx.translation.plugin;

import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.TranslationParent;
import org.hippoecm.addon.workflow.WorkflowSNSException;
import org.hippoecm.frontend.plugins.standardworkflow.validators.SameNameSiblingsUtil;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.frontend.translation.workflow.JcrFolderTranslationFactory;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.HippoWorkspace;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.api.WorkflowManager;
import org.hippoecm.repository.standardworkflow.DefaultWorkflow;
import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.hippoecm.repository.translation.TranslationWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import javax.jcr.nodetype.NodeType;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class DocumentTranslator {
    public static final String COULD_NOT_CREATE_FOLDERS = "could-not-create-folders";
    private static final Logger LOG = LoggerFactory.getLogger(DocumentTranslator.class);

    public void cloneDocumentAndFolderStructure(Node docNode,
                                                ILocaleProvider.HippoLocale targetLocale,
                                                Session session,
                                                TranslationWorkflow workflow)
            throws TranslationException, WorkflowException, RepositoryException, RemoteException, ObjectBeanManagerException {
        ChangeSet change = new ChangeSet(targetLocale);
        JcrDocument document = new JcrDocument(docNode);
        change.addDocument(document);
        change.populateFolders(document);
        if (document.isNodeType(Page.JCR_TYPE)) {
            // Convert the node to a HippoBean so we can check the type
            HippoBean bean = document.asHippoBean();
            if (bean instanceof TranslationParent) {
                TranslationParent parent = (TranslationParent)bean;
                String[] childJcrTypes = parent.getChildJcrTypes();
                Node containingFolder = document.getContainingFolder();
                // Getting the nodes that are in the folder will give us all the folders and hippo:handle
                // nodes in the folder. We want to convert these into the unpublished versions of the nodes.
                NodeIterator handleIterator = containingFolder.getNodes();
                while(handleIterator.hasNext()) {
                    JcrDocument siblingDocument = new JcrDocument(handleIterator.nextNode());
                    if (siblingDocument.isNodeType(childJcrTypes)) {
                        change.addDocument(siblingDocument);
                    }
                }
            }
        }

        applyChangeSet(change, session, workflow);
    }

    public void applyChangeSet(ChangeSet change,
                               Session session,
                               TranslationWorkflow workflow)
            throws TranslationException, WorkflowException, RepositoryException, RemoteException {
        // working from index zero work our way up the folders creating them as we go
        // there must always be a root folder and it always exists so skipping to index 1
        List<FolderTranslation> folders = change.getFolders();
        change.checkForSameNameSiblings(session);
        for (int index = 1; index < folders.size(); index++) {
            FolderTranslation translation = folders.get(index);
            if (translation.isEditable()) {
                if (!saveFolder(translation, session, change.getTargetLocale().getName())) {
                    throw new TranslationException(COULD_NOT_CREATE_FOLDERS);
                }
            }
        }

        for (FolderTranslation document : change.getDocuments()) {
            workflow.addTranslation(change.getTargetLocale().getName(), document.getNamefr());
        }
    }

    protected boolean saveFolder(FolderTranslation ft, Session session, String targetLanguage) {
        if (!ft.isEditable()) {
            throw new UnsupportedOperationException("Translation is immutable");
        }
        String id = ft.getId();
        try {
            Node node = session.getNodeByIdentifier(id);
            WorkflowManager manager = ((HippoWorkspace) node.getSession().getWorkspace()).getWorkflowManager();
            TranslationWorkflow tw = (TranslationWorkflow) manager.getWorkflow("translation", node);
            String namefr = ft.getNamefr();
            String urlfr = ft.getUrlfr();
            Document translationDoc = tw.addTranslation(targetLanguage, urlfr);
            if (namefr != null && !urlfr.equals(namefr)) {
                DefaultWorkflow defaultWorkflow = (DefaultWorkflow) manager.getWorkflow("core", translationDoc);
                defaultWorkflow.setDisplayName(namefr);
            }
            return true;
        } catch (RepositoryException e) {
            LOG.error("Could not persist folder translation for {}", id, e);
        } catch (RemoteException e) {
            LOG.error("Could not contact repository when storing folder translation for {}", id, e);
        } catch (WorkflowException e) {
            LOG.error("Workflow prevented storing translation for {}", id, e);
        }
        return false;
    }
}
