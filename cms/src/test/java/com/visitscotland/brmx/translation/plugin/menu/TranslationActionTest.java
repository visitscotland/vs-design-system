package com.visitscotland.brmx.translation.plugin.menu;

import com.visitscotland.brmx.translation.plugin.DocumentTranslator;
import com.visitscotland.brmx.translation.plugin.SessionFactory;
import com.visitscotland.brmx.translation.plugin.TranslationWorkflowPlugin;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.util.tester.WicketTester;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.frontend.translation.ILocaleProvider;
import org.hippoecm.repository.api.HippoSession;
import org.hippoecm.repository.translation.TranslationWorkflow;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TranslationActionTest {
    private WicketTester wicket;
    @Mock
    private TranslationWorkflowPlugin mockWorkflowPlugin;
    @Mock
    private IModel<ILocaleProvider.HippoLocale> mockLocaleModel;
    @Mock
    private TranslationWorkflow mockWorkflow;
    @Mock
    private SessionFactory mockSessionFactory;
    @Mock
    private HippoSession mockHippoSession;
    private Set<String> availableLanguages;
    @Mock
    private ILocaleProvider mockLocaleProvider;
    @Mock
    LoadableDetachableModel<String> mockNameModel;
    @Mock
    private WorkflowDescriptorModel mockWorkflowDescriptorModel;
    @Mock
    private DocumentTranslator mockTranslator;
    private TranslationAction action;

    @BeforeEach
    public void beforeEach() {
        // This is needed to initialise the Wicket application so we can create the component.
        wicket = new WicketTester();

        availableLanguages = new HashSet<>();
        lenient().when(mockWorkflowPlugin.getAvailableLanguages()).thenReturn(availableLanguages);
        lenient().when(mockWorkflowPlugin.getLocaleProvider()).thenReturn(mockLocaleProvider);
        lenient().when(mockSessionFactory.getJcrSession()).thenReturn(mockHippoSession);
        lenient().when(mockNameModel.getObject()).thenReturn("translationName");
        action = new TranslationAction(
                mockWorkflowPlugin,
                "translation",
                mockNameModel,
                mockLocaleModel,
                mockSessionFactory,
                mockTranslator);
    }

    @Test
    public void execute_none_to_translate() throws Exception {
        // will test that if run with no languages to translate does not cause an error
        // should never happen but is a valid path through the code
        assertNull(action.execute(mockWorkflow));
    }

    @Test
    public void execute_with_mixed_translations() throws Exception {
        Node mockDocNode = mock(Node.class);
        when(mockWorkflowPlugin.getDefaultModel()).thenReturn((IModel) mockWorkflowDescriptorModel);
        when(mockWorkflowDescriptorModel.getNode()).thenReturn(mockDocNode);
        when(mockTranslator.getTranslatedDocumentName(anyList())).thenReturn("translated");

        addTranslatedLocale("en");
        addUntranslatedLocale("es");
        addUntranslatedLocale("de");

        action.execute(mockWorkflow);

        verify(mockTranslator, times(2)).cloneDocumentAndFolderStructure(
                any(Node.class), anyList(), any(ILocaleProvider.HippoLocale.class), any(Session.class));

        verify(mockWorkflow).addTranslation(eq("es"), eq("translated"));
        verify(mockWorkflow).addTranslation(eq("de"), eq("translated"));
    }

    @Test
    public void execute_with_error() throws Exception {
        Node mockDocNode = mock(Node.class);
        when(mockWorkflowPlugin.getDefaultModel()).thenReturn((IModel) mockWorkflowDescriptorModel);
        when(mockWorkflowDescriptorModel.getNode()).thenReturn(mockDocNode);
        when(mockTranslator.cloneDocumentAndFolderStructure(
                same(mockDocNode),
                anyList(),
                any(ILocaleProvider.HippoLocale.class),
                any(Session.class))
        ).thenReturn("error message");

        addTranslatedLocale("en");
        addUntranslatedLocale("de");
        addUntranslatedLocale("es");

        String message = action.execute(mockWorkflow);

        assertEquals("error message", message);
        verify(mockTranslator).cloneDocumentAndFolderStructure(same(mockDocNode), anyList(), any(ILocaleProvider.HippoLocale.class), any(Session.class));
        verify(mockWorkflow, never()).addTranslation(anyString(), anyString());
    }

    private void addTranslatedLocale(String isoString) {
        availableLanguages.add(isoString);
        when(mockWorkflowPlugin.hasLocaleTranslation(eq(isoString))).thenReturn(true);
    }

    private void addUntranslatedLocale(String isoString) {
        availableLanguages.add(isoString);
        when(mockWorkflowPlugin.hasLocaleTranslation(eq(isoString))).thenReturn(false);
        ILocaleProvider.HippoLocale mockLocale = mock(ILocaleProvider.HippoLocale.class);
        when(mockLocaleProvider.getLocale(eq(isoString))).thenReturn(mockLocale);
        lenient().when(mockLocale.getName()).thenReturn(isoString);
    }
}
