package com.visitscotland.brxm.utils;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;

public class CustomCommonComponent extends EssentialsContentComponent {

    @Override
    public void setContentBeanWith404(HstRequest request, HstResponse response) {
        HstRequestContext context = request.getRequestContext();
        HippoBean bean = context.getContentBean();
        HippoBean translateBean = (HippoBean) context.getAttribute("document");
        if (bean == null && translateBean == null) {
            this.pageNotFound(response);
        } else if (bean == null) {
            request.setModel("document", translateBean);
        } else {
            request.setModel("document", bean);
        }
    }
}
