package com.visitscotland.brmx.beans;

import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.hippoecm.hst.content.beans.standard.HippoMirror;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;

import java.util.List;
import java.util.stream.Collectors;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:LongContentSection")
@Node(jcrType = "visitscotland:LongContentSection")
public class LongContentSection extends HippoCompound {
    @HippoEssentialsGenerated(internalName = "visitscotland:copy")
    public HippoHtml getCopy() {
        return getHippoHtml("visitscotland:copy");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:Quote")
    public Quote getQuote() {
        return getBean("visitscotland:Quote", Quote.class);
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:media", allowModifications = false)
    public List<HippoBean> getMedia() {
        return getChildBeansByName("visitscotland:media", HippoBean.class).stream().map(hippoBean -> {
                    if (hippoBean instanceof HippoMirror) {
                        return ((HippoMirror) hippoBean).getReferencedBean();
                    }
                    return hippoBean;
                }
        ).collect(Collectors.toList());
    }

      public HippoBean getMediaItem(){
        return getOnlyChild(getMedia());
    }

    //TODO Refactor
    protected <T> T getOnlyChild(List<T> children) {
        if (children.size() == 0) {
            return null;
        } else if (children.size() == 1) {
            return children.get(0);
        } else {
//            logger.warn("This list in supposed to have only one child");
            return children.get(0);
        }
    }
//    @HippoEssentialsGenerated(internalName = "visitscotland:Quote")
//    public Quote getQuote() {
//        return getBean("visitscotland:Quote", Quote.class);
//    }
}
