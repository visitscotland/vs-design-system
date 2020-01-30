package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "visitscotland:Coordinates")
@Node(jcrType = "visitscotland:Coordinates")
public class Coordinates extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:latitude")
    public Double getLatitude() {
        return getSingleProperty("visitscotland:latitude");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:longitude")
    public Double getLongitude() {
        return getSingleProperty("visitscotland:longitude");
    }
}
