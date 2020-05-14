package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@HippoEssentialsGenerated(internalName = "visitscotland:MegaLinkItem")
@Node(jcrType = "visitscotland:MegaLinkItem")
public class MegaLinkItem extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:feature")
    public Boolean getFeature() {
        return getSingleProperty("visitscotland:feature");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:link")
    public HippoBean getLink() {
        return getLinkedBean("visitscotland:link", HippoBean.class);
    }
}
