package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "visitscotland:Itinerary")
@Node(jcrType = "visitscotland:Itinerary")
public class Itinerary extends Page {
    @HippoEssentialsGenerated(internalName = "visitscotland:summary")
    public String getSummary() {
        return getSingleProperty("visitscotland:summary");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:start")
    public String getStart() {
        return getSingleProperty("visitscotland:start");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:finish")
    public String getFinish() {
        return getSingleProperty("visitscotland:finish");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:distance")
    public Double getDistance() {
        return getSingleProperty("visitscotland:distance");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:theme")
    public String getTheme() {
        return getSingleProperty("visitscotland:theme");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:transport")
    public String[] getTransport() {
        return getMultipleProperty("visitscotland:transport");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:text")
    public String getText() {
        return getSingleProperty("visitscotland:text");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:areas")
    public String[] getAreas() {
        return getMultipleProperty("visitscotland:areas");
    }
}
