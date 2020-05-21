package com.visitscotland.brmx.beans;

import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:MegaLinks")
@Node(jcrType = "visitscotland:MegaLinks")
public class MegaLinks extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    //TODO: Change from list to listView.
    @HippoEssentialsGenerated(internalName = "visitscotland:list")
    public Boolean getList() {
        return getSingleProperty("visitscotland:list");
    }

    //TODO: Change from hideTeaser to showTeaser (and marked by default) (Readability improves with positive verbs as name of fields)
    @HippoEssentialsGenerated(internalName = "visitscotland:hideTeaser")
    public Boolean getHideTeaser() {
        return getSingleProperty("visitscotland:hideTeaser");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:introduction")
    public HippoHtml getIntroduction() {
        return getHippoHtml("visitscotland:introduction");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:singleImageLinks", allowModifications = false)
    public List<SingleImageModule> getSingleImageLinks() {
        return getChildBeansByName("visitscotland:singleImageLinks", SingleImageModule.class);
    }

    public SingleImageModule getSingleImageModule() {
        return getOnlyChild(getSingleImageLinks());
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:megaLinkItems", allowModifications = false)
    public List<MegaLinkItem> getMegaLinkItems() {
        return getChildBeansByName("visitscotland:megaLinkItems", MegaLinkItem.class);
    }
}
