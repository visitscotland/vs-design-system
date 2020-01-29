package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;

@HippoEssentialsGenerated(internalName = "visitscotland:ProductSearch")
@Node(jcrType = "visitscotland:ProductSearch")
public class ProductSearch extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:productType")
    public String getProductType() {
        return getSingleProperty("visitscotland:productType");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:location")
    public String getLocation() {
        return getSingleProperty("visitscotland:location");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:dmsCategories")
    public String getDmsCategories() {
        return getSingleProperty("visitscotland:dmsCategories");
    }
}
