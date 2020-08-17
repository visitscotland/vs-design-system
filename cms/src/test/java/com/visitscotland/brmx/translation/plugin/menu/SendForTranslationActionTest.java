package com.visitscotland.brmx.translation.plugin.menu;

import com.visitscotland.brmx.translation.plugin.DocumentTranslator;
import com.visitscotland.brmx.translation.plugin.TranslationWorkflow;
import com.visitscotland.brmx.translation.plugin.TranslationWorkflowPlugin;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.util.tester.WicketTester;
import org.hippoecm.frontend.plugins.standards.icon.HippoIconStack;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SendForTranslationActionTest {
    private WicketTester wicket;
    private SendForTranslationAction sendForTranslationAction;
    @Mock
    private TranslationWorkflowPlugin mockWorkflowPlugin;
    @Mock
    private Page mockParent;

    @BeforeEach
    public void before() {
        wicket = new WicketTester();
        sendForTranslationAction = new SendForTranslationAction(mockWorkflowPlugin,
                "COMP_ID");
        sendForTranslationAction.setParent(mockParent);
        lenient().when(mockWorkflowPlugin.getCurrentlySelectedDocumentLocale()).thenReturn("en");
    }

    @Test
    public void isVisible_defaultToFalse_findParent() {
        // When the super is not visible or the component is not on a Page it should default to false.
        sendForTranslationAction.setParent(null);
        assertFalse(sendForTranslationAction.isVisible());
        verify(mockWorkflowPlugin, never()).canTranslateModel();
        verify(mockWorkflowPlugin, never()).isChangePending();
    }

    @Test
    public void isVisible_defaultToFalse_superNotVisible() {
        // When the super is not visible or the component is not on a Page it should default to false.
        sendForTranslationAction.clearSuperVisible();
        assertFalse(sendForTranslationAction.isVisible());
        verify(mockWorkflowPlugin, never()).canTranslateModel();
        verify(mockWorkflowPlugin, never()).isChangePending();
    }
    @Test
    public void isVisible_hasTranslationPermissions_noChangePending() {
        // When the user has translation permissions and the document does not have a pending change
        // the menu item should not be visible
        when(mockWorkflowPlugin.currentDocumentHasTranslation()).thenReturn(true);
        when(mockWorkflowPlugin.canTranslateModel()).thenReturn(true);
        when(mockWorkflowPlugin.isChangePending()).thenReturn(false);
        assertFalse(sendForTranslationAction.isVisible());
    }

    @Test
    public void isVisible_notEnglishDocument() {
        // When the document is not English do not show the menu item
        when(mockWorkflowPlugin.getCurrentlySelectedDocumentLocale()).thenReturn("es");
        assertFalse(sendForTranslationAction.isVisible());
        verify(mockWorkflowPlugin, never()).canTranslateModel();
        verify(mockWorkflowPlugin, never()).isChangePending();
    }

    @Test
    public void isVisible_noTranslationPermissions() {
        // When the user does not have translation permissions
        // the menu item should not be visible
        when(mockWorkflowPlugin.currentDocumentHasTranslation()).thenReturn(true);
        when(mockWorkflowPlugin.canTranslateModel()).thenReturn(false);
        assertFalse(sendForTranslationAction.isVisible());
        verify(mockWorkflowPlugin, never()).isChangePending();
    }

    @Test
    public void isVisible_hasNoTranslations() {
        // When the English document has no existing translations the menu should not be visible
        when(mockWorkflowPlugin.currentDocumentHasTranslation()).thenReturn(false);
        assertFalse(sendForTranslationAction.isVisible());
        verify(mockWorkflowPlugin, never()).canTranslateModel();
        verify(mockWorkflowPlugin, never()).isChangePending();
    }

    @Test
    public void isVisible_hasTranslationPermissions_andChangePending() {
        // When the user has translation permissions and the document has a pending change
        // the menu item should be visible
        when(mockWorkflowPlugin.currentDocumentHasTranslation()).thenReturn(true);
        when(mockWorkflowPlugin.canTranslateModel()).thenReturn(true);
        when(mockWorkflowPlugin.isChangePending()).thenReturn(true);
        assertTrue(sendForTranslationAction.isVisible());
    }

    @Test
    public void getIcon() {
        // Check that the id supplied is set in the returned icon
        Component iconComponent = sendForTranslationAction.getIcon("ICON_ID");
        assertNotNull(iconComponent);
        assertTrue(iconComponent instanceof HippoIconStack);
        HippoIconStack iconStack = (HippoIconStack) iconComponent;
        assertEquals("ICON_ID", iconStack.getId());
    }

    @Test
    public void execute() throws Exception {
        // Should only return null for now
        TranslationWorkflow mockWorkflow = mock(TranslationWorkflow.class);
        assertNull(sendForTranslationAction.execute(mockWorkflow));
    }

    @Test
    public void getName() {
        assertEquals(SendForTranslationAction.MENU_TEXT, sendForTranslationAction.getStdWorkflowName());
    }
}
