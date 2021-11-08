package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.utils.HippoUtilsService;
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
    private final HippoUtilsService hippoUtilsService;

    public ContentComponent() {
        hippoUtilsService = VsComponentManager.get(HippoUtilsService.class);
    }

    @Override
    public void setContentBeanWith404(HstRequest request, HstResponse response) {
        Optional<HippoBean> contentBean = hippoUtilsService.getContentBeanWithTranslationFallback(request);
        if (contentBean.isPresent()) {
            request.setModel("document", contentBean.get());
        } else {
            this.pageNotFound(response);
        }
    }
}
