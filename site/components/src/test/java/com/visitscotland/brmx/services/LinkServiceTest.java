package com.visitscotland.brmx.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.components.content.factory.LinkModuleFactoryMockitoTest;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LinkServiceTest {

    LinkService service;

    @Mock
    DMSDataService dmsData;

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

    @Test
    void DMS_SharedLink() throws IOException {
        JsonNode node = new ObjectMapper().readTree(LinkModuleFactoryMockitoTest.MOCK_JSON);

        String link = service.getPlainLink(mockSharedLink(LinkModuleFactoryMockitoTest.LinkType.DMS), node);

        assertTrue(link.contains(LinkModuleFactoryMockitoTest.DMS_ID));
    }



    private SharedLink mockSharedLink(LinkModuleFactoryMockitoTest.LinkType linkType) {
        SharedLink link = mock(SharedLink.class, withSettings().lenient());
        when(link.getImage()).thenReturn(mock(Image.class, withSettings().lenient()));

        switch (linkType) {
            case DMS:
                DMSLink type = mock(DMSLink.class, withSettings().lenient());
                when(type.getProduct()).thenReturn(LinkModuleFactoryMockitoTest.DMS_ID);
                when(link.getLinkType()).thenReturn(type);
                break;
            case PRODUCT_SEARCH:
                ProductsSearch ps = mock(ProductsSearch.class, withSettings().lenient());
                when(builder.fromHippoBean(ps)).thenReturn(builder);
                when(builder.build()).thenReturn(LinkModuleFactoryMockitoTest.PSR_URL);
                when(link.getLinkType()).thenReturn(ps);
                break;
            case EXTERNAL:
                ExternalLink external = mock(ExternalLink.class, withSettings().lenient());
                when(external.getLink()).thenReturn(LinkModuleFactoryMockitoTest.EXTERNAL_URL);
                when(link.getLinkType()).thenReturn(external);
                break;
            default:

        }
        return link;
    }
}
