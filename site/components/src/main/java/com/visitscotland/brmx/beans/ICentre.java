package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import com.visitscotland.brmx.beans.ICentreQuote;

@HippoEssentialsGenerated(internalName = "visitscotland:ICentre")
@Node(jcrType = "visitscotland:ICentre")
public class ICentre extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:description")
    public String getDescription() {
        return getSingleProperty("visitscotland:description");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:iknowTitle")
    public String getIknowTitle() {
        return getSingleProperty("visitscotland:iknowTitle");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:iknowDescription")
    public String getIknowDescription() {
        return getSingleProperty("visitscotland:iknowDescription");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public HippoGalleryImageSet getImage() {
        return getLinkedBean("visitscotland:image", HippoGalleryImageSet.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:ICentreQuote")
    public ICentreQuote getICentreQuote() {
        return getBean("visitscotland:ICentreQuote", ICentreQuote.class);
    }
}
