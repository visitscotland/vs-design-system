package com.visitscotland.brxm.menu;

import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.translation.plugin.JcrDocument;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.*;

@Component
public class MenuItemProvider {

    private static final String PAGE_PATH = "content";
    private static final String NEW_PAGE_MENU = "new-page";
    private static final String NEW_MODULE_MENU = "new-module";

    private static final Logger logger = LoggerFactory.getLogger(MenuItemProvider.class);

    public MenuItemProvider() {
    }

    public void constructMenuItems(Node subjectNode, Map<String, Set<String>> prototypes) {
        try {
            if (prototypes.containsKey(NEW_PAGE_MENU) && prototypes.containsKey(NEW_MODULE_MENU)) {
                Optional<Page> optionalPage = getPageContentBean(subjectNode);
                if (optionalPage.isPresent()) {
                    prototypes.remove(NEW_PAGE_MENU);
                    prototypes.put(NEW_MODULE_MENU, new TreeSet<>(Arrays.asList((optionalPage.get()).getChildJcrTypes())));
                } else {
                    prototypes.remove(NEW_MODULE_MENU);
                }
            }

        } catch (RepositoryException | ObjectBeanManagerException ex) {
            logger.warn("Failed to obtain child JCR types for menu selection", ex);
        }
    }

    private Optional<Page> getPageContentBean(Node subjectNode) throws RepositoryException, ObjectBeanManagerException {
        if (!subjectNode.hasNode(PAGE_PATH) || !subjectNode.getNode(PAGE_PATH).isNodeType(JcrDocument.HIPPO_HANDLE)) {
            return Optional.empty();
        }

        HippoBean subjectBean = new JcrDocument(subjectNode.getNode(PAGE_PATH)).asHippoBean();
        if (!(subjectBean instanceof Page)) {
            return Optional.empty();
        }
        return Optional.of((Page)subjectBean);
    }

}
