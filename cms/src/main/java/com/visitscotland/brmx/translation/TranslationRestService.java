package com.visitscotland.brmx.translation;

import com.visitscotland.brmx.translation.plugin.JcrDocument;
import com.visitscotland.brmx.translation.plugin.JcrDocumentFactory;
import org.hippoecm.repository.api.Workflow;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.jcr.*;
import java.rmi.RemoteException;
import java.util.Locale;

@RestController
public class TranslationRestService {
    private static final Logger log = LoggerFactory.getLogger(TranslationRestService.class);
    private SessionFactory sessionFactory;
    private JcrDocumentFactory jcrDocumentFactory;

    @Autowired
    public TranslationRestService(SessionFactory sessionFactory,
                                  JcrDocumentFactory jcrDocumentFactory) {
        this.sessionFactory = sessionFactory;
        this.jcrDocumentFactory = jcrDocumentFactory;
    }

    @GetMapping(value = "/vs-service/node/{handleId}/translation/diff", produces = "application/json")
    public String getNodeDifference(@PathVariable String handleId) {
        try {
            Node handleNode = sessionFactory.getJcrSession().getNodeByIdentifier(handleId);
            JcrDocument jcrDocument = jcrDocumentFactory.createFromNode(handleNode);

            Node unpublishedNode = jcrDocument.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
            Property localeProperty = unpublishedNode.getProperty(HippoTranslationNodeType.LOCALE);
            if (Locale.ENGLISH.getLanguage().equals(localeProperty.getString())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to get difference for English document");
            }

            if (unpublishedNode.hasProperty(JcrDocument.VS_TRANSLATION_DIFF)) {
                Property diffJson = unpublishedNode.getProperty(JcrDocument.VS_TRANSLATION_DIFF);
                return diffJson.getString();
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No difference json found for document");
            }
        } catch(ItemNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No node found with given node identifier");
        } catch(Exception ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @DeleteMapping(value = "/vs-service/node/{handleId}/translation/flag", produces = "application/json")
    public void deleteTranslationFlag(@PathVariable String handleId) {
        try {
            Session jcrSession = sessionFactory.getJcrSession();
            Node handleNode = jcrSession.getNodeByIdentifier(handleId);
            JcrDocument jcrDocument = jcrDocumentFactory.createFromNode(handleNode);

            if (jcrDocument.isDraftBeingEdited()) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Document being edited");
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
                } catch(WorkflowException ex) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Unable to lock document for edit");
                } finally {
                    try {
                        if (null != editableWorkflow) {
                            editableWorkflow.disposeEditableInstance();
                        }
                    }catch (WorkflowException ex) {
                        log.error("Unable to dispose of locked document", ex);
                    }
                }
                jcrSession.save();
                jcrSession.refresh(true);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to get EditableWorkflow");
            }
        } catch(ItemNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No node found with given node identifier");
        } catch(RepositoryException | RemoteException ex) {
            if (ex.getCause() instanceof IllegalArgumentException) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
            } else {
                throw new ResponseStatusException((HttpStatus.INTERNAL_SERVER_ERROR));
            }
        }
    }
}
