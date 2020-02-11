package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "visitscotland:InstagramImage")
@Node(jcrType = "visitscotland:InstagramImage")
public class InstagramImage extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:id")
    public String getId() {
        return getSingleProperty("visitscotland:id");
    }
}
