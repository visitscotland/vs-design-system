package com.visitscotland.brxm.translation.report;

import com.visitscotland.brxm.translation.SessionFactory;
import com.visitscotland.brxm.translation.plugin.JcrDocument;
import com.visitscotland.brxm.translation.plugin.JcrDocumentFactory;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.api.Workflow;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.impl.NodeDecorator;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TranslationReportService {

    private static final Logger log = LoggerFactory.getLogger(TranslationReportService.class);
    private static final String VS_TRANSLATION_FLAG = "visitscotland:translationFlag";
    private static final String VS_TRANSLATION_PRIORITY = "visitscotland:translationPriority";
    private static final String LAST_MODIFIED_BY_PROPERTY = "hippostdpubwf:lastModifiedBy";
    private static final String LAST_MODIFIED_DATE_PROPERTY = "hippostdpubwf:lastModificationDate";
    private static final String EDIT_WORKFLOW_NAME = "editing";
    private static final Set<String> SUPPORTED_LOCALES = new HashSet<>(Arrays.asList("en", "fr", "nl", "it", "de", "es"));
    private Set<String> cachedPageTypes;
    private Set<String> cachedModuleTypes;

    private final SessionFactory sessionFactory;
    private final JcrDocumentFactory jcrDocumentFactory;
    private final JcrQueryService jcrQueryService;

    @Autowired
    public TranslationReportService(SessionFactory sessionFactory, JcrDocumentFactory jcrDocumentFactory, JcrQueryService jcrQueryService) {
        this.sessionFactory = sessionFactory;
        this.jcrDocumentFactory = jcrDocumentFactory;
        this.jcrQueryService = jcrQueryService;
    }

    public void setTranslationPriority(String handleId, TranslationPriority priority) {
        try {
            Node jcrNode = sessionFactory.getJcrSession().getNodeByIdentifier(handleId);
            JcrDocument doc = jcrDocumentFactory.createFromNode(jcrNode);
            setTranslationPriority(doc, priority);
        } catch (ItemNotFoundException ex) {
            throw new TranslationReportException("Can not find item", ex);
        } catch (RepositoryException | RemoteException ex) {
            throw new TranslationReportException("Failed to update item", ex);
        } catch (WorkflowException ex) {
            throw new TranslationReportException("Document being modified by another user", ex);
        }
    }

    public List<TranslationModel> getUntranslatedDocuments(String targetLocale) {
        List<TranslationModel> docModels = new ArrayList<>();
        try {
            for (JcrDocument englishDoc : jcrQueryService.getAllEnglishDocuments()) {
                List<String> translatedLocales = new ArrayList<>();
                List<String> sendForTranslationLocales = new ArrayList<>();
                TranslationStatus infoStatus = TranslationStatus.NOT_SENT_FOR_TRANSLATION;

                for (String locale : SUPPORTED_LOCALES) {
                    TranslationStatus status = getDocumentTranslationStatus(englishDoc, locale);
                    if (status == TranslationStatus.TRANSLATED) {
                        translatedLocales.add(locale);
                    } else if (status == TranslationStatus.SEND_FOR_TRANSLATION) {
                        sendForTranslationLocales.add(locale);
                    }

                    if (locale.equals(targetLocale)) {
                        infoStatus = status;
                    }
                }
                if (infoStatus == TranslationStatus.TRANSLATED) continue;
                String displayName = ((HippoNode) englishDoc.getHandle()).getDisplayName();
                TranslationPriority translationPriority = getNodeTranslationPriority(englishDoc);
                Node unpublished = englishDoc.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
                String primaryNodeType = unpublished.getProperty("jcr:primaryType").getString().replace("visitscotland:", "");
                String lastModifiedBy = unpublished.getProperty(LAST_MODIFIED_BY_PROPERTY).getString();
                String lastModified = getDateAsIsoString(unpublished, LAST_MODIFIED_DATE_PROPERTY);
                docModels.add(new TranslationModel(englishDoc.getHandle().getIdentifier(), displayName,
                        infoStatus.toString(), translationPriority, translatedLocales, sendForTranslationLocales,
                        primaryNodeType, lastModified, lastModifiedBy, getDocumentPublishStatus(englishDoc)));
            }
        } catch (RepositoryException ex) {
            throw new TranslationReportException("Failed to access repository", ex);
        }

        return docModels;
    }

    public Set<String> getPageTypes() {
        try {
            if (cachedPageTypes == null) cachedPageTypes = jcrQueryService.getTypesDeriving("visitscotland:Page");
        } catch (RepositoryException ex) {
            throw new TranslationReportException("Failed to access repository", ex);
        }

        return cachedPageTypes;
    }

    public Set<String> getModuleTypes() {
        try {
            if (cachedModuleTypes == null) cachedModuleTypes = jcrQueryService.getTypesDeriving("visitscotland:basedocument");
        } catch (RepositoryException ex) {
            throw new TranslationReportException("Failed to access repository", ex);
        }

        return cachedModuleTypes;
    }

    public boolean isLocaleSupported(String locale) {
        return SUPPORTED_LOCALES.contains(locale);
    }

    private static String getDateAsIsoString(Node node, String property) throws RepositoryException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(node.getProperty(property).getDate().getTime());
    }


    private void setTranslationPriority(JcrDocument document, TranslationPriority priority) throws RepositoryException, WorkflowException, RemoteException {
        Workflow editing = sessionFactory.getUserSession().getWorkflowManager().getWorkflow(EDIT_WORKFLOW_NAME, document.getHandle());
        Node unpublishedNode = document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
        Session jcrSession = sessionFactory.getJcrSession();
        if (!document.getLocaleName().equals("en")) {
            throw new IllegalArgumentException("Can not set " + VS_TRANSLATION_PRIORITY + " on Node with locale" + document.getLocaleName());
        }

        if (editing instanceof EditableWorkflow) {
            EditableWorkflow editableWorkflow = (EditableWorkflow) editing;
            editableWorkflow.obtainEditableInstance();
            try {
                unpublishedNode.setProperty(VS_TRANSLATION_PRIORITY, priority.toString());
            } finally {
                editableWorkflow.disposeEditableInstance();
            }

            jcrSession.save();
            jcrSession.refresh(true);
        } else {
            throw new IllegalStateException("Unable to get EditableWorkflow");
        }
    }

    private static PublishStatus getDocumentPublishStatus(JcrDocument document) throws RepositoryException {
        Node unpublishedNode = document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
        if (unpublishedNode == null) return PublishStatus.NOT_LIVE;
        String state = unpublishedNode.getProperty("hippostd:stateSummary").getString();
        if (state.equals("live")) return PublishStatus.CURR_VERSION_LIVE;
        if (state.equals("new")) return PublishStatus.NOT_LIVE;
        if (state.equals("changed")) return PublishStatus.PREV_VERSION_LIVE;
        return PublishStatus.UNKNOWN;
    }

    private static TranslationPriority getNodeTranslationPriority(JcrDocument document) throws RepositoryException {
        Node node = document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
        String priorityString = node.hasProperty(VS_TRANSLATION_PRIORITY) ?
                node.getProperty(VS_TRANSLATION_PRIORITY).getString() : "";
        if (priorityString.isEmpty()) return TranslationPriority.NORMAL;

        try {
            return TranslationPriority.valueOf(priorityString);
        } catch (IllegalArgumentException ex) {
            log.error("Bad " + VS_TRANSLATION_PRIORITY + " on node " + node.getIdentifier() + " of " + priorityString, ex);
        }
        return TranslationPriority.NORMAL;
    }

    private static TranslationStatus getDocumentTranslationStatus(JcrDocument englishDoc, String locale) throws RepositoryException {
        if (locale.equals("en")) return TranslationStatus.TRANSLATED;
        if (!englishDoc.getTranslationLocaleNames().contains(locale)) {
            // If the clone has not been created, then the document can not have been sent for translation
            return TranslationStatus.NOT_SENT_FOR_TRANSLATION;
        } else {
            Node translatedClone = englishDoc.getTranslation(locale);
            if (!translatedClone.hasProperty(VS_TRANSLATION_FLAG) || translatedClone.getProperty(VS_TRANSLATION_FLAG).getString().isEmpty()) {
                // If the translationFlag does not exist on the document, then it has not been sent
                return TranslationStatus.NOT_SENT_FOR_TRANSLATION;
            } else {
                // The translationFlag is only set once the document has been sent for translation
                // Initially to true to indicate that the document has been sent, and then to false once the Translation
                // Completed button has been pressed
                return translatedClone.getProperty(VS_TRANSLATION_FLAG).getBoolean() ? TranslationStatus.SEND_FOR_TRANSLATION : TranslationStatus.TRANSLATED;
            }
        }
    }

}
