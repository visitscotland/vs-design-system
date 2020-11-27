package com.visitscotland.brmx.beans;

import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import com.visitscotland.brmx.beans.Image;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import java.util.List;
import com.visitscotland.brmx.beans.LongContentSection;

@HippoEssentialsGenerated(internalName = "visitscotland:LongContent")
@Node(jcrType = "visitscotland:LongContent")
public class LongContent extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public Image getImage() {
        return getLinkedBean("visitscotland:image", Image.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:introduction")
    public HippoHtml getIntroduction() {
        return getHippoHtml("visitscotland:introduction");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:parragraphs")
    public List<LongContentSection> getParragraphs() {
        return getChildBeansByName("visitscotland:parragraphs",
                LongContentSection.class);
    }
}
