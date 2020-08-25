package com.visitscotland.brmx.translation.plugin;

import com.visitscotland.brmx.beans.TranslationLinkContainer;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.api.*;
import org.hippoecm.repository.ext.InternalWorkflow;
import org.hippoecm.repository.standardworkflow.CopyWorkflow;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.hippoecm.repository.standardworkflow.FolderWorkflow;
import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.hippoecm.repository.util.JcrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.*;

public class TranslationWorkflowImpl implements TranslationWorkflow, InternalWorkflow {
    public static final String CAFEBABE = "cafebabe-";
    private static final Logger log = LoggerFactory.getLogger(TranslationWorkflowImpl.class);
    private final Session userSession;
    private final Session rootSession;
    private final WorkflowContext workflowContext;
    private final Node rootSubject;
    private final Node userSubject;
    private JcrDocumentFactory jcrDocumentFactory;
    private DocumentFactory documentFactory;

    public TranslationWorkflowImpl(final WorkflowContext context, final Session userSession, final Session rootSession,
                                   final Node subject) throws RepositoryException {
        this(context, userSession, rootSession, subject, new JcrDocumentFactory(), new DocumentFactory());
    }

    protected TranslationWorkflowImpl(final WorkflowContext context, final Session userSession, final Session rootSession,
                                   final Node subject, JcrDocumentFactory jcrDocumentFactory, DocumentFactory documentFactory) throws RepositoryException {
        this.workflowContext = context;
        this.rootSession = rootSession;
        this.userSession = userSession;
        this.userSubject = userSession.getNodeByIdentifier(subject.getIdentifier());
        this.rootSubject = rootSession.getNodeByIdentifier(subject.getIdentifier());
        this.jcrDocumentFactory = jcrDocumentFactory;
        this.documentFactory = documentFactory;

        if (!userSubject.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)) {
            throw new RepositoryException("Node is not of type " + HippoTranslationNodeType.NT_TRANSLATED);
        }
    }

    @Override
    public Document addTranslation(String language, String newDocumentName) throws WorkflowException, RepositoryException, RemoteException, ObjectBeanManagerException {
        return addTranslation(language, newDocumentName, userSubject);
    }

    @Override
    public Document addTranslation(final String language, final String newDocumentName, final Node sourceNode) throws WorkflowException,
            RepositoryException, RemoteException, ObjectBeanManagerException {
        Node userSourceSubject = userSession.getNodeByIdentifier(sourceNode.getIdentifier());

        Node rootSource = rootSession.getNodeByIdentifier(sourceNode.getIdentifier());
        final HippoTranslatedNode originNode = new HippoTranslatedNode(rootSource);
        final Node originFolder = originNode.getContainingFolder();
        if (originFolder == null || !originFolder.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)) {
            throw new WorkflowException("No translated ancestor folder found");
        }

        final HippoTranslatedNode originTranslatedFolderNode = new HippoTranslatedNode(originFolder);
        final Node targetFolderNode = originTranslatedFolderNode.getTranslation(language);

        Node copiedNode;
        if (userSourceSubject.getParent().isNodeType(HippoNodeType.NT_HANDLE)) {
            copiedNode = addTranslatedDocument(language, newDocumentName, userSourceSubject, targetFolderNode);
        } else {
            copiedNode = addTranslatedFolder(language, newDocumentName, targetFolderNode);
        }

        return new Document(copiedNode);
    }

    @Override
    public void saveSession() throws RepositoryException {
        rootSession.save();
        rootSession.refresh(false);
    }

    protected Node addTranslatedDocument(final String language, final String newDocumentName, final Node sourceDocumentNode, final Node targetFolderNode)
            throws WorkflowException, RepositoryException, RemoteException, ObjectBeanManagerException {

        Node copyRootSubject = rootSession.getNodeByIdentifier(sourceDocumentNode.getIdentifier());
        getOriginsCopyWorkflow(copyRootSubject).copy(documentFactory.createFromNode(targetFolderNode), newDocumentName);

        Node newDocumentHandle = null;

        final NodeIterator siblings = targetFolderNode.getNodes(newDocumentName);
        while (siblings.hasNext()) {
            final Node sibling = siblings.nextNode();
            if (sibling.isNodeType(HippoNodeType.NT_HANDLE)) {
                newDocumentHandle = sibling;
            }
        }
        if (newDocumentHandle == null) {
            throw new WorkflowException("Could not locate handle for document after copying");
        }

        // Iterate over the child Nodes in the document looking for Translatable children
        JcrDocument jcrDocument = new JcrDocument(newDocumentHandle);
        String[] translatableLinkNames = new String[]{};
        boolean containsTranslatableTypes = false;
        if (jcrDocument.asHippoBean() instanceof TranslationLinkContainer) {
            containsTranslatableTypes = true;
            TranslationLinkContainer container = jcrDocument.asHippoBean(TranslationLinkContainer.class);
            translatableLinkNames = container.getTranslatableLinkNames();
        }

        final NodeIterator copiedVariants = newDocumentHandle.getNodes(newDocumentHandle.getName());
        // Now that the Node has been copied to the language channel update all the properties that can be translated
        // Iterate over all the variants that exist for the document, looks like it should only have an unpublished
        // variant when first copied.
        while (copiedVariants.hasNext()) {
            // Ensure the document is checked out so we can make changes
            final Node copiedVariant = copiedVariants.nextNode();
            JcrUtils.ensureIsCheckedOut(copiedVariant);
            // Update the locale that this copy is for,
            // will currently have the value of the node that was copied (English)
            copiedVariant.setProperty(HippoTranslationNodeType.LOCALE, language);

            // If the document has Translatable children we need to attempt to change
            // the links to point to the relevant translated child
            if (containsTranslatableTypes) {
                for (String childName : translatableLinkNames) {
                    NodeIterator childIterator = copiedVariant.getNodes(childName);
                    while (childIterator.hasNext()) {
                        // This Node it a Translatable child link
                        Node childNode = childIterator.nextNode();
                        if (!childNode.hasProperty("hippo:docbase")) {
                            log.warn("Unable to find linking node UUID");
                            continue;
                        }
                        // Now we have the UUID of the node we are linking to, get the Node and see if there is a
                        // translation for the current language.
                        // If the linkUUID does not exist or points to the root Node then skip it.
                        String linkUUID = childNode.getProperty("hippo:docbase").getString();
                        if (linkUUID == null || linkUUID.equals("") || linkUUID.startsWith(CAFEBABE)) {
                            log.warn("Link contains an empty Node");
                            continue;
                        }
                        Node linkedNode = rootSession.getNodeByIdentifier(linkUUID);
                        JcrDocument linkedJcrDocument = new JcrDocument(linkedNode);
                        if (linkedJcrDocument.hasTranslation(language)) {
                            Node targetNode = linkedJcrDocument.getTranslation(language);
                            childNode.setProperty("hippo:docbase", targetNode.getIdentifier());
                        } else {
                            log.warn("Missing link translation node");
                        }
                    }
                }
            }
        }

        return newDocumentHandle;
    }

    private CopyWorkflow getOriginsCopyWorkflow(Node copyRootSubject) throws RepositoryException, WorkflowException {
        // first check if a copy workflow is configured on the handle itself
        Workflow workflow = workflowContext.getWorkflow("translation-copy", documentFactory.createFromNode(copyRootSubject.getParent()));

        if (workflow == null) {
            // No? Fallback to a copy workflow on the subject itself
            workflow = workflowContext.getWorkflow("translation-copy", documentFactory.createFromNode(copyRootSubject));
        }

        if (workflow instanceof CopyWorkflow) {
            return (CopyWorkflow) workflow;
        } else {
            throw new WorkflowException("No copy workflow defined; cannot copy document");
        }
    }

    private Node addTranslatedFolder(final String language, final String newDocumentName, final Node targetFolderNode)
            throws WorkflowException, RemoteException, RepositoryException {

        final FolderWorkflow workflowOfTargetFolder = getFolderWorkflow(targetFolderNode);
        final Map<String, Set<String>> prototypes = getPrototypes(workflowOfTargetFolder);

        // find best matching category and type from prototypes
        final String primaryType = userSubject.getPrimaryNodeType().getName();
        String category = null;
        String type = null;

        for (Map.Entry<String, Set<String>> candidate : prototypes.entrySet()) {
            final String categoryName = candidate.getKey();
            final Set<String> types = candidate.getValue();
            if (types.contains(primaryType)) {
                category = categoryName;
                type = primaryType;
                break;
            }
            if (category == null) {
                category = categoryName;
            }
            if (type == null && types.size() > 0) {
                type = types.iterator().next();
            }
        }

        if (category == null) {
            throw new WorkflowException("No category found to use for adding translation to target folder");
        }
        if (type == null) {
            throw new WorkflowException("No type found to use for adding translation to target folder");
        }

        final String newFolderPath = workflowOfTargetFolder.add(category, type, newDocumentName);
        Node copiedNode = rootSession.getNode(newFolderPath);

        JcrUtils.ensureIsCheckedOut(copiedNode);
        if (!copiedNode.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)) {
            copiedNode.addMixin(HippoTranslationNodeType.NT_TRANSLATED);
        }
        copiedNode.setProperty(HippoTranslationNodeType.ID,
                userSubject.getProperty(HippoTranslationNodeType.ID).getString());
        copiedNode.setProperty(HippoTranslationNodeType.LOCALE, language);
        rootSession.save();

        copyFolderTypes(copiedNode, prototypes);

        return copiedNode;
    }

    private Map<String, Set<String>> getPrototypes(final FolderWorkflow folderWorkflow) throws WorkflowException,
            RemoteException, RepositoryException {

        final Map<String, Set<String>> prototypes = (Map<String, Set<String>>) folderWorkflow.hints().get("prototypes");
        if (prototypes == null) {
            throw new WorkflowException("No prototype hints available in workflow of target folder.");
        }
        return prototypes;
    }

    private FolderWorkflow getFolderWorkflow(final Node targetFolderNode) throws WorkflowException,
            RepositoryException {

        Workflow workflow = workflowContext.getWorkflow("internal", documentFactory.createFromNode(targetFolderNode));
        if (!(workflow instanceof FolderWorkflow)) {
            throw new WorkflowException("Target folder does not have a folder workflow in the category 'internal'.");
        }
        return (FolderWorkflow) workflow;
    }

    public void addTranslation(final String language, final Document document) throws WorkflowException,
            RepositoryException {
        HippoTranslatedNode translatedNode = new HippoTranslatedNode(rootSubject);
        if (translatedNode.hasTranslation(language)) {
            throw new WorkflowException("Language already exists");
        }

        Node copiedDocNode = document.getNode(rootSession);
        JcrUtils.ensureIsCheckedOut(copiedDocNode);
        if (!copiedDocNode.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)) {
            copiedDocNode.addMixin(HippoTranslationNodeType.NT_TRANSLATED);
        }
        copiedDocNode.setProperty(HippoTranslationNodeType.LOCALE, language);
        copiedDocNode.setProperty(HippoTranslationNodeType.ID, userSubject.getProperty(HippoTranslationNodeType.ID)
                .getString());

        rootSession.save();
        rootSession.refresh(false);
    }

    @Override
    public List<JcrDocument> setTranslationRequiredFlag() throws WorkflowException, RepositoryException, RemoteException {
        JcrDocument rootJcrDocument = jcrDocumentFactory.createFromNode(rootSubject);
        if (rootJcrDocument.isNodeType("visitscotland:translatable")) {
            Set<JcrDocument> jcrTranslations = rootJcrDocument.getTranslations();

            // The Hippo CMS uses the handle of the document variants to perform checkout, commit and discard
            // operations. But the getEditableNode returns the unpublished Node so we need to keep hold of the
            // handle so we can perform the commit, or discard operations.

            // HashMap<Handle, Editable>
            HashMap<Node, Node> editableNodes = new HashMap<>();
            List<JcrDocument> nodesBeingEdited = new ArrayList<>();

            // Need to check if the root (English) document is being edited by another user. Don't want to send for
            // translation unless it is
            if (rootJcrDocument.isDraftBeingEdited()) {
                nodesBeingEdited.add(rootJcrDocument);
            }

            for (JcrDocument translatedDocument : jcrTranslations) {
                Node handle = translatedDocument.getHandle();
                if (translatedDocument.isDraftBeingEdited()) {
                    nodesBeingEdited.add(translatedDocument);
                    log.debug("Document already checked out for edit, unable to send for translation");
                    continue;
                }

                try {
                    // The editable node returned is the draft variant of the document. If we apply changes to the
                    // draft, and then commit changes to the node it also flags the document as changed.
                    // We do not want that, we want the editor to choose if the document has changed.
                    // Getting the editable node still ensures that nobody else is editing the document, but if the
                    // changes are applied to the unpublished variant and the draft is discarded it should have the
                    // desired result, changes applied without flagging the document as changed.
                    getEditableNode(handle);
                    Node unpublishedNode = translatedDocument.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
                    editableNodes.put(handle, unpublishedNode);
                } catch (WorkflowException ex) {
                    // If we get a workflow exception we have not been able to check the document out
                    // add the Node to a list of failed documents
                    log.debug("Document already checked out for edit, unable to send for translation", ex);
                    nodesBeingEdited.add(translatedDocument);
                }
            }

            if (nodesBeingEdited.isEmpty()) {
                if (!editableNodes.isEmpty()) {
                    for (HashMap.Entry<Node, Node> editableNodeEntry : editableNodes.entrySet()) {
                        editableNodeEntry.getValue().setProperty("visitscotland:translationFlag", true);
                        // If this was the draft node we would want to commit the changes
                        discardEditableNode(editableNodeEntry.getKey());
                    }
                    rootSession.save();
                }
            } else {
                for (Node handle : editableNodes.keySet()) {
                    discardEditableNode(handle);
                }
            }
            rootSession.refresh(false);
            return nodesBeingEdited;
        }
        return Collections.emptyList();
    }

    protected Node getEditableNode(Node handle) throws RemoteException, WorkflowException, RepositoryException {
        final Workflow editing = workflowContext.getWorkflow("editing", documentFactory.createFromNode(handle));
        if (editing instanceof EditableWorkflow) {
            EditableWorkflow editableWorkflow = (EditableWorkflow) editing;
            Document editableDocument = editableWorkflow.obtainEditableInstance();
            return editableDocument.getNode(rootSession);
        } else {
            throw new WorkflowException("Unable to obtain an EditableWorkflow to perform translation");
        }
    }

    protected void discardEditableNode(Node toDiscardHandle) throws RemoteException, WorkflowException, RepositoryException {
        final Workflow editing = workflowContext.getWorkflow("editing", documentFactory.createFromNode(toDiscardHandle));
        if (editing instanceof EditableWorkflow) {
            EditableWorkflow editableWorkflow = (EditableWorkflow) editing;
            editableWorkflow.disposeEditableInstance();
        } else {
            throw new WorkflowException("Unable to obtain an EditableWorkflow to discard checkout");
        }
    }

    // An example of how we would commit a draft version of a document to mark the document as changed
    // We would want to do this if we were setting any data on the document
    protected void commitEditableNode(Node toCommitHandle) throws RemoteException, WorkflowException, RepositoryException {
        final Workflow editing = workflowContext.getWorkflow("editing", documentFactory.createFromNode(toCommitHandle));
        if (editing instanceof EditableWorkflow) {
            EditableWorkflow editableWorkflow = (EditableWorkflow) editing;
            editableWorkflow.commitEditableInstance();
        } else {
            throw new WorkflowException("Unable to obtain an EditableWorkflow to commit changes");
        }
    }

    public Map<String, Serializable> hints() throws WorkflowException, RepositoryException {
        Map<String, Serializable> hints = new TreeMap<>();

        if (userSubject.isNodeType(HippoStdNodeType.NT_PUBLISHABLE)) {
            String state = userSubject.getProperty(HippoStdNodeType.HIPPOSTD_STATE).getString();
            if (HippoStdNodeType.DRAFT.equals(state)) {
                hints.put("addTranslation", Boolean.FALSE);
            } else {
                NodeIterator siblings = userSubject.getParent().getNodes(userSubject.getName());
                Node unpublished = null;
                Node published = null;
                while (siblings.hasNext()) {
                    Node sibling = siblings.nextNode();
                    if (sibling.isNodeType(HippoStdNodeType.NT_PUBLISHABLE)) {
                        String siblingState = sibling.getProperty(HippoStdNodeType.HIPPOSTD_STATE).getString();
                        if (HippoStdNodeType.UNPUBLISHED.equals(siblingState)) {
                            unpublished = sibling;
                        } else if (HippoStdNodeType.PUBLISHED.equals(siblingState)) {
                            published = sibling;
                        }
                    }
                }
                if (unpublished != null && published != null) {
                    if (HippoStdNodeType.PUBLISHED.equals(state)) {
                        hints.put("addTranslation", Boolean.FALSE);
                    }
                }
            }
        }

        final HippoTranslatedNode translatedNode = new HippoTranslatedNode(userSubject);
        Set<String> translations;
        try {
            translations = translatedNode.getTranslations();
        } catch (RepositoryException ex) {
            throw new WorkflowException("Exception during searching for available translations", ex);
        }

        hints.put("locales", (Serializable) translations);

        Set<String> available = new TreeSet<>();
        // for all the available translations we pick the highest ancestor of userSubject of type
        // HippoTranslationNodeType.NT_TRANSLATED, and take all the translations for that node
        Node highestTranslatedNode = translatedNode.getFarthestTranslatedAncestor();
        if (highestTranslatedNode != null) {
            try {
                available = new HippoTranslatedNode(highestTranslatedNode).getTranslations();
            } catch (RepositoryException ex) {
                throw new WorkflowException("Exception during searching for available translations", ex);
            }
        }

        hints.put("available", (Serializable) available);
        hints.put("locale", translatedNode.getLocale());
        return hints;
    }

    private void copyFolderTypes(final Node copiedNode, final Map<String, Set<String>> prototypes)
            throws RepositoryException {

        try {
            // check if we have all subject folder types
            final FolderWorkflow folderWorkflow = getFolderWorkflow(rootSubject);
            final Map<String, Set<String>> copyPrototypes = getPrototypes(folderWorkflow);

            // check if equal
            if (copyPrototypes == null || copyPrototypes.size() == 0) {
                return;
            }

            final Set<String> protoKeys = prototypes.keySet();
            final Set<String> copyKeys = copyPrototypes.keySet();
            // check if we have a difference and overwrite
            if (copyKeys.size() != protoKeys.size() || !copyKeys.containsAll(protoKeys)) {
                final String[] newValues = copyKeys.toArray(new String[0]);
                copiedNode.setProperty(HippoStdNodeType.HIPPOSTD_FOLDERTYPE, newValues);
            }
        } catch (WorkflowException e) {
            log.warn(e.getClass().getName() + ": " + e.getMessage(), e);
        } catch (RemoteException e) {
            log.error(e.getClass().getName() + ": " + e.getMessage(), e);
        }
    }

    public static class DocumentFactory {
        public Document createFromNode(Node node) throws RepositoryException {
            return new Document(node);
        }
    }

}
