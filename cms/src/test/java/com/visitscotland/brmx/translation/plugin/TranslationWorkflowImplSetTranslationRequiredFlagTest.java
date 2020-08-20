package com.visitscotland.brmx.translation.plugin;

import com.visitscotland.brmx.translation.plugin.TranslationWorkflow.SetTranslationRequiredResult;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.WorkflowContext;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.standardworkflow.EditableWorkflow;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import java.rmi.RemoteException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TranslationWorkflowImplSetTranslationRequiredFlagTest {
    private TranslationWorkflowImpl translationWorkflow;
    @Mock
    private WorkflowContext mockContext;
    @Mock
    private Session mockUserSession;
    @Mock
    private Session mockRootSession;
    @Mock
    private Node mockSubject;
    @Mock
    private Node mockUserSessionSubject;
    @Mock
    private Node mockRootSessionSubject;
    @Mock
    private JcrDocumentFactory mockJcrDocumentFactory;
    @Mock
    private TranslationWorkflowImpl.DocumentFactory mockDocumentFactory;

    @BeforeEach
    public void beforeEach() throws Exception {
        when(mockSubject.getIdentifier()).thenReturn("subject-id");
        when(mockUserSessionSubject.isNodeType(eq(HippoTranslationNodeType.NT_TRANSLATED))).thenReturn(true);
        when(mockUserSession.getNodeByIdentifier(eq("subject-id"))).thenReturn(mockUserSessionSubject);
        when(mockRootSession.getNodeByIdentifier(eq("subject-id"))).thenReturn(mockRootSessionSubject);
        translationWorkflow = new TranslationWorkflowImpl(mockContext, mockUserSession, mockRootSession, mockSubject,
                mockJcrDocumentFactory, mockDocumentFactory);
    }

    @Test
    public void setTranslationRequiredFlag_notTranslatable() throws Exception {
        // When the document is not a visitscotland:translatable type, expect it to not throw an exception
        JcrDocument mockRootDocument = mock(JcrDocument.class);
        when(mockJcrDocumentFactory.createFromNode(same(mockRootSessionSubject))).thenReturn(mockRootDocument);
        when(mockRootDocument.isNodeType(eq(TranslationWorkflowImpl.VS_TRANSLATABLE))).thenReturn(false);
        SetTranslationRequiredResult result = translationWorkflow.setTranslationRequiredFlag();
        assertNotNull(result);
        assertTrue(result.getFlaggedNodes().isEmpty());
        assertTrue(result.getNodesBlockingTranslation().isEmpty());
    }

    @Test
    public void setTranslationRequiredFlag_noTranslations() throws Exception {
        // When a document has no translations it should not attempt to save the session
        JcrDocument mockRootDocument = mock(JcrDocument.class);
        when(mockJcrDocumentFactory.createFromNode(same(mockRootSessionSubject))).thenReturn(mockRootDocument);
        when(mockRootDocument.isNodeType(eq(TranslationWorkflowImpl.VS_TRANSLATABLE))).thenReturn(true);
        when(mockRootDocument.getTranslations()).thenReturn(Collections.emptySet());
        SetTranslationRequiredResult result = translationWorkflow.setTranslationRequiredFlag();
        assertNotNull(result);
        assertTrue(result.getFlaggedNodes().isEmpty());
        assertTrue(result.getNodesBlockingTranslation().isEmpty());
        verify(mockRootSession, never()).save();
    }

    @Test
    public void setTranslationRequiredFlag_withTranslationBeingEdited() throws Exception {
        // When a document has a translation that is already being edited it should not save the session and should
        // dispose of any documents made editable
        JcrDocument mockRootDocument = mock(JcrDocument.class);
        Node rootHandle = mock(Node.class);
        when(mockRootDocument.getHandle()).thenReturn(rootHandle);
        when(mockRootDocument.isDraftBeingEdited()).thenReturn(true);
        when(mockJcrDocumentFactory.createFromNode(same(mockRootSessionSubject))).thenReturn(mockRootDocument);
        when(mockRootDocument.isNodeType(eq(TranslationWorkflowImpl.VS_TRANSLATABLE))).thenReturn(true);
        Set<JcrDocument> translations = new HashSet<>();
        Node doc1Handle = mock(Node.class);
        Node doc1Editable = mock(Node.class);
        EditableWorkflow doc1Workflow = mock(EditableWorkflow.class);
        Node doc2Handle = mock(Node.class);
        Node doc2Editable = mock(Node.class);
        EditableWorkflow doc2Workflow = mock(EditableWorkflow.class);
        Node doc3Handle = mock(Node.class);
        Node doc3Editable = mock(Node.class);
        EditableWorkflow doc3Workflow = mock(EditableWorkflow.class);
        Node doc4Handle = mock(Node.class);
        Node doc4Editable = mock(Node.class);
        EditableWorkflow doc4Workflow = mock(EditableWorkflow.class);
        translations.add(initialiseTranslatedDocument(false, false, doc1Handle, doc1Editable, doc1Workflow));
        translations.add(initialiseTranslatedDocument(true, false, doc2Handle, doc2Editable, doc2Workflow));
        translations.add(initialiseTranslatedDocument(false, true, doc3Handle, doc3Editable, doc3Workflow));
        translations.add(initialiseTranslatedDocument(true, false, doc4Handle, doc4Editable, doc4Workflow));

        when(mockRootDocument.getTranslations()).thenReturn(translations);
        SetTranslationRequiredResult result = translationWorkflow.setTranslationRequiredFlag();

        assertNotNull(result);
        assertTrue(result.getFlaggedNodes().isEmpty());
        assertFalse(result.getNodesBlockingTranslation().isEmpty());

        assertTrue(result.getNodesBlockingTranslation().contains(rootHandle));
        assertTrue(result.getNodesBlockingTranslation().contains(doc2Handle));
        assertTrue(result.getNodesBlockingTranslation().contains(doc3Handle));
        assertTrue(result.getNodesBlockingTranslation().contains(doc4Handle));

        verify(mockRootSession, never()).save();

        verify(doc1Workflow).obtainEditableInstance();
        verify(doc2Workflow, never()).obtainEditableInstance();
        verify(doc3Workflow).obtainEditableInstance();
        verify(doc4Workflow, never()).obtainEditableInstance();

        verify(doc1Workflow, never()).commitEditableInstance();
        verify(doc2Workflow, never()).commitEditableInstance();
        verify(doc3Workflow, never()).commitEditableInstance();
        verify(doc4Workflow, never()).commitEditableInstance();

        verify(doc1Workflow).disposeEditableInstance();
        verify(doc2Workflow, never()).disposeEditableInstance();
        verify(doc3Workflow, never()).disposeEditableInstance();
        verify(doc4Workflow, never()).disposeEditableInstance();
    }

    @Test
    public void setTranslationRequiredFlag_noTranslationsBeingEdited() throws Exception {
        // When a document has translations and none are being edited it should set the flag
        JcrDocument mockRootDocument = mock(JcrDocument.class);
        when(mockJcrDocumentFactory.createFromNode(same(mockRootSessionSubject))).thenReturn(mockRootDocument);
        when(mockRootDocument.isNodeType(eq(TranslationWorkflowImpl.VS_TRANSLATABLE))).thenReturn(true);
        Set<JcrDocument> translations = new HashSet<>();
        Node doc1Handle = mock(Node.class);
        Node doc1Editable = mock(Node.class);
        EditableWorkflow doc1Workflow = mock(EditableWorkflow.class);
        Node doc2Handle = mock(Node.class);
        Node doc2Editable = mock(Node.class);
        EditableWorkflow doc2Workflow = mock(EditableWorkflow.class);
        Node doc3Handle = mock(Node.class);
        Node doc3Editable = mock(Node.class);
        EditableWorkflow doc3Workflow = mock(EditableWorkflow.class);
        Node doc4Handle = mock(Node.class);
        Node doc4Editable = mock(Node.class);
        EditableWorkflow doc4Workflow = mock(EditableWorkflow.class);
        translations.add(initialiseTranslatedDocument(false, false, doc1Handle, doc1Editable, doc1Workflow));
        translations.add(initialiseTranslatedDocument(false, false, doc2Handle, doc2Editable, doc2Workflow));
        translations.add(initialiseTranslatedDocument(false, false, doc3Handle, doc3Editable, doc3Workflow));
        translations.add(initialiseTranslatedDocument(false, false, doc4Handle, doc4Editable, doc4Workflow));

        when(mockRootDocument.getTranslations()).thenReturn(translations);
        SetTranslationRequiredResult result = translationWorkflow.setTranslationRequiredFlag();

        assertNotNull(result);
        assertFalse(result.getFlaggedNodes().isEmpty());
        assertTrue(result.getNodesBlockingTranslation().isEmpty());

        assertTrue(result.getFlaggedNodes().contains(doc1Handle));
        assertTrue(result.getFlaggedNodes().contains(doc2Handle));
        assertTrue(result.getFlaggedNodes().contains(doc3Handle));
        assertTrue(result.getFlaggedNodes().contains(doc4Handle));

        verify(mockRootSession).save();

        verify(doc1Workflow).obtainEditableInstance();
        verify(doc2Workflow).obtainEditableInstance();
        verify(doc3Workflow).obtainEditableInstance();
        verify(doc4Workflow).obtainEditableInstance();

        verify(doc1Workflow).commitEditableInstance();
        verify(doc2Workflow).commitEditableInstance();
        verify(doc3Workflow).commitEditableInstance();
        verify(doc4Workflow).commitEditableInstance();

        verify(doc1Workflow, never()).disposeEditableInstance();
        verify(doc2Workflow, never()).disposeEditableInstance();
        verify(doc3Workflow, never()).disposeEditableInstance();
        verify(doc4Workflow, never()).disposeEditableInstance();

        verify(doc1Editable).setProperty(eq("visitscotland:translationFlag"), eq(true));
        verify(doc2Editable).setProperty(eq("visitscotland:translationFlag"), eq(true));
        verify(doc3Editable).setProperty(eq("visitscotland:translationFlag"), eq(true));
        verify(doc4Editable).setProperty(eq("visitscotland:translationFlag"), eq(true));
    }

    private JcrDocument initialiseTranslatedDocument(boolean beingEdited, boolean throwCheckoutException, Node handle, Node editableNode, EditableWorkflow mockWorkflow) throws RemoteException, WorkflowException, RepositoryException {
        JcrDocument foreignDocument = mock(JcrDocument.class);
        when(foreignDocument.getHandle()).thenReturn(handle);
        when(foreignDocument.isDraftBeingEdited()).thenReturn(beingEdited);
        if (!beingEdited) {
            Document mockEditableDocument = mock(Document.class);
            when(mockDocumentFactory.createFromNode(same(handle))).thenReturn(mockEditableDocument);
            when(mockContext.getWorkflow(eq("editing"), same(mockEditableDocument))).thenReturn(mockWorkflow);
            if (throwCheckoutException) {
                when(mockWorkflow.obtainEditableInstance()).thenThrow(new WorkflowException(""));
            } else {
                when(mockEditableDocument.getNode(same(mockRootSession))).thenReturn(editableNode);
                when(mockWorkflow.obtainEditableInstance()).thenReturn(mockEditableDocument);
            }
        }
        return foreignDocument;
    }

}
