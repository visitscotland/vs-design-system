package com.visitscotland.brmx.utils;

import org.easymock.EasyMock;
import org.easymock.EasyMockSupport;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.mock.core.request.MockHstRequestContext;
import org.hippoecm.hst.resourcebundle.ResourceBundleRegistry;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.easymock.EasyMock.*;

public class HippoUtilsServiceTest extends EasyMockSupport {

    ResourceBundleRegistry resourceBundleRegistry;
    HippoUtilsService utils;



    @Disabled
    @Test
    void resourceBundle(){
        final String BUNDLE = "test-bundle";
        final String KEY = "key";
        ResourceBundleRegistry resourceBundleRegistry = createNiceMock(ResourceBundleRegistry.class);
        HippoUtilsService utils = new HippoUtilsService(resourceBundleRegistry, new MockHstRequestContext());
        ResourceBundle bundle = createNiceMock(ResourceBundle.class);

        expect(resourceBundleRegistry.getBundle(BUNDLE)).andReturn(bundle);
        expect(bundle.containsKey(KEY)).andReturn(true);

        //getString() cannot be mocked with EasyMock since It's not capable of overriding final classes.
        expect(bundle.getString(KEY)).andReturn("value");

        replayAll();

        utils.getResourceBundle(KEY,BUNDLE, null);

        verifyAll();
    }

}
