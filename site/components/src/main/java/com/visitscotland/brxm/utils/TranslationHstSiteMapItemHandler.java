package com.visitscotland.brxm.utils;

import org.hippoecm.hst.configuration.hosting.VirtualHosts;
import org.hippoecm.hst.container.RequestContextProvider;
import org.hippoecm.hst.core.container.ContainerConstants;
import org.hippoecm.hst.core.internal.HstMutableRequestContext;
import org.hippoecm.hst.core.request.ResolvedMount;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.request.ResolvedVirtualHost;
import org.hippoecm.hst.core.request.SiteMapItemHandlerConfiguration;
import org.hippoecm.hst.core.sitemapitemhandler.HstSiteMapItemHandler;
import org.hippoecm.hst.core.sitemapitemhandler.HstSiteMapItemHandlerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TranslationHstSiteMapItemHandler implements HstSiteMapItemHandler {

    private static final Logger logger = LoggerFactory.getLogger(TranslationHstSiteMapItemHandler.class);
    private final String PAGE_NOT_FOUND_COMPONENT = "hst:pages/pagenotfound";
    // hst:alias on english mount (/hst:visitscotland/hst:hosts/<vhost>/hst:root)
    private final String ENGLISH_MOUNT_ALIAS = "en";

    @Override
    public void init(ServletContext servletContext, SiteMapItemHandlerConfiguration siteMapItemHandlerConfiguration) throws HstSiteMapItemHandlerException {

    }

    @Override
    public ResolvedSiteMapItem process(ResolvedSiteMapItem resolvedSiteMapItem, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws HstSiteMapItemHandlerException {
        if (resolvedSiteMapItem == null || resolvedSiteMapItem.getResolvedMount() == null || ENGLISH_MOUNT_ALIAS.equals(resolvedSiteMapItem.getResolvedMount().getMount().getAlias())) {
            return resolvedSiteMapItem;
        }
        if (isPageNotFound(resolvedSiteMapItem)) {
            Object possibleResolvedVirtualHost = httpServletRequest.getAttribute(ContainerConstants.VIRTUALHOSTS_REQUEST_ATTR);
            if (!(possibleResolvedVirtualHost instanceof ResolvedVirtualHost)) {
                logger.error("Failed to get ResolvedVirtualHost from request servlet");
                return resolvedSiteMapItem;
            }
            ResolvedMount englishMount = ((ResolvedVirtualHost) possibleResolvedVirtualHost).matchMount(ENGLISH_MOUNT_ALIAS);
            HstMutableRequestContext requestContext = (HstMutableRequestContext)RequestContextProvider.get();
            requestContext.setResolvedMount(englishMount);
            ResolvedSiteMapItem englishSiteMapItem = englishMount.matchSiteMapItem("/" + resolvedSiteMapItem.getPathInfo());
            requestContext.setResolvedSiteMapItem(englishSiteMapItem);
            if (!isPageNotFound(englishSiteMapItem)) {
                // An english version of the component has been found, which means that it just has not been translated yet
                requestContext.setAttribute("document", requestContext.getContentBean());
                requestContext.setResolvedMount(resolvedSiteMapItem.getResolvedMount());
                return englishSiteMapItem;
            }
        }
        return resolvedSiteMapItem;
    }

    private boolean isPageNotFound(ResolvedSiteMapItem resolvedSiteMapItem) {
        if (resolvedSiteMapItem == null || resolvedSiteMapItem.getHstComponentConfiguration() == null) {
            return false;
        }
        return resolvedSiteMapItem.getHstComponentConfiguration().getId().equals(PAGE_NOT_FOUND_COMPONENT);
    }

    @Override
    public void destroy() throws HstSiteMapItemHandlerException {

    }
}
