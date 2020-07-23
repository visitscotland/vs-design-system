package com.visitscotland.brmx.components.navigation;


import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.Widget;
import com.visitscotland.brmx.components.navigation.info.MenuComponentInfo;
import com.visitscotland.brmx.services.ResourceBundleService;
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

@ParametersInfo(
        type = MenuComponentInfo.class
)
public class MenuComponent extends EssentialsMenuComponent {

    private static final String NAVIGATION_LINKS = "meganav";
    private static final String HEADER = "header";
    private static final String CTA_SUFFIX = ".cta";

    static final String ENHANCED_MENU = "enhancedMenu";

    private ResourceBundleService bundle;
    private HippoUtilsService utils;


    public MenuComponent(){
        bundle = new ResourceBundleService();
        utils = new HippoUtilsService();
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        List<HstSiteMenuItem> enhancedMenu = new ArrayList<>();



        if (request.getModel("menu") != null) {
            for (HstSiteMenuItem item: ((HstSiteMenu) request.getModel("menu")).getSiteMenuItems()) {
                enhancedMenu.add(exploreMenu(request, null, item));
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

    private VsHstSiteMenuItemImpl exploreMenu(HstRequest request, VsHstSiteMenuItemImpl parent, HstSiteMenuItem menu){
        VsHstSiteMenuItemImpl enhancedMenu = new VsHstSiteMenuItemImpl(parent, menu);
        boolean documentExist = true;

        String nodeName = ((HstSiteMenu) request.getModel("menu")).getName();
        String resourceBundle = null;
        if ("main".equals(nodeName)){
            resourceBundle = NAVIGATION_LINKS;
        } else {
            resourceBundle = "navigation";
        }

        //By default the name would be populated by the resourceBundle
        enhancedMenu.setTitle(bundle.getResourceBundle(resourceBundle, menu.getName(), request.getLocale()));

        //if document base page or widget, we enhance the document
        if (isDocumentBased(menu.getHstLink())) {
            ResolvedSiteMapItem rsi = menu.resolveToSiteMapItem();
            if (rsi != null) {
                HippoBean bean = utils.getBeanForResolvedSiteMapItem(request, menu.resolveToSiteMapItem());
                //if the document does not exist or no publish
                if (bean != null && !(bean instanceof HippoFolder)){
                    //By default the name would be populated by the resourceBundle
                    enhancedMenu.setTitle(bundle.getResourceBundle(resourceBundle, menu.getName(), request.getLocale()));

                    //Widget document
                    if (bean instanceof Widget) {
                        enhancedMenu.setWidget((Widget) bean);
                    } else {
                        if (Contract.isEmpty(enhancedMenu.getTitle()) && bean instanceof Page) {
                            enhancedMenu.setTitle(((Page) bean).getTitle());
                        }

                        if (bundle.existsResourceBundleKey(resourceBundle,menu.getName()+ CTA_SUFFIX,  request.getLocale())){
                            enhancedMenu.setCta(bundle.getResourceBundle(resourceBundle,menu.getName()+ CTA_SUFFIX, request.getLocale()));
                        } else {
                            String seeAll = bundle.getResourceBundle(HEADER,"see-all-cta", request.getLocale());
                            if (seeAll != null) {
                                enhancedMenu.setCta(String.format(seeAll, enhancedMenu.getTitle()));
                            }
                        }
                    }

                }

            } else {
                //By default the name would be populated by the resourceBundle
                enhancedMenu.setTitle(bundle.getResourceBundle(resourceBundle, menu.getName(), request.getLocale()));
                //TODO: Check if the page exists on the global channel
            }
        }



        //Childen will add themselves to the parent on the method exploreMenu
        for (HstSiteMenuItem child : menu.getChildMenuItems()) {
            exploreMenu(request, enhancedMenu, child);
        }

        return enhancedMenu;
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
