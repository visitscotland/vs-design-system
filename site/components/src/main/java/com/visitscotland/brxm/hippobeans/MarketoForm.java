package com.visitscotland.brxm.hippobeans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@HippoEssentialsGenerated(internalName = "visitscotland:MarketoForm")
@Node(jcrType = "visitscotland:MarketoForm")
public class MarketoForm extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:marketoId")
    public String getMarketoId() {
        return getSingleProperty("visitscotland:marketoId");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:munchkinId")
    public String getMunchkinId() {
        return getSingleProperty("visitscotland:munchkinId");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:thankYouPage")
    public HippoBean getThankYouPage() {
        return getLinkedBean("visitscotland:thankYouPage", HippoBean.class);
    }
}
