package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;

@HippoEssentialsGenerated(internalName = "visitscotland:ICentreQuote")
@Node(jcrType = "visitscotland:ICentreQuote")
public class ICentreQuote extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:quote")
    public String getQuote() {
        return getSingleProperty("visitscotland:quote");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:name")
    public String getName() {
        return getSingleProperty("visitscotland:name");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:productId")
    public String getProductId() {
        return getSingleProperty("visitscotland:productId");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public HippoGalleryImageSet getImage() {
        return getLinkedBean("visitscotland:image", HippoGalleryImageSet.class);
    }
}
