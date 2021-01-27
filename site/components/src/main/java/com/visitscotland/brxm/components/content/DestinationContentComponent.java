package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.beans.Destination;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DestinationContentComponent extends PageContentComponent<Destination> {

    private static final Logger logger = LoggerFactory.getLogger(DestinationContentComponent.class);

    private PageTemplateBuilder builder;

    public DestinationContentComponent(PageUtils utils, PageTemplateBuilder builder){
        super(utils);
        this.builder = builder;
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addAttributesToRequest(request);
    }

    void addAttributesToRequest(HstRequest request) {
        Destination document = (Destination) request.getAttribute("document");

        addHeroCoordinates(request);
        builder.addModules(request, document.getLocation());
    }

}
