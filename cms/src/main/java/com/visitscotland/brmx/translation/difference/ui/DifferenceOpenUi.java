package com.visitscotland.brmx.translation.difference.ui;

import com.visitscotland.brmx.translation.TranslationService;
import com.visitscotland.brmx.translation.difference.*;
import com.visitscotland.brmx.translation.difference.ui.model.FieldDifferenceModel;
import com.visitscotland.brmx.translation.difference.ui.model.MultipleFieldDifferenceModel;
import com.visitscotland.brmx.translation.difference.ui.model.SingleFieldDifferenceModel;
import com.visitscotland.brmx.translation.plugin.JcrDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.context.annotation.RequestScope;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestScope
public class DifferenceOpenUi {
    public static final String ATTR_NODE_ID = "nodeId";
    public static final String ATTR_HAS_TRANSLATION_PENDING = "hasTranslationPending";
    public static final String ATTR_TRANSLATION_FLAG = "translationFlag";
    public static final String ATTR_ERROR_MESSAGE = "errorMessage";
    public static final String ATTR_STACK_TRACE = "stackTrace";
    public static final String ATTR_FIELD_LIST = "fieldList";
    public static final String ENGLISH_TEMPLATE = "diffButtonContentEnglish";
    public static final String FOREIGN_TEMPLATE = "diffButtonContent";
    public static final String ERROR_TEMPLATE = "diffViewError";
    public static final String DIFF_VIEW_TEMPLATE = "diffView";

    private TranslationService translationService;

    @Autowired
    public DifferenceOpenUi(TranslationService translationService) {
        this.translationService = translationService;
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
            DocumentDifference diff = translationService.getDocumentDifference(nodeId);
            if ( null != diff ) {
                List<FieldDifferenceModel> fieldList = new ArrayList<>();
                for (FieldDifference field : diff.getDifferences()) {
                    if (field.getLatest() instanceof MultipleField) {
                        List<FieldValue> previousValueList = ((MultipleField) field.getPrevious()).getField();
                        List<FieldValue> latestValueList = ((MultipleField) field.getLatest()).getField();
                        MultipleFieldDifferenceModel fieldDiff = new MultipleFieldDifferenceModel(field.getCaption(), previousValueList, latestValueList);
                        fieldList.add(fieldDiff);
                    } else {
                        SingleField previousValue = (SingleField) field.getPrevious();
                        SingleField latestValue = (SingleField) field.getLatest();
                        SingleFieldDifferenceModel fieldDiff = new SingleFieldDifferenceModel(
                                field.getCaption(),
                                previousValue.getField().getValue(),
                                latestValue.getField().getValue());
                        fieldList.add(fieldDiff);
                    }
                }
                model.addAttribute(ATTR_FIELD_LIST, fieldList);
            } else {
                return gotoErrorPage(new IllegalStateException("No difference found for document."), model);
            }
            return DIFF_VIEW_TEMPLATE;
        } catch(RepositoryException | IOException ex) {
            return gotoErrorPage(ex, model);
        }
    }

    public String gotoErrorPage(Exception cause, Model model) {
        model.addAttribute(ATTR_ERROR_MESSAGE, cause.getMessage());
        model.addAttribute(ATTR_STACK_TRACE, ExceptionUtils.getStackTrace(cause));
        return ERROR_TEMPLATE;
    }
}
