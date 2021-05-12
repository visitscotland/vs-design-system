package com.visitscotland.brxm.hippobeans;

import org.hippoecm.hst.content.beans.Node;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:FeaturedWidget")
@Node(jcrType = "visitscotland:FeaturedWidget")
public class FeaturedWidget extends Widget {

    @HippoEssentialsGenerated(internalName = "visitscotland:megaLinkItems")
    public List<MegalinkItem> getMegaLinkItems() {
        return getChildBeansByName("visitscotland:megaLinkItems",
                MegalinkItem.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:items", allowModifications = false)
    public List<MegalinkItem> getItems() {
        return getLinkedBeans("visitscotland:items", MegalinkItem.class);
    }
}
