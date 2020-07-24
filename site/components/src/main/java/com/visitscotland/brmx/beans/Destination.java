package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;

import java.util.List;

@HippoEssentialsGenerated(internalName = "visitscotland:Destination")
@Node(jcrType = "visitscotland:Destination")
public class Destination extends Page implements TranslationParent{
    @HippoEssentialsGenerated(internalName = "visitscotland:location")
    public String getLocation() {
        return getSingleProperty("visitscotland:location");
    }

    public List<BaseDocument> getItems() {
        return getExternalBeansByType(BaseDocument.class);
    }

    @Override
    public String[] getChildJcrTypes() {
        return new String[]{"visitscotland:Megalinks", "visitscotland:iKnowIcentre"};
    }
}
