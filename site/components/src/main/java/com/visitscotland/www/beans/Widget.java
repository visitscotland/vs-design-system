package com.visitscotland.www.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "visitscotland:Widget")
@Node(jcrType = "visitscotland:Widget")
public class Widget extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:component")
    public String getComponent() {
        return getSingleProperty("visitscotland:component");
    }
}
