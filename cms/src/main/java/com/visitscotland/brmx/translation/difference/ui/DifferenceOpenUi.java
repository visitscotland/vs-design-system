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
    public String openUiFragment(@PathVariable String nodeId, Model model) {
        try {
            JcrDocument document = translationService.getDocument(nodeId);
            model.addAttribute("nodeId", nodeId);
            if ( "en".equals(document.getLocaleName()) ) {
                model.addAttribute("hasTranslationPending", translationService.hasPendingTranslations(document));
                return "diffButtonContentEnglish";
            } else {
                model.addAttribute("translationFlag", translationService.getTranslationFlag(document));
                return "diffButtonContent";
            }

        } catch(RepositoryException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("stackTrace", ExceptionUtils.getStackTrace(ex));
            return gotoErrorPage(ex, model);
        }
    }

    @GetMapping("/vs-openui/diff/{nodeId}/view")
    public String diffView(@PathVariable String nodeId, Model model) {
        try {
            model.addAttribute("nodeId", nodeId);
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
                model.addAttribute("fieldList", fieldList);
            } else {
                return gotoErrorPage(new IllegalStateException("No difference found for document."), model);
            }
            return "diffView";
        } catch(RepositoryException | IOException ex) {
            return gotoErrorPage(ex, model);
        }
    }

    public String gotoErrorPage(Exception cause, Model model) {
        model.addAttribute("errorMessage", cause.getMessage());
        model.addAttribute("stackTrace", ExceptionUtils.getStackTrace(cause));
        return "diffViewError";
    }
}
