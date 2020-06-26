package com.visitscotland.brmx.translation.plugin;

import org.apache.wicket.model.LoadableDetachableModel;
import org.hippoecm.addon.workflow.WorkflowDescriptorModel;
import org.hippoecm.repository.translation.HippoTranslationNodeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.*;

final class LanguageModel extends LoadableDetachableModel<String> {
    private static Logger log = LoggerFactory.getLogger(LanguageModel.class);
    public static final String UNKNOWN_VALUE = "unknown";
    private TranslationWorkflowPlugin translationWorkflow;

    public LanguageModel(TranslationWorkflowPlugin translationWorkflow) {
        this.translationWorkflow = translationWorkflow;
    }

    @Override
    protected String load() {
        WorkflowDescriptorModel workflowModel = (WorkflowDescriptorModel) translationWorkflow.getDefaultModel();
        if (workflowModel == null) {
            log.debug("WorkflowDescriptorModel is null, defaulting LanguageModel to unknown");
            return UNKNOWN_VALUE;
        }

        try {
            Node documentNode = workflowModel.getNode();
            return documentNode.getProperty(HippoTranslationNodeType.LOCALE).getString();
        } catch(RepositoryException ex) {
            log.error(ex.getMessage(), ex);
            return UNKNOWN_VALUE;
        }
    }

}
