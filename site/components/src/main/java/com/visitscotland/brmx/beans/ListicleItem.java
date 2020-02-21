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

    @HippoEssentialsGenerated(internalName = "visitscotland:products", allowModifications = false)
    public List<HippoBean> getProducts() {
        return getChildBeansByName("visitscotland:products", HippoBean.class);
    }

    public HippoBean getListicleItem() {
        return getOnlyChild(getProducts());
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:images", allowModifications = false)
    public List<HippoBean> getImages() {
        return getChildBeansByName("visitscotland:images", HippoBean.class);
    }

    public HippoBean getListicleItemImage() {
        return getOnlyChild(getImages());
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:extraLinks", allowModifications = false)
    public List<HippoBean> getExtraLinks() {
        return getChildBeansByName("visitscotland:extraLinks", HippoBean.class);
    }
}
