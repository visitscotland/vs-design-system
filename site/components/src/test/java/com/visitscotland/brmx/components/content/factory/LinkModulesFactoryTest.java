package com.visitscotland.brmx.components.content.factory;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.megalinks.AbstractLayout;
import com.visitscotland.brmx.beans.mapping.megalinks.FeaturedLayout;
import org.easymock.EasyMock;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Locale;

public class LinkModulesFactoryTest {

    private final String TITLE = "Megalink title";
    
    //This constants must not generate the name from the class since Freemarker is not aware of them so any change would break the template 
    final String LIST = "ListLayout";
    final String FEATURED = "FeaturedLayout";
    final String SINGLE_IMAGE = "SingleImageLayout";

    static LinkModulesFactory factory;


    static MegalinksMockService megalinkService;
    static MegalinkItemMockService megalinkItemService;

    /**
     * {@code factory} needs an static method (createUrl) to be mocked since it relies on a static BloomReach dependency
     *
     * {@code page} represent a dummy link.
     */
    @BeforeAll
    static void init(){
        megalinkService = new MegalinksMockService();
        megalinkItemService = new MegalinkItemMockService();

        factory = EasyMock.partialMockBuilder(LinkModulesFactory.class)
                .addMockedMethod("createUrl", HippoBean.class)
                .addMockedMethod("getLocation", String.class, Locale.class)
                .createMock();

        EasyMock.expect(factory.createUrl(EasyMock.anyObject(Page.class))).andReturn("/fake-url/mock").anyTimes();
        EasyMock.expect(factory.getLocation(EasyMock.anyString(), EasyMock.anyObject(Locale.class))).andReturn(null).anyTimes();
        EasyMock.replay(factory);
    }

    @Test
    void getListLayout(){
        MegaLinks mega = megalinkService.createMock(TITLE, false, true, true, 0);
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);
        Assertions.assertEquals(layout.getType(), LIST);
    }

    @Test
    void getListLayoutOn7OrMoreItems(){
        MegaLinks mega = megalinkService.createMock(TITLE, false, false, true, LinkModulesFactory.MAX_ITEMS);
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);
        Assertions.assertNotEquals(layout.getType(), LIST);

        mega = megalinkService.createMock(TITLE, false, false, true, LinkModulesFactory.MAX_ITEMS + 1);
        layout = factory.getMegalinkModule(mega, Locale.UK);
        Assertions.assertEquals(layout.getType(), LIST);
    }

    @Test
    void getSingleImage(){
        MegaLinks mega = megalinkService.createMock(TITLE, false, false, true, 0, "Single image title");
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);
        Assertions.assertEquals(layout.getType(), SINGLE_IMAGE);
    }

    @Test
    void getSingleImageWithLargeAmountOfItems(){
        MegaLinks mega = megalinkService.createMock(TITLE, false, false, true, LinkModulesFactory.MAX_ITEMS + 1, "Single image title");
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);
        Assertions.assertEquals(layout.getType(), SINGLE_IMAGE);
    }

    @Test
    void getFeatured(){
        for (int i= 0; i <= LinkModulesFactory.MAX_ITEMS; i++) {
            MegaLinks mega = megalinkService.createMock(TITLE, false, false, true, i);
            AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);
            Assertions.assertEquals(layout.getType(), FEATURED);
        }
    }

    @Test
    void countFeaturedItems(){
        createFeaturedLayoutAndCheckItems(1,0,0);
        createFeaturedLayoutAndCheckItems(2,0,0);
        createFeaturedLayoutAndCheckItems(3,0,1);
        createFeaturedLayoutAndCheckItems(4,1,2);
        createFeaturedLayoutAndCheckItems(5,1,2);
        createFeaturedLayoutAndCheckItems(6,1,2);

        //Check that from 7 items is not Featured any longer.
        MegaLinks mega = megalinkService.createMock(TITLE, false, false, true, 7);
        AbstractLayout layout = factory.getMegalinkModule(mega, Locale.UK);
        Assertions.assertNotEquals(layout.getType(), FEATURED);
    }
    
    private void createFeaturedLayoutAndCheckItems(int total, int minItems, int maxItems){
        MegaLinks min = megalinkService.createMock(TITLE, false, false, true, 0);
        MegaLinks max = megalinkService.createMock(TITLE, false, false, true, 0);

        for (int i = 0; i < total; i++){
            min.getMegaLinkItems().add(megalinkItemService.createMock(false));
            max.getMegaLinkItems().add(megalinkItemService.createMock(true));
        }

        Assertions.assertEquals(((FeaturedLayout) factory.getMegalinkModule(min, Locale.UK)).getFeaturedLinks().size(), minItems);
        Assertions.assertEquals(((FeaturedLayout) factory.getMegalinkModule(max, Locale.UK)).getFeaturedLinks().size(), maxItems);
    }

    @Test
    void addValidLinkElements(){
        MegaLinkItem mi = megalinkItemService.createMock(false);

        Assertions.assertEquals(factory.convertoToFlatLinks(Arrays.asList(mi)).size(), 1);
        Assertions.assertEquals(factory.convertToEnhancedLinks(Arrays.asList(mi), Locale.UK).size(), 1);
    }

    @Test
    void skipNullLinkElements(){

        MegaLinkItem mi = EasyMock.createMock(MegaLinkItem.class);

        EasyMock.expect(mi.getFeature()).andReturn(false).anyTimes();
        EasyMock.expect(mi.getLink()).andReturn(null).anyTimes();
        EasyMock.expect(mi.getPath()).andReturn("path/to/node").times(2);

        EasyMock.replay(mi);

        Assertions.assertEquals(factory.convertoToFlatLinks(Arrays.asList(mi)).size(), 0);
        Assertions.assertEquals(factory.convertToEnhancedLinks(Arrays.asList(mi), Locale.UK).size(), 0);

        //This verifies that messages were generated and include the problematic node
        EasyMock.verify(mi);
    }

    @Test
    void skipInvalidLinkElements(){

        MegaLinkItem mi = EasyMock.createMock(MegaLinkItem.class);

        EasyMock.expect(mi.getFeature()).andReturn(false).anyTimes();
        EasyMock.expect(mi.getLink()).andReturn(EasyMock.createNiceMock(MegaLinks.class)).anyTimes();
        EasyMock.expect(mi.getPath()).andReturn("path/to/node").times(2);

        EasyMock.replay(mi);

        Assertions.assertEquals(factory.convertoToFlatLinks(Arrays.asList(mi)).size(), 0);
        Assertions.assertEquals(factory.convertToEnhancedLinks(Arrays.asList(mi), Locale.UK).size(), 0);

        //This verifies that messages were generated and include the problematic node
        EasyMock.verify(mi);
    }
}
