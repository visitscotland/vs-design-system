package com.visitscotland.brmx.components.content;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

//import static org.easymock.EasyMock.*;

//TODO what to do with this class
@ExtendWith(MockitoExtension.class)
public class DestinationContentComponentTest {
//
//    MegalinksMockService megalinkService;
//
//    HstRequest request;
//
//    DestinationContentComponent component;
//
//    Destination document;
//
//    @Mock
//    LinkModulesFactory linksFactory;
//
//    @BeforeEach
//    void init(){
//        megalinkService = new MegalinksMockService(null);
//
//        request = new MockHstRequest();
//
//        document = mock(Destination.class);
//
//        //Adds a mock document to the Request
//        request.setAttribute("document", document);
//
//        //Mocks the constructor so it avoid invocations of static methods.
//        component = mock(DestinationContentComponent.class, CALLS_REAL_METHODS);
//        component.linksFactory = mock(LinkModulesFactory.class);
//    }
//
//
//
//    /**
//     * Since BloomReach initialize some variables that depends on
//     * @return
//     */
//    private DestinationContentComponent mockComponent(Destination document){
//
//        DestinationContentComponent dcc = mock(DestinationContentComponent.class);
////        DestinationContentComponent dcc = createMockBuilder(DestinationContentComponent.class)
////                .addMockedMethod("getDocument", HstRequest.class)
////                .createMock();
////
////        expect(dcc.getDocument(anyObject())).andReturn(document);
////        replay(dcc);
//
//        //Mocking the object prevents the initialization of the variables
////        dcc.linksFactory = new LinkModulesFactory(createNiceMock(HippoUtilsService.class), new DMSDataService(), createNiceMock(ResourceBundleService.class));
//
//        return dcc;
//    }
//
//    @Ignore
//    @Test
//    void doBeforeRender(){
//        /* doBeforeRender() method cannot be tested because its parents calls relies on static of private methods or variables
//         * and it is really complex to test them.
//         *
//          The complexity is too high compared to the gain of testing it. The readability lose is not worthy
//         */
//    }
//
//    @Test
//    void pageWithoutElements(){
//        //Page with no documents associated.
//
//        when(document.getItems()).thenReturn(Arrays.asList());
//
//        component.addModules(request);
//
//        List items = (List) request.getAttribute(DestinationContentComponent.PAGE_ITEMS);
//
//        assertEquals(0, items.size());
//    }
//
//    @Test
//    void addMegalinksModules(){
//        //Page with one Megalinks document associated
//        Megalinks megalinks = megalinkService.mockMultiImage();
//
//        when(document.getItems()).thenReturn(Arrays.asList(megalinks));
//
//        component.addModules(request);
//        List items = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);
//
//        assertEquals(1, items.size());
//    }

//    /**
//     * Styles alternate and the last repeats the first colour
//     */
//    @Test
//    void checkMegalinksAlternateColours(){
//        HstRequest request = new MockHstRequest();
//        Destination document = createNiceMock(Destination.class);
//
//        expect(document.getItems()).andReturn(Arrays.asList(
//                megalinkService.createMock("Section 1"),
//                megalinkService.createMock("Section 2"),
//                megalinkService.createMock("Section 3"),
//                megalinkService.createMock("Section 4")));
//        replay(document);
//
//        DestinationContentComponent dcc = mockComponent(document);
//
//        dcc.addModules(request);
//        List<LinksModule> items = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);
//
//        assertEquals(4, items.size());
//
//        //
//        for (int i = 0; i < 4; i++) {
//            assertEquals(DestinationContentComponent.styles[i%3], items.get(i).getStyle());
//        }
//    }
//
//    /**
//     * 3 first items share colour because their title is null, 4th is different
//     */
//    @Test
//    void checkMegalinksSkipAlternateColours(){
//        HstRequest request = new MockHstRequest();
//        Destination document = createNiceMock(Destination.class);
//
//        expect(document.getItems()).andReturn(Arrays.asList(
//                megalinkService.createMock("Section 1"),
//                megalinkService.createMock(null),
//                megalinkService.createMock(null),
//                megalinkService.createMock("Section 2")));
//        replay(document);
//
//        DestinationContentComponent dcc = mockComponent(document);
//
//        dcc.addModules(request);
//        List<LinksModule> items = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);
//
//        assertEquals(4, items.size());
//
//        for (int i = 0; i < 4; i++) {
//
//            assertEquals(DestinationContentComponent.styles[i!=3?0:1], items.get(i).getStyle());
//        }
//    }
//
//    /**
//     * First item always have the same style independently of if the section title is defined
//     */
//    @Test
//    void checkMegalinksFirstItemColour(){
//        HstRequest request = new MockHstRequest();
//        Destination documentA = createNiceMock(Destination.class);
//        Destination documentB = createNiceMock(Destination.class);
//
//        expect(documentA.getItems()).andReturn(Arrays.asList(megalinkService.createMock(null)));
//        expect(documentB.getItems()).andReturn(Arrays.asList(megalinkService.createMock("Section A")));
//        replay(documentA, documentB);
//
//        DestinationContentComponent dccA = mockComponent(documentA);
//        DestinationContentComponent dccB = mockComponent(documentB);
//
//        dccA.addModules(request);
//        dccB.addModules(request);
//        List<LinksModule> listA = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);
//        List<LinksModule> listB = (List)request.getAttribute(DestinationContentComponent.PAGE_ITEMS);
//
//        assertEquals(listA.get(0).getStyle(), listB.get(0).getStyle());
//    }
}
