package com.visitscotland.brmx.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import java.util.List;
import com.visitscotland.brmx.beans.OTYML;
import com.visitscotland.brmx.beans.ListicleClosing;

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

    @Override
    public String[] getChildJcrTypes() {
        return new String[] { "visitscotland:ListicleItem" };
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:ListicleClosing")
    public ListicleClosing getListicleClosing() {
        return getBean("visitscotland:ListicleClosing", ListicleClosing.class);
    }
}
