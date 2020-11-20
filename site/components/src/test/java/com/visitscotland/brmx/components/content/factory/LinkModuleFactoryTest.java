package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.capabilities.Linkable;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.megalinks.EnhancedLink;
import com.visitscotland.brmx.beans.mapping.megalinks.HorizontalListLinksModule;
import com.visitscotland.brmx.beans.mapping.megalinks.LinksModule;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.LocationLoader;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.mock.MegalinksMockBuilder;
import com.visitscotland.brmx.services.LinkService;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class LinkModuleFactoryTest {

    public static final String DMS_ID = "0123456798";
    public static final String EXTERNAL_URL = "http://www.fake.site";
    public static final String PLAIN_LINK = "http://www.plain-link.site";
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

    @Mock(lenient = true)
    LinkService linkService;

    @Mock
    LocationLoader locationLoader;

    @BeforeEach
    public void beforeEach() {
        when(linkService.getPlainLink(any(SharedLink.class), any())).thenReturn(PLAIN_LINK);

        factory = new LinkModulesFactory(utils, dmsData, linkService, resourceBundleService,locationLoader);
    }


    @Test
    void MegalinkItem_convertToFlatLinks_allowedLinkTypes() {
        //Test allowed types
        MegalinkItem page = mock(MegalinkItem.class);
        when(page.getLink()).thenReturn(mockPage());
        assertEquals(1, factory.convertToFlatLinks(Collections.singletonList(page), Locale.UK).size());

        MegalinkItem sharedLink = mock(MegalinkItem.class);
        SharedLink sl = mockSharedLink(LinkType.DMS);
        when(sharedLink.getLink()).thenReturn(sl);
        assertEquals(1, factory.convertToFlatLinks(Collections.singletonList(sharedLink), Locale.UK).size());
    }

    @Test
    void MegalinkItem_convertToFlatLinks_notAllowedLinkTypes() {
        //Test that not allowed types gets skipped without throwing exception
        MegalinkItem page = mock(MegalinkItem.class);
        when(page.getLink()).thenReturn(null);
        assertEquals(0, factory.convertToFlatLinks(Collections.singletonList(page), Locale.UK).size());

        MegalinkItem sharedLink = mock(MegalinkItem.class);
        when(sharedLink.getLink()).thenReturn(mock(Stop.class));
        assertEquals(0, factory.convertToFlatLinks(Collections.singletonList(sharedLink), Locale.UK).size());
    }

    @Test
    @DisplayName("DMSLink invoke a request to DMSData")
    void DMS_SharedLink() throws IOException {
        //Verifies that a DMSLink on calls dmsData to request the data
        JsonNode node = new ObjectMapper().readTree(MOCK_JSON);
        when(dmsData.productCard(DMS_ID, Locale.UK)).thenReturn(node);
        when(linkService.getPlainLink(any(SharedLink.class), eq(node))).thenReturn("wwww.vs-dms.com");

        FlatLink link = factory.convertToFlatLinks(Collections.singletonList(mockItem(false, LinkType.DMS)), Locale.UK).get(0);

        assertEquals("wwww.vs-dms.com", link.getLink());
    }

    @Test
    void DMS_enhanced_SharedLink_defaultsImage() throws IOException {
        //Test that the image is loaded from the DMS
        JsonNode node = new ObjectMapper().readTree(MOCK_JSON);
        when(dmsData.productCard(DMS_ID, Locale.UK)).thenReturn(node);

        MegalinkItem item = mockItem(false, LinkType.DMS);
        SharedLink sl = (SharedLink) item.getLink();
        when(sl.getImage()).thenReturn(null);

        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK,false).get(0);

        assertEquals(PLAIN_LINK, link.getLink());
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

        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK,false).get(0);

        assertEquals(PLAIN_LINK, link.getLink());
        assertNull(link.getImage());
    }

    @Test
    void ProductSearch_SharedLink() {
        FlatLink link = factory.convertToFlatLinks(Collections.singletonList(mockItem(false, LinkType.PRODUCT_SEARCH)), Locale.UK).get(0);

        assertNotNull(PSR_URL, link.getLink());
    }

    @Test
    void ProductSearch_enhanced_SharedLink() {
        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(mockItem(false, LinkType.PRODUCT_SEARCH)), Locale.UK,false).get(0);

        assertEquals(PLAIN_LINK, link.getLink());
    }

    @Test
    void External_SharedLink() {
        FlatLink link = factory.convertToFlatLinks(
                Collections.singletonList(mockItem(false, LinkType.EXTERNAL)), Locale.UK).get(0);

        assertEquals(PLAIN_LINK, link.getLink());
    }

    @Test
    void External_enhanced_SharedLink() {
        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(mockItem(false, LinkType.EXTERNAL)), Locale.UK,false).get(0);

        assertEquals(PLAIN_LINK, link.getLink());
    }

    @Test
    void sharedLink_noImageDefined() {
        //Verifies that a content issue is logged when the image is not defined.
        MegalinkItem item = mockItem(false, LinkType.DMS);
        SharedLink sl = (SharedLink) item.getLink();
        when(sl.getImage()).thenReturn(null);

        //TODO verify that a contentIssue is triggered
        EnhancedLink link = factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK,false).get(0);

        assertNull(link.getImage());
    }

    @Test
    void unexpectedTypeGetsSkipped() {
        //Tests that the addition of a new type will not introduce an exception
        HippoBean link = mock(HippoBean.class, withSettings().extraInterfaces(Linkable.class));
        MegalinkItem item = mock(MegalinkItem.class);
        when(item.getLink()).thenReturn(link);


        assertEquals(0, factory.convertToEnhancedLinks(Collections.singletonList(item), Locale.UK,false).size());
    }

    @Test
    @DisplayName("CTA is populated when the ProductItem field is populated")
    void multiImageLayout_optionCTA() {
        //Verifies that when the field ProductItem is populated
        ExternalLink mockLink = mock(ExternalLink.class);
        Megalinks mega = mockMultiImage();
        when(mega.getProductItem()).thenReturn(mockLink);
        when(linkService.createLink(any(Locale.class), eq(mockLink))).thenReturn(new FlatLink(null, "cta-link", null));

        LinksModule layout = factory.multiImageLayout(mega, Locale.UK);

        assertEquals("cta-link", layout.getCta().getLink());
    }
    @Test
    @DisplayName("Get a horizontal layout")
    void getMegalinkModule_horizontalListLayout() {
        Megalinks mega = new MegalinksMockBuilder().horizontalLayout().build();

        LinksModule linkModule = factory.getMegalinkModule(mega,Locale.UK);
        assertEquals("HorizontalListLinksModule", linkModule.getType());
    }

//    @Test
//    @DisplayName("CTA is populated when the ProductItem field is populated")
//    void multiImageLayout_optionCTA() {
//
//    }
}
