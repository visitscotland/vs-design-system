package com.visitscotland.brmx.translation.difference.ui;

import com.visitscotland.brmx.translation.SessionFactory;
import com.visitscotland.brmx.translation.TranslationService;
import com.visitscotland.brmx.translation.plugin.JcrDocument;
import com.visitscotland.brmx.translation.plugin.JcrDocumentFactory;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.request.resource.ResourceReference;
import org.hippoecm.frontend.plugins.standards.image.CachingImage;
import org.hippoecm.frontend.service.IconSize;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.annotation.RequestScope;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestScope
public class DifferenceOpenUi {
    public static final String ATTR_NODE_ID = "nodeId";
    public static final String ATTR_HAS_TRANSLATION_PENDING = "hasTranslationPending";
    public static final String ATTR_HAS_CHANGED = "hasDocumentChanged";
    public static final String ATTR_TRANSLATION_FLAG = "translationFlag";
    public static final String ATTR_ERROR_MESSAGE = "errorMessage";
    public static final String ATTR_STACK_TRACE = "stackTrace";
    public static final String ATTR_DIFF_CONTENT = "diffContent";
    public static final String ATTR_TRANSLATION_LIST = "translations";
    public static final String ENGLISH_TEMPLATE = "diffButtonContentEnglish";
    public static final String FOREIGN_TEMPLATE = "diffButtonContent";
    public static final String ERROR_TEMPLATE = "diffViewError";
    public static final String DIFF_VIEW_TEMPLATE = "diffView";
    public static final String CONFIRM_VIEW_TEMPLATE = "dialogTranslationConfirm";

    private TranslationService translationService;
    private JcrDocumentFactory jcrDocumentFactory;
    private SessionFactory sessionFactory;

    @Autowired
    public DifferenceOpenUi(TranslationService translationService,
                            JcrDocumentFactory jcrDocumentFactory,
                            SessionFactory sessionFactory) {
        this.translationService = translationService;
        this.jcrDocumentFactory = jcrDocumentFactory;
        this.sessionFactory = sessionFactory;
    }

    @GetMapping("/vs-openui/diff")
    public String openUiComponent() {
        return "diffButtonWrapper";
    }

    @GetMapping("/vs-openui/diff/{nodeId}")
    public String openUiButton(@PathVariable String nodeId, Model model) {
        try {
            model.addAttribute(ATTR_NODE_ID, nodeId);
            JcrDocument document = translationService.getDocument(nodeId);
            if ( "en".equals(document.getLocaleName()) ) {
                model.addAttribute(ATTR_HAS_TRANSLATION_PENDING, translationService.hasPendingTranslations(document));
                model.addAttribute(ATTR_HAS_CHANGED, translationService.hasChangePending(document));
                return ENGLISH_TEMPLATE;
            } else {
                model.addAttribute(ATTR_TRANSLATION_FLAG, translationService.getTranslationFlag(document));
                return FOREIGN_TEMPLATE;
            }
        } catch(RepositoryException ex) {
            return gotoErrorPage(ex, model);
        }
    }

    @GetMapping("/vs-openui/diff/{nodeId}/view")
    public String diffView(@PathVariable String nodeId, Model model) {
        try {
            model.addAttribute(ATTR_NODE_ID, nodeId);
            String diff = translationService.getDocumentDifference(nodeId);
            if ( null != diff ) {
                model.addAttribute(ATTR_DIFF_CONTENT, diff);
            } else {
                return gotoErrorPage(new IllegalStateException("No difference found for document."), model);
            }
            return DIFF_VIEW_TEMPLATE;
        } catch(RepositoryException | IOException ex) {
            return gotoErrorPage(ex, model);
        }
    }

    @GetMapping("/vs-openui/{nodeId}/dialog/translation/confirm")
    public String dialogTranslationConfirm(@PathVariable String nodeId, Model model) {
        // Need a list of all the translation languages that the translation will be copied to
        try {
            Node englishNode = sessionFactory.getJcrSession().getNodeByIdentifier(nodeId);
            JcrDocument jcrDocument = jcrDocumentFactory.createFromNode(englishNode);
            Set<JcrDocument> translationDocuments = jcrDocument.getTranslations();
            List<TranslationDocument> translationList = new ArrayList<>();
            for (JcrDocument jcrTranslation : translationDocuments) {
                HippoNode unpublishedNode = (HippoNode)jcrTranslation.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
                TranslationDocument translationDocument = new TranslationDocument();
                translationDocument.name = unpublishedNode.getDisplayName();
                String localeString = unpublishedNode.getProperty(HippoTranslationNodeType.LOCALE).getString();
                translationDocument.image = localeString;
                translationList.add(translationDocument);
            }
            model.addAttribute(ATTR_TRANSLATION_LIST, translationList);
            model.addAttribute(ATTR_NODE_ID, nodeId);
            return CONFIRM_VIEW_TEMPLATE;
        } catch(RepositoryException ex) {
            return gotoErrorPage(ex, model);
        }
    }

    public class TranslationDocument {
        public String name;
        public String image;
    }

    public String gotoErrorPage(Exception cause, Model model) {
        model.addAttribute(ATTR_ERROR_MESSAGE, cause.getMessage());
        model.addAttribute(ATTR_STACK_TRACE, ExceptionUtils.getStackTrace(cause));
        return ERROR_TEMPLATE;
    }
}
