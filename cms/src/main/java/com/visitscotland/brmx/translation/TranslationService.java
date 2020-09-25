package com.visitscotland.brmx.translation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.translation.difference.DocumentDifference;
import com.visitscotland.brmx.translation.plugin.JcrDocument;
import com.visitscotland.brmx.translation.plugin.JcrDocumentFactory;
import org.hippoecm.repository.api.Workflow;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Locale;
import java.util.Set;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class TranslationService {
    private static final Logger log = LoggerFactory.getLogger(TranslationService.class);
    private JcrDocumentFactory jcrDocumentFactory;
    private ObjectMapper objectMapper;
    private SessionFactory sessionFactory;

    @Autowired
    public TranslationService(JcrDocumentFactory jcrDocumentFactory,
                              ObjectMapper objectMapper,
                              SessionFactory sessionFactory) {
        this.jcrDocumentFactory = jcrDocumentFactory;
        this.objectMapper = objectMapper;
        this.sessionFactory = sessionFactory;
    }

    public JcrDocument getDocument(String nodeId) throws RepositoryException {
        Node jcrNode = sessionFactory.getJcrSession().getNodeByIdentifier(nodeId);
        return jcrDocumentFactory.createFromNode(jcrNode);
    }

    public boolean hasPendingTranslations(JcrDocument document) throws RepositoryException {
        Set<JcrDocument> translations = document.getTranslations();
        for ( JcrDocument translation : translations) {
            Node unpublishedNode = translation.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
            if ( unpublishedNode.hasProperty(JcrDocument.VS_TRANSLATION_FLAG) &&
                    unpublishedNode.getProperty(JcrDocument.VS_TRANSLATION_FLAG).getBoolean()) {
                return true;
            }
        }
        return false;
    }

    public boolean getTranslationFlag(JcrDocument document) throws RepositoryException {
        Node unpublishedNode = document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
        if ( unpublishedNode.hasProperty(JcrDocument.VS_TRANSLATION_FLAG) ) {
            return unpublishedNode.getProperty(JcrDocument.VS_TRANSLATION_FLAG).getBoolean();
        }
        return false;
    }

    public DocumentDifference getDocumentDifference(String nodeId) throws RepositoryException, IOException {
        JcrDocument document = getDocument(nodeId);
        String diffJson = getDocumentDifferenceJson(document);
        if ( null != diffJson ) {
            DocumentDifference diff = objectMapper.reader().readValue(diffJson, DocumentDifference.class);
            return diff;
        }
        return null;
    }

    public String getDocumentDifferenceJson(JcrDocument document) throws RepositoryException {
        Node unpublishedNode = document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
        if ( unpublishedNode.hasProperty(JcrDocument.VS_TRANSLATION_DIFF) ) {
            return unpublishedNode.getProperty(JcrDocument.VS_TRANSLATION_DIFF).getString();
        }
        return null;
    }

    public void clearTranslationFlag(JcrDocument jcrDocument)
            throws RepositoryException, WorkflowException, RemoteException {
        Session jcrSession = sessionFactory.getJcrSession();

        if (jcrDocument.isDraftBeingEdited()) {
            throw new WorkflowException("Document being edited");
        }

        Workflow editing = sessionFactory.getUserSession().getWorkflowManager().getWorkflow("editing", jcrDocument.getHandle());
        if (editing instanceof EditableWorkflow) {
            EditableWorkflow editableWorkflow = null;
            try {
                editableWorkflow = (EditableWorkflow) editing;
                editableWorkflow.obtainEditableInstance();

                Node unpublishedNode = jcrDocument.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
                Property localeProperty = unpublishedNode.getProperty(HippoTranslationNodeType.LOCALE);
                if (Locale.ENGLISH.getLanguage().equals(localeProperty.getString())) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to clear translation flag for English document");
                }

                unpublishedNode.setProperty(JcrDocument.VS_TRANSLATION_FLAG, false);
                if(unpublishedNode.hasProperty(JcrDocument.VS_TRANSLATION_DIFF)) {
                    Property diffProperty = unpublishedNode.getProperty(JcrDocument.VS_TRANSLATION_DIFF);
                    diffProperty.remove();
                }
            } finally {
                if (null != editableWorkflow) {
                    editableWorkflow.disposeEditableInstance();
                }
            }
            jcrSession.save();
            jcrSession.refresh(true);
        } else {
            throw new IllegalStateException("Unable to get EditableWorkflow");
        }
    }
}
