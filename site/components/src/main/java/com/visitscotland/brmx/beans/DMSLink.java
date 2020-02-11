package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import com.visitscotland.brmx.beans.Image;

@HippoEssentialsGenerated(internalName = "visitscotland:DMSLink")
@Node(jcrType = "visitscotland:DMSLink")
public class DMSLink extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:product")
    public String getProduct() {
        return getSingleProperty("visitscotland:product");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public Image getImage() {
        return getLinkedBean("visitscotland:image", Image.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:label")
    public String getLabel() {
        return getSingleProperty("visitscotland:label");
    }
}
