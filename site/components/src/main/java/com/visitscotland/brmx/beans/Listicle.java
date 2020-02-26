package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;

@HippoEssentialsGenerated(internalName = "visitscotland:Listicle")
@Node(jcrType = "visitscotland:Listicle")
public class Listicle extends Page {
    public List<ListicleItem> getItems() {
        return getExternalBeansByType(ListicleItem.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:descOrder")
    public Boolean getDescOrder() {
        return getSingleProperty("visitscotland:descOrder");
    }
}
