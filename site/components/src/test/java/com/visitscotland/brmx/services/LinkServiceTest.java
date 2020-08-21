package com.visitscotland.brmx.services;

import com.visitscotland.brmx.dms.DMSDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class LinkServiceTest {

    LinkService service;

    @Mock
    DMSDataService dmsData;

    @BeforeEach
    public void init(){
        service = new LinkService();
    }

    @Test
    void DMS_SharedLink() throws IOException {
//        JsonNode node = new ObjectMapper().readTree(LinkModuleFactoryMockitoTest.MOCK_JSON);
//        when(dmsData.productCard(LinkModuleFactoryMockitoTest.DMS_ID, Locale.UK)).thenReturn(node);
//
//        service.getPlainLink(LinkModuleFactoryMockitoTest.mockItem(false, LinkType.DMS))
//
//        FlatLink link = factory.convertToFlatLinks(Collections.singletonList(LinkModuleFactoryMockitoTest.mockItem(false, LinkType.DMS)), Locale.UK).get(0);
//
//        assertTrue(link.getLink().contains(LinkModuleFactoryMockitoTest.DMS_ID));
    }
}
