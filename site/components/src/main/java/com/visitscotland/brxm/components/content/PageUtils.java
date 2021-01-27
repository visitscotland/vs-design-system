package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.components.content.factory.LinkModulesFactory;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import org.springframework.stereotype.Component;

/**
 * This class is Intended to be use ONLY  for those classes that extends from PageContentComponent
 *
 * By using this class, The parameters on the constructors can be reduced considerably.
 *
 * A long term solution would be to get rid of the PageContent component and to implement that functionality as
 * composition instead of inheritance
 */
@Component
public class PageUtils {

    private ResourceBundleService bundle;
    private LinkService linksService;
    private LocationLoader locationLoader;
    private LinkModulesFactory linksFactory;

    public PageUtils(ResourceBundleService bundle, LinkService linkService, LocationLoader locationLoader, LinkModulesFactory linksFactory){
        this.bundle = bundle;
        this.linksService = linkService;
        this.locationLoader = locationLoader;
        this.linksFactory = linksFactory;
    }

    public ResourceBundleService getBundle() {
        return bundle;
    }

    public LinkService getLinksService() {
        return linksService;
    }

    public LocationLoader getLocationLoader() {
        return locationLoader;
    }

    public LinkModulesFactory getLinksFactory() {
        return linksFactory;
    }
}
