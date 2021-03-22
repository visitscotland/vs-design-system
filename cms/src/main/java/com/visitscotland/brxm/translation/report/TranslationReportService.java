package com.visitscotland.brxm.translation.report;

import com.visitscotland.brxm.beans.Page;
import com.visitscotland.brxm.translation.SessionFactory;
import com.visitscotland.brxm.translation.plugin.JcrDocument;
import com.visitscotland.brxm.translation.plugin.JcrDocumentFactory;
import com.visitscotland.brxm.translation.plugin.TranslationException;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.repository.api.Workflow;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.impl.NodeDecorator;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jcr.*;
import javax.jcr.query.Row;
import javax.jcr.query.RowIterator;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class TranslationReportService {

    private static final Logger log = LoggerFactory.getLogger(TranslationReportService.class);
    private static final String VS_TRANSLATION_FLAG = "visitscotland:translationFlag";
    private static final String VS_TRANSLATION_PRIORITY = "visitscotland:translationPriority";
    public static final Set<String> SUPPORTED_LOCALES = new HashSet<>(Arrays.asList("en", "fr", "nl", "it", "de", "es"));
    private static final String EDIT_WORKFLOW_NAME = "editing";
    private Set<String> cachedPageTypes;
    private Set<String> cachedModuleTypes;

    private final SessionFactory sessionFactory;
    private final JcrDocumentFactory jcrDocumentFactory;

    @Autowired
    public TranslationReportService(SessionFactory sessionFactory, JcrDocumentFactory jcrDocumentFactory) {
        this.sessionFactory = sessionFactory;
        this.jcrDocumentFactory = jcrDocumentFactory;
    }

    public void setTranslationPriority(String handleId, TranslationPriority priority) {
        try {
            Node jcrNode = sessionFactory.getJcrSession().getNodeByIdentifier(handleId);
            JcrDocument doc = jcrDocumentFactory.createFromNode(jcrNode);
            setTranslationPriority(doc, priority);
        } catch (ItemNotFoundException ex) {
            log.info("Failed to get item with handle " + handleId, ex);
            throw new TranslationReportException("Can not find item", ex);
        } catch (RepositoryException | RemoteException ex) {
            log.info("Failed to access repository", ex);
            throw new TranslationReportException("Failed to update item", ex);
        } catch (WorkflowException ex) {
            log.info("Another user editing document " + handleId, ex);
            throw new TranslationReportException("Document being modified by another user", ex);
        }
    }

    public static List<TranslationModel> getUntranslatedDocuments(String targetLocale) {
        List<TranslationModel> docModels = new ArrayList<>();
        try {
            for (JcrDocument englishDoc : getAllEnglishDocuments()) {
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
                String displayName =  ((NodeDecorator)englishDoc.getHandle()).getDisplayName();
                TranslationPriority translationPriority = getNodeTranslationPriority(englishDoc);
                Node unpublished = englishDoc.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
                String primaryNodeType = unpublished.getProperty("jcr:primaryType").getString().replace("visitscotland:", "");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
                df.setTimeZone(TimeZone.getTimeZone("UTC"));
                String lastModified  = df.format(unpublished.getProperty("hippostdpubwf:lastModificationDate").getDate().getTime());
                String lastModifiedBy = unpublished.getProperty("hippostdpubwf:lastModifiedBy").getString();

                docModels.add(new TranslationModel(englishDoc.getHandle().getIdentifier(), displayName,
                        infoStatus.toString(), translationPriority, translatedLocales, sendForTranslationLocales,
                        primaryNodeType, lastModified,lastModifiedBy, getDocumentPublishStatus(englishDoc)));
            }
        } catch (RepositoryException ex) {
            // TODO propagate this error to report frontend
            log.error("Repository exception whilst retrieving untranslated documents", ex);
            return Collections.emptyList();
        }

        return docModels;
    }

    public Set<String> getPageTypes() {
        try {
            if (cachedPageTypes == null) cachedPageTypes = getTypesDeriving("visitscotland:Page");
        } catch (RepositoryException ex) {
            // FIXME
            log.error("Repo exception", ex);
            return Collections.emptySet();
        }

        return cachedPageTypes;
    }

    public Set<String> getModuleTypes() {
        try {
            if (cachedModuleTypes == null) cachedModuleTypes = getTypesDeriving("visitscotland:basedocument");
        } catch (RepositoryException ex) {
            // FIXME
            log.error("Repo exception", ex);
            return Collections.emptySet();
        }

        return cachedModuleTypes;
    }


    private Set<String> getTypesDeriving(String supertype) throws RepositoryException {
        String query = String.format("//element(*, hipposysedit:nodetype)[jcr:contains(@hipposysedit:supertype, \"%s\")]/../..", supertype);
        RowIterator result = sessionFactory.getJcrSession().getWorkspace().getQueryManager().createQuery(query, "xpath").execute().getRows();
        Set<String> types = new HashSet<>();

        while (result.hasNext()) {
            Node node = result.nextRow().getNode();
            types.add(node.getName());
        }

        return types;
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

    private static String getImageLink(String locale) {
        if (locale.equals("en")) locale = "uk";
        String url = "./wicket/resource/org.hippoecm.frontend.translation.LocaleProviderPlugin/icons/flags/flag-16_" + locale +".png";
        return "<img src=\"" + url + "\" style=\"display: inline\" />";
    }

    private static TranslationPriority getNodeTranslationPriority(JcrDocument document) throws RepositoryException{
        Node node =  document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
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

    private static TranslationStatus getDocumentTranslationStatus(JcrDocument englishDoc, String locale) throws RepositoryException{
        // TODO has Erik already written code to do this? Look into how the translationDiff field works
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

    private static List<JcrDocument> getAllEnglishDocuments() throws RepositoryException {
        List<NodeDecorator> nodes =  queryNodes( "select * from visitscotland:basedocument where hippostd:state = 'unpublished' and hippotranslation:locale = 'en'");
        List<JcrDocument> jcrDocuments = new ArrayList<>();
        for (NodeDecorator node : nodes) {
            jcrDocuments.add(new JcrDocument(node));
        }
        return jcrDocuments;
    }

    private static List<NodeDecorator> queryNodes(String sqlQuery) throws RepositoryException {
        RowIterator allPagesRows = UserSession.get().getJcrSession().getWorkspace()
                .getQueryManager().createQuery(sqlQuery, "sql").execute().getRows();

        List<NodeDecorator> nodes = new ArrayList<>();
        while (allPagesRows.hasNext()) {
            nodes.add((NodeDecorator)allPagesRows.nextRow().getNode());
        }

        return nodes;
    }



}
