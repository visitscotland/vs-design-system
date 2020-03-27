package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import java.util.List;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

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

    @HippoEssentialsGenerated(internalName = "visitscotland:summaryTitle")
    public String getSummaryTitle() {
        return getSingleProperty("visitscotland:summaryTitle");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:summary")
    public HippoHtml getSummary() {
        return getHippoHtml("visitscotland:summary");
    }
}
