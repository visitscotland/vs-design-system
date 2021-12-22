package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.dms.DMSConstants;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.hippobeans.Destination;
import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.model.PSModule;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.Language;
import org.hippoecm.hst.core.component.HstRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.visitscotland.brxm.dms.DMSConstants.PSType;

@Component
public class ProductSearchWidgetFactory {

    static final String BUNDLE_ID = "product-search-widget";

    final ResourceBundleService bundle;
    final LocationLoader locationLoader;

    public ProductSearchWidgetFactory(ResourceBundleService bundle, LocationLoader locationLoader) {
        this.bundle = bundle;
        this.locationLoader = locationLoader;
    }

    public PSModule getWidget(@NotNull HstRequest request, Locale locale){
        PSModule module = new PSModule();
        PSType type = PSType.getType(request.getRequestURI());

        module.setTitle(bundle.getResourceBundle(BUNDLE_ID, type.getPathVariable() + ".title", locale));
        module.setDescription(bundle.getResourceBundle(BUNDLE_ID, type.getPathVariable() + ".description", locale));
        module.setCategory(type);
        module.setLocation(getLocation(request));
        module.setSearchUrl(Language.DUTCH.getDMSPathVariable() + String.format(DMSConstants.PRODUCT_SEARCH, type.getPathVariable()));

        return module;
    }

    /**
     * @param request
     * @return
     */
    private LocationObject getLocation(HstRequest request){
        Page page = request.getModel("document");

        while (page != null && !(page instanceof Destination)){
            page = page.getParentBean().getParentBean().getBean("content");
        }

        if (page != null) {
            //"furnance" "Furnace" --> loc="Furnace", locplace=351
            return locationLoader.getLocation(((Destination) request.getModel("document")).getLocation(), request.getLocale());
        } else {
            return null;
        }
    }
}
