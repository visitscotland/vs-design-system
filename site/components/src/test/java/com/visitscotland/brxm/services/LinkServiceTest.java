package com.visitscotland.brxm.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brxm.factory.ImageFactory;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.mock.MegalinksMockBuilder;
import com.visitscotland.brxm.mock.SharedLinkMockBuilder;
import com.visitscotland.brxm.model.FlatImage;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.LinkType;
import com.visitscotland.brxm.config.VsComponentManager;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.ProductSearchBuilder;
import com.visitscotland.brxm.model.Module;
import com.visitscotland.brxm.model.megalinks.EnhancedLink;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.brxm.utils.Properties;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.container.ComponentManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LinkServiceTest {

    @Mock
    private DMSDataService dmsData;

    @Mock(answer = Answers.RETURNS_SELF)
    private ProductSearchBuilder builder;

    @Mock
    private ResourceBundleService resourceBundle;

    @Mock
    private HippoUtilsService utils;

    @Mock
    private Properties properties;

    @Mock
    private CommonUtilsService commonUtils;

    @Mock
    private DocumentUtilsService documentUtilsService;

    @Mock
    private ImageFactory imageFactory;

    @Resource
    @InjectMocks
    LinkService service;

    private void initProductSearchBuilder(){
        ComponentManager context = mock(ComponentManager.class, withSettings().lenient());
        when(context.getComponent(ProductSearchBuilder.class)).thenReturn(builder);
        new VsComponentManager().setComponentManager(context);
    }

    @Test
    @DisplayName("Create a link from an ExternalLink Compound")
    void externalLink() {
        ExternalLink externalLink = mock(ExternalLink.class, withSettings().lenient());
        when(externalLink.getLink()).thenReturn("http://fake.link");
        when(externalLink.getLabel()).thenReturn("");

        when(properties.getDmsHost()).thenReturn("http://localhost:8080");
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
    @DisplayName("Create a url from an SharedLink with an External document ")
    void getPlainLink_externalDocument() {
        ExternalDocument externalDocument = mock(ExternalDocument.class, withSettings().lenient());
        SharedLink sharedLink = mock(SharedLink.class);

        when(sharedLink.getLinkType()).thenReturn(externalDocument);
        when(externalDocument.getLink()).thenReturn("https://www.visitscotland.com/ebrochures/en/what-to-see-and-do/perthshireanddundee.pdf");

        String link = service.getPlainLink(sharedLink, null);

        assertEquals("https://www.visitscotland.com/ebrochures/en/what-to-see-and-do/perthshireanddundee.pdf", link);
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
    @DisplayName("A non existing DMS link doesn't return a link")
    void dmsLink_notExistingProduct() {
        //Verifies that when and DMS item doesn't exist, the link is not created.
        DMSLink dmsLink = mock(DMSLink.class);
        when(dmsLink.getProduct()).thenReturn("123");

        when(dmsData.productCard("123", Locale.UK)).thenReturn(null);

        FlatLink link = service.createLink(Locale.UK, dmsLink);

        assertNull(link);
    }

    @Test
    @DisplayName("Create a link form a DMSLink Compound")
    void dmsLink() {
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
        initProductSearchBuilder();

        ProductSearchLink productSearchLink = mock(ProductSearchLink.class, withSettings().lenient());
        ProductsSearch ps = mock(ProductsSearch.class);
        when(productSearchLink.getSearch()).thenReturn(ps);

        FlatLink link = service.createLink(Locale.UK, productSearchLink);

        verify(builder, times(1)).build();
        assertEquals(LinkType.INTERNAL, link.getType());
    }

    @Test
    @DisplayName("Create a url from an SharedLink with an ProductSearchLink Compound ")
    void getPlainLink_productSearchLink() {
        initProductSearchBuilder();

        SharedLink sharedLink = mock(SharedLink.class);
        ProductSearchLink productSearchLink = mock(ProductSearchLink.class, withSettings().lenient());
        ProductsSearch ps = mock(ProductsSearch.class);

        when(productSearchLink.getSearch()).thenReturn(ps);
        when(sharedLink.getLinkType()).thenReturn(productSearchLink, productSearchLink);

        String link = service.getPlainLink(sharedLink, null);

        verify(builder, times(1)).build();
    }

    @Test
    @DisplayName("Create a url from an SharedLink with a ProductSearch Compound ")
    void getPlainLink_productSearch() {
        initProductSearchBuilder();

        SharedLink sharedLink = mock(SharedLink.class);
        ProductsSearch productSearch = mock(ProductsSearch.class);

        when(sharedLink.getLinkType()).thenReturn(productSearch, productSearch);

        String link = service.getPlainLink(sharedLink, null);

        verify(builder, times(1)).build();
    }

    @Test
    @DisplayName("Create a link from a ProductSearchLink  Compound")
    void unexpectedLinkReturnsNull() {
        //Tests that the addition of a new type will not introduce an exception
        SharedLink sharedLink = mock(SharedLink.class);
        assertNull(service.getPlainLink(sharedLink, null));
    }

    @Test
    @DisplayName("Null link doesn't throw an exception and returns null")
    void getType_null() {
        assertNull(service.getType(null));
        assertNull(service.getType(""));
    }

    @Test
    @DisplayName("Identifies internal URL patterns")
    void getType() {
        when(properties.getDmsHost()).thenReturn("//dms");
        assertEquals(LinkType.INTERNAL, service.getType("http://www.visitscotland.com/something"));
        assertEquals(LinkType.INTERNAL, service.getType("http://feature.visitscotland.com"));
        assertEquals(LinkType.INTERNAL, service.getType("http://localhost:8080/site"));
        assertEquals(LinkType.INTERNAL, service.getType("http://localhost:1234/site"));
        assertEquals(LinkType.INTERNAL, service.getType(properties.getDmsHost() + "/info/edinburgh-castle-p00001"));
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

    @Test
    @DisplayName("Return the category for the link/page")
    void getLinkCategory() {
        when(properties.getDmsHost()).thenReturn("http://localhost:8080");

        assertEquals("eBooks", service.getLinkCategory("https://ebooks.visitscotland.com/whisky-distilleries-guides/",Locale.UK));

        String blog = "Travel Blog";
        when(resourceBundle.getResourceBundle("navigation.main", "blog", Locale.UK ,true)).thenReturn(blog);
        assertEquals(blog, getCategory("https://blog.visitscotland.com/discover-our-best-ebooks", "navigation.main", "blog", blog));
        assertEquals(blog, getCategory("https://www.visitscotland.com/blog/culture/scottish-words-meanings/", "navigation.main", "blog", blog));

        String seeDo= "See do";
        when(resourceBundle.getResourceBundle("navigation.main", "see-do", Locale.UK ,true)).thenReturn(seeDo);
        assertEquals(seeDo, getCategory("https://www.visitscotland.com/destinations-maps/edinburgh/see-do/", "navigation.main", "see-do", seeDo));
        assertEquals(seeDo, getCategory("https://www.visitscotland.com/info/events/developing-a-garden-sketchbook-after-hours-p2216101", "navigation.main", "see-do", seeDo));
        assertEquals(seeDo, getCategory("https://www.visitscotland.com/info/tours/shore-excursion-from-invergordon-battles-loch-ness-whisky-a56a372f", "navigation.main", "see-do", seeDo));
        assertEquals(seeDo, getCategory("https://www.visitscotland.com/info/see-do/riverside-museum-p995001", "navigation.main", "see-do", seeDo));
        assertEquals(seeDo, getCategory("https://www.visitscotland.com/site-search-results", "navigation.main", "see-do", seeDo));

        when(resourceBundle.getResourceBundle("navigation.main", "accommodation", Locale.UK ,true)).thenReturn("Accommodation");
        assertEquals("Accommodation", getCategory("https://www.visitscotland.com/destinations-maps/edinburgh/accommodation/self-catering/", "navigation.main", "accommodation", "Accommodation"));

        String destination = "Places to go";
        when(resourceBundle.getResourceBundle("navigation.main", "destinations-map", Locale.UK ,true)).thenReturn(destination);
        assertEquals(destination, getCategory("https://www.visitscotland.com/destinations-maps/edinburgh/", "navigation.main", "destinations-map", destination));
        assertEquals(destination, getCategory("https://www.visitscotland.com/destinations-maps/perthshire/short-break-itinerary", "navigation.main", "destinations-map", destination));
        assertEquals(destination, getCategory("https://www.visitscotland.com/info/towns-villages/ayr-p242821", "navigation.main", "destinations-map", destination));
        assertEquals(destination, getCategory("https://www.visitscotland.com/destinations-maps/island/orkney", "navigation.main", "destinations-map", destination));

        String travel = "Plan your trip";
        when(resourceBundle.getResourceBundle("navigation.main", "travel-planning", Locale.UK ,true)).thenReturn(travel);
        assertEquals(travel, getCategory("https://www.visitscotland.com/holidays-breaks/scotland-life/sam-audrey-scottish-road-trip/", "navigation.main", "travel-planning", travel));
        assertEquals(travel, getCategory("https://www.visitscotland.com/travel/getting-around-scotland/coach/", "navigation.main", "travel-planning", travel));
        assertEquals(travel, getCategory("https://www.visitscotland.com/info/transport/turner-hire-drive-edinburgh-p1916901", "navigation.main", "travel-planning", travel));

        when(resourceBundle.getResourceBundle("navigation.main", "inspiration", Locale.UK ,true)).thenReturn("Inspiration");
        assertEquals("Inspiration", getCategory("https://www.visitscotland.com/brochures/", "navigation.main", "inspiration", "Inspiration"));

        String information = "Visitor information";
        when(resourceBundle.getResourceBundle("navigation.footer", "footer.visitor-information", Locale.UK ,true)).thenReturn(information);
        assertEquals(information, getCategory("https://www.visitscotland.com/about-us/", "navigation.footer", "footer.visitor-information", information));
        assertEquals(information, getCategory("https://www.visitscotland.com/info/services/fort-william-icentre-p333001", "navigation.footer", "footer.visitor-information", information));
        assertEquals(information, getCategory("https://www.visitscotland.com/contact-us/", "navigation.footer", "footer.visitor-information", information));
        assertEquals(information, getCategory("https://www.visitscotland.com/policies/acceptable-use/", "navigation.footer", "footer.visitor-information", information));

        assertEquals("visitwales.com".toUpperCase(), service.getLinkCategory("https://www.visitwales.com/brochures",Locale.UK));

    }

    @Test
    @DisplayName("An exception if the URL is mal formed")
    void getLinkCategory_MalformedURLException(){
        when(properties.getDmsHost()).thenReturn("http://localhost:8080");

        assertNull(service.getLinkCategory("http//example.com",Locale.UK));
    }

    private String getCategory(String url, String bundle, String key, String value){
        when(resourceBundle.getResourceBundle(bundle, key, Locale.UK ,true)).thenReturn(value);
        String result = service.getLinkCategory(url,Locale.UK);

        return result;
    }

    @Test
    @DisplayName("VS-2308 External document definition with category")
    void createEnhancedLink_externalDocument_category() {
        final String url= "https://www.visitscotland.com/ebrochures/en/what-to-see-and-do/perthshireanddundee.pdf";
        final String category= "see-do";
        SharedLink externalDocument = new SharedLinkMockBuilder().externalDocument("title",url,category).build();

        when (resourceBundle.getResourceBundle("essentials.global", "label.download", Locale.UK ,true)).thenReturn("DOWNLOAD");
        when(commonUtils.getExternalDocumentSize(any(), any())).thenReturn("PDF 15.5MB");
        EnhancedLink enhancedLink = service.createEnhancedLink(externalDocument,null, Locale.UK, true);

        assertEquals("title (DOWNLOAD PDF 15.5MB)", enhancedLink.getLabel());
        assertEquals(com.visitscotland.brxm.model.LinkType.DOWNLOAD, enhancedLink.getType());
        assertEquals(category, enhancedLink.getCategory());
    }

    @Test
    @DisplayName("VS-1696 - If size cannot be calculated the link still appears")
    void createEnhancedLink_externalDocument_broken() {
        final String url = "https://www.visitscotland.com/ebrochures/en/what-to-see-and-do/perthshireanddundee.pdf";
        final Module module = new Module();

        when (resourceBundle.getResourceBundle("essentials.global", "label.download", Locale.UK ,true)).thenReturn("DOWNLOAD");
        EnhancedLink enhancedLink = service.createEnhancedLink(
                new SharedLinkMockBuilder().externalDocument("title",url,"see-do").build(), module,
                Locale.UK, true);

        assertEquals("title (DOWNLOAD)", enhancedLink.getLabel());
        assertTrue(module.getErrorMessages().contains("The Link to the External document might be broken"));
    }

    @Test
    @DisplayName("Itineraries have days and main transport added")
    void createEnhancedLink_itinerary() {
        Itinerary itinerary = new MegalinksMockBuilder().getItinerary("bus");
        when(documentUtilsService.getSiblingDocuments(itinerary,Day.class, "visitscotland:Day")).thenReturn(Arrays.asList(mock(Day.class), mock(Day.class)));

        //TODO Review
        EnhancedLink enhancedLink = service.createEnhancedLink(itinerary, null, Locale.UK, false);

        assertEquals(2, enhancedLink.getItineraryDays());
        assertEquals("bus",enhancedLink.getItineraryTransport());
    }

    @Test
    @DisplayName("VS-2308 External document definition without category")
    void createEnhancedLink_externalDocument() {
        final String url= "https://www.visitscotland.com/ebrochures/en/what-to-see-and-do/perthshireanddundee.pdf";
        SharedLink externalDocument = new SharedLinkMockBuilder().externalDocument("title",url,  null).build();

        when(resourceBundle.getResourceBundle("essentials.global", "label.download", Locale.UK ,true)).thenReturn("DOWNLOAD");
        when(commonUtils.getExternalDocumentSize(any(), any())).thenReturn("PDF 15.5MB");
        //TODO Review
        EnhancedLink enhancedLink = service.createEnhancedLink(externalDocument, null, Locale.UK, false);

        assertEquals("title (DOWNLOAD PDF 15.5MB)", enhancedLink.getLabel());
        assertEquals(com.visitscotland.brxm.model.LinkType.DOWNLOAD, enhancedLink.getType());
        Mockito.verify((ExternalDocument)externalDocument.getLinkType(),Mockito.never()).getCategory();
    }

    @Test
    @DisplayName("DMSLink - Test that the image is loaded from the DMS")
    void DMS_enhanced_SharedLink_defaultsImage() throws IOException {
        JsonNode node = new ObjectMapper().readTree(MegalinksMockBuilder.MOCK_JSON);
        Module module = new Module();

        SharedLink dmsLink = new SharedLinkMockBuilder().dmsLink(dmsData, node).build();
        when(imageFactory.createImage(node, module)).thenReturn(new FlatImage());

        EnhancedLink link = service.createEnhancedLink(dmsLink, module,Locale.UK,false);

        assertNotNull(link.getImage());
    }

    @Test
    @DisplayName("No image throw an warning on preview mode")
    void noImageDefined_DMS_defaultsImageNotFound() throws IOException {
        final String NO_IMAGE_JSON = "{" +
                " \"url\":\"/info/fake-product-p0123456798\", " +
                " \"name\":\"Fake Product\" " +
                "}";
        JsonNode node = new ObjectMapper().readTree(NO_IMAGE_JSON);
        SharedLink dmsLink = new SharedLinkMockBuilder().dmsLink(dmsData, node).build();
        Module module = new Module();

        when(properties.getDmsHost()).thenReturn("");

        EnhancedLink link = service.createEnhancedLink(dmsLink, module,Locale.UK,false);
        assertEquals("/info/fake-product-p0123456798", link.getLink());
        assertEquals(1, module.getErrorMessages().size());
        assertNull(link.getImage());
    }

}
