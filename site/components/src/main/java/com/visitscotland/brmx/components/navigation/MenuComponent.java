package com.visitscotland.brmx.components.navigation;


import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.utils.HippoUtils;
import com.visitscotland.brmx.beans.Widget;
import com.visitscotland.brmx.components.navigation.info.MenuComponentInfo;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoFolder;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.linking.HstLink;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.hippoecm.hst.core.request.ResolvedSiteMapItem;
import org.hippoecm.hst.core.sitemenu.*;
import org.onehippo.cms7.essentials.components.EssentialsMenuComponent;

import java.util.*;

@ParametersInfo(
        type = MenuComponentInfo.class
)
public class MenuComponent extends EssentialsMenuComponent {

    private static final String NAVIGATION_BUNDLE = "navigation";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        List<HstSiteMenuItem> enhancedMenu = new ArrayList<>();

        for (HstSiteMenuItem item: ((HstSiteMenu) request.getModel("menu")).getSiteMenuItems()) {
            enhancedMenu.add(exploreMenu(request, null, item));
        }

        request.setModel("enhancedMenu", enhancedMenu);
    }

    private VsHstSiteMenuItemImpl exploreMenu(HstRequest request, VsHstSiteMenuItemImpl parent, HstSiteMenuItem menu){
        VsHstSiteMenuItemImpl enhancedMenu = new VsHstSiteMenuItemImpl(parent, menu);
        boolean documentExist = true;
        //if document base page or widget, we enhance the document
        if (isDocumentBased(menu.getHstLink())) {
            ResolvedSiteMapItem rsi = menu.resolveToSiteMapItem();
            if (rsi != null) {
                HippoBean bean = getBeanForResolvedSiteMapItem(request, menu.resolveToSiteMapItem());
                //if the document does not exist or no publish
                if (bean == null || bean instanceof HippoFolder){
                    documentExist = false;
                }

                else {
                    //Widget document
                    if (bean instanceof Widget) {
                        enhancedMenu.setWidget((Widget) bean);
                    } else if (HippoUtils.existsResourceBundleKey(menu.getName(), NAVIGATION_BUNDLE, request.getLocale())) {
                        enhancedMenu.setTitle(HippoUtils.getResourceBundle(menu.getName(), NAVIGATION_BUNDLE, request.getLocale()));
                    } else if (bean instanceof Page) {
                        enhancedMenu.setTitle(((Page) bean).getTitle());
                    }
                }

            }
        }

        if (enhancedMenu.getTitle() == null && documentExist) {
            String value = HippoUtils.getResourceBundle(menu.getName(), NAVIGATION_BUNDLE, request.getLocale());
            enhancedMenu.setTitle(value);
        }

        for (HstSiteMenuItem child: menu.getChildMenuItems()){
            exploreMenu(request, enhancedMenu, child);
        }

        return enhancedMenu;
    }



    private boolean isDocumentBased(HstLink link){
        return link != null && link.getPath() != null && link.getPath().length() > 0;
    }

}
