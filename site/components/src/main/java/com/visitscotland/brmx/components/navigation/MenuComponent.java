package com.visitscotland.brmx.components.navigation;


import com.visitscotland.brmx.utils.HippoUtils;
import com.visitscotland.brmx.beans.ContentDocument;
import com.visitscotland.brmx.beans.Widget;
import com.visitscotland.brmx.components.navigation.info.MenuComponentInfo;
import org.hippoecm.hst.content.beans.standard.HippoBean;
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

        if (System.getenv().containsKey("BRANCH_NAME")){
            String branch = System.getenv("BRANCH_NAME");
            //TODO: We might want to add more information to the header
            request.setModel("branch", branch);
        }


    }

    private VsHstSiteMenuItemImpl exploreMenu(HstRequest request, VsHstSiteMenuItemImpl parent, HstSiteMenuItem menu){
        VsHstSiteMenuItemImpl enhancedMenu = new VsHstSiteMenuItemImpl(parent, menu);

        //if document base page or widget, we enhance the document
        if(isDocumentBased(menu.getHstLink())){
            ResolvedSiteMapItem rsi = menu.resolveToSiteMapItem();
            if (rsi != null){
                HippoBean bean = getBeanForResolvedSiteMapItem(request, menu.resolveToSiteMapItem());

                //Widget document
                if (bean instanceof Widget){
                    enhancedMenu.setWidget((Widget) bean);
                    // TODO: IF BEAN IS INSTANCE OF BASEDOCUMENT (when Base document properly defined)
                } else if (bean instanceof ContentDocument){
                    if (HippoUtils.existsResourceBundleKey(menu.getName(),NAVIGATION_BUNDLE, request.getLocale())){
                        enhancedMenu.setTitle(HippoUtils.getResourceBundle(menu.getName(),NAVIGATION_BUNDLE, request.getLocale()));
                    } else {
                        enhancedMenu.setTitle(((ContentDocument) bean).getTitle());
                    }
                }
            }
        }

        if (enhancedMenu.getTitle() == null){
            String value = HippoUtils.getResourceBundle(menu.getName(),NAVIGATION_BUNDLE, request.getLocale());
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
