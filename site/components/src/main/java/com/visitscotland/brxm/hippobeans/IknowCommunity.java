package com.visitscotland.brxm.hippobeans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

@HippoEssentialsGenerated(internalName = "visitscotland:IknowCommunity")
@Node(jcrType = "visitscotland:IknowCommunity")
public class IknowCommunity extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:tagscheckboxes")
    public String[] getTagscheckboxes() {
        return getMultipleProperty("visitscotland:tagscheckboxes");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:tags")
    public String[] getTags() {
        return getMultipleProperty("visitscotland:tags");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:copy")
    public String getCopy() {
        return getSingleProperty("visitscotland:copy");
    }
}
