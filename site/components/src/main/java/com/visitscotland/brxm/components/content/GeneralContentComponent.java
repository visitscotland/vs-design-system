package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.beans.Destination;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeneralContentComponent extends PageContentComponent<Destination> {

    private static final Logger logger = LoggerFactory.getLogger(GeneralContentComponent.class);

    public PageTemplateBuilder builder;

    public GeneralContentComponent(PageUtils utils, PageTemplateBuilder builder){
        super(utils);
        this.builder = builder;
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addHeroCoordinates(request);

        builder.addModules(request);

    }

}
