package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import com.visitscotland.brmx.beans.Coordinates;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;

@HippoEssentialsGenerated(internalName = "visitscotland:Image")
@Node(jcrType = "visitscotland:Image")
public class Image extends HippoGalleryImageSet {
    @HippoEssentialsGenerated(internalName = "visitscotland:altText")
    public String getAltText() {
        return getSingleProperty("visitscotland:altText");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:credit")
    public String getCredit() {
        return getSingleProperty("visitscotland:credit");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:caption")
    public String getCaption() {
        return getSingleProperty("visitscotland:caption");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:Coordinates")
    public Coordinates getCoordinates() {
        return getBean("visitscotland:Coordinates", Coordinates.class);
    }
}
