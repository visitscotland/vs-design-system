package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import com.visitscotland.brmx.beans.Coordinates;
import com.visitscotland.brmx.beans.Image;

@HippoEssentialsGenerated(internalName = "visitscotland:ExternalLink")
@Node(jcrType = "visitscotland:ExternalLink")
public class ExternalLink extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:link")
    public String getLink() {
        return getSingleProperty("visitscotland:link");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:label")
    public String getLabel() {
        return getSingleProperty("visitscotland:label");
    }
}
