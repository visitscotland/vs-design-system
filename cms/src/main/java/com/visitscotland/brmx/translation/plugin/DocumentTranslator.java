package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.addon.workflow.WorkflowSNSException;
import org.hippoecm.frontend.plugins.standardworkflow.validators.SameNameSiblingsUtil;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.frontend.translation.workflow.JcrFolderTranslationFactory;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.HippoWorkspace;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.api.WorkflowManager;
import org.hippoecm.repository.standardworkflow.DefaultWorkflow;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.hippoecm.repository.translation.TranslationWorkflow;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;

public class DocumentTranslator {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentTranslator.class);
    public static final String COULD_NOT_CREATE_FOLDERS = "could-not-create-folders";

    private HippoTranslatedNodeFactory translatedNodeFactory;

    DocumentTranslator() {
        this(new HippoTranslatedNodeFactory());
    }

    DocumentTranslator(HippoTranslatedNodeFactory translatedNodeFactory) {
        this.translatedNodeFactory = translatedNodeFactory;
    }

    public String getTranslatedDocumentName(List<FolderTranslation> folders) {
        if (folders == null || folders.isEmpty()) {
            throw new IllegalStateException("must have at least one document");
        }
        int docIndex = folders.size() - 1;
        return folders.get(docIndex).getNamefr();
    }

    public String cloneTranslationFolderStructure(Node docNode, List<FolderTranslation> folders, ILocaleProvider.HippoLocale targetLocale, Session session) throws WorkflowException, RepositoryException {
        Node handle = docNode.getParent();
        FolderTranslation documentTranslation = JcrFolderTranslationFactory.createFolderTranslation(handle, null);
        documentTranslation.setNamefr(documentTranslation.getName() + " (" + targetLocale.getName() + ")");
        documentTranslation.setUrlfr(documentTranslation.getUrl());
        folders.add(documentTranslation);

        populateFolders(handle, targetLocale.getName(), folders);

        // Find the index of the deepest translated folder.
        // The caller is to guarantee that at least the root node is translated (hence starting i at 1),
        // and that there is a document handle node at the end of the list (representing the to-be-translated document).
        final int indexOfDeepestFolder = folders.size() - 1;
        int i = 1;
        while (i < indexOfDeepestFolder && !folders.get(i).isEditable()) {
            i++;
        }

        int indexOfDeepestTranslatedFolder = i - 1;
        avoidSameNameSiblings(session, indexOfDeepestTranslatedFolder, targetLocale.getName(), folders);

        // Try to create new target folders for all not yet translated source folders
        for (; i < indexOfDeepestFolder; i++) {
            if (!saveFolder(folders.get(i), session, targetLocale.getName())) {
                return COULD_NOT_CREATE_FOLDERS;
            }
        }

        return null;
    }

    void populateFolders(Node handle, String targetLanguage, List<FolderTranslation> folders) throws RepositoryException {
        Node sourceFolder = findHighestTranslatedSourceFolder(handle);
        if (sourceFolder == null) return;

        TranslatedFolder sourceTranslatedFolder = addAllUntranslatedFolders(targetLanguage, folders, new TranslatedFolder(sourceFolder));

        TranslatedFolder targetTranslatedFolder = sourceTranslatedFolder.getSibling(targetLanguage);
        assert targetTranslatedFolder != null;
        while (sourceTranslatedFolder != null) {
            {
                FolderTranslation ft = JcrFolderTranslationFactory.createFolderTranslation(
                        sourceTranslatedFolder.getNode(), targetTranslatedFolder.getNode());
                ft.setEditable(false);
                folders.add(ft);
            }

            // walk up the source tree until a translated ancestor is found
            sourceTranslatedFolder = sourceTranslatedFolder.getParent();
            if (sourceTranslatedFolder == null) {
                break;
            }
            TranslatedFolder sourceSibling = sourceTranslatedFolder.getSibling(targetLanguage);
            while (sourceSibling == null) {
                FolderTranslation ft = JcrFolderTranslationFactory.createFolderTranslation(
                        sourceTranslatedFolder.getNode(), null);
                ft.setEditable(false);
                folders.add(ft);

                sourceTranslatedFolder = sourceTranslatedFolder.getParent();
                if (sourceTranslatedFolder == null) {
                    break;
                }
                sourceSibling = sourceTranslatedFolder.getSibling(targetLanguage);
            }
            if (sourceTranslatedFolder == null) {
                break;
            }

            // walk up the target tree until a translated ancestor is found
            targetTranslatedFolder = targetTranslatedFolder.getParent();
            while (targetTranslatedFolder != null) {
                if (targetTranslatedFolder.equals(sourceSibling)) {
                    break;
                }
                TranslatedFolder backLink = targetTranslatedFolder.getSibling(targetLanguage);
                if (backLink != null) {
                    if (!targetTranslatedFolder.equals(sourceSibling)) {
                        break;
                    }
                }

                FolderTranslation ft = JcrFolderTranslationFactory.createFolderTranslation(null,
                        targetTranslatedFolder.getNode());
                ft.setEditable(false);
                folders.add(ft);

                targetTranslatedFolder = targetTranslatedFolder.getParent();
            }
            if (targetTranslatedFolder == null || !targetTranslatedFolder.equals(sourceSibling)) {
                break;
            }
        }
        Collections.reverse(folders);
    }

    Node findHighestTranslatedSourceFolder(Node sourceFolder) throws RepositoryException {
        try {
            while (!sourceFolder.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)) {
                sourceFolder = sourceFolder.getParent();
            }
        } catch (ItemNotFoundException infe) {
            LOG.warn("Parent folder of translatable document could not be found", infe);
            return null;
        } catch (AccessDeniedException ade) {
            LOG.warn("Parent folder of translatable document is not accessible", ade);
            return null;
        }
        return sourceFolder;
    }

    TranslatedFolder addAllUntranslatedFolders(String targetLanguage, List<FolderTranslation> folders, TranslatedFolder sourceFolder) throws RepositoryException {
        // walk up the source tree until a translated ancestor is found
        while (sourceFolder.getSibling(targetLanguage) == null) {
            FolderTranslation ft = JcrFolderTranslationFactory.createFolderTranslation(sourceFolder.getNode(),
                    null);
            ft.setEditable(true);
            ft.setNamefr(ft.getName());
            ft.setUrlfr(ft.getUrl());
            folders.add(ft);

            sourceFolder = sourceFolder.getParent();
            if (sourceFolder == null) {
                throw new RepositoryException("Unable to find root folder for language " + targetLanguage);
            }
        }
        return sourceFolder;
    }

    /**
     * Prevent the creation of same-name-sibling (SNS) folders when translating a document (or folder?).
     * This affects
     *
     *   1) the case where the deepest existing folder already has a child node with the same (node-)name
     *   2) the case where the deepest existing folder already has a child node with the same localized name
     *
     * An exception of type {@link WorkflowSNSException} will be thrown if there is an SNS issue.
     */
    void avoidSameNameSiblings(Session session, int indexOfDeepestTranslatedFolder, String targetLanguage, List<FolderTranslation> folders)
            throws WorkflowSNSException, RepositoryException {

        final FolderTranslation deepestTranslatedFolder = folders.get(indexOfDeepestTranslatedFolder);
        final Node deepestTranslatedSourceNode = session.getNodeByIdentifier(deepestTranslatedFolder.getId());
        final Node deepestTranslatedTargetNode = translatedNodeFactory.createFromNode(deepestTranslatedSourceNode).getTranslation(targetLanguage);

        if (deepestTranslatedTargetNode == null) {
            // this means that there's a programmatic problem in the construction ot the "folders" list.
            LOG.error("Invalid deepestTranslatedNode parameter. Target translation node for '{}' could not be found.",
                    deepestTranslatedFolder.getName());
            return;
        }
        // highest untranslated item can be folder OR document
        final FolderTranslation highestUntranslatedItem = folders.get(indexOfDeepestTranslatedFolder + 1);
        String targetUrlName = highestUntranslatedItem.getUrlfr();
        String targetLocalizedName = highestUntranslatedItem.getNamefr();
        if (deepestTranslatedTargetNode.hasNode(targetUrlName)) {
            throw new WorkflowSNSException("A folder or document with name '" + targetUrlName + "' already exists", targetUrlName);
        }
        // check for duplicated localized name
        if (SameNameSiblingsUtil.hasChildWithDisplayName(deepestTranslatedTargetNode, targetLocalizedName)) {
            throw new WorkflowSNSException("A folder or document with localized name '" + targetLocalizedName + "' already exists", targetLocalizedName);
        }
        // No SNS issue!
    }

    boolean saveFolder(FolderTranslation ft, Session session, String targetLanguage) {
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
