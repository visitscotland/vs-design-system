package com.visitscotland.brmx.dms;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.utils.CommonUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Locale;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DMSDataServiceTest {

    DMSDataService service;

    @Mock
    CommonUtils utils;

    @BeforeEach
    void init (){
        service = new DMSDataService(utils);
    }

    @Test
    void productCard() throws IOException {
        //Regular scenario: ID and locale are defined and a request is passed to vs-dms-products with both values
        ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class);
        when(utils.requestUrl(anyString())).thenReturn("{ \"data\":[]}");

        JsonNode node = service.productCard("0123456789", Locale.UK);

        verify(utils).requestUrl(url.capture());

        Assertions.assertTrue(url.getValue().contains("id=0123456789"));
        Assertions.assertTrue(url.getValue().contains("locale="+Locale.UK.getLanguage()));
        Assertions.assertNotNull(node);
    }

    @Test
    void noLocale() throws IOException{
        //When locale is not defined the parameter is not set
        //Regular scenario: ID and locale are defined and a request is passed to vs-dms-products with both values
        ArgumentCaptor<String> url = ArgumentCaptor.forClass(String.class);
        when(utils.requestUrl(anyString())).thenReturn("{ \"data\":[]}");

        JsonNode node = service.productCard("0123456789", null);

        verify(utils).requestUrl(url.capture());

        Assertions.assertTrue(url.getValue().contains("id=0123456789"));
        Assertions.assertFalse(url.getValue().contains("locale="));
        Assertions.assertNotNull(node);
    }

    @Test
    void noProductsId () throws IOException {
        //When a productId is not defined a null object is returned
        JsonNode node = service.productCard(null, Locale.UK);

        verify(utils, never()).requestUrl(anyString());

        Assertions.assertNull(node);
    }

    @Test
    void corruptedResponseReturnsNull() throws IOException{

        when(utils.requestUrl(anyString())).thenReturn("{}");
        JsonNode node = service.productCard("0123456789", Locale.UK);
        Assertions.assertNull(node);

        when(utils.requestUrl(anyString())).thenReturn("{ \"dat");
        JsonNode corruptedNode = service.productCard("0123456789", Locale.UK);
        Assertions.assertNull(corruptedNode);

        when(utils.requestUrl(anyString())).thenThrow(new IOException());
        JsonNode unstable = service.productCard("0123456789", Locale.UK);
        Assertions.assertNull(unstable);
    }


}
