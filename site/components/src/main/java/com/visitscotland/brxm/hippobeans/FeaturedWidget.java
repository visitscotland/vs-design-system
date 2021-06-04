package com.visitscotland.brxm.hippobeans;

import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

import java.util.List;

@HippoEssentialsGenerated(internalName = "visitscotland:FeaturedWidget")
@Node(jcrType = "visitscotland:FeaturedWidget")
public class FeaturedWidget extends Widget {

    @HippoEssentialsGenerated(internalName = "visitscotland:items", allowModifications = false)
    public <T extends Linkable> List<T> getItems() {
        return (List) getLinkedBeans("visitscotland:items", HippoBean.class);
    }
}
