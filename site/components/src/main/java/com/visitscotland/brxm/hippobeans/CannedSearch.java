package com.visitscotland.brxm.hippobeans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import com.visitscotland.brxm.hippobeans.ProductsSearch;
import com.visitscotland.brxm.hippobeans.ProductSearchLink;

@HippoEssentialsGenerated(internalName = "visitscotland:CannedSearch")
@Node(jcrType = "visitscotland:CannedSearch")
public class CannedSearch extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:copy")
    public HippoHtml getCopy() {
        return getHippoHtml("visitscotland:copy");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:criteria")
    public ProductSearchLink getCriteria() {
        return getBean("visitscotland:criteria", ProductSearchLink.class);
    }
}
