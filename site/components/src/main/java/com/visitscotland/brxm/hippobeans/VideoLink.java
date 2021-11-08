package com.visitscotland.brxm.hippobeans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoBean;

@HippoEssentialsGenerated(internalName = "visitscotland:VideoLink")
@Node(jcrType = "visitscotland:VideoLink")
public class VideoLink extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:videoLink")
    public HippoBean getVideoLink() {
        return getLinkedBean("visitscotland:videoLink", HippoBean.class);
    }
}
