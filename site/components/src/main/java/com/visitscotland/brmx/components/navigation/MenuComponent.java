package com.visitscotland.brmx.components.navigation;


import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.Widget;
import com.visitscotland.brmx.components.navigation.info.MenuComponentInfo;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.HstSiteMenu;
import org.hippoecm.hst.core.sitemenu.HstSiteMenuItem;
import org.onehippo.cms7.essentials.components.EssentialsMenuComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.MissingFormatArgumentException;

@ParametersInfo(
        type = MenuComponentInfo.class
)
public class MenuComponent extends EssentialsMenuComponent {

    static final String STATIC = "static";
    static final String NAVIGATION_PREFIX = "navigation.";
    static final String CTA_SUFFIX = ".cta";

    static final String ENHANCED_MENU = "enhancedMenu";
    static final String MENU = "menu";

    private ResourceBundleService bundle;
    private HippoUtilsService utils;

    public MenuComponent(){
        this(new ResourceBundleService(), new HippoUtilsService());
    }

    public MenuComponent(ResourceBundleService bundle, HippoUtilsService utils){
        this.bundle = bundle;
        this.utils = utils;
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        enhanceRequest(request);
    }

    protected void enhanceRequest(HstRequest request){
        bundle.registerIn(request);
        exploreMenu(request);
    }

    protected void exploreMenu(HstRequest request){
        List<HstSiteMenuItem> enhancedMenu = new ArrayList<>();

        if (request.getModel(MENU) != null) {
            for (HstSiteMenuItem hstItem: ((HstSiteMenu) request.getModel(MENU)).getSiteMenuItems()) {
                MenuItem menuItem = exploreMenu(request, hstItem);
                if (menuItem != null) {
                    enhancedMenu.add(menuItem);
                }
            }

            request.setModel(ENHANCED_MENU, enhancedMenu);

            // TODO transform the list of elements into an element so it can be used is hst.cmseditmenu
//            VsHstSiteMenuItemImpl root = new VsHstSiteMenuItemImpl(null, request.getModel("menu"));
//            for (HstSiteMenuItem item : ((HstSiteMenu) request.getModel("menu")).getSiteMenuItems()) {
//                exploreMenu(request, root, item);
//            }
//
//            request.setModel("enhancedMenu", enhancedMenu);
//            request.setModel("enhancedMenuItem", root);
//
//            //TODO update references
////            request.setModel("menu", enhancedMenu);

        }

    }

    private MenuItem exploreMenu(HstRequest request, HstSiteMenuItem hstItem){
        MenuItem menuItem = new MenuItem(hstItem);


        String nodeName = ((HstSiteMenu) request.getModel(MENU)).getName();
        String resourceBundle = NAVIGATION_PREFIX + nodeName;

        //By default the name would be populated by the resourceBundle
        menuItem.setTitle(bundle.getResourceBundle(resourceBundle, hstItem.getName(), request.getLocale(), true));

        //if document base page or widget, we enhance the document
        if (isDocumentBased(hstItem.getHstLink())) {
            ResolvedSiteMapItem rsi = hstItem.resolveToSiteMapItem();
            if (rsi != null) {
                HippoBean bean = utils.getBeanForResolvedSiteMapItem(request, rsi);
                //if the document does not exist or no publish
                if (bean != null && !(bean instanceof HippoFolder)){
                    //By default the name would be populated by the resourceBundle
                    menuItem.setTitle(bundle.getResourceBundle(resourceBundle, hstItem.getName(), request.getLocale(), true));

                    //Widget document
                    if (bean instanceof Widget) {
                        menuItem.setWidget((Widget) bean);
                        if (menuItem.getTitle() == null){
                            menuItem.setTitle(hstItem.getName());
                        }
                    } else {
                        if (Contract.isEmpty(menuItem.getTitle()) && bean instanceof Page) {
                            menuItem.setTitle(((Page) bean).getTitle());
                        }

                        if (bundle.existsResourceBundleKey(resourceBundle,hstItem.getName()+ CTA_SUFFIX,  request.getLocale())){
                            menuItem.setCta(bundle.getResourceBundle(resourceBundle,hstItem.getName()+ CTA_SUFFIX, request.getLocale()));
                        } else if (menuItem.getTitle() != null){
                            String seeAll = bundle.getResourceBundle(STATIC,"see-all-cta", request.getLocale());
                            if (seeAll != null) {
                                try {
                                    menuItem.setCta(String.format(seeAll, menuItem.getTitle()));
                                } catch (MissingFormatArgumentException e){
                                    //Catch the exception and eliminate the parameters
                                    //TODO: log error
                                    CommonUtils.contentIssue("The label '%s' has more parameters than expected. File: %s, key: %s",
                                            seeAll, STATIC, "see-all-cta");
                                    menuItem.setCta(seeAll.replace("%s", ""));
                                }
                            }
                        }
                    }
                }

            } else {
                //By default the name would be populated by the resourceBundle
                //TODO coverage
                menuItem.setTitle(bundle.getResourceBundle(resourceBundle, hstItem.getName(), request.getLocale()));
                //TODO: Check if the page exists on the global channel
            }
        }

        if (menuItem.getTitle() == null) {
            return null;
        } else {
            //Children will add themselves to the parent on the method exploreMenu
            for (HstSiteMenuItem hstChild : hstItem.getChildMenuItems()) {
                MenuItem item = exploreMenu(request, hstChild);
                if (item != null) {
                    menuItem.addChild(item);
                }
            }
            return menuItem;
        }


    }



//    private VsHstSiteMenuItemImpl populateMenuItem(VsHstSiteMenuItemImpl parent, HstSiteMenuItem menu, HippoBean bean, Locale locale){
//        VsHstSiteMenuItemImpl enhancedMenu = new VsHstSiteMenuItemImpl(parent, menu);
//        //if the document does not exist or no publish
//        if (bean == null || bean instanceof HippoFolder){
//            return null;
//        } else if (bean instanceof Widget) {
//            enhancedMenu.setWidget((Widget) bean);
//        } else {
//           TODO: Body of the method
//        }
//
//        return enhancedMenu;
//    }

    private boolean isDocumentBased(HstLink link){
        return link != null && link.getPath() != null && link.getPath().length() > 0;
    }

}
