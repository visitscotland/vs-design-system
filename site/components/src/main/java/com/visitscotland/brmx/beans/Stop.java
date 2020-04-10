package com.visitscotland.brmx.beans;

import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFacetSelect;
import org.onehippo.cms7.essentials.dashboard.annotations.HippoEssentialsGenerated;
import org.hippoecm.hst.content.beans.Node;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import java.util.List;
import javax.jcr.RepositoryException;

/** 
 * TODO: Beanwriter: Failed to create getter for node type: hippo:compound
 */
@HippoEssentialsGenerated(internalName = "visitscotland:Stop")
@Node(jcrType = "visitscotland:Stop")
public class Stop extends BaseDocument {
    @HippoEssentialsGenerated(internalName = "visitscotland:tipsTitle")
    public String getTipsTitle() {
        return getSingleProperty("visitscotland:tipsTitle");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:title")
    public String getTitle() {
        return getSingleProperty("visitscotland:title");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:description")
    public HippoHtml getDescription() {
        return getHippoHtml("visitscotland:description");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:tips")
    public HippoHtml getTips() {
        return getHippoHtml("visitscotland:tips");
    }

    public List<?> getProducts() {
        return getChildBeansByName("visitscotland:product");
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:product", allowModifications = false)
    public List<HippoBean> getStop() {
        return getChildBeansByName("visitscotland:product", HippoBean.class);
    }

    public HippoBean getStopItem() {
        return getOnlyChild(getStop());
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:image")
    public Image getImage() {
        return getLinkedBean("visitscotland:image", Image.class);
    }

    public HippoBean getStopItemImage() {
        HippoBean image = getImage();
        if (image instanceof HippoFacetSelect) {
            try {
                return RequestContextProvider
                        .get()
                        .getQueryManager()
                        .createQuery(
                                RequestContextProvider
                                        .get()
                                        .getSession()
                                        .getNodeByIdentifier(
                                                image.getSingleProperty("hippo:docbase")))
                        .execute().getHippoBeans().nextHippoBean();
            } catch (QueryException | RepositoryException e) {
                e.printStackTrace();
            }
        }
        return image;
    }

    @HippoEssentialsGenerated(internalName = "visitscotland:subtitle")
    public String getSubtitle() {
        return getSingleProperty("visitscotland:subtitle");
    }
}
