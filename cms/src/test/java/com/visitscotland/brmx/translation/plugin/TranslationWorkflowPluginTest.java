package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.model.IModel;
import org.apache.wicket.util.tester.WicketTester;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.frontend.plugin.IPluginContext;
import org.hippoecm.frontend.plugin.config.IPluginConfig;
import org.hippoecm.frontend.session.UserSession;
import org.hippoecm.repository.HippoStdNodeType;
import org.hippoecm.repository.api.HippoNode;
import org.hippoecm.repository.api.WorkflowDescriptor;
import org.hippoecm.repository.api.WorkflowException;
import org.hippoecm.repository.api.WorkflowManager;
import org.hippoecm.repository.translation.HippoTranslatedNode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TranslationWorkflowPluginTest {
    private WicketTester tester;
    private TranslationWorkflowPlugin plugin;
    @Mock
    private IPluginContext mockContext;
    @Mock
    private IPluginConfig mockConfig;
    @Mock
    private HippoTranslatedNodeFactory mockTranslatedNodeFactory;
    @Mock
    private JcrDocumentFactory mockJcrDocumentFactory;
    @Mock
    private JcrDocument mockDocument;
    @Mock
    private WorkflowDescriptorModel mockDefaultModel;
    @Mock
    private Node mockCurrentNode;
    @Mock
    private UserSessionFactory mockUserSessionFactory;

    @BeforeEach
    public void beforeEach() throws Exception {
        tester = new WicketTester();
        plugin = new TranslationWorkflowPlugin(mockContext, mockConfig, mockTranslatedNodeFactory,
                mockJcrDocumentFactory, mockUserSessionFactory);
        plugin.setDefaultModel(mockDefaultModel);
    }

    @Test
    public void isChangePending_missingVariant() throws Exception {
        when(mockJcrDocumentFactory.createFromNode(same(mockCurrentNode))).thenReturn(mockDocument);
        when(mockDefaultModel.getNode()).thenReturn(mockCurrentNode);
        when(mockDocument.getVariantNode(any())).thenThrow(new RepositoryException());
        assertFalse(plugin.isChangePending());
    }

    @Test
    public void isChangePending_missingProperty() throws Exception {
        when(mockJcrDocumentFactory.createFromNode(same(mockCurrentNode))).thenReturn(mockDocument);
        when(mockDefaultModel.getNode()).thenReturn(mockCurrentNode);
        HippoNode mockUnpublishedNode = mock(HippoNode.class);
        when(mockDocument.getVariantNode(eq(JcrDocument.VARIANT_UNPUBLISHED))).thenReturn(mockUnpublishedNode);
        when(mockUnpublishedNode.hasProperty(eq(HippoStdNodeType.HIPPOSTD_STATESUMMARY))).thenReturn(false);
        assertFalse(plugin.isChangePending());
        verify(mockUnpublishedNode, never()).getProperty(eq(HippoStdNodeType.HIPPOSTD_STATESUMMARY));
    }

    @Test
    public void isChangePending_notChanged() throws RepositoryException {
        when(mockJcrDocumentFactory.createFromNode(same(mockCurrentNode))).thenReturn(mockDocument);
        when(mockDefaultModel.getNode()).thenReturn(mockCurrentNode);
        HippoNode mockUnpublishedNode = mock(HippoNode.class);
        when(mockDocument.getVariantNode(eq(JcrDocument.VARIANT_UNPUBLISHED))).thenReturn(mockUnpublishedNode);
        when(mockUnpublishedNode.hasProperty(eq(HippoStdNodeType.HIPPOSTD_STATESUMMARY))).thenReturn(true);
        Property mockStateSummary = mock(Property.class);
        when(mockStateSummary.getString()).thenReturn("not-changed");
        when(mockUnpublishedNode.getProperty(eq(HippoStdNodeType.HIPPOSTD_STATESUMMARY))).thenReturn(mockStateSummary);
        assertFalse(plugin.isChangePending());
    }

    @Test
    public void isChangePending_changed()throws Exception {
        when(mockJcrDocumentFactory.createFromNode(same(mockCurrentNode))).thenReturn(mockDocument);
        when(mockDefaultModel.getNode()).thenReturn(mockCurrentNode);
        HippoNode mockUnpublishedNode = mock(HippoNode.class);
        when(mockDocument.getVariantNode(eq(JcrDocument.VARIANT_UNPUBLISHED))).thenReturn(mockUnpublishedNode);
        when(mockUnpublishedNode.hasProperty(eq(HippoStdNodeType.HIPPOSTD_STATESUMMARY))).thenReturn(true);
        Property mockStateSummary = mock(Property.class);
        when(mockStateSummary.getString()).thenReturn("changed");
        when(mockUnpublishedNode.getProperty(eq(HippoStdNodeType.HIPPOSTD_STATESUMMARY))).thenReturn(mockStateSummary);
        assertTrue(plugin.isChangePending());
    }

    private void initialiseAvailableLanguages(String[] availableLanguages)
            throws WorkflowException, RemoteException, RepositoryException {
        // Sets up mock to return a value from the TranslationWorkflowPlugin.getAvailableLanguages method
        WorkflowDescriptor mockDescriptor = mock(WorkflowDescriptor.class);
        when(mockDefaultModel.getObject()).thenReturn(mockDescriptor);
        UserSession mockUserSession = mock(UserSession.class);
        when(mockUserSessionFactory.getUserSession()).thenReturn(mockUserSession);
        WorkflowManager mockWorkflowManager = mock(WorkflowManager.class);
        when(mockUserSession.getWorkflowManager()).thenReturn(mockWorkflowManager);
        TranslationWorkflow mockTranslationWorkflow = mock(TranslationWorkflow.class);
        when(mockWorkflowManager.getWorkflow(same(mockDescriptor))).thenReturn(mockTranslationWorkflow);
        HashMap<String, Serializable> hintsMap = new HashMap<>();
        when(mockTranslationWorkflow.hints()).thenReturn(hintsMap);

        hintsMap.put("available", new HashSet<>(Arrays.asList(availableLanguages)));
    }

    @Test
    public void currentDocumentHasTranslation_noAvailableLanguages() throws Exception {
        initialiseAvailableLanguages(new String[] {});
        assertFalse(plugin.currentDocumentHasTranslation());
    }

    @Test
    public void currentDocumentHasTranslation_errorCreatingTranslatedNode() throws Exception {
        initialiseAvailableLanguages(new String[] {"en", "es"});
        when(mockTranslatedNodeFactory.createFromNode(any())).thenThrow(new RepositoryException());
        assertFalse(plugin.currentDocumentHasTranslation());
    }

    @Test
    public void currentDocumentHasTranslation_withTranslation() throws Exception {
        initialiseAvailableLanguages(new String[] {"en", "fr", "es"});
        when(mockDefaultModel.getNode()).thenReturn(mockCurrentNode);
        HippoTranslatedNode mockTranslatedNode = mock(HippoTranslatedNode.class);
        when(mockTranslatedNodeFactory.createFromNode(same(mockCurrentNode))).thenReturn(mockTranslatedNode);
        when(mockTranslatedNode.hasTranslation("fr")).thenReturn(false);
        when(mockTranslatedNode.hasTranslation("es")).thenReturn(true);
        assertTrue(plugin.currentDocumentHasTranslation());
    }

    @Test
    public void currentDocumentHasTranslation_enOnly() throws Exception {
        initialiseAvailableLanguages(new String[] {"en", "fr", "es"});
        when(mockDefaultModel.getNode()).thenReturn(mockCurrentNode);
        HippoTranslatedNode mockTranslatedNode = mock(HippoTranslatedNode.class);
        when(mockTranslatedNodeFactory.createFromNode(same(mockCurrentNode))).thenReturn(mockTranslatedNode);
        when(mockTranslatedNode.hasTranslation("fr")).thenReturn(false);
        when(mockTranslatedNode.hasTranslation("es")).thenReturn(false);
        assertFalse(plugin.currentDocumentHasTranslation());
    }
}
