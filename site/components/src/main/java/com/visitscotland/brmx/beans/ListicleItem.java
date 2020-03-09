package com.visitscotland.brmx.beans;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.content.beans.standard.HippoFacetSelect;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import javax.jcr.RepositoryException;
import java.util.List;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:ListicleItem")
@Node(jcrType = "visitscotland:ListicleItem")
public class ListicleItem extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:subtitle")
    public String getSubtitle() {
        return getSingleProperty("visitscotland:subtitle");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:description")
    public HippoHtml getDescription() {
        return getHippoHtml("visitscotland:description");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:products", allowModifications = false)
    public List<HippoCompound> getProducts() {
        return getChildBeansByName("visitscotland:products", HippoCompound.class);
    }

    public HippoCompound getListicleItem() {
        return getOnlyChild(getProducts());
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:images", allowModifications = false)
    public List<HippoBean> getImages() {
        return getChildBeansByName("visitscotland:images", HippoBean.class);
    }

    public HippoBean getListicleItemImage() {
        HippoBean image = getOnlyChild(getImages());
        if (image instanceof HippoFacetSelect) {
            try {
                //TODO: This is a workaround to an issue found in the CMS when a content block is composed of Image Links
                return RequestContextProvider.get().getQueryManager()
                        .createQuery(RequestContextProvider.get().getSession().getNodeByIdentifier(
                                image.getSingleProperty("hippo:docbase"))).execute().getHippoBeans().nextHippoBean();
            } catch (QueryException | RepositoryException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:extraLinks", allowModifications = false)
    public List<HippoCompound> getExtraLinks() {
        return getChildBeansByName("visitscotland:extraLinks", HippoCompound.class);
    }
}
