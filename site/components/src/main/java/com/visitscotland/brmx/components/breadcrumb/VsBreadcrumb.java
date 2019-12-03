package com.visitscotland.brmx.components.breadcrumb;


import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.ComponentConfiguration;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.onehippo.cms7.essentials.components.CommonComponent;
import org.onehippo.forge.breadcrumb.om.Breadcrumb;
import org.onehippo.forge.breadcrumb.om.BreadcrumbItem;

import javax.servlet.ServletContext;

public class VsBreadcrumb extends CommonComponent {

    private VsBreadCrumbProvider breadcrumbProvider;

    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        //Requested URL to identify the current page from the breadcrumb
        request.setAttribute("requestedURI", request.getRequestURI());
        //Identify if the page is the home page independently from the environment (local, dev, acct, prod) and language
        request.setAttribute("isHome", "root".equals(request.getRequestContext().getResolvedSiteMapItem().getHstSiteMapItem().getId()));
        //Breadcrumb Items list
        request.setAttribute("breadcrumb", this.breadcrumbProvider.getBreadcrumb(request));
    }

    public void init(ServletContext servletContext, ComponentConfiguration componentConfig) throws HstComponentException {
        super.init(servletContext, componentConfig);
        this.breadcrumbProvider = new VsBreadCrumbProvider(this);
    }

}
