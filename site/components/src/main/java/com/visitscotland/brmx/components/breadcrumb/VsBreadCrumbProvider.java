package com.visitscotland.brmx.components.breadcrumb;

import com.visitscotland.brmx.beans.ContentDocument;
import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.HippoUtils;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.onehippo.forge.breadcrumb.components.BreadcrumbProvider;
import org.onehippo.forge.breadcrumb.om.BreadcrumbItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class VsBreadCrumbProvider extends BreadcrumbProvider {

    private static final Logger logger = LoggerFactory.getLogger(VsBreadCrumbProvider.class.getName());

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

    /**
     * Create a Breadcrumb item from a Navigation Item (MenuItem).
     */
    protected BreadcrumbItem getBreadcrumbItem(HstRequest request, HstSiteMenuItem menuItem) {
        if (menuItem.resolveToSiteMapItem() != null) {
            HippoBean bean = getComponent().getBeanForResolvedSiteMapItem(request, menuItem.resolveToSiteMapItem());
            if (bean != null) {
                return new BreadcrumbItem(menuItem.getHstLink(), getBreadcrumbText(bean));
            } else {
                return null;
            }
        } else {
            //If this warning message is logged and it is required that a menu item appears in the breadcrumb even though it
            //is not backed from a document, I'd be useful to use the logic of enhancedmenu.
            logger.warn (CommonUtils.contentIssue("The menu Item {} does point to a document.", menuItem.getName()));
            //The following error message flags a possible issue and a solution. If the implementation is required please remove the log message.
            logger.warn (CommonUtils.contentIssue("If previous message is not an unexpected issue, some logic migh", menuItem.getName()));
            return new BreadcrumbItem(menuItem.getHstLink(), menuItem.getName());
        }
    }

    /**
     * Creates a breadcrumb item from a Content driven page (Hippo bean)
     */
    protected BreadcrumbItem getBreadcrumbItem(final HstRequest request, final HippoBean bean,
                                               final boolean navigationStateful) {
        final HstRequestContext context = request.getRequestContext();

        if (navigationStateful) {
            return new BreadcrumbItem(
                    context.getHstLinkCreator().create(bean.getNode(), context, null, true, true),
                    getBreadcrumbText(bean));
        } else {
            return new BreadcrumbItem(context.getHstLinkCreator().create(bean, context), getBreadcrumbText(bean));
        }
    }

    /**
     * Add breadcrumb items based on an ancestor.
     *
     * Note: This method has been overriden so we can adapt the way it to our sitemap structure
     *
     * @param items          list of breadcrumb items
     * @param currentFolder a bean described by URL that is in the child tree of the ancestor bean
     * @param ancestorBean a bean that the ancestor of the current bean
     * @param request     HST request
     */
    @Override
    protected void addAncestorBasedParentItems(final List<BreadcrumbItem> items, final HippoBean currentFolder,
                                               final HippoBean ancestorBean, final HstRequest request) {

        final ResolvedSiteMapItem currentSmi = request.getRequestContext().getResolvedSiteMapItem();
        final HippoBean currentItem = getComponent().getBeanForResolvedSiteMapItem(request, currentSmi);

        HippoBean currentItemBean = currentItem;
        while (!currentItemBean.getParentBean().isSelf(ancestorBean)) {
            final BreadcrumbItem item = getBreadcrumbItem(request, currentItemBean);
            if (item != null) {
                items.add(item);
            }

            currentItemBean = currentItemBean.getParentBean().getParentBean().getBean("content");
        }

    }

    /**
     * Extract the proper name from the bean.
     * @param bean
     * @return
     */
    private String getBreadcrumbText(final HippoBean bean) {
        if (bean instanceof Page) {
            Page page = ((Page) bean);
            if (!CommonUtils.isEmpty(page.getBreadcrumb())){
                return page.getBreadcrumb();
            } else if (!CommonUtils.isEmpty(page.getTitle())){
                return page.getTitle();
            }
            logger.warn (CommonUtils.contentIssue("The document {} does not have a title so breadcrumb is showing its title ({})", bean.getDisplayName(), bean.getPath()));
        }

        return bean.getDisplayName();
    }


}
