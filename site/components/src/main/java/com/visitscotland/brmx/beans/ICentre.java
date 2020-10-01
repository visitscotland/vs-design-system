package com.visitscotland.brmx.beans;

import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoGalleryImageSet;
import com.visitscotland.brmx.beans.Quote;
import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:ICentre")
@Node(jcrType = "visitscotland:ICentre")
public class ICentre extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public Image getImage() {
        return getLinkedBean("visitscotland:image", Image.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:quotes", allowModifications = false)
    public List<Quote> getQuotes() {
        return getChildBeansByName("visitscotland:quotes", Quote.class);
    }

    public Quote getQuote() {
        return getOnlyChild(getQuotes());
    }

    //TODO method from Basedocument, decide how to use it for compounds
    public <T> T getOnlyChild(List<T> children){
        if (children.size() == 0) {
            return null;
        } else if (children.size() == 1) {
            return children.get(0);
        } else {
            //logger.warn("This list in supposed to have only one child");
            return children.get(0);
        }
    }
}
