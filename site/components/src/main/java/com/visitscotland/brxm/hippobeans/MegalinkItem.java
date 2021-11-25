package com.visitscotland.brxm.hippobeans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

import java.util.List;

@HippoEssentialsGenerated(internalName = "visitscotland:MegalinkItem")
@Node(jcrType = "visitscotland:MegalinkItem")
public class MegalinkItem extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:feature")
    public Boolean getFeature() {
        return getSingleProperty("visitscotland:feature");
    }

    /**
     * @deprecated Remove after all Video Adjustments are done
     */
    @Deprecated
    @HippoEssentialsGenerated(internalName = "visitscotland:link")
    public HippoBean getLink() {
        return getLinkedBean("visitscotland:link", HippoBean.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:links", allowModifications = false)
    public List<HippoCompound> getLinks() {
        return getChildBeansByName("visitscotland:links", HippoCompound.class);
    }

    public HippoBean getLinkItem(){
        return BaseDocument.getOnlyChild(getLinks());
    }
}
