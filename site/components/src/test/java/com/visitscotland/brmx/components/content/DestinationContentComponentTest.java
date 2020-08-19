package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.Destination;
import com.visitscotland.brmx.beans.Megalinks;
import com.visitscotland.brmx.beans.MegalinksMockService;
import com.visitscotland.brmx.beans.mapping.megalinks.LinksModule;
import com.visitscotland.brmx.components.content.factory.LinkModulesFactory;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.easymock.EasyMock.*;


public class DestinationContentComponentTest {

    private static MegalinksMockService megalinkService;

    @BeforeAll
    static void init(){

        megalinkService = new MegalinksMockService();

    }

    /**
     * Since BloomReach initialize some variables that depends on
     * @return
     */
    private DestinationContentComponent mockComponent(Destination document){
        DestinationContentComponent dcc = createMockBuilder(DestinationContentComponent.class)
                .addMockedMethod("getDocument", HstRequest.class)
                .createMock();

        expect(dcc.getDocument(anyObject())).andReturn(document);
        replay(dcc);

        //Mocking the object prevents the initialization of the variables
        dcc.linksFactory = new LinkModulesFactory(createNiceMock(HippoUtilsService.class), new ProductSearchBuilder(), new DMSDataService());

        return dcc;
    }

    @Ignore
    @Test
    void doBeforeRender(){
        /* doBeforeRender() method cannot be tested because its parents calls relies on static of private methods or variables
         * and it is really complex to test them.
         *
          The complexity is too high compared to the gain of testing it. The readability lose is not worthy
         */
    }

    @Test
    void pageWithoutElements(){
        HstRequest request = new MockHstRequest();
        Destination document = createNiceMock(Destination.class);
        DestinationContentComponent dcc = mockComponent(document);


        expect(document.getItems()).andReturn(Arrays.asList());
        replay(document);

        dcc.addModules(request);
        List items = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);

        Assertions.assertEquals(0, items.size());
    }

    @Test
    void addMegalinksModules(){
        HstRequest request = new MockHstRequest();
        Megalinks megaLinks = megalinkService.createMock("Section 1");
        Destination document = createNiceMock(Destination.class);

        expect(document.getItems()).andReturn(Arrays.asList(megaLinks));
        replay(document);

        DestinationContentComponent dcc = mockComponent(document);

        dcc.addModules(request);
        List items = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);

        Assertions.assertEquals(1, items.size());
    }

    /**
     * Styles alternate and the last repeats the first colour
     */
    @Test
    void checkMegalinksAlternateColours(){
        HstRequest request = new MockHstRequest();
        Destination document = createNiceMock(Destination.class);

        expect(document.getItems()).andReturn(Arrays.asList(
                megalinkService.createMock("Section 1"),
                megalinkService.createMock("Section 2"),
                megalinkService.createMock("Section 3"),
                megalinkService.createMock("Section 4")));
        replay(document);

        DestinationContentComponent dcc = mockComponent(document);

        dcc.addModules(request);
        List<LinksModule> items = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);

        Assertions.assertEquals(4, items.size());

        //
        for (int i = 0; i < 4; i++) {
            Assertions.assertEquals(DestinationContentComponent.styles[i%3], items.get(i).getStyle());
        }
    }

    /**
     * 3 first items share colour because their title is null, 4th is different
     */
    @Test
    void checkMegalinksSkipAlternateColours(){
        HstRequest request = new MockHstRequest();
        Destination document = createNiceMock(Destination.class);

        expect(document.getItems()).andReturn(Arrays.asList(
                megalinkService.createMock("Section 1"),
                megalinkService.createMock(null),
                megalinkService.createMock(null),
                megalinkService.createMock("Section 2")));
        replay(document);

        DestinationContentComponent dcc = mockComponent(document);

        dcc.addModules(request);
        List<LinksModule> items = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);

        Assertions.assertEquals(4, items.size());

        for (int i = 0; i < 4; i++) {

            Assertions.assertEquals(DestinationContentComponent.styles[i!=3?0:1], items.get(i).getStyle());
        }
    }

    /**
     * First item always have the same style independently of if the section title is defined
     */
    @Test
    void checkMegalinksFirstItemColour(){
        HstRequest request = new MockHstRequest();
        Destination documentA = createNiceMock(Destination.class);
        Destination documentB = createNiceMock(Destination.class);

        expect(documentA.getItems()).andReturn(Arrays.asList(megalinkService.createMock(null)));
        expect(documentB.getItems()).andReturn(Arrays.asList(megalinkService.createMock("Section A")));
        replay(documentA, documentB);

        DestinationContentComponent dccA = mockComponent(documentA);
        DestinationContentComponent dccB = mockComponent(documentB);

        dccA.addModules(request);
        dccB.addModules(request);
        List<LinksModule> listA = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);
        List<LinksModule> listB = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);

        Assertions.assertEquals(listA.get(0).getStyle(), listB.get(0).getStyle());
    }
}
