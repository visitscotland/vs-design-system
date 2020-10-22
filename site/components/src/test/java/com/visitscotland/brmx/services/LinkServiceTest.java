package com.visitscotland.brmx.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.LinkType;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.brmx.utils.Properties;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    LinkService service;

    @Mock
    private DMSDataService dmsData;

    @Mock
    private ProductSearchBuilder builder;

    @Mock
    private ResourceBundleService resourceBundle;

    @Mock
    private HippoUtilsService utils;

    @BeforeEach
    public void init() {
        service = new LinkService(dmsData, resourceBundle, utils);
    }

    @Test
    @DisplayName("Create a link from an ExternalLink Compound")
    void externalLink() {
        ExternalLink externalLink = mock(ExternalLink.class, withSettings().lenient());
        when(externalLink.getLink()).thenReturn("http://fake.link");
        when(externalLink.getLabel()).thenReturn("");

        when(resourceBundle.getCtaLabel(eq(""), any())).thenReturn("Find out more");

        FlatLink link = service.createLink(Locale.UK, externalLink);

        assertEquals("http://fake.link", link.getLink());
        assertEquals("Find out more", link.getLabel());
        assertEquals(LinkType.EXTERNAL, link.getType());
    }

    @Test
    @DisplayName("Create a url from an SharedLink with an ExternalLink Compound ")
    void getPlainLink_externalLink() {
        ExternalLink externalLink = mock(ExternalLink.class, withSettings().lenient());
        SharedLink sharedLink = mock(SharedLink.class);

        when(sharedLink.getLinkType()).thenReturn(externalLink);
        when(externalLink.getLink()).thenReturn("http://fake.link");
        when(externalLink.getLabel()).thenReturn("");

        String link = service.getPlainLink(sharedLink, null);

        assertEquals("http://fake.link", link);
    }

    @Test
    @DisplayName("Create a link from an CMSLink Compound")
    void cmsLink() {
        CMSLink cmsLink = mock(CMSLink.class, withSettings().lenient());
        when(cmsLink.getLink()).thenReturn(mock(HippoBean.class));

        when(utils.createUrl(any(HippoBean.class))).thenReturn("http://cms-url");

        FlatLink link = service.createLink(Locale.UK, cmsLink);

        assertEquals("http://cms-url", link.getLink());
        assertEquals(LinkType.INTERNAL, link.getType());
    }

    @Test
    @DisplayName("An exception in the DMS Data Service doesn't get propagated")
    void dmsLink_dmsDataThrowException() throws IOException {
        //Verifies that handles the exception from DMSDataService and returns null
        DMSLink dmsLink = mock(DMSLink.class);
        when(dmsLink.getProduct()).thenReturn("123");
        when(dmsLink.getPath()).thenReturn("path/to/node");

        when(dmsData.productCard("123", Locale.UK)).thenThrow(new IOException());

        FlatLink link = service.createLink(Locale.UK, dmsLink);

        assertNull(link);
    }

    @Test
    @DisplayName("A non existing DMS link doesn't return a link")
    void dmsLink_notExistingProduct() throws IOException {
        //Verifies that when and DMS item doesn't exist, the link is not created.
        DMSLink dmsLink = mock(DMSLink.class);
        when(dmsLink.getProduct()).thenReturn("123");

        when(dmsData.productCard("123", Locale.UK)).thenReturn(null);

        FlatLink link = service.createLink(Locale.UK, dmsLink);

        assertNull(link);
    }

    @Test
    @DisplayName("Create a link form a DMSLink Compound")
    void dmsLink() throws IOException {
        //Verifies that is able to create a link from DMSLink and the url is taken from the JSON Response
        JsonNode node = mock(JsonNode.class);
        JsonNode url = mock(JsonNode.class);

        DMSLink dmsLink = mock(DMSLink.class);
        when(dmsLink.getProduct()).thenReturn("123");

        when(dmsData.productCard("123", Locale.UK)).thenReturn(node);
        when(node.get(LinkService.URL)).thenReturn(url);
        when(url.asText()).thenReturn("/dms-page");

        FlatLink link = service.createLink(Locale.UK, dmsLink);

        assertTrue(link.getLink().endsWith("/dms-page"));
        assertEquals(LinkType.INTERNAL, link.getType());

    }

    @Test
    @DisplayName("Create a link from a ProductSearchLink  Compound")
    void productSearchLink() {
        //Verifies that it can create a URL from the ProductSearchLink
        ProductSearchLink productSearchLink = mock(ProductSearchLink.class, withSettings().lenient());
        ProductsSearch ps = mock(ProductsSearch.class);
        when(ps.getProductType()).thenReturn("acco");
        when(productSearchLink.getSearch()).thenReturn(ps);

        FlatLink link = service.createLink(Locale.UK, productSearchLink);

        assertTrue(link.getLink().contains("acco") && link.getLink().contains("search-results"));
        assertEquals(LinkType.INTERNAL, link.getType());
    }

    @Test
    @DisplayName("Create a url from an SharedLink with an ExternalLink Compound ")
    void getPlainLink_productSearchLink() {
        SharedLink sharedLink = mock(SharedLink.class);
        ProductSearchLink productSearchLink = mock(ProductSearchLink.class, withSettings().lenient());
        ProductsSearch ps = mock(ProductsSearch.class);

        when(ps.getProductType()).thenReturn("acco");
        when(productSearchLink.getSearch()).thenReturn(ps);
        when(sharedLink.getLinkType()).thenReturn(productSearchLink, productSearchLink);

        String link = service.getPlainLink(sharedLink, null);

        assertTrue(link.contains("acco") && link.contains("search-results"));
    }

    @Test
    @DisplayName("Create a link from a ProductSearchLink  Compound")
    void unexpectedLinkReturnsNull() {
        //Tests that the addition of a new type will not introduce an exception
        SharedLink sharedLink = mock(SharedLink.class);
        assertNull(service.getPlainLink(sharedLink, null));
    }

    @Test
    @DisplayName("Identifies internal URL patterns")
    void getType() {
        assertEquals(LinkType.INTERNAL, service.getType("http://www.visitscotland.com/something"));
        assertEquals(LinkType.INTERNAL, service.getType("http://feature.visitscotland.com"));
        assertEquals(LinkType.INTERNAL, service.getType("http://localhost:8080/site"));
        assertEquals(LinkType.INTERNAL, service.getType("http://localhost:1234/site"));
        assertEquals(LinkType.INTERNAL, service.getType(Properties.VS_DMS_SERVICE + "/info/edinburgh-castle-p00001"));
        assertEquals(LinkType.INTERNAL, service.getType("http://future.visitscotland.com"));

        assertEquals(LinkType.EXTERNAL, service.getType("http://www.edinburgh.com/"));
        assertEquals(LinkType.EXTERNAL, service.getType("http://www.gov-uk.com/visitscotland"));

        //TODO: Amend gettype to fulfil the following assertion
//        assertEquals(LinkType.EXTERNAL, service.getType("http://www.prize-draw.com/scotland?referral=www.visitscotland.com"));
    }

    @Test
    @DisplayName("Return a link from a DMSLink")
    void getPlainLink_dmsLink() throws IOException {
        //Verifies that when and DMS item doesn't exist, the link is not created.
        SharedLink sharedLink = mock(SharedLink.class);
        DMSLink dmsLink = mock(DMSLink.class);
        JsonNode node = mock(JsonNode.class);
        JsonNode url = mock(JsonNode.class);

        when(sharedLink.getLinkType()).thenReturn(dmsLink);
        when(node.get(LinkService.URL)).thenReturn(url);
        when(url.asText()).thenReturn("/dms-page");

        String link = service.getPlainLink(sharedLink, node);

        assertTrue(link.contains("/dms-page"));
    }

    @Test
    @DisplayName("A non existing DMS link doesn't return a link for getPlainLnk")
    void getPlainLink_dmsLink_notExistingProduct() throws IOException {
        //Verifies that when and DMS item doesn't exist, the link is not created.
        SharedLink sharedLink = mock(SharedLink.class);
        DMSLink dmsLink = mock(DMSLink.class);
        when(sharedLink.getLinkType()).thenReturn(dmsLink);

        String link = service.getPlainLink(sharedLink, null);

        assertNull(link);
    }
}
