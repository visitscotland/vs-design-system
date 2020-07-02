package com.visitscotland.brmx.translation.plugin;

import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.TranslationParent;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.repository.api.*;
import org.hippoecm.repository.standardworkflow.DefaultWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class DocumentTranslator {
    public static final String COULD_NOT_CREATE_FOLDERS = "could-not-create-folders";
    private static final Logger LOG = LoggerFactory.getLogger(DocumentTranslator.class);

    /**
     * Build a change set for each language, listing the folders and documents that are not already translated.
     * If the document is a TrasnlationParent then add untranslated siblings also.
     * @param sourceDocument
     * @param targetLocaleList
     * @return
     */
    public List<ChangeSet> buildChangeSetList(Node sourceDocument, List<ILocaleProvider.HippoLocale> targetLocaleList)
            throws ObjectBeanManagerException, RepositoryException {
        List<ChangeSet> changeSetList = new ArrayList<>();
        for (ILocaleProvider.HippoLocale targetLocale : targetLocaleList) {
            ChangeSet change = createChangeSet(targetLocale);
            JcrDocument document = createJcrDocument(sourceDocument);
            change.populateFolders(document);
            if (!document.hasTranslation(targetLocale)) {
                change.addDocument(document);
            }

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
                        HippoNode siblingNode = (HippoNode)handleIterator.nextNode();
                        // We want to make sure we are not cloning folders and other types.
                        if (!siblingNode.isNodeType("hippostd:folder") &&
                                (siblingNode.isNodeType(JcrDocument.HIPPO_HANDLE) ||
                                        siblingNode.isNodeType(JcrDocument.HIPPO_TRANSLATED))) {
                            JcrDocument siblingDocument = createJcrDocument(siblingNode);
                            if (siblingDocument.isNodeType(childJcrTypes) &&
                                    !siblingDocument.hasTranslation(targetLocale)) {
                                change.addDocument(siblingDocument);
                            }
                        }
                    }
                }
            }

            if (!change.getDocuments().isEmpty()) {
                changeSetList.add(change);
            }
        }

        return changeSetList;
    }

    protected ChangeSet createChangeSet(ILocaleProvider.HippoLocale targetLocale) {
        return new ChangeSet(targetLocale);
    }

    protected JcrDocument createJcrDocument(Node sourceNode) throws RepositoryException {
        return new JcrDocument(sourceNode);
    }

    public void applyChangeSet(ChangeSet change,
                               Session session,
                               TranslationWorkflow workflow)
            throws TranslationException, WorkflowException, RepositoryException, RemoteException {
        // working from index zero work our way up the folders creating them as we go
        // there must always be a root folder and it always exists so skipping to index 1
        change.checkForSameNameSiblings(session);
        List<FolderTranslation> folders = change.getFolders();
        for (int index = 1; index < folders.size(); index++) {
            FolderTranslation translation = folders.get(index);
            if (translation.isEditable()) {
                if (!saveFolder(translation, session, change.getTargetLocale().getName())) {
                    throw new TranslationException(COULD_NOT_CREATE_FOLDERS);
                }
            }
        }

        for (FolderTranslation document : change.getDocuments()) {
            JcrDocument sourceDocument = createJcrDocument(session.getNodeByIdentifier(document.getId()));
            workflow.addTranslation(change.getTargetLocale().getName(), document.getNamefr(),
                    sourceDocument.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED));
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
