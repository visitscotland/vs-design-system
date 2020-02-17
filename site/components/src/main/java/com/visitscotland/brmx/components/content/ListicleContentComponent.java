package com.visitscotland.brmx.components.content;

import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ListicleContentComponent extends EssentialsContentComponent {

    private static final Logger logger = LoggerFactory.getLogger(ListicleContentComponent.class);


    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        request.setAttribute("message", "The component is ready!");
    }
}