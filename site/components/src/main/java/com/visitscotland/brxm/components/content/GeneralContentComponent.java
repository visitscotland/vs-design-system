package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.hippobeans.Destination;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.utils.PageTemplateBuilder;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralContentComponent extends PageContentComponent<Destination> {

    private static final Logger logger = LoggerFactory.getLogger(GeneralContentComponent.class);

    public static final String SIMPLE = "Simple";
    public static final String STANDARD = "Standard";
    static final String SEARCH_RESULTS = "searchResultsPage";

    private PageTemplateBuilder builder;

    public GeneralContentComponent(){
        logger.debug("GeneralContentComponent initialized");
        this.builder = VsComponentManager.get(PageTemplateBuilder.class);
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        if (getDocument(request).getPath().contains("site-search-results")){
            request.setAttribute(SEARCH_RESULTS, true);
        }
        builder.addModules(request);
    }

}
