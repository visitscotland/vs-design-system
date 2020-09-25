package com.visitscotland.brmx.translation.difference.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.translation.TranslationService;
import com.visitscotland.brmx.translation.difference.*;
import com.visitscotland.brmx.translation.plugin.JcrDocument;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.hippoecm.frontend.plugins.standards.diff.DiffService;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestScope
public class DifferenceOpenUi {
    private TranslationService translationService;
    private ObjectMapper objectMapper;
    private DiffService diffService;

    @Autowired
    public DifferenceOpenUi(TranslationService translationService, ObjectMapper objectMapper, DiffService diffService) {
        this.translationService = translationService;
        this.objectMapper = objectMapper;
        this.diffService = diffService;
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
            JcrDocument document = translationService.getDocument(nodeId);
            model.addAttribute("nodeId", nodeId);
            Node unpublishedNode = document.getVariantNode(JcrDocument.VARIANT_UNPUBLISHED);
            if ( unpublishedNode.hasProperty(JcrDocument.VS_TRANSLATION_DIFF) ) {
                DocumentDifference diff = objectMapper.reader().readValue(
                        unpublishedNode.getProperty(JcrDocument.VS_TRANSLATION_DIFF).getString(),
                        DocumentDifference.class);

                List<FieldDifferenceModel> fieldList = new ArrayList<>();
                for ( FieldDifference field : diff.getDifferences() ) {
                    if ( field.getLatest() instanceof MultipleField ) {
                        List<FieldValue> previousValueList = ((MultipleField)field.getPrevious()).getField();
                        List<FieldValue> latestValueList = ((MultipleField)field.getLatest()).getField();
                        MultipleFieldDifferenceModel fieldDiff = new MultipleFieldDifferenceModel(field.getCaption(), previousValueList, latestValueList);
                        fieldList.add(fieldDiff);
                    } else {
                        SingleField previousValue = (SingleField)field.getPrevious();
                        SingleField latestValue = (SingleField)field.getLatest();
                        SingleFieldDifferenceModel fieldDiff = new SingleFieldDifferenceModel(
                                field.getCaption(),
                                previousValue.getField().getValue(),
                                latestValue.getField().getValue());
                        fieldList.add(fieldDiff);
                    }
                }

                model.addAttribute("fieldList", fieldList);
            }
            return "diffView";
        } catch(RepositoryException | IOException ex) {
            return gotoErrorPage(ex, model);
        }
    }

    public List<DiffMatchPatch.Diff> linkedListDiff(List<FieldValue> previous, List<FieldValue> latest) {
        LinkedList<FieldValue> previousList = new LinkedList<>(previous);
        LinkedList<FieldValue> latestList = new LinkedList<>(latest);

        List<DiffMatchPatch.Diff> diffList = new LinkedList<>();
        while ( previousList.size() > 0 ) {
            FieldValue firstValue = previousList.pollFirst();
            int indexInLatest = latestList.indexOf(firstValue);
            if ( indexInLatest == -1 ) {
                // has been deleted
                diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.DELETE, firstValue.getValue()));
            } else if ( indexInLatest > 0 ) {
                // have items in latest that have been inserted
                FieldValue inserted;
                while (!(inserted = latestList.pollFirst()).equals(firstValue)) {
                    diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, inserted.getValue()));
                }
                diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, firstValue.getValue()));
            } else {
                latestList.pollFirst();
                diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.EQUAL, firstValue.getValue()));
            }
        }

        while (latestList.size() > 0) {
            // Any item at the end of the latest list have been inserted
            FieldValue inserted = latestList.pollFirst();
            diffList.add(new DiffMatchPatch.Diff(DiffMatchPatch.Operation.INSERT, inserted.getValue()));
        }

        return diffList;
    }

    public String gotoErrorPage(Exception cause, Model model) {
        model.addAttribute("errorMessage", cause.getMessage());
        model.addAttribute("stackTrace", ExceptionUtils.getStackTrace(cause));
        return "diffViewError";
    }

    public interface FieldDifferenceModel {
        String getFieldName();
        boolean isMultiple();
        List<DiffMatchPatch.Diff> getDiffList();
    }

    public class SingleFieldDifferenceModel implements FieldDifferenceModel {
        private String fieldName;
        private List<DiffMatchPatch.Diff> diffList;

        public SingleFieldDifferenceModel(String fieldName, String previousValue, String latestValue) {
            this.fieldName = fieldName;
            DiffMatchPatch dmp = new DiffMatchPatch();
            diffList = dmp.diffMain(previousValue, latestValue, false);
        }

        @Override
        public String getFieldName() {
            return fieldName;
        }

        @Override
        public boolean isMultiple() {
            return false;
        }

        @Override
        public List<DiffMatchPatch.Diff> getDiffList() {
            return diffList;
        }
    }

    public class MultipleFieldDifferenceModel implements FieldDifferenceModel {
        private String fieldName;
        private List<DiffMatchPatch.Diff> diffList;

        public MultipleFieldDifferenceModel(String fieldName, List<FieldValue> previous, List<FieldValue> latest) {
            this.fieldName = fieldName;
            diffList = linkedListDiff(previous, latest);
        }

        @Override
        public String getFieldName() {
            return fieldName;
        }

        @Override
        public boolean isMultiple() {
            return true;
        }

        @Override
        public List<DiffMatchPatch.Diff> getDiffList() {
            return diffList;
        }
    }
}
