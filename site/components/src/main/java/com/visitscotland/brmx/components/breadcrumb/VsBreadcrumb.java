package com.visitscotland.brmx.components.breadcrumb;


import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.ComponentConfiguration;
import org.hippoecm.hst.component.support.bean.BaseHstComponent;

import javax.servlet.ServletContext;

public class VsBreadcrumb extends BaseHstComponent {

    private VsBreadCrumbProvider breadcrumbProvider;

    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);
        request.setAttribute("breadcrumb", this.breadcrumbProvider.getBreadcrumb(request));
    }

    public void init(ServletContext servletContext, ComponentConfiguration componentConfig) throws HstComponentException {
        super.init(servletContext, componentConfig);
        this.breadcrumbProvider = new VsBreadCrumbProvider(this);
    }

}
