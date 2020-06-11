package com.visitscotland.brmx.beans;

import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:Megalinks")
@Node(jcrType = "visitscotland:Megalinks")
public class Megalinks extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:introduction")
    public HippoHtml getIntroduction() {
        return getHippoHtml("visitscotland:introduction");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:singleImageLinks", allowModifications = false)
    public List<SingleImageModule> getSingleImageLinks() {
        return getChildBeansByName("visitscotland:singleImageLinks",
                SingleImageModule.class);
    }

    public SingleImageModule getSingleImageModule() {
        return getOnlyChild(getSingleImageLinks());
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:megalinkItems", allowModifications = false)
    public List<MegalinkItem> getMegalinkItems() {
        return getChildBeansByName("visitscotland:megalinkItems",
                MegalinkItem.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:listLayout")
    public Boolean getListLayout() {
        return getSingleProperty("visitscotland:listLayout");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:teaserVisible")
    public Boolean getTeaserVisible() {
        return getSingleProperty("visitscotland:teaserVisible");
    }
}
