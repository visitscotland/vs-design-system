package com.visitscotland.brxm.utils;

import org.easymock.EasyMockSupport;
import org.hippoecm.hst.resourcebundle.ResourceBundleRegistry;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ResourceBundle;

import static org.easymock.EasyMock.expect;

public class HippoUtilsServiceTest extends EasyMockSupport {

    ResourceBundleRegistry resourceBundleRegistry;
    HippoUtilsService utils;



    @Disabled
    @Test
    void resourceBundle(){
        final String BUNDLE = "test-bundle";
        final String KEY = "key";
        ResourceBundleRegistry resourceBundleRegistry = createNiceMock(ResourceBundleRegistry.class);
        HippoUtilsService utils = new HippoUtilsService();
        ResourceBundle bundle = createNiceMock(ResourceBundle.class);

        expect(resourceBundleRegistry.getBundle(BUNDLE)).andReturn(bundle);
        expect(bundle.containsKey(KEY)).andReturn(true);

        //getString() cannot be mocked with EasyMock since It's not capable of overriding final classes.
        expect(bundle.getString(KEY)).andReturn("value");

        replayAll();

        //This method has been removed
        //utils.getResourceBundle(KEY,BUNDLE, null);

        verifyAll();
    }

}
