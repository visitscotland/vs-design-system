package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "visitscotland:ICentreQuote")
@Node(jcrType = "visitscotland:ICentreQuote")
public class ICentreQuote extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:productId")
    public String getProductId() {
        return getSingleProperty("visitscotland:productId");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public Image getImage() {
        return getLinkedBean("visitscotland:image", Image.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:author")
    public String getAuthor() {
        return getSingleProperty("visitscotland:author");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:quote")
    public HippoHtml getQuote() {
        return getHippoHtml("visitscotland:quote");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:role")
    public String getRole() {
        return getSingleProperty("visitscotland:role");
    }
}
