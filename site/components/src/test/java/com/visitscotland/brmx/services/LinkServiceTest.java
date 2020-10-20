package com.visitscotland.brmx.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LinkServiceTest {

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
    public void init(){
        service = new LinkService(dmsData,builder,resourceBundle, utils);
    }

//    @Test
//    void dmsLink() throws IOException {
//        JsonNode node = new ObjectMapper().readTree(LinkModuleFactoryMockitoTest.MOCK_JSON);
//
//        String link = service.getPlainLink(mockSharedLink(LinkModuleFactoryMockitoTest.LinkType.DMS), node);
//
//        assertTrue(link.contains(LinkModuleFactoryMockitoTest.DMS_ID));
//    }

    @Test
    void externalLink(){
        ExternalLink externalLink = mock(ExternalLink.class, withSettings().lenient());
        when(externalLink.getLink()).thenReturn("http://fake.link");
        when(externalLink.getLabel()).thenReturn("");

        when(resourceBundle.getCtaLabel(eq(""), any())).thenReturn("Find out more");

        FlatLink link = service.createLink(Locale.UK, externalLink);

        assertEquals("http://fake.link", link.getLink());
        assertEquals("Find out more", link.getLabel());
    }

    @Test
    void cmsLink(){
        CMSLink cmsLink = mock(CMSLink.class, withSettings().lenient());
        when(cmsLink.getLink()).thenReturn(mock(HippoBean.class));

        when(utils.createUrl(any(HippoBean.class))).thenReturn("http://cms-url");

        FlatLink link = service.createLink(Locale.UK, cmsLink);

        assertEquals("http://cms-url", link.getLink());
    }

//     if (item instanceof DMSLink) {
//        DMSLink dmsLink = (DMSLink) item;
//        try {
//            JsonNode product = dmsData.productCard(dmsLink.getProduct(), locale);
//            if (product == null) {
//                logger.warn(CommonUtils.contentIssue("There is no product with the id '%s', (%s) ",
//                        dmsLink.getProduct(), item.getPath()));
//            } else {
//                //TODO build the link for the DMS product properly
//                return new FlatLink(resourceBundle.getCtaLabel(dmsLink.getLabel(), locale), Properties.VS_DMS_SERVICE + product.get(URL).asText());
//            }
//        } catch (IOException e) {
//            logger.error(String.format("Error while querying the DMS for '%s', (%s)",
//                    dmsLink.getProduct(), item.getPath()));
//        }

    @Test
    void dmsLink_dmsDataThrowException() throws IOException {
        DMSLink dmsLink = mock(DMSLink.class);
        when(dmsLink.getProduct()).thenReturn("123");
        when(dmsLink.getPath()).thenReturn("path/to/node");

        when(dmsData.productCard("123", Locale.UK)).thenThrow(new IOException());

        FlatLink link = service.createLink(Locale.UK, dmsLink);

        assertNull(link);
    }

    @Test
    void dmsLink_notExistingProduct() throws IOException {
        DMSLink dmsLink = mock(DMSLink.class);
        when(dmsLink.getProduct()).thenReturn("123");

        when(dmsData.productCard("123", Locale.UK)).thenReturn(null);

        FlatLink link = service.createLink(Locale.UK, dmsLink);

        assertNull(link);
    }

    @Test
    void dmsLink() throws IOException {
        JsonNode node = mock(JsonNode.class);
        JsonNode url = mock(JsonNode.class);

        DMSLink dmsLink = mock(DMSLink.class);
        when(dmsLink.getProduct()).thenReturn("123");

        when(dmsData.productCard("123", Locale.UK)).thenReturn(node);
        when(node.get(LinkService.URL)).thenReturn(url);
        when(url.asText()).thenReturn("/dms-page");

        FlatLink link = service.createLink(Locale.UK, dmsLink);

        assertTrue(link.getLink().endsWith("/dms-page"));
    }

    @Test
    void productSearchLink(){
        ProductSearchLink productSearchLink = mock(ProductSearchLink.class, withSettings().lenient());
        ProductsSearch ps = mock(ProductsSearch.class);
        when(ps.getProductType()).thenReturn("acco");
        when(productSearchLink.getSearch()).thenReturn(ps);



//        when(builder.fromHippoBean(productSearchLink.getSearch())).thenReturn(builder);
//        when(builder.build()).thenReturn("http://product-search");

        FlatLink link = service.createLink(Locale.UK, productSearchLink);

        assertTrue(link.getLink().contains("acco") && link.getLink().contains("search-results"));
    }
}
