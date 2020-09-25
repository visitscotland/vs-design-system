package com.visitscotland.brmx.translation.difference.ui;

import com.visitscotland.brmx.translation.TranslationService;
import com.visitscotland.brmx.translation.difference.*;
import com.visitscotland.brmx.translation.difference.ui.model.FieldDifferenceModel;
import com.visitscotland.brmx.translation.plugin.JcrDocument;
import org.apache.commons.math3.analysis.function.Sin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import javax.jcr.RepositoryException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DifferenceOpenUiTest {
    private DifferenceOpenUi differenceOpenUi;
    @Mock
    TranslationService mockTranslationService;
    @Mock
    Model mockModel;

    @BeforeEach
    public void beforeEach() {
        differenceOpenUi = new DifferenceOpenUi(mockTranslationService);
    }

    @DisplayName("openUiButton - an english document, no translations pending")
    @Test
    public void buttonWithEnglishDocument() throws Exception {
        JcrDocument mockJcrDocument = mock(JcrDocument.class);
        when(mockJcrDocument.getLocaleName()).thenReturn("en");
        when(mockTranslationService.getDocument(eq("nodeId"))).thenReturn(mockJcrDocument);
        when(mockTranslationService.hasPendingTranslations(eq(mockJcrDocument))).thenReturn(false);

        String template = differenceOpenUi.openUiButton("nodeId", mockModel);
        assertThat(template).isEqualTo(DifferenceOpenUi.ENGLISH_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_HAS_TRANSLATION_PENDING), eq(false));
        verify(mockModel, times(2)).addAttribute(any(), any());
    }

    @DisplayName("openUiButton - an english document, with translations pending")
    @Test
    public void buttonWithEnglishDocumentAndTranslationsPending() throws Exception {
        JcrDocument mockJcrDocument = mock(JcrDocument.class);
        when(mockJcrDocument.getLocaleName()).thenReturn("en");
        when(mockTranslationService.getDocument(eq("nodeId"))).thenReturn(mockJcrDocument);
        when(mockTranslationService.hasPendingTranslations(eq(mockJcrDocument))).thenReturn(true);

        String template = differenceOpenUi.openUiButton("nodeId", mockModel);
        assertThat(template).isEqualTo(DifferenceOpenUi.ENGLISH_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_HAS_TRANSLATION_PENDING), eq(true));
        verify(mockModel, times(2)).addAttribute(any(), any());
    }

    @DisplayName("openUiButton - a foreign document, no translation pending")
    @Test
    public void buttonWithForeignDocument() throws Exception {
        JcrDocument mockJcrDocument = mock(JcrDocument.class);
        when(mockJcrDocument.getLocaleName()).thenReturn("es");
        when(mockTranslationService.getDocument(eq("nodeId"))).thenReturn(mockJcrDocument);
        when(mockTranslationService.getTranslationFlag(eq(mockJcrDocument))).thenReturn(false);

        String template = differenceOpenUi.openUiButton("nodeId", mockModel);
        assertThat(template).isEqualTo(DifferenceOpenUi.FOREIGN_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_TRANSLATION_FLAG), eq(false));
        verify(mockModel, times(2)).addAttribute(any(), any());
    }

    @DisplayName("openUiButton - a foreign document, no translation pending")
    @Test
    public void buttonWithForeignDocumentWithTranslationFlag() throws Exception {
        JcrDocument mockJcrDocument = mock(JcrDocument.class);
        when(mockJcrDocument.getLocaleName()).thenReturn("es");
        when(mockTranslationService.getDocument(eq("nodeId"))).thenReturn(mockJcrDocument);
        when(mockTranslationService.getTranslationFlag(eq(mockJcrDocument))).thenReturn(true);

        String template = differenceOpenUi.openUiButton("nodeId", mockModel);
        assertThat(template).isEqualTo(DifferenceOpenUi.FOREIGN_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_TRANSLATION_FLAG), eq(true));
        verify(mockModel, times(2)).addAttribute(any(), any());
    }

    @DisplayName("openUiButton - exception")
    @Test
    public void openUiButtonException() throws Exception {
        JcrDocument mockJcrDocument = mock(JcrDocument.class);
        when(mockTranslationService.getDocument(eq("nodeId"))).thenThrow(new RepositoryException("message"));

        String template = differenceOpenUi.openUiButton("nodeId", mockModel);
        assertThat(template).isEqualTo(DifferenceOpenUi.ERROR_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_ERROR_MESSAGE), eq("message"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_STACK_TRACE), anyString());
        verify(mockModel, times(3)).addAttribute(any(), any());
    }

    @DisplayName("diffView - document has no difference saved")
    @Test
    public void diffViewNoDiff() throws Exception {
        when(mockTranslationService.getDocumentDifference(eq("nodeId"))).thenReturn(null);

        String template = differenceOpenUi.diffView("nodeId", mockModel);

        assertThat(template).isEqualTo(DifferenceOpenUi.ERROR_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_ERROR_MESSAGE), anyString());
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_STACK_TRACE), anyString());
        verify(mockModel, times(3)).addAttribute(any(), any());
    }

    @DisplayName("diffView - RepositoryException thrown getting difference")
    @Test
    public void diffViewNoDiffRepositoryException() throws Exception {
        when(mockTranslationService.getDocumentDifference(eq("nodeId"))).thenThrow(new RepositoryException("message"));

        String template = differenceOpenUi.diffView("nodeId", mockModel);

        assertThat(template).isEqualTo(DifferenceOpenUi.ERROR_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_ERROR_MESSAGE), eq("message"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_STACK_TRACE), anyString());
        verify(mockModel, times(3)).addAttribute(any(), any());
    }

    @DisplayName("diffView - no fields in the DocumentDifference")
    @Test
    public void diffViewNoFieldsInDiff() throws Exception {
        DocumentDifference diff = new DocumentDifference();
        when(mockTranslationService.getDocumentDifference(eq("nodeId"))).thenReturn(diff);

        String template = differenceOpenUi.diffView("nodeId", mockModel);

        assertThat(template).isEqualTo(DifferenceOpenUi.DIFF_VIEW_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        ArgumentCaptor<List<FieldDifferenceModel>> fieldModelCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_FIELD_LIST), fieldModelCaptor.capture());
        verify(mockModel, times(2)).addAttribute(any(), any());

        assertThat(fieldModelCaptor.getValue()).isNotNull().isEmpty();
    }

    @DisplayName("diffView - has fields in the DocumentDifference")
    @Test
    public void diffViewWithFields() throws Exception {
        DocumentDifference diff = new DocumentDifference();
        FieldDifference multipleDiff = new FieldDifference();
        multipleDiff.setLatest(createValidMultipleField());
        multipleDiff.setPrevious(createValidMultipleField());
        diff.getDifferences().add(multipleDiff);
        FieldDifference singleDiff = new FieldDifference();
        singleDiff.setLatest(createValidSingleField());
        singleDiff.setPrevious(createValidSingleField());
        diff.getDifferences().add(singleDiff);
        when(mockTranslationService.getDocumentDifference(eq("nodeId"))).thenReturn(diff);

        String template = differenceOpenUi.diffView("nodeId", mockModel);

        assertThat(template).isEqualTo(DifferenceOpenUi.DIFF_VIEW_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        ArgumentCaptor<List<FieldDifferenceModel>> fieldModelCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_FIELD_LIST), fieldModelCaptor.capture());
        verify(mockModel, times(2)).addAttribute(any(), any());

        assertThat(fieldModelCaptor.getValue()).isNotNull().isNotEmpty().hasSize(2);
    }

    protected MultipleField createValidMultipleField() {
        MultipleField field = new MultipleField();
        field.setField(Arrays.asList(new FieldValue("value")));
        return field;
    }

    protected SingleField createValidSingleField() {
        SingleField field = new SingleField();
        field.setField(new FieldValue("value"));
        return field;
    }

    @DisplayName("diffView - IOException thrown getting the DocumentDifference")
    @Test
    public void diffViewIOException() throws Exception {
        when(mockTranslationService.getDocumentDifference(eq("nodeId"))).thenThrow(new IOException("message"));

        String template = differenceOpenUi.diffView("nodeId", mockModel);

        assertThat(template).isEqualTo(DifferenceOpenUi.ERROR_TEMPLATE);
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_NODE_ID), eq("nodeId"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_ERROR_MESSAGE), eq("message"));
        verify(mockModel).addAttribute(eq(DifferenceOpenUi.ATTR_STACK_TRACE), anyString());
        verify(mockModel, times(3)).addAttribute(any(), any());
    }
}
