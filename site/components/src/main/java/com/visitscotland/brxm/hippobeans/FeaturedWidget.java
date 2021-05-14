package com.visitscotland.brxm.hippobeans;

import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:FeaturedWidget")
@Node(jcrType = "visitscotland:FeaturedWidget")
public class FeaturedWidget extends Widget {

    @HippoEssentialsGenerated(internalName = "visitscotland:items", allowModifications = false)
    public <T extends HippoBean, Linkable> List<T> getItems() {
        return (List<T>) getLinkedBeans("visitscotland:items", HippoBean.class);
    }
}
