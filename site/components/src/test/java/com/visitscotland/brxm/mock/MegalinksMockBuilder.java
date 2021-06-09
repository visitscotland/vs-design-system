package com.visitscotland.brxm.mock;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import com.visitscotland.brxm.factory.MegalinkFactoryTest;
import com.visitscotland.brxm.factory.MegalinkFactory;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.mockito.Mockito.*;

public class MegalinksMockBuilder {

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

    public MegalinksMockBuilder dmsLink(DMSDataService dmsData, JsonNode node) {
        linkable = Mockito.mock(SharedLink.class);
        DMSLink link = mock(DMSLink.class);

        when(link.getProduct()).thenReturn("id");
        when(((SharedLink)linkable).getLinkType()).thenReturn(link);
        when(dmsData.productCard("id", Locale.UK)).thenReturn(node);

        return this;
    }

    public MegalinksMockBuilder productSearchLink() {
        linkable = Mockito.mock(SharedLink.class);
        ProductsSearch ps = mock(ProductsSearch.class);
        when(((SharedLink)linkable).getLinkType()).thenReturn(mock(ProductsSearch.class));

        return this;
    }

    public enum LinkType {CMS, DMS, EXTERNAL, PRODUCT_SEARCH}

    private Megalinks megalinks;
    private Linkable linkable;
    private List<MegalinkItem> megalinkItems;

    public MegalinksMockBuilder() {
        megalinks = Mockito.mock(Megalinks.class);
    }

    public Megalinks build() {
        if (megalinkItems != null) {
            when(megalinks.getMegalinkItems()).thenReturn(megalinkItems);
        }
        return megalinks;
    }

    public SharedLink buildSharedLink(){
        return (SharedLink) linkable;
    }

    public MegalinksMockBuilder horizontalLayout() {
        megalinks = mock(Megalinks.class);
        when (megalinks.getLayout()).thenReturn(MegalinkFactory.HORIZONTAL_LAYOUT);

        return this;
    }

    public MegalinksMockBuilder singleImage(SingleImageModule singleImageModule) {
        when(megalinks.getSingleImageModule()).thenReturn(singleImageModule);
        return this;
    }

    public MegalinksMockBuilder emptySingleImage() {
        SingleImageModule singleImageModule = mock(SingleImageModule.class);
        Image image = mock(Image.class);
        when(singleImageModule.getImage()).thenReturn(image);
        return this.singleImage(singleImageModule);
    }

    public MegalinksMockBuilder megalinkItem(boolean featured, MegalinkFactoryTest.LinkType type, String title) {
        if (megalinkItems == null) {
            megalinkItems = new ArrayList<>();
        }
        megalinkItems.add(createMockItem(featured, type, title));
        return this;
    }

    public Itinerary getItinerary(String mainTransport){
        Itinerary itinerary = mock(Itinerary.class, RETURNS_DEEP_STUBS);

        when (itinerary.getTransports()).thenReturn(new String[]{mainTransport});

        return itinerary;
    }
    public Linkable getPage(){
       return mock(Page.class);
    }

    public Linkable getExternalDocument(String title, String url, String category){
        SharedLink sharedLink = mock(SharedLink.class, RETURNS_DEEP_STUBS);
        ExternalDocument externalDocument = mock(ExternalDocument.class, RETURNS_DEEP_STUBS);

        when (sharedLink.getLinkType()).thenReturn(externalDocument);
        when (sharedLink.getTitle()).thenReturn(title);
        when (externalDocument.getLink()).thenReturn(url);
        if (category!=null) {
            when(externalDocument.getCategory()).thenReturn(category);
        }
        return sharedLink;
    }

    public MegalinkItem mockItem() {
        return createMockItem(false, MegalinkFactoryTest.LinkType.CMS, null);
    }

    public MegalinkItem createMockItem(boolean featured, MegalinkFactoryTest.LinkType type, String title) {
        MegalinkItem item = mock(MegalinkItem.class, withSettings().lenient());

        when(item.getFeature()).thenReturn(featured);
        if (type == MegalinkFactoryTest.LinkType.CMS) {
            when(item.getLink()).thenReturn(mockPage());
        } else {
            SharedLink link = mockSharedLink(type);
            when(link.getLinkType()).thenReturn(mock(ExternalDocument.class));
            when(item.getLink()).thenReturn(link);
            if (title != null) {
                when(link.getTitle()).thenReturn(title);
            }
        }

        return item;
    }

    private SharedLink mockSharedLink(MegalinkFactoryTest.LinkType linkType) {
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

}
