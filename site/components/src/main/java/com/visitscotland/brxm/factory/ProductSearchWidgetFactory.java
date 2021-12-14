package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.hippobeans.Destination;
import com.visitscotland.brxm.hippobeans.Page;
import com.visitscotland.brxm.model.PSModule;
import com.visitscotland.brxm.services.ResourceBundleService;
import org.hippoecm.hst.core.component.HstRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.visitscotland.brxm.dms.DMSConstants.PSType;

@Component
public class ProductSearchWidgetFactory {

    static final String BUNDLE_ID = "product-search-widget";

    final ResourceBundleService bundle;

    public ProductSearchWidgetFactory(ResourceBundleService bundle) {
        this.bundle = bundle;
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
        Page page = (Page) request.getModel("document");

        return PSType.SEE_DO;
    }

    private String getLocation(HstRequest request){
        Page page = (Page) request.getModel("document");

        if (page instanceof Destination){
            return ((Destination) page).getLocation(); //"Furnance" --> loc="Furnance", locplace=351
                                                       //loc="Sky", locpoly=351
        } else {
            return "";
        }
    }
}
