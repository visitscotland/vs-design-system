package com.visitscotland.brxm.hippobeans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "visitscotland:iKnowCommunityTag")
@Node(jcrType = "visitscotland:iKnowCommunityTag")
public class IKnowCommunityTag extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:tag")
    public String getTag() {
        return getSingleProperty("visitscotland:tag");
    }
}
