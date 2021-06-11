package com.visitscotland.brxm.menu;

import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.menu.MenuItemProvider;
import org.hippoecm.repository.api.WorkflowContext;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;

/**
 * This custom workflow is enabled in /hippo:configuration/hippo:workflows/threepane/folder-permissions
 * Used to dynamically specify the new document/folder menus and the types of documents that can be created form them
 */
public class CustomFolderWorkflowImpl extends org.hippoecm.repository.standardworkflow.FolderWorkflowImpl {

    private final Node subjectNode;
    private final MenuItemProvider menuItemProvider;

    public CustomFolderWorkflowImpl(WorkflowContext context, Session userSession, Session rootSession, Node subject) throws RepositoryException {
        super(context, userSession, rootSession, subject);
        subjectNode = subject;
        menuItemProvider = VsComponentManager.get(MenuItemProvider.class);
    }

    @Override
    protected Map<String, Set<String>> prototypes() {
        Map<String, Set<String>> prototypes = super.prototypes();
        menuItemProvider.constructMenuItems(this.subjectNode, prototypes);
        return prototypes;
    }

}
