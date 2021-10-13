package com.visitscotland.brxm.utils;

import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.container.ContainerConstants;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.ResolvedVirtualHost;
import org.hippoecm.hst.util.PathUtils;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class CustomCommonComponent extends EssentialsContentComponent {
    private static final Logger logger = LoggerFactory.getLogger(CustomCommonComponent.class);

    @Override
    public void setContentBeanWith404(HstRequest request, HstResponse response) {
        HstRequestContext context = request.getRequestContext();
        if (context.getContentBean() != null) {
            super.setContentBeanWith404(request, response);
        } else {
            Optional<HippoBean> englishContentBean = getEnglishContentBean(request);
            if (englishContentBean.isPresent()) {
                request.setModel("document", englishContentBean.get());
            } else {
                this.pageNotFound(response);
            }
        }
    }

    private Optional<HippoBean> getEnglishContentBean(HstRequest request) {
        HstRequestContext context = request.getRequestContext();
        ResolvedVirtualHost virtualHost = ((ResolvedVirtualHost) context.getServletRequest().getAttribute(ContainerConstants.VIRTUALHOSTS_REQUEST_ATTR));
        if (virtualHost == null) {
            logger.error("Failed to obtain ResolvedVirtualHost from request context");
            return Optional.empty();
        }
        String englishContentPath = virtualHost.matchMount("en").getMount().getContentPath();
        String englishContent = englishContentPath + "/" + PathUtils.normalizePath(context.getResolvedSiteMapItem().getRelativeContentPath());
        try {
            Object bean = context.getObjectBeanManager().getObject(englishContent);
            return (bean instanceof HippoBean) ? Optional.of((HippoBean) bean) : Optional.empty();
        } catch (ObjectBeanManagerException e) {
            logger.info("Failed to get hippo bean at {}", englishContent, e);
            return Optional.empty();
        }
    }
}
