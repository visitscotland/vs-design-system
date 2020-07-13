package com.visitscotland.brmx.components.content.factory;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.dms.LocationObject;
import com.visitscotland.brmx.beans.mapping.megalinks.AbstractLayout;
import com.visitscotland.brmx.beans.mapping.megalinks.FeaturedLayout;
import com.visitscotland.brmx.beans.mapping.megalinks.SingleImageLayout;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.easymock.EasyMockSupport;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Locale;

import static org.easymock.EasyMock.*;


class LinkModulesFactoryTest extends EasyMockSupport {

    private final String TITLE = "Megalink title";
    
    //This constants must not generate the name from the class since Freemarker is not aware of them so any change would break the template 
    private final String LIST = "ListLayout";
    private final String FEATURED = "FeaturedLayout";
    private final String SINGLE_IMAGE = "SingleImageLayout";

    private static MegalinksMockService megalinkService;
    private static MegalinkItemMockService megalinkItemService;

    private LinkModulesFactory factory;
    private HippoUtilsService utils;
    private ProductSearchBuilder psb;

    /**
     * {@code factory} needs an static method (createUrl) to be mocked since it relies on a static BloomReach dependency
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
        utils = createNiceMock(HippoUtilsService.class);
        psb = new ProductSearchBuilder();

        expect(utils.createUrl(anyObject(HippoBean.class))).andStubReturn("/fake-url/mock");

        factory = partialMockBuilder(LinkModulesFactory.class).withConstructor(HippoUtilsService.class, ProductSearchBuilder.class).withArgs(utils, psb)
                .addMockedMethod("getLocation", String.class, Locale.class)
                .createMock();

        expect(factory.getLocation(anyString(), anyObject(Locale.class))).andStubReturn(null);
    }

    @Test
    void getListLayout(){
        Megalinks mega = megalinkService.createMock(TITLE, false, true, true, 0);
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);
        replayAll();

        verifyAll();
        Assertions.assertEquals(layout.getType(), LIST);
    }

    @Test
    void getListLayoutOn7OrMoreItems(){
        replayAll();

        //6 elements => Featured Layout
        Megalinks mega = megalinkService.createMock(TITLE, false, false, true, LinkModulesFactory.MAX_ITEMS);
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertNotEquals(layout.getType(), LIST);

        // 7 elements => Convert to List Layout
        mega = megalinkService.createMock(TITLE, false, false, true, LinkModulesFactory.MAX_ITEMS + 1);
        layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertEquals(layout.getType(), LIST);
    }

    @Test
    void getSingleImage(){
        replayAll();

        Megalinks mega = megalinkService.createMock(TITLE, false, false, true, 0, "Single image title");
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertEquals(layout.getType(), SINGLE_IMAGE);
    }

    @Test
    void getSingleImageWithCoordinates(){
        LocationObject location = new LocationObject("edinburgh","key", "Edinburgh","DISTRICT", 10.,-10., Collections.emptyList(), Collections.emptySet());

        reset(factory);
        expect(factory.getLocation(anyString(), anyObject(Locale.class))).andReturn(location);
        replayAll();

        Megalinks mega = megalinkService.createMock(TITLE, false, false, true, 0, "Single image title");
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertEquals(layout.getType(), SINGLE_IMAGE);
        Assertions.assertNotNull(((SingleImageLayout)layout).getImage().getCoordinates());
    }

    @Test
    void getSingleImageWithLargeAmountOfItems(){
        replayAll();

        Megalinks mega = megalinkService.createMock(TITLE, false, false, true, LinkModulesFactory.MAX_ITEMS + 1, "Single image title");
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertEquals(layout.getType(), SINGLE_IMAGE);
    }

    @Test
    void getFeatured(){
        replayAll();

        for (int i= 0; i <= LinkModulesFactory.MAX_ITEMS; i++) {
            Megalinks mega = megalinkService.createMock(TITLE, false, false, true, i);
            AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);
            Assertions.assertEquals(layout.getType(), FEATURED);
        }

        verifyAll();
    }

    @Test
    void countFeaturedItems(){
        replayAll();

        createFeaturedLayoutAndCheckItems(1,0,0);
        createFeaturedLayoutAndCheckItems(2,0,0);
        createFeaturedLayoutAndCheckItems(3,0,1);
        createFeaturedLayoutAndCheckItems(4,1,2);
        createFeaturedLayoutAndCheckItems(5,1,2);
        createFeaturedLayoutAndCheckItems(6,1,2);

        //Check that from 7 items is not Featured any longer.
        Megalinks mega = megalinkService.createMock(TITLE, false, false, true, 7);
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);

        verifyAll();
        Assertions.assertNotEquals(layout.getType(), FEATURED);
    }
    
    private void createFeaturedLayoutAndCheckItems(int total, int minItems, int maxItems){
        Megalinks min = megalinkService.createMock(TITLE, false, false, true, 0);
        Megalinks max = megalinkService.createMock(TITLE, false, false, true, 0);

        for (int i = 0; i < total; i++){
            min.getMegalinkItems().add(megalinkItemService.createMock(false));
            max.getMegalinkItems().add(megalinkItemService.createMock(true));
        }

        verifyAll();
        Assertions.assertEquals(((FeaturedLayout) factory.getMegalinkModule(min, Locale.UK)).getFeaturedLinks().size(), minItems);
        Assertions.assertEquals(((FeaturedLayout) factory.getMegalinkModule(max, Locale.UK)).getFeaturedLinks().size(), maxItems);
    }

    @Test
    void addValidLinkElements(){
        replayAll();

        MegalinkItem mi = megalinkItemService.createMock(false);

        verifyAll();
        Assertions.assertEquals(factory.convertoToFlatLinks(Collections.singletonList(mi), null).size(), 1);
        Assertions.assertEquals(factory.convertToEnhancedLinks(Collections.singletonList(mi), Locale.UK).size(), 1);
    }

    @Test
    void skipNullLinkElements(){
        MegalinkItem mi = createMock(MegalinkItem.class);

        expect(mi.getFeature()).andReturn(false).anyTimes();
        expect(mi.getLink()).andReturn(null).anyTimes();
        expect(mi.getPath()).andReturn("path/to/node").times(2);

        replayAll();


        Assertions.assertEquals(factory.convertoToFlatLinks(Collections.singletonList(mi), null).size(), 0);
        Assertions.assertEquals(factory.convertToEnhancedLinks(Collections.singletonList(mi), Locale.UK).size(), 0);

        //This verifies that messages were generated and include the problematic node
        verify(mi);
    }

    @Test
    void skipInvalidLinkElements(){

        MegalinkItem mi = createMock(MegalinkItem.class);

        expect(mi.getFeature()).andReturn(false).anyTimes();
        expect(mi.getLink()).andReturn(createNiceMock(Megalinks.class)).anyTimes();
        expect(mi.getPath()).andReturn("path/to/node").times(2);

        replay(mi);

        Assertions.assertEquals(factory.convertoToFlatLinks(Collections.singletonList(mi), null).size(), 0);
        Assertions.assertEquals(factory.convertToEnhancedLinks(Collections.singletonList(mi), Locale.UK).size(), 0);

        //This verifies that messages were generated and include the problematic node
        verify(mi);
    }

    @Test
    void useOfSharedLinks(){

    }

    @Test
    void DMS_SharedLink(){

    }

    @Test
    void ProductSearch_SharedLink(){

    }

    @Test
    void External_SharedLink(){

    }

    @Test
    void DMS_enhanced_SharedLink(){

    }

    @Test
    void ProductSearch_enhanced_SharedLink(){

    }

    @Test
    void External_enhanced_SharedLink(){

    }

    @Test
    void External_defaultToDMSImage(){

    }

    @Test
    void DMS_productDoesNotExist(){

    }

    @Test
    void sharedLink_noImageDefined(){

    }





}
