package com.visitscotland.brxm.components.content;

import org.hippoecm.hst.configuration.hosting.Mount;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.util.PathUtils;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * Implements the translation fallback functionality. If the content can not be found on a non-english mount, then
 * check the english mount for content instead
 */
public class ContentComponent extends EssentialsContentComponent {
    private static final Logger logger = LoggerFactory.getLogger(ContentComponent.class);
    private static final String ENGLISH_MOUNT_ALIAS = "en";

    @Override
    public void setContentBeanWith404(HstRequest request, HstResponse response) {
        HstRequestContext context = request.getRequestContext();
        if (context.getContentBean() != null) {
            super.setContentBeanWith404(request, response);
        } else {
            Optional<HippoBean> englishContentBean = getEnglishContentBean(context);
            if (englishContentBean.isPresent()) {
                request.setModel("document", englishContentBean.get());
            } else {
                this.pageNotFound(response);
            }
        }
    }

    private Optional<HippoBean> getEnglishContentBean(HstRequestContext context) {
        Mount englishMount = context.getMount(ENGLISH_MOUNT_ALIAS);
        if (englishMount == null || context.getResolvedSiteMapItem() == null || context.getObjectBeanManager() == null) {
            return Optional.empty();
        }
        String englishContentPath = englishMount.getContentPath();
        String englishContent = "/" + PathUtils.normalizePath(englishContentPath) + "/" + PathUtils.normalizePath(context.getResolvedSiteMapItem().getRelativeContentPath());
        try {
            Object bean = context.getObjectBeanManager().getObject(englishContent);
            return (bean instanceof HippoBean) ? Optional.of((HippoBean) bean) : Optional.empty();
        } catch (ObjectBeanManagerException e) {
            logger.info("Failed to get hippo bean at {}", englishContent, e);
            return Optional.empty();
        }
    }
}
