package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.Component;
import org.apache.wicket.model.IModel;
import org.hippoecm.addon.workflow.StdWorkflow;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.addon.workflow.WorkflowSNSException;
import org.hippoecm.frontend.dialog.IDialogService;
import org.hippoecm.frontend.plugins.standardworkflow.validators.SameNameSiblingsUtil;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.frontend.translation.workflow.JcrFolderTranslationFactory;
import org.hippoecm.repository.api.*;
import org.hippoecm.repository.standardworkflow.DefaultWorkflow;
import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.hippoecm.repository.translation.TranslationWorkflow;
import org.hippoecm.repository.util.JcrUtils;
import org.hippoecm.repository.util.NodeIterable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.hippoecm.repository.HippoStdNodeType.HIPPOSTD_STATE;
import static org.hippoecm.repository.HippoStdNodeType.UNPUBLISHED;
import static org.hippoecm.repository.api.HippoNodeType.NT_HANDLE;

final class TranslationAction extends StdWorkflow<TranslationWorkflow> {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationAction.class);
    public static final String COULD_NOT_CREATE_FOLDERS = "could-not-create-folders";

    private TranslationWorkflowPlugin workflowPlugin;
    private final String language;
    private final IModel<String> languageModel;
    private final IModel<ILocaleProvider.HippoLocale> localeModel;

    private String url;

    private List<FolderTranslation> folders;

    TranslationAction(TranslationWorkflowPlugin workflowPlugin, String id, IModel<String> name, IModel<ILocaleProvider.HippoLocale> localeModel, String language, IModel<String> languageModel) {
        super(id, name, workflowPlugin.getPluginContext(), (WorkflowDescriptorModel) workflowPlugin.getModel());
        this.workflowPlugin = workflowPlugin;
        this.language = language;
        this.languageModel = languageModel;
        this.localeModel = localeModel;
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
        return workflowPlugin.getActionIcon(id, localeModel);
    }

    @Override
    protected String execute(TranslationWorkflow workflow) throws WorkflowException, RepositoryException, RemoteException {
        Session session = UserSession.get().getJcrSession();

        // Only want to add languages we do not have a translation for
        List<ILocaleProvider.HippoLocale> untranslatedLocales = new ArrayList<>();
        for (String language : workflowPlugin.getAvailableLanguages()) {
            if (!workflowPlugin.hasLocaleTranslation(language)) {
                untranslatedLocales.add(workflowPlugin.getLocaleProvider().getLocale(language));
            }
        }

        for(ILocaleProvider.HippoLocale targetLocale : untranslatedLocales) {

            Node docNode = ((WorkflowDescriptorModel) workflowPlugin.getDefaultModel()).getNode();
            url = docNode.getName();
            folders = new LinkedList<>();
            Node handle = docNode.getParent();
            FolderTranslation documentTranslation = JcrFolderTranslationFactory.createFolderTranslation(handle, null);
            documentTranslation.setNamefr(documentTranslation.getName() + " (" + targetLocale.getName() + ")");
            documentTranslation.setUrlfr(documentTranslation.getUrl());
            folders.add(documentTranslation);

            populateFolders(handle, targetLocale.getName());

            // Find the index of the deepest translated folder.
            // The caller is to guarantee that at least the root node is translated (hence starting i at 1),
            // and that there is a document handle node at the end of the list (representing the to-be-translated document).
            final int indexOfDeepestFolder = folders.size() - 1;
            int i = 1;
            while (i < indexOfDeepestFolder && !folders.get(i).isEditable()) {
                i++;
            }

            int indexOfDeepestTranslatedFolder = i - 1;
            avoidSameNameSiblings(session, indexOfDeepestTranslatedFolder, targetLocale.getName());

            // Try to create new target folders for all not yet translated source folders
            for (; i < indexOfDeepestFolder; i++) {
                if (!saveFolder(folders.get(i), session, targetLocale.getName())) {
                    return COULD_NOT_CREATE_FOLDERS;
                }
            }

            FolderTranslation docTranslation = folders.get(folders.size() - 1);
            this.url = docTranslation.getUrlfr();

            workflow.addTranslation(targetLocale.getName(), url);

        }
        return null;
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
    private void avoidSameNameSiblings(final Session session, final int indexOfDeepestTranslatedFolder, final String targetLanguage)
            throws WorkflowSNSException, RepositoryException {

        final FolderTranslation deepestTranslatedFolder = folders.get(indexOfDeepestTranslatedFolder);
        final Node deepestTranslatedSourceNode = session.getNodeByIdentifier(deepestTranslatedFolder.getId());
        final Node deepestTranslatedTargetNode = new HippoTranslatedNode(deepestTranslatedSourceNode).getTranslation(targetLanguage);

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

    private Document getTranslatedVariant(final Document translatedDocument) throws RepositoryException {
        final Node translatedNode = translatedDocument.getNode(UserSession.get().getJcrSession());
        if (translatedNode.isNodeType(NT_HANDLE)) {
            Node variant = null;
            for (Node node : new NodeIterable(translatedNode.getNodes(translatedNode.getName()))) {
                variant = node;
                final String state = JcrUtils.getStringProperty(translatedNode, HIPPOSTD_STATE, null);
                if (UNPUBLISHED.equals(state)) {
                    break;
                }
            }
            return variant != null ? new Document(variant) : null;
        }
        return translatedDocument;
    }

    @Override
    protected IDialogService.Dialog createRequestDialog() {
        return new TranslationConfirmationDialog(this, new UntranslatedLocaleProvider(workflowPlugin, workflowPlugin.getLocaleProvider()));
    }

    private void populateFolders(Node handle, String targetLanguage) throws RepositoryException {
        Node sourceFolder = handle;
        try {
            while (!sourceFolder.isNodeType(HippoTranslationNodeType.NT_TRANSLATED)) {
                sourceFolder = sourceFolder.getParent();
            }
        } catch (ItemNotFoundException infe) {
            LOG.warn("Parent folder of translatable document could not be found", infe);
            return;
        } catch (AccessDeniedException ade) {
            LOG.warn("Parent folder of translatable document is not accessible", ade);
            return;
        }

        TranslatedFolder sourceTranslatedFolder = new TranslatedFolder(sourceFolder);

        // walk up the source tree until a translated ancestor is found
        while (sourceTranslatedFolder.getSibling(targetLanguage) == null) {
            FolderTranslation ft = JcrFolderTranslationFactory.createFolderTranslation(sourceTranslatedFolder.getNode(),
                    null);
            ft.setEditable(true);
            ft.setNamefr(ft.getName());
            ft.setUrlfr(ft.getUrl());
            folders.add(ft);

            sourceTranslatedFolder = sourceTranslatedFolder.getParent();
            if (sourceTranslatedFolder == null) {
                break;
            }
        }
        if (sourceTranslatedFolder == null) {
            throw new RepositoryException("Unable to find root folder for language " + targetLanguage);
        }

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
                TranslatedFolder backLink = targetTranslatedFolder.getSibling(languageModel.getObject());
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

    private boolean saveFolder(FolderTranslation ft, Session session, String targetLanguage) {
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
