package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.utils.LinkModulesFactory;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DestinationContentComponent extends PageContentComponent<Destination> {

    private static final Logger logger = LoggerFactory.getLogger(DestinationContentComponent.class);

    private LinkModulesFactory linksFactory = new LinkModulesFactory();

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addLinkModules(request);
    }

    private void addLinkModules(HstRequest request){
        List<MegaLinks> links = new ArrayList<>();
        for (MegaLinks mega: getDocument(request).getItems()){
            linksFactory.getMegalinkModule(mega, request.getLocale());
        }
    }

}
