package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "visitscotland:ProductsSearch")
@Node(jcrType = "visitscotland:ProductsSearch")
public class ProductsSearch extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:location")
    public String getLocation() {
        return getSingleProperty("visitscotland:location");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:distance")
    public Long getDistance() {
        return getSingleProperty("visitscotland:distance");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:producttype")
    public String getProducttype() {
        return getSingleProperty("visitscotland:producttype");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:dmscategories")
    public String[] getDmscategories() {
        return getMultipleProperty("visitscotland:dmscategories");
    }
}
