package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import com.visitscotland.brmx.beans.Coordinates;
import com.visitscotland.brmx.beans.Image;

@HippoEssentialsGenerated(internalName = "visitscotland:ExternalProductLink")
@Node(jcrType = "visitscotland:ExternalProductLink")
public class ExternalProductLink extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:Link")
    public String getLink() {
        return getSingleProperty("visitscotland:Link");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:timeToExplore")
    public String getTimeToExplore() {
        return getSingleProperty("visitscotland:timeToExplore");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:Coordinates")
    public Coordinates getCoordinates() {
        return getBean("visitscotland:Coordinates", Coordinates.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public Image getImage() {
        return getLinkedBean("visitscotland:image", Image.class);
    }
}
