package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.hippobeans.Banner;
import com.visitscotland.brxm.model.BannerModule;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.Properties;
import org.hippoecm.hst.content.beans.ObjectBeanManagerException;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import java.util.Locale;

@Component
public class BannerFactory {

    private final LinkService linkService;
    private final HippoUtilsService hippoUtilsService;
    private final Properties properties;
    private static final Logger logger = LoggerFactory.getLogger(BannerFactory.class);
    private static final Logger contentLogger = LoggerFactory.getLogger("content");

    public BannerFactory(LinkService linkService, HippoUtilsService hippoUtilsService, Properties properties) {
        this.linkService = linkService;
        this.hippoUtilsService = hippoUtilsService;
        this.properties = properties;
    }

    public BannerModule getBannerModule(HstRequest request) {
        try {
            HippoBean bean = hippoUtilsService.getDocumentFromContent(properties.getBannerContentPath());
            if (bean instanceof Banner) {
                return getBannerModule((Banner)bean, request.getLocale());
            } else {
               contentLogger.warn("Expected Banner bean at {}, but found {}", properties.getBannerContentPath(), bean == null ? "null" : bean.getClass().getSimpleName());
            }
        } catch (PathNotFoundException e) {
            logger.debug("No published banner found at {}", properties.getBannerContentPath());
        } catch (RepositoryException | ObjectBeanManagerException | QueryException e) {
            logger.error("Failed to access repo to access banner at location {}", properties.getBannerContentPath(), e);
        }
        return null;
    }

    private BannerModule getBannerModule(Banner document, Locale locale) {
        BannerModule module = new BannerModule();
        module.setTitle(document.getTitle());
        module.setCopy(document.getCopy());
        FlatLink ctaLink = linkService.createFindOutMoreLink(module, locale, document.getCtaLink());
        module.setCtaLink(ctaLink);
        return module;
    }
}
