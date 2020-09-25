package com.visitscotland.brmx.translation;

import com.visitscotland.brmx.translation.plugin.JcrDocument;
import com.visitscotland.brmx.translation.plugin.JcrDocumentFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.util.Set;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
public class TranslationService {
    private Session jcrSession;
    private JcrDocumentFactory jcrDocumentFactory;

    @Autowired
    public TranslationService(Session jcrSession, JcrDocumentFactory jcrDocumentFactory) {
        this.jcrSession = jcrSession;
        this.jcrDocumentFactory = jcrDocumentFactory;
    }

    public JcrDocument getDocument(String nodeId) throws RepositoryException {
        Node jcrNode = jcrSession.getNodeByIdentifier(nodeId);
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
}
