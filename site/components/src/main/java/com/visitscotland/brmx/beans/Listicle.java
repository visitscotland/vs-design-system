package com.visitscotland.brmx.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

import java.util.List;

@HippoEssentialsGenerated(internalName = "visitscotland:Listicle")
@Node(jcrType = "visitscotland:Listicle")
public class Listicle extends Page {
    @Deprecated
    public List<ListicleItem> getItems() {
        return getPageChildrenByType(ListicleItem.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:descOrder")
    public Boolean getDescOrder() {
        return getSingleProperty("visitscotland:descOrder");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:summaryTitle")
    public String getSummaryTitle() {
        return getSingleProperty("visitscotland:summaryTitle");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:summary")
    public HippoHtml getSummary() {
        return getHippoHtml("visitscotland:summary");
    }

    @Override
    public String[] getChildJcrTypes() {
        return new String[] { "visitscotland:ListicleItem" };
    }
}
