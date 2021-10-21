package com.visitscotland.brxm.components.content;

import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.container.ContainerConstants;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.ResolvedMount;
import org.hippoecm.hst.core.request.ResolvedVirtualHost;
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

    @Override
    public void setContentBeanWith404(HstRequest request, HstResponse response) {
        HstRequestContext context = request.getRequestContext();
        if (context.getContentBean() != null) {
            super.setContentBeanWith404(request, response);
        } else {
            Optional<HippoBean> englishContentBean = getEnglishContentBean(request, context);
            if (englishContentBean.isPresent()) {
                logger.debug("Found english fallback document {}", englishContentBean.get().getPath());
                request.setModel("document", englishContentBean.get());
            } else {
                this.pageNotFound(response);
            }
        }
    }

    private Optional<HippoBean> getEnglishContentBean(HstRequest httpServletRequest, HstRequestContext context) {
        ResolvedVirtualHost resolvedVirtualHost = (ResolvedVirtualHost) httpServletRequest.getAttribute(ContainerConstants.VIRTUALHOSTS_REQUEST_ATTR);
        if (resolvedVirtualHost == null) {
            logger.error("Failed to get ResolvedVirtualHost from request servlet");
            return Optional.empty();
        }
        ResolvedMount englishMount = resolvedVirtualHost.matchMount("/");
        if (englishMount == null || context.getResolvedSiteMapItem() == null || context.getObjectBeanManager() == null || englishMount.getMount() == null) {
            return Optional.empty();
        }
        String englishContentPath = englishMount.getMount().getContentPath();
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
