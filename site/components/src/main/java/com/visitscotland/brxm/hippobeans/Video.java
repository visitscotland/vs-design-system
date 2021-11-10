package com.visitscotland.brxm.hippobeans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;

@HippoEssentialsGenerated(internalName = "visitscotland:Video")
@Node(jcrType = "visitscotland:Video")
public class Video extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:url")
    public String getUrl() {
        return getSingleProperty("visitscotland:url");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:teaser")
    public String getTeaser() {
        return getSingleProperty("visitscotland:teaser");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:label")
    public String getLabel() {
        return getSingleProperty("visitscotland:label");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public HippoGalleryImageSet getImage() {
        return getLinkedBean("visitscotland:image", HippoGalleryImageSet.class);
    }
}
