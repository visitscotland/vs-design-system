package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.mock.MegalinksMockBuilder;
import com.visitscotland.brxm.model.megalinks.LinksModule;
import com.visitscotland.brxm.model.megalinks.MultiImageLinksModule;
import com.visitscotland.brxm.model.megalinks.SingleImageLinksModule;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.DMSProxy;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.services.CommonUtilsService;
import com.visitscotland.brxm.services.DocumentUtilsService;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.Properties;
import org.easymock.EasyMockSupport;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Locale;

import static org.easymock.EasyMock.*;


class MegalinkFactoryLegacyTest extends EasyMockSupport {

    private final String TITLE = "Megalink title";
    
    //These constants must not generate the name from the class since Freemarker is not aware of them so any change would break the template
    private final String LIST = "ListLinksModule";
    private final String FEATURED = "MultiImageLinksModule";
    private final String SINGLE_IMAGE = "SingleImageLinksModule";

    private static MegalinksMockService megalinkService;
    private static MegalinkItemMockService megalinkItemService;

    private MegalinkFactory factory;

    private LinkService linkService;
    private ImageFactory imageFactory;

    private HippoUtilsService utils;

    private DMSDataService dms;
    private Properties properties;




    /**
     * {@code factory} needs a static method (createUrl) to be mocked since it relies on a static BloomReach dependency
     *
     * {@code page} represent a dummy link.
     */
    @BeforeAll
    static void init() {
        megalinkService = new MegalinksMockService();
        megalinkItemService = new MegalinkItemMockService();
    }

    @BeforeEach
    void initFactory(){
        properties = mock(Properties.class);
        utils = createNiceMock(HippoUtilsService.class);
        dms = new DMSDataService(new DMSProxy(properties));

        ResourceBundleService rs = createNiceMock(ResourceBundleService.class);
        utils = createNiceMock(HippoUtilsService.class);
        linkService = new LinkService(dms, rs,utils, properties);

        expect(utils.createUrl(anyObject(HippoBean.class))).andStubReturn("/fake-url/mock");

        factory = new MegalinkFactory(linkService, rs, imageFactory);
    }

    @Test
    void getListLayout(){
        Megalinks mega = megalinkService.createMock(TITLE, false, true, true, 0);
        LinksModule layout = factory.getMegalinkModule(mega, Locale.UK);
        replayAll();

        verifyAll();
        Assertions.assertEquals(layout.getType(), LIST);
    }

    @Test
    void getListLayoutOn7OrMoreItems(){
        replayAll();

        //6 elements => Featured Layout
        Megalinks mega = megalinkService.createMock(TITLE, false, false, true, MegalinkFactory.MAX_ITEMS);
        LinksModule layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertNotEquals(layout.getType(), LIST);

        // 7 elements => Convert to List Layout
        mega = megalinkService.createMock(TITLE, false, false, true, MegalinkFactory.MAX_ITEMS + 1);
        layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertEquals(layout.getType(), LIST);
    }

    @Test
    void getListLayoutWhenSingleImageLinksIsHigherThan6(){
        replayAll();

        Megalinks mega = megalinkService.createMock(TITLE, false, false, true, MegalinkFactory.MAX_ITEMS + 1, "List Layout Title");
        LinksModule layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertEquals(LIST, layout.getType());
    }
}

