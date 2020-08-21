package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.capabilities.Linkable;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.megalinks.EnhancedLink;
import com.visitscotland.brmx.beans.mapping.megalinks.LinksModule;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.services.LinkService;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class LinkModuleFactoryMockitoTest {

    public static final String DMS_ID = "0123456798";
    public static final String EXTERNAL_URL = "http://www.fake.site";
    public static final String PSR_URL = "http://psr.visitscotland.com/info/search?value1&value2";
    public static final String MOCK_JSON = "{" +
            " \"url\":\"https://mock.visitscotland.com/info/fake-product-p" + DMS_ID + "\", " +
            " \"name\":\"Fake Product\", " +
            " \"images\":[{" +
            "    \"mediaUrl\":\"https://img.visitscotland.com/fake-product.jpg\"" +
            "}]}";

    public enum LinkType {CMS, DMS, EXTERNAL, PRODUCT_SEARCH}

    @Mock
    private ProductSearchBuilder builder;

    @Mock
    private HippoUtilsService utils;

    @Mock
    private DMSDataService dmsData;

    @Mock
    private ResourceBundleService resourceBundleService;

    private LinkService linkService;
    private LinkModulesFactory factory;

    private Megalinks mockMultiImage() {
        Megalinks mega = mock(Megalinks.class, withSettings().lenient());
        MegalinkItem item = mockItem();

        when(mega.getMegalinkItems()).thenReturn(Collections.singletonList(item));

        return mega;
    }

    public MegalinkItem mockItem() {
        return mockItem(false, LinkType.CMS);
    }

    public MegalinkItem mockItem(boolean featured, LinkType type) {
        MegalinkItem item = mock(MegalinkItem.class, withSettings().lenient());

        when(item.getFeature()).thenReturn(featured);
        if (type == LinkType.CMS) {
            when(item.getLink()).thenReturn(mockPage());
        } else {
            SharedLink link = mockSharedLink(type);
            when(item.getLink()).thenReturn(link);
        }

        return item;
    }


    private SharedLink mockSharedLink(LinkType linkType) {
        SharedLink link = mock(SharedLink.class, withSettings().lenient());
        when(link.getImage()).thenReturn(mock(Image.class, withSettings().lenient()));

        switch (linkType) {
            case DMS:
                DMSLink type = mock(DMSLink.class, withSettings().lenient());
                when(type.getProduct()).thenReturn(DMS_ID);
                when(link.getLinkType()).thenReturn(type);
                break;
            case PRODUCT_SEARCH:
                ProductsSearch ps = mock(ProductsSearch.class, withSettings().lenient());
                when(builder.fromHippoBean(ps)).thenReturn(builder);
                when(builder.build()).thenReturn(PSR_URL);
                when(link.getLinkType()).thenReturn(ps);
                break;
            case EXTERNAL:
                ExternalLink external = mock(ExternalLink.class, withSettings().lenient());
                when(external.getLink()).thenReturn(EXTERNAL_URL);
                when(link.getLinkType()).thenReturn(external);
                break;
            default:

        }
        return link;
    }

    private Page mockPage() {
        return mock(Page.class);
    }

    @BeforeEach
    public void beforeEach() {
        linkService = new LinkService(dmsData,builder,resourceBundleService,utils);
        factory = new LinkModulesFactory(utils, dmsData,linkService);
    }


    @Test
    void MegalinkItem_convertToFlatLinks_allowedLinkTypes() {
        //Test allowed types
        MegalinkItem page = mock(MegalinkItem.class);
        when(page.getLink()).thenReturn(mockPage());
        assertTrue(factory.convertToFlatLinks(Collections.singletonList(page), Locale.UK).size() == 1);

        MegalinkItem sharedLink = mock(MegalinkItem.class);
        SharedLink sl = mockSharedLink(LinkType.DMS);
        when(sharedLink.getLink()).thenReturn(sl);
        assertTrue(factory.convertToFlatLinks(Collections.singletonList(sharedLink), Locale.UK).size() == 1);
    }

    @Test
    void MegalinkItem_convertToFlatLinks_notAllowedLinkTypes() {
        //Test that not allowed types gets skipped without throwing exception
        MegalinkItem page = mock(MegalinkItem.class);
        when(page.getLink()).thenReturn(null);
        assertTrue(factory.convertToFlatLinks(Collections.singletonList(page), Locale.UK).size() == 0);

        MegalinkItem sharedLink = mock(MegalinkItem.class);
        when(sharedLink.getLink()).thenReturn(mock(Stop.class));
        assertTrue(factory.convertToFlatLinks(Collections.singletonList(sharedLink), Locale.UK).size() == 0);
    }

    @Test
    void DMS_SharedLink() throws IOException {
        JsonNode node = new ObjectMapper().readTree(MOCK_JSON);
        when(dmsData.productCard(DMS_ID, Locale.UK)).thenReturn(node);

        FlatLink link = factory.convertToFlatLinks(Collections.singletonList(mockItem(false, LinkType.DMS)), Locale.UK).get(0);

        assertTrue(link.getLink().contains(DMS_ID));
    }

    @Test
    void DMS_enhanced_SharedLink_defaultsImage() throws IOException {
        //Test that the image is loaded from the DMS
        JsonNode node = new ObjectMapper().readTree(MOCK_JSON);
        when(dmsData.productCard(DMS_ID, Locale.UK)).thenReturn(node);

        MegalinkItem item = mockItem(false, LinkType.DMS);
        SharedLink sl = (SharedLink)item.getLink();
        when(sl.getImage()).thenReturn(null);

        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK).get(0);

        assertTrue(link.getLink().contains(DMS_ID));
        assertNotNull(link.getImage());
    }

    @Test
    void noImageDefined_DMS_defaultsImageNotFound() throws IOException {
        //Test that the image is loaded from the DMS
        //In order to achieve that we rename the "images" field with "skip"
        final String NO_IMAGE_JSON = "{" +
                " \"url\":\"https://mock.visitscotland.com/info/fake-product-p" + DMS_ID + "\", " +
                " \"name\":\"Fake Product\" " +
                "}";
        JsonNode node = new ObjectMapper().readTree(NO_IMAGE_JSON);

        when(dmsData.productCard(DMS_ID, Locale.UK)).thenReturn(node);

        MegalinkItem item = mockItem(false, LinkType.DMS);
        SharedLink sl = (SharedLink) item.getLink();
        when(sl.getImage()).thenReturn(null);

        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK).get(0);

        assertTrue(link.getLink().contains(DMS_ID));
        assertNull(link.getImage());
    }

    @Test
    void ProductSearch_SharedLink() {
        FlatLink link = factory.convertToFlatLinks(Collections.singletonList(mockItem(false, LinkType.PRODUCT_SEARCH)), Locale.UK).get(0);

        assertNotNull(PSR_URL, link.getLink());
    }

    @Test
    void ProductSearch_enhanced_SharedLink() {
        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(mockItem(false, LinkType.PRODUCT_SEARCH)), Locale.UK).get(0);

        assertEquals(PSR_URL, link.getLink());
    }

    @Test
    void External_SharedLink() {
        FlatLink link = factory.convertToFlatLinks(Collections.singletonList(mockItem(false, LinkType.EXTERNAL)), Locale.UK).get(0);

        assertNotNull(EXTERNAL_URL, link.getLink());
    }

    @Test
    void External_enhanced_SharedLink() {
        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(mockItem(false, LinkType.EXTERNAL)), Locale.UK).get(0);

        assertEquals(EXTERNAL_URL, link.getLink());
    }

    @Test
    void DMS_productDoesNotExist() {
        //The DMSLink does not throw exceptions when the product stop existing
        MegalinkItem item = mockItem(false, LinkType.DMS);
        DMSLink dmsLink = (DMSLink)((SharedLink)item.getLink()).getLinkType();
        when(dmsLink.getProduct()).thenReturn("non-existing");

        FlatLink flatLink = factory.convertToFlatLinks(Collections.singletonList(item), Locale.UK).get(0);
        EnhancedLink enhancedLink = factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK).get(0);

        assertNull(flatLink.getLink());
        assertNull(enhancedLink.getLink());
    }

    @Test
    void sharedLink_noImageDefined() {
        //Verifies that a content issue is logged when the image is not defined.
        MegalinkItem item = mockItem(false, LinkType.DMS);
        SharedLink sl = (SharedLink)item.getLink();
        when(sl.getImage()).thenReturn(null);

        //TODO verify that a contentIssue is triggered
        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK).get(0);

        assertNull(link.getImage());
    }

    @Test
    void UnexpectedLinkReturnsNull (){
        //Tests that the addition of a new type will not introduce an exception
        SharedLink link = mock(SharedLink.class);
        MegalinkItem item = mock(MegalinkItem.class);
        when(item.getLink()).thenReturn(link);

        FlatLink flatLink = factory.convertToFlatLinks(Collections.singletonList(item), Locale.UK).get(0);

        assertNull(flatLink.getLink());
    }

    @Test
    void UnexpectedTypeGetsSkipped (){
        //Tests that the addition of a new type will not introduce an exception
        HippoBean link = mock(HippoBean.class, withSettings().extraInterfaces(Linkable.class));
//        when(link.getClass()).thenReturn()
        MegalinkItem item = mock(MegalinkItem.class);
        when(item.getLink()).thenReturn(link);


        assertTrue(factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK).size() == 0);
    }

    @Test
    void productCardThrowsAnException () throws IOException {
        //Test Behaviour when the data from DMS is corrupted
        DMSDataService dmsDataService = mock(DMSDataService.class);
        when(dmsDataService.productCard(anyString(), any(Locale.class))).thenThrow(new IOException());
        LinkModulesFactory factory = new LinkModulesFactory(utils, dmsDataService, linkService);
        MegalinkItem item = mockItem(false,LinkType.DMS);

        FlatLink flatLink = factory.convertToFlatLinks(Collections.singletonList(item), Locale.UK).get(0);

        assertNull(flatLink.getLink());
    }

//    @Test
//    void getSingleImage(){
//        replayAll();
//
//        Megalinks mega = megalinkService.createMock(TITLE, false, false, true, 0, "Single image title");
//        LinksModule layout = factory.getMegalinkModule(mega, Locale.UK);
//
//        verifyAll();
//        Assertions.assertEquals(layout.getType(), SINGLE_IMAGE);
//    }
//
//    @Test
//    void singleImageLayout_optionCTA(){
//
//        Megalinks mega = mockMultiImage();
//
//        FlatLink mockLink = mock(linkService.getClass())
//        //if getPr
//        when(linkService.createLink(any(Locale.class), any(HippoCompound.class))).thenReturn(mockLink);
//    }


    @Test
    void multiImageLayout_optionCTA(){
        ExternalLink mockLink = mock(ExternalLink.class);
        when(mockLink.getLink()).thenReturn("http://www.visitscotland.com");


        Megalinks mega = mockMultiImage();
        when(mega.getProductItem()).thenReturn(mockLink);

        LinksModule layout = factory.multiImageLayout(mega, Locale.UK);

        assertEquals("http://www.visitscotland.com", layout.getCta().getLink());
    }

}
