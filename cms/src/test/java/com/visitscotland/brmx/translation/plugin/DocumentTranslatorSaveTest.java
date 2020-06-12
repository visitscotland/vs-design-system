package com.visitscotland.brmx.translation.plugin;

import org.hippoecm.frontend.translation.components.document.FolderTranslation;
import org.hippoecm.repository.api.Document;
import org.hippoecm.repository.api.HippoWorkspace;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.api.WorkflowManager;
import org.hippoecm.repository.standardworkflow.DefaultWorkflow;
import org.hippoecm.repository.translation.TranslationWorkflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DocumentTranslatorSaveTest {
    @Mock
    Session mockSession;
    private DocumentTranslator translator;

    @BeforeEach
    public void beforeEach() {
        translator = new DocumentTranslator();
    }

    @Test
    public void saveFolder_immutable() {
        FolderTranslation mockFolder = createMockFolderTranslation(false);

        assertThrows(UnsupportedOperationException.class,
                () -> translator.saveFolder(mockFolder, mockSession, "fr"));
    }

    @Test
    public void saveFolder_getNodeByIdentifier_RepositoryException() throws Exception {
        String nodeId = "ft1";
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId);
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenThrow(new RepositoryException());

        assertFalse(translator.saveFolder(mockFolder, mockSession, "es"));
    }

    @Test
    public void saveFolder_getSession_RepositoryException() throws Exception {
        String nodeId = "ft2";
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId);
        Node mockNode = mock(Node.class);
        when(mockNode.getSession()).thenThrow(new RepositoryException());
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);

        assertFalse(translator.saveFolder(mockFolder, mockSession, "de"));
    }

    @Test
    public void saveFolder_getWorkflowManager_RepositoryException() throws Exception {
        String nodeId = "ft3";
        HippoWorkspace mockWorkspace = mock(HippoWorkspace.class);
        when(mockWorkspace.getWorkflowManager()).thenThrow(new RepositoryException());
        Session mockNodeSession = createMockNodeSession(mockWorkspace);
        Node mockNode = createMockNode(mockNodeSession);
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId);

        assertFalse(translator.saveFolder(mockFolder, mockSession, "it"));
    }

    @Test
    public void saveFolder_getWorkflow_RepositoryException() throws Exception {
        String nodeId = "ft4";
        WorkflowManager mockManager = mock(WorkflowManager.class);
        when(mockManager.getWorkflow(anyString(), any(Node.class))).thenThrow(new RepositoryException());
        HippoWorkspace mockWorkspace = createMockHippoWorkspace(mockManager);
        Session mockNodeSession = createMockNodeSession(mockWorkspace);
        Node mockNode = createMockNode(mockNodeSession);
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId);

        assertFalse(translator.saveFolder(mockFolder, mockSession, "en"));
    }

    @Test
    public void saveFolder_addTranslation_WorkflowException() throws Exception {
        String nodeId = "ft5";
        TranslationWorkflow mockWorkflow = mock(TranslationWorkflow.class);
        when(mockWorkflow.addTranslation(anyString(), anyString())).thenThrow(new WorkflowException("workflow"));
        WorkflowManager mockManager = createMockWorkflowManager(mockWorkflow);
        HippoWorkspace mockWorkspace = createMockHippoWorkspace(mockManager);
        Session mockNodeSession = createMockNodeSession(mockWorkspace);
        Node mockNode = createMockNode(mockNodeSession);
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId, "translatedName", "translatedUrl");

        assertFalse(translator.saveFolder(mockFolder, mockSession, "nn"));
    }

    @Test
    public void saveFolder_addTranslation_RepositoryException() throws Exception {
        String nodeId = "ft6";
        TranslationWorkflow mockWorkflow = mock(TranslationWorkflow.class);
        when(mockWorkflow.addTranslation(anyString(), anyString())).thenThrow(new RepositoryException());
        WorkflowManager mockManager = createMockWorkflowManager(mockWorkflow);
        HippoWorkspace mockWorkspace = createMockHippoWorkspace(mockManager);
        Session mockNodeSession = createMockNodeSession(mockWorkspace);
        Node mockNode = createMockNode(mockNodeSession);
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId, "translatedName", "translatedUrl");

        assertFalse(translator.saveFolder(mockFolder, mockSession, "nl"));
    }

    @Test
    public void saveFolder_addTranslation_RemoteException() throws Exception {
        String nodeId = "ft7";
        TranslationWorkflow mockWorkflow = mock(TranslationWorkflow.class);
        when(mockWorkflow.addTranslation(anyString(), anyString())).thenThrow(new RemoteException());
        WorkflowManager mockManager = createMockWorkflowManager(mockWorkflow);
        HippoWorkspace mockWorkspace = createMockHippoWorkspace(mockManager);
        Session mockNodeSession = createMockNodeSession(mockWorkspace);
        Node mockNode = createMockNode(mockNodeSession);
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId, "translatedName", "translatedUrl");

        assertFalse(translator.saveFolder(mockFolder, mockSession, "no"));
    }

    @Test
    public void saveFolder_nameFr_not_equal_urlFr() throws Exception {
        String nodeId = "ft8";

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);

        Document mockDocument = mock(Document.class);
        TranslationWorkflow mockWorkflow = mock(TranslationWorkflow.class);
        when(mockWorkflow.addTranslation(anyString(), anyString())).thenReturn(mockDocument);
        WorkflowManager mockManager = createMockWorkflowManager(mockWorkflow);
        HippoWorkspace mockWorkspace = createMockHippoWorkspace(mockManager);
        Session mockNodeSession = createMockNodeSession(mockWorkspace);
        Node mockNode = createMockNode(mockNodeSession);
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId, "translatedName", "translatedUrl");

        DefaultWorkflow mockDefaultWorkflow = mock(DefaultWorkflow.class);
        when(mockManager.getWorkflow(eq("core"), any(Document.class))).thenReturn(mockDefaultWorkflow);

        assertTrue(translator.saveFolder(mockFolder, mockSession, "no"));

        verify(mockDefaultWorkflow).setDisplayName(eq("translatedName"));
    }

    @Test
    public void saveFolder_nameFr_equals_urlFr() throws Exception {
        String nodeId = "ft8";

        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);

        Document mockDocument = mock(Document.class);
        TranslationWorkflow mockWorkflow = mock(TranslationWorkflow.class);
        when(mockWorkflow.addTranslation(anyString(), anyString())).thenReturn(mockDocument);
        WorkflowManager mockManager = createMockWorkflowManager(mockWorkflow);
        HippoWorkspace mockWorkspace = createMockHippoWorkspace(mockManager);
        Session mockNodeSession = createMockNodeSession(mockWorkspace);
        Node mockNode = createMockNode(mockNodeSession);
        when(mockSession.getNodeByIdentifier(eq(nodeId))).thenReturn(mockNode);
        FolderTranslation mockFolder = createMockFolderTranslation(true, nodeId, "translatedName", "translatedName");

        assertTrue(translator.saveFolder(mockFolder, mockSession, "no"));

    }

    private FolderTranslation createMockFolderTranslation(boolean editable) {
        FolderTranslation ft = mock(FolderTranslation.class);
        when(ft.isEditable()).thenReturn(editable);
        return ft;
    }

    private FolderTranslation createMockFolderTranslation(boolean editable, String id) {
        FolderTranslation ft = createMockFolderTranslation(editable);
        when(ft.getId()).thenReturn(id);
        return ft;
    }

    private FolderTranslation createMockFolderTranslation(boolean editable, String id, String translatedName, String translatedUrl) {
        FolderTranslation ft = createMockFolderTranslation(editable, id);
        when(ft.getNamefr()).thenReturn(translatedName);
        when(ft.getUrlfr()).thenReturn(translatedUrl);
        return ft;
    }

    private Node createMockNode(Session mockNodeSession) throws Exception {
        Node mockNode = mock(Node.class);
        when(mockNode.getSession()).thenReturn(mockNodeSession);
        return mockNode;
    }

    private Session createMockNodeSession(HippoWorkspace mockWorkspace) {
        Session mockNodeSession = mock(Session.class);
        when(mockNodeSession.getWorkspace()).thenReturn(mockWorkspace);
        return mockNodeSession;
    }

    private HippoWorkspace createMockHippoWorkspace(WorkflowManager mockManager) throws Exception {
        HippoWorkspace mockWorkspace = mock(HippoWorkspace.class);
        when(mockWorkspace.getWorkflowManager()).thenReturn(mockManager);
        return mockWorkspace;
    }

    private WorkflowManager createMockWorkflowManager(TranslationWorkflow mockWorkflow) throws Exception {
        WorkflowManager mockManager = mock(WorkflowManager.class);
        when(mockManager.getWorkflow(eq("translation"), any(Node.class))).thenReturn(mockWorkflow);
        return mockManager;
    }
}
