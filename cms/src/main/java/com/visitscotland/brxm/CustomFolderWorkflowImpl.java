package com.visitscotland.brxm;

import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.report.translation.JcrUtilService;
import com.visitscotland.brxm.translation.plugin.JcrDocument;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.repository.api.WorkflowContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.util.*;
import java.util.stream.Collectors;

public class CustomFolderWorkflowImpl extends org.hippoecm.repository.standardworkflow.FolderWorkflowImpl {

    private final Node subjectNode;
    private static final String PAGE_PATH = "content";
    private static final Logger logger = LoggerFactory.getLogger(CustomFolderWorkflowImpl.class);
    private final JcrUtilService jcrUtilService;

    public CustomFolderWorkflowImpl(WorkflowContext context, Session userSession, Session rootSession, Node subject) throws RepositoryException {
        super(context, userSession, rootSession, subject);
        subjectNode = subject;
        jcrUtilService = VsComponentManager.get(JcrUtilService.class);
    }

    @Override
    protected Map<String, Set<String>> prototypes() {
        Map<String, Set<String>> prototypes  = super.prototypes();
        try {
            Set<String> pageTypes = jcrUtilService.getTypesDeriving("visitscotland:Page")
                    .stream().map(x -> "visitscotland:" + x).collect(Collectors.toSet());
            prototypes.put("new-document", pageTypes);

            if (!this.subjectNode.hasNode(PAGE_PATH) || !this.subjectNode.getNode(PAGE_PATH).isNodeType(JcrDocument.HIPPO_HANDLE)) {
                return prototypes;
            }
            HippoBean subjectBean = new JcrDocument(this.subjectNode.getNode(PAGE_PATH)).asHippoBean();
            if (!(subjectBean instanceof Page)) {
                return prototypes;
            }
            prototypes.put("new-document", new TreeSet<>(Arrays.asList(((Page) subjectBean).getChildJcrTypes())));
        } catch (RepositoryException | ObjectBeanManagerException ex) {
            logger.warn("Failed to obtain child JCR types for new-document menu selection", ex);
        }

        return prototypes;
    }

}
