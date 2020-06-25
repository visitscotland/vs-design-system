package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.addon.workflow.WorkflowSNSException;
import org.hippoecm.frontend.plugins.standardworkflow.validators.SameNameSiblingsUtil;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.frontend.translation.workflow.JcrFolderTranslationFactory;
import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Is used to contain all the changes required for a translation operation.
 * This is split into a list of the folders to be created and the documents to be cloned.
 * All of the documents to be cloned exist in a single folder, this will be the tip of the folders list.
 * A ChangeSet usually specifies the changes to be applied for a single language.
 */
public class ChangeSet {
    private static final Logger LOG = LoggerFactory.getLogger(ChangeSet.class);
    private List<FolderTranslation> folders;
    private List<FolderTranslation> documents;
    private ILocaleProvider.HippoLocale targetLocale;

    public ChangeSet(ILocaleProvider.HippoLocale targetLocale) {
        folders = new ArrayList<>();
        documents = new ArrayList<>();
        this.targetLocale = targetLocale;
    }

    public List<FolderTranslation> getFolders() {
        return folders;
    }

    public List<FolderTranslation> getDocuments() {
        return documents;
    }

    /**
     * Converts the JcrDocume to a FolderTranslation instance and adds it to the List of documents to be cloned.
     * @param document The JcrDocument to be added to the document list
     * @throws RepositoryException
     */
    public void addDocument(JcrDocument document) throws RepositoryException {
        FolderTranslation documentTranslation = JcrFolderTranslationFactory.createFolderTranslation(document.getHandle(), null);
        documentTranslation.setNamefr(documentTranslation.getName() + " (" + targetLocale.getName() + ")");
        documentTranslation.setUrlfr(documentTranslation.getUrl());
        documents.add(documentTranslation);
    }

    public void populateFolders(JcrDocument sourceDocument) throws RepositoryException {
        Node sourceFolder = findHighestTranslatedSourceFolder(sourceDocument.getHandle());
        String targetLanguage = targetLocale.getName();
        if (sourceFolder == null) return;

        TranslatedFolder sourceTranslatedFolder = addAllUntranslatedFolders(targetLanguage, folders, createTranslatedFolder(sourceFolder));

        TranslatedFolder targetTranslatedFolder = sourceTranslatedFolder.getSibling(targetLanguage);
        assert targetTranslatedFolder != null;
        while (sourceTranslatedFolder != null) {
            {
                FolderTranslation ft = JcrFolderTranslationFactory.createFolderTranslation(
                        sourceTranslatedFolder.getNode(), targetTranslatedFolder.getNode());
                ft.setEditable(false);
                folders.add(ft);
            }


            sourceTranslatedFolder = sourceTranslatedFolder.getParent();
            if (sourceTranslatedFolder == null) {
                break;
            }
            TranslatedFolder sourceSibling = sourceTranslatedFolder.getSibling(targetLanguage);
            // This while loop catches cases where the parent of the translated folder
            // points back to the source translation folders
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


            targetTranslatedFolder = targetTranslatedFolder.getParent();
            while (targetTranslatedFolder != null) {
                if (targetTranslatedFolder.equals(sourceSibling)) {
                    break;
                }
                TranslatedFolder backLink = targetTranslatedFolder.getSibling(targetLanguage);
                if (backLink != null) {
                    // This will always be true,
                    // if targetTranslatedFolder equals sourceSibling the previous break would have been hit
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

    protected Node findHighestTranslatedSourceFolder(Node sourceFolder) throws RepositoryException {
        // The JCR has a pair of Nodes to represent a document. The highest node has a Mixin of
        // hippo:translated, but its parent does not but is hippo:named. It is the hippo:named Node that
        // is passed to the populateFolders method this method recurses over the nodes to find the first
        // folder that the document is in.
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

    protected TranslatedFolder addAllUntranslatedFolders(String targetLanguage, List<FolderTranslation> folders, TranslatedFolder sourceFolder) throws RepositoryException {
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

    protected TranslatedFolder createTranslatedFolder(Node node) {
        return new TranslatedFolder(node);
    }

    public ILocaleProvider.HippoLocale getTargetLocale() {
        return targetLocale;
    }

    protected int getIndexOfDeepestTranslatedFolder() {
        if (folders.isEmpty()) {
            throw new IllegalStateException("Unable to find a translated folder when empty");
        }
        if (folders.get(0).isEditable()) {
            throw new IllegalStateException("The root folder must never be untranslated");
        }
        int index = folders.size() - 1;
        while (index > 0 &&
                folders.get(index).isEditable()) {
            index--;
        }

        return index;
    }

    public void checkForSameNameSiblings(Session session)
            throws WorkflowSNSException, RepositoryException {
        final int indexOfDeepestTranslatedFolder = getIndexOfDeepestTranslatedFolder();
        final FolderTranslation deepestTranslatedFolder = folders.get(indexOfDeepestTranslatedFolder);
        final Node deepestTranslatedSourceNode = session.getNodeByIdentifier(deepestTranslatedFolder.getId());
        final Node deepestTranslatedTargetNode = createFromNode(deepestTranslatedSourceNode).getTranslation(targetLocale.getName());

        if (deepestTranslatedTargetNode == null) {
            // this means that there's a programmatic problem in the construction ot the "folders" list.
            throw new WorkflowSNSException("Invalid folder structure unable to find translated folder");
        }

        // if the highest untranslated item is a folder then no need to check the documents
        // if it is a document then we need to check every document for a same name sibling
        if (indexOfDeepestTranslatedFolder < folders.size() - 1) {
            final FolderTranslation highestUntranslatedItem = folders.get(indexOfDeepestTranslatedFolder + 1);
            hasSameNameSibling(highestUntranslatedItem, deepestTranslatedTargetNode);
        } else {
            for (FolderTranslation document : documents) {
                hasSameNameSibling(document, deepestTranslatedTargetNode);
            }
        }
        // No SNS issue!
    }

    protected void hasSameNameSibling(FolderTranslation targetFolderTranslation, Node sourceFolderNode) throws WorkflowSNSException, RepositoryException {
        String targetUrlName = targetFolderTranslation.getUrlfr();
        String targetLocalizedName = targetFolderTranslation.getNamefr();
        if (sourceFolderNode.hasNode(targetUrlName)) {
            throw new WorkflowSNSException("A folder or document with name '" + targetUrlName + "' already exists", targetUrlName);
        }
        // check for duplicated localized name
        if (SameNameSiblingsUtil.hasChildWithDisplayName(sourceFolderNode, targetLocalizedName)) {
            throw new WorkflowSNSException("A folder or document with localized name '" + targetLocalizedName + "' already exists", targetLocalizedName);
        }
    }

    protected HippoTranslatedNode createFromNode(Node node) throws RepositoryException {
        return new HippoTranslatedNode(node);
    }
}
