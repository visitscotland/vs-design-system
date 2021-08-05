package com.visitscotland.brxm.factory;


import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.ProductSearchBuilder;
import com.visitscotland.brxm.hippobeans.CannedSearch;
import com.visitscotland.brxm.model.CannedSearchModule;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CannedSearchFactory {

    private static final Logger logger = LoggerFactory.getLogger(CannedSearchFactory.class);

    static final String BUNDLE_ID = "canned-search";

    private final ResourceBundleService bundle;
    private final LinkService linkService;

    public CannedSearchFactory(ResourceBundleService bundle, LinkService linkService){
        this.bundle = bundle;
        this.linkService = linkService;
    }

    public CannedSearchModule getCannedSearchModule(CannedSearch document, Locale locale){
        logger.info("Creating CannedSearchModule for {}", document.getPath());

        CannedSearchModule module = new CannedSearchModule ();
        module.setHippoBean(document);
        module.setTitle(document.getTitle());
        module.setDescription(document.getCopy());

        FlatLink viewAllCta = linkService.createLink(locale, document.getCriteria());

        if (Contract.isEmpty(document.getCriteria().getLabel())) {
            viewAllCta.setLabel(bundle.getResourceBundle(BUNDLE_ID, "canned-search.listview", locale));
        }
        module.setViewAllLink(viewAllCta);

        module.setCannedSearchEndpoint(productSearch().fromHippoBean(document.getCriteria().getSearch()).buildCannedSearch());

        if (document.getCriteria().getSearch().getProductType().equals("even")){
            module.setCredit(bundle.getResourceBundle(BUNDLE_ID,"canned-search.credit-events", locale));
        }


        return module;
    }

    /**
     * Fetches a new Product Search Object
     */
    private ProductSearchBuilder productSearch(){
        return VsComponentManager.get(ProductSearchBuilder.class);
    }

}
