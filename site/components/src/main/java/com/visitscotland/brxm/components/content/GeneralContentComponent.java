package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.beans.Destination;
import com.visitscotland.brxm.cfg.SpringContext;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.hippoecm.hst.core.container.ComponentManager;
import org.hippoecm.hst.site.HstServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GeneralContentComponent extends PageContentComponent<Destination> {

    private static final Logger logger = LoggerFactory.getLogger(GeneralContentComponent.class);

    public PageTemplateBuilder builder;

    public GeneralContentComponent(){
        this.builder = SpringContext.getPageTemplateBuilder();
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addHeroCoordinates(request);

        builder.addModules(request);

    }

}
