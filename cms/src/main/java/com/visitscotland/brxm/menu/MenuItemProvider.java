package com.visitscotland.brxm.menu;

import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.translation.plugin.JcrDocument;
import com.visitscotland.brxm.utils.HippoUtilsService;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
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
    private final HippoUtilsService hippoUtilsService;

    public MenuItemProvider(HippoUtilsService hippoUtilsService) {
        this.hippoUtilsService = hippoUtilsService;
    }

    public void constructPageAndModuleMenus(Node subjectNode, Map<String, Set<String>> prototypes) {
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
        } catch (RepositoryException | ObjectBeanManagerException | QueryException ex) {
            logger.warn("Failed to obtain child JCR types for menu selection", ex);
        }
    }

    private Optional<Page> getPageContentBean(Node subjectNode) throws RepositoryException, ObjectBeanManagerException, QueryException {
        if (!subjectNode.hasNode(PAGE_PATH) || !subjectNode.getNode(PAGE_PATH).isNodeType(JcrDocument.HIPPO_HANDLE)) {
            return Optional.empty();
        }

        HippoBean subjectBean = hippoUtilsService.getDocumentFromNode(subjectNode.getNode(PAGE_PATH), true);
        if (!(subjectBean instanceof Page)) {
            return Optional.empty();
        }
        return Optional.of((Page)subjectBean);
    }

}
