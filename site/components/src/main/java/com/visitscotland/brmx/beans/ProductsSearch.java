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

       @HippoEssentialsGenerated(internalName = "visitscotland:producttype")
    public String getProducttype() {
        return getSingleProperty("visitscotland:producttype");
    }


    public String[] getDmsCategories() {
        return getMultipleProperty("visitscotland:dmsCategories");
    }
    public String[] getDmsFacilities() {

        return getMultipleProperty("visitscotland:dmsFacilities");
    }
    public String[] getDmsAwards() {

        return getMultipleProperty("visitscotland:dmsAwards");
    }
}
