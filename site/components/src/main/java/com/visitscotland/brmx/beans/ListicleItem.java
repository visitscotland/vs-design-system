package com.visitscotland.brmx.beans;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:ListicleItem")
@Node(jcrType = "visitscotland:ListicleItem")
public class ListicleItem extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:subtitle")
    public String getSubtitle() {
        return getSingleProperty("visitscotland:subtitle");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:description")
    public HippoHtml getDescription() {
        return getHippoHtml("visitscotland:description");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:product", allowModifications = false)
    public List<HippoBean> getItem() {
        return getChildBeansByName("visitscotland:product", HippoBean.class);
    }

    public HippoBean getListicleItem() {
        return getOnlyChild(getItem());
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image", allowModifications = false)
    public List<HippoBean> getImage() {
        return getChildBeansByName("visitscotland:image", HippoBean.class);
    }

    public HippoBean getListicleItemImage() {
        return getOnlyChild(getImage());
    }
}
