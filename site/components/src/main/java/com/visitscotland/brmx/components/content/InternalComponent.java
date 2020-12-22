package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.onehippo.cms7.essentials.components.CommonComponent;


public class InternalComponent extends CommonComponent {

    ResourceBundleService bundle;
    HippoUtilsService utils;
    InternalParameterProcessor processor;

    public InternalComponent() {
        bundle = new ResourceBundleService();
        utils = new HippoUtilsService();
        processor = new InternalParameterProcessor(bundle, utils);
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        processor.addLocalizedURLs(request);
        processor.processParameters(request);
    }
}
