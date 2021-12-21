package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.components.breadcrumb.VsBreadcrumbComponent;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.hippobeans.Destination;
import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.model.PSModule;
import com.visitscotland.brxm.services.ResourceBundleService;
import org.hippoecm.hst.core.component.HstRequest;
import org.jetbrains.annotations.NotNull;
import org.onehippo.forge.breadcrumb.components.BreadcrumbProvider;
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
        PSType type = getType(request);

        module.setTitle(bundle.getResourceBundle(BUNDLE_ID, type.getPathVariable() + ".title", locale));
        module.setDescription(bundle.getResourceBundle(BUNDLE_ID, type.getPathVariable() + ".description", locale));
        module.setCategory(type);

        module.setLocation(getLocation(request));

        return module;
    }

    private PSType getType(HstRequest request){
        Page page = request.getModel("document");

        return PSType.SEE_DO;
    }



    private LocationObject getLocation(HstRequest request){

        if (request.getModel("document") instanceof Destination){
            return locationLoader.getLocation(((Destination) request.getModel("document")).getLocation(), request.getLocale()); //"furnance" "Furnance" --> loc="Furnance", locplace=351
        } else {
            return null;
        }
    }
}
