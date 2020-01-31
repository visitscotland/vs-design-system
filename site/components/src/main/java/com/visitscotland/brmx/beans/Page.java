package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import com.visitscotland.brmx.beans.Image;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

@HippoEssentialsGenerated(internalName = "visitscotland:Page")
@Node(jcrType = "visitscotland:Page")
public class Page extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:seoTitle")
    public String getSeoTitle() {
        return getSingleProperty("visitscotland:seoTitle");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:breadcrumb")
    public String getBreadcrumb() {
        return getSingleProperty("visitscotland:breadcrumb");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:heroImage")
    public Image getHeroImage() {
        return getLinkedBean("visitscotland:heroImage", Image.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:introduction")
    public HippoHtml getIntroduction() {
        return getHippoHtml("visitscotland:introduction");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:seoNoIndex")
    public Boolean getSeoNoIndex() {
        return getSingleProperty("visitscotland:seoNoIndex");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:seoDescription")
    public String getSeoDescription() {
        return getSingleProperty("visitscotland:seoDescription");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:teaser")
    public String getTeaser() {
        return getSingleProperty("visitscotland:teaser");
    }
}
