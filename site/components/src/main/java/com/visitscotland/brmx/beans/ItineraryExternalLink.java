package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import com.visitscotland.brmx.beans.ExternalLink;
import com.visitscotland.brmx.beans.Coordinates;

@HippoEssentialsGenerated(internalName = "visitscotland:ItineraryExternalLink")
@Node(jcrType = "visitscotland:ItineraryExternalLink")
public class ItineraryExternalLink extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:ExternalLink")
    public ExternalLink getExternalLink() {
        return getBean("visitscotland:ExternalLink", ExternalLink.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:Coordinates")
    public Coordinates getCoordinates() {
        return getBean("visitscotland:Coordinates", Coordinates.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:timeToExplore")
    public String getTimeToExplore() {
        return getSingleProperty("visitscotland:timeToExplore");
    }
}
