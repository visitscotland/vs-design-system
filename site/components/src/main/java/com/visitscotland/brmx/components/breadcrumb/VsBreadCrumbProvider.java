package com.visitscotland.brmx.components.breadcrumb;

import com.visitscotland.brmx.beans.ContentDocument;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.onehippo.forge.breadcrumb.components.BreadcrumbProvider;
import org.onehippo.forge.breadcrumb.om.BreadcrumbItem;

public class VsBreadCrumbProvider extends BreadcrumbProvider {

    /**
     * Constructor with an extra flag that determines behaviour for the trailing items
     *
     * @param component               component that creates this provider
     * @param addTrailingDocumentOnly flag determining behaviour whether to add just one trailing document or all.
     */
    @SuppressWarnings("unused")
    public VsBreadCrumbProvider(BaseHstComponent component, boolean addTrailingDocumentOnly) {
        super(component, addTrailingDocumentOnly);
    }

    public VsBreadCrumbProvider(BaseHstComponent component) {
        super(component);
    }

    protected BreadcrumbItem getBreadcrumbItem(HstRequest request, HstSiteMenuItem menuItem) {
        String title = menuItem.getName();
        if (menuItem.resolveToSiteMapItem() != null) {
            HippoBean bean = getBeanForResolvedSiteMapItem(request, menuItem.resolveToSiteMapItem());

            // TODO: IF BEAN IS INSTANCE OF BASEDOCUMENT (when Base document properly defined)
            if (bean instanceof ContentDocument) {
                title = ((ContentDocument) bean).getTitle();
            }
        }
        return new BreadcrumbItem(menuItem.getHstLink(), title);
    }
}
