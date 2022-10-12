package com.visitscotland.brxm.hippobeans;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoMirror;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:MapCategory")
@Node(jcrType = "visitscotland:MapCategory")
public class MapCategory extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:links", allowModifications = false)
    public HippoBean getLinkItem() {
        if (getBean("visitscotland:links") instanceof HippoMirror) {
            return getLinkedBean("visitscotland:links", HippoBean.class);
        } else {
            return getBean("visitscotland:links");
        }
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:mapPins", allowModifications = false)
    public List<HippoBean> getMapPins() {
        return getChildBeansByName("visitscotland:mapPins",
                HippoBean.class);
    }

    public HippoBean getProductItem() {
        return getOnlyChild(getMapPins());
    }

    //TODO Refactor
    protected <T> T getOnlyChild(List<T> children) {
        if (children.size() == 0) {
            return null;
        } else if (children.size() == 1) {
            return children.get(0);
        } else {
            return children.get(0);
        }
    }
}
