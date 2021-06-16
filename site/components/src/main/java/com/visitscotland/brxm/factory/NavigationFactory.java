package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.components.navigation.MenuItem;
import com.visitscotland.brxm.components.navigation.RootMenuItem;
import com.visitscotland.brxm.hippobeans.CMSLink;
import com.visitscotland.brxm.hippobeans.FeaturedWidget;
import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import com.visitscotland.brxm.model.megalinks.EnhancedLink;
import com.visitscotland.brxm.model.navigation.FeaturedItem;
import com.visitscotland.brxm.model.navigation.NavigationWidget;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingFormatArgumentException;

@Component
public class NavigationFactory {

    private static final Logger logger = LoggerFactory.getLogger(NavigationFactory.class);
    private static final Logger contentLogger = LoggerFactory.getLogger("content");

    static final String STATIC = "navigation.static";
    static final String CTA_SUFFIX = ".cta";
    static final String NAVIGATION_PREFIX = "navigation.";

    private ResourceBundleService bundle;
    private HippoUtilsService utils;
    private LinkService linkService;

    public NavigationFactory(ResourceBundleService bundle, HippoUtilsService utils, LinkService linkService) {
        this.bundle = bundle;
        this.utils = utils;
        this.linkService = linkService;
    }

    /**
     * Builds a VisitScotland enhanced menu from the out of the box menu
     */
    public RootMenuItem buildMenu(HstRequest request, HstSiteMenu hstSiteMenu) {
        List<HstSiteMenuItem> enhancedMenu = new ArrayList<>();
        RootMenuItem root = new RootMenuItem(hstSiteMenu);

        if (hstSiteMenu != null) {
            //Calculate the resource bundle id
            String resourceBundle = NAVIGATION_PREFIX + hstSiteMenu.getName();

            for (HstSiteMenuItem hstItem : hstSiteMenu.getSiteMenuItems()) {
                MenuItem menuItem = getMenuItem(request, hstItem, resourceBundle);
                if (menuItem != null) {
                    enhancedMenu.add(menuItem);
                }
            }

            root.setSiteMenuItems(enhancedMenu);
        }
        return root;
    }

    /**
     * Creates a new MenuItem that Matches with Bloomreach's MenuItem specification. which enhanced information
     * about the linked item
     */
    private MenuItem getMenuItem(HstRequest request, HstSiteMenuItem hstItem, String resourceBundle) {
        MenuItem menuItem = new MenuItem(hstItem);

        //By default, the name would be populated by the resourceBundle
        menuItem.setTitle(bundle.getResourceBundle(resourceBundle, hstItem.getName(), request.getLocale(), true));

        //if document base page or widget, we enhance the document
        if (isDocumentBased(hstItem.getHstLink())) {
            ResolvedSiteMapItem rsi = hstItem.resolveToSiteMapItem();
            if (rsi != null) {
                HippoBean bean = utils.getBeanForResolvedSiteMapItem(request, rsi);
                //if the document does not exist or no publish
                if (bean != null && !(bean instanceof HippoFolder)) {
                    processItem(request, bean, menuItem, resourceBundle);
                }
            }
        }

        if (menuItem.getTitle() != null || menuItem.getWidget() != null) {
            //Process all children
            for (HstSiteMenuItem hstChild : hstItem.getChildMenuItems()) {
                menuItem.addChild(getMenuItem(request, hstChild, resourceBundle));
            }
            return menuItem;
        } else {
            //Menu Items with no title cannot be displayed, so they are not included in the list of menu Items.
            return null;
        }
    }

    /**
     * Identifies the type of document linked and populated the data on the menu item accordingly
     */
    private void processItem(HstRequest request, HippoBean bean, MenuItem menuItem, String resourceBundle) {
        if (bean instanceof FeaturedWidget) {
            menuItem.setWidget(addFeatureItem((FeaturedWidget) bean, request.getLocale()));
        } else if (bean instanceof Page) {
            createMenuItemFromPage(menuItem, (Page) bean, resourceBundle, request.getLocale());
        } else {
            contentLogger.warn("Skipping Unexpected document type: {}", bean.getClass().getSimpleName());
        }
    }

    /**
     * Adds a Featured Navigation Widget
     */
    private NavigationWidget addFeatureItem(FeaturedWidget document, Locale locale) {
       FeaturedItem widget = new FeaturedItem();
        ArrayList<EnhancedLink> items = new ArrayList<>();

        for (CMSLink cmsLink : document.getItems()) {
            if (!(cmsLink.getLink() instanceof Linkable)){
                contentLogger.warn("An incorrect Type of link has been set in a featured item: {}", document.getPath());
                continue;
            }
            items.add(linkService.createEnhancedLink((Linkable) cmsLink.getLink(), widget, locale, false));
        }

        widget.setLinks(items);
        widget.setHippoBean(document);
        return widget;
    }

    /**
     * Updates the menu item with enhanced information taken from the labels, or the core document where they link to
     *
     * @param menuItem Menu Item to enhance
     * @param document HippoBean that contains the relevant document that is linked from the header
     * @param bundleId Resource Bundle where the labels of the menu item might come from
     * @param locale   Request Locale
     */
    private void createMenuItemFromPage(MenuItem menuItem, Page document, String bundleId, Locale locale) {
        //If the menu hasn't been set we use the title coming from the document.
        if (Contract.isEmpty(menuItem.getTitle())) {
            if (!Contract.isEmpty(document.getBreadcrumb())) {
                menuItem.setTitle(document.getBreadcrumb());
            } else {
                menuItem.setTitle(document.getTitle());
            }
        }

        if (bundle.existsResourceBundleKey(bundleId, menuItem.getHstMenuItem().getName() + CTA_SUFFIX, locale)) {
            menuItem.setCta(bundle.getResourceBundle(bundleId, menuItem.getHstMenuItem().getName() + CTA_SUFFIX, locale));
        } else if (menuItem.getTitle() != null) {
            String seeAll = bundle.getResourceBundle(STATIC, "see-all-cta", locale);
            if (seeAll != null) {
                try {
                    menuItem.setCta(String.format(seeAll, menuItem.getTitle()));
                } catch (MissingFormatArgumentException e) {

                    contentLogger.warn("The label '{}' has more parameters than expected. File: {}, key: {}",
                            seeAll, STATIC, "see-all-cta");
                    //After Catching the exception, we can eliminate the parameters.
                    menuItem.setCta(seeAll.replace("%s", ""));
                }
            }
        } else {
            //Title is a mandatory field for all page documents. The following line being executed might mean that the node is corrupted
            logger.error("The node {} does not have a title", document.getPath());
        }
    }

    /**
     * Indicates if the link is based on a document
     */
    private boolean isDocumentBased(HstLink link) {
        return link != null && link.getPath() != null && link.getPath().length() > 0;
    }
}
