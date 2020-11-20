package com.visitscotland.brmx.mock;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.components.content.factory.LinkModuleFactoryTest;
import com.visitscotland.brmx.components.content.factory.LinkModulesFactory;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import java.util.Collections;

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

    public enum LinkType {CMS, DMS, EXTERNAL, PRODUCT_SEARCH}

    private Megalinks megalinks;

    public MegalinksMockBuilder() {
        megalinks = Mockito.mock(Megalinks.class);
    }

    public Megalinks build() {
        return megalinks;
    }

    public MegalinksMockBuilder horizontalLayout() {
        megalinks = mock(Megalinks.class);
        when (megalinks.getLayout()).thenReturn(LinkModulesFactory.HORIZONTAL_LAYOUT);

        return this;
    }

    public MegalinkItem mockItem() {
        return mockItem(false, LinkModuleFactoryTest.LinkType.CMS);
    }

    public MegalinkItem mockItem(boolean featured, LinkModuleFactoryTest.LinkType type) {
        MegalinkItem item = mock(MegalinkItem.class, withSettings().lenient());

        when(item.getFeature()).thenReturn(featured);
        if (type == LinkModuleFactoryTest.LinkType.CMS) {
            when(item.getLink()).thenReturn(mockPage());
        } else {
            SharedLink link = mockSharedLink(type);
            when(item.getLink()).thenReturn(link);
        }

        return item;
    }

    private SharedLink mockSharedLink(LinkModuleFactoryTest.LinkType linkType) {
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
