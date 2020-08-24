package com.visitscotland.brmx.components.breadcrumb;


import com.visitscotland.brmx.beans.Page;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.request.ComponentConfiguration;
import org.onehippo.cms7.essentials.components.CommonComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;

public class VsBreadcrumbComponent extends CommonComponent {

    private static final Logger logger = LoggerFactory.getLogger(VsBreadcrumbComponent.class.getName());

    final String REQUESTED_URI = "requestedURI";
    final String IS_HOME = "isHome";
    final String BREADCRUMB = "breadcrumb";
    final String DOCUMENT = "document";

    private VsBreadCrumbProvider breadcrumbProvider;

    public void doBeforeRender(HstRequest request, HstResponse response) throws HstComponentException {
        super.doBeforeRender(request, response);

        //Requested URL to identify the current page from the breadcrumb
        request.setAttribute(REQUESTED_URI, request.getRequestURI());
        //Identify if the page is the home page independently from the environment (local, dev, acct, prod) and language
        request.setAttribute(IS_HOME, "root".equals(request.getRequestContext().getResolvedSiteMapItem().getHstSiteMapItem().getId()));
        //Breadcrumb Items list
        request.setAttribute(BREADCRUMB, this.breadcrumbProvider.getBreadcrumb(request));
        //Main document for the page
        setDocument(request);
    }

    private void setDocument(HstRequest request){
        HippoBean document = request.getRequestContext().getContentBean();
        if (document != null && document instanceof Page) {
            request.setAttribute(DOCUMENT, document);
        } else {
            logger.error("There is not a document associated for the following request: " + request.getRequestURI());
        }
    }

    public void init(ServletContext servletContext, ComponentConfiguration componentConfig) throws HstComponentException {
        super.init(servletContext, componentConfig);
        this.breadcrumbProvider = new VsBreadCrumbProvider(this);
    }

}
