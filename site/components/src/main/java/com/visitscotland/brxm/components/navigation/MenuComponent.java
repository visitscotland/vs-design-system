package com.visitscotland.brxm.components.navigation;


import com.visitscotland.brxm.components.navigation.info.MenuComponentInfo;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.factory.BannerFactory;
import com.visitscotland.brxm.factory.NavigationFactory;
import com.visitscotland.brxm.services.DocumentUtilsService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.InternalParameterProcessor;
import com.visitscotland.brxm.utils.Language;
import com.visitscotland.brxm.utils.Properties;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.parameters.ParametersInfo;
import org.onehippo.cms7.essentials.components.EssentialsMenuComponent;

import java.util.Locale;

@ParametersInfo(
        type = MenuComponentInfo.class
)
public class MenuComponent extends EssentialsMenuComponent {

    public static final String MENU = "menu";
    public static final String VS_LOCALE = "vsLocale";
    public static final String LOCALIZED_URLS = "localizedURLs";

    private NavigationFactory factory;
    private HippoUtilsService utils;
    private Properties properties;


    public MenuComponent() {
        this(VsComponentManager.get(NavigationFactory.class),
                VsComponentManager.get(HippoUtilsService.class),
                VsComponentManager.get(Properties.class));
    }

    public MenuComponent(NavigationFactory factory, HippoUtilsService utils, Properties properties) {
        this.factory = factory;
        this.utils = utils;
        this.properties = properties;
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        Language language = Language.getLanguageForLocale(request.getLocale());

        if (request.getRequestURI().startsWith(properties.getDmsInternalPath())) {
            String vsLocale = utils.getParameterFromUrl(request, InternalParameterProcessor.PARAM_LOCALE);
            if (vsLocale != null) {
                language = Language.getLanguageForLocale(Locale.forLanguageTag(vsLocale));
                request.setModel(VS_LOCALE, language.getLocale());
            }
        }

        request.setModel(MENU, factory.buildMenu(request, request.getModel(MENU), language));
    }
}
