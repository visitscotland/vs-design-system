package com.visitscotland.brmx.components.content.factory;

import com.visitscotland.brmx.beans.IKnow;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.IKnowModule;
import com.visitscotland.brmx.dms.DMSConstants;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class IKnowFactory {

    private static final Logger logger = LoggerFactory.getLogger(IKnowFactory.class);

    static final String BUNDLE_ID = "modules";

    private final HippoUtilsService utils;
    private final ResourceBundleService bundle;

    public IKnowFactory(){
        this(new HippoUtilsService(), new ResourceBundleService());
    }

    public IKnowFactory(HippoUtilsService utils, ResourceBundleService bundle){
        this.utils = utils;
        this.bundle = bundle;
    }

    public IKnowModule getIKnowModule(IKnow document, String location, Locale locale){
        logger.info("Creating iCentreModule for {}", document.getPath());

        IKnowModule module = new IKnowModule ();

        if (Contract.isEmpty(document.getTitle())){
            //TODO: Create labels
            module.setTitle(bundle.getResourceBundle("iknow.title.default", BUNDLE_ID, locale));
        } else {
            module.setTitle(document.getTitle());
        }

        module.setDescription(document.getDescription());

        FlatLink link = new FlatLink();

        //TODO get prodTypes from Labels (Configuration)
        //TODO Chekc with http://localhost:8080/cms/content/path/content/documents/administration/options/product-search
        link.setLink(new ProductSearchBuilder().locale(locale).productTypes(DMSConstants.TYPE_SEE_DO).award(DMSConstants.AWARD_IKNOW).location(location).build());
        //TODO: Create labels
        link.setLabel(bundle.getResourceBundle("iknow.link.label", BUNDLE_ID, locale));

        module.setLink(link);

        return module;
    }
}
