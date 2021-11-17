package com.visitscotland.brxm.components.navigation;


import com.visitscotland.brxm.components.navigation.info.MenuComponentInfo;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.factory.NavigationFactory;
import com.visitscotland.brxm.services.DocumentUtilsService;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsMenuComponent;

@ParametersInfo(
        type = MenuComponentInfo.class
)
public class MenuComponent extends EssentialsMenuComponent {

    public static final String MENU = "menu";
    public static final String LOCALIZED_URLS = "localizedURLs";

    private NavigationFactory factory;
    private DocumentUtilsService utils;


    public MenuComponent() {
        this(VsComponentManager.get(NavigationFactory.class),
                VsComponentManager.get(DocumentUtilsService.class));
    }

    public MenuComponent(NavigationFactory factory, DocumentUtilsService utils) {
        this.factory = factory;
        this.utils = utils;

    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        request.setModel(MENU, factory.buildMenu(request, request.getModel(MENU)));
        request.setModel(LOCALIZED_URLS, utils.getLocalizedURLs(request, false));
    }
}
