package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.beans.ICentre;
import com.visitscotland.brmx.beans.mapping.ICentreModule;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ICentreFactoryTest {

    @Mock
    HippoUtilsService utils;

    @Mock
    DMSDataService dmsData;

    @Mock
    LinkModulesFactory linkFactory;

    ICentreFactory factory;

    public static final String MOCK_JSON = "[{" +
            " \"properties\": {" +
            " \"name\":\"name\", \"id\":\"id\" " +
            "}}]";

    @BeforeEach
    void init() {
        factory = new ICentreFactory(utils, dmsData, linkFactory);
    }

    private ICentre iCentreMock(){
        return mock(ICentre.class);
    }

    @Test
    public void getNoModule(){
        //Gets a null when there is no location defined
        ICentreModule module = factory.getModule(iCentreMock(), Locale.UK, "");

        assertNull(module);
    }

    @Test
    public void getModule() throws JsonProcessingException {
        //Returns a basic module when the location is provided
        //Also verifies that the list of iCentre match with the location
        String location = "Edinburgh";
        JsonNode node = new ObjectMapper().readTree(MOCK_JSON);

        ArgumentCaptor<ProductSearchBuilder> captor = ArgumentCaptor.forClass(ProductSearchBuilder.class);
        doReturn(node).when(dmsData).legacyMapSearch(captor.capture());

        ICentreModule module = factory.getModule(iCentreMock(), Locale.UK, location);

        assertTrue(captor.getValue().build().contains(location), "The queryString " +captor.getValue()+ "does not contain "+ location);
        assertNotNull(module);
    }

    @Test
    public void getModule_whenLocationIsScotland(){
        //When the location is Scotland ... (Requirement not currently defined)
        //TODO: TBC
    }

    @Test
    public void getModule_documentDataGetsPopulated_NoQuote(){
        // Verifies that all data coming from the document gets populated in the module (Except Quote)
        // Verifies that the quote is not populated

    }

    @Test
    void getModule_quotePopulatesFieldsFromDocument(){
        // Verifies that all field from the Quote get populated
    }

    @Test
    public void getModule_defaultTitle(){
        // Verifies that the default title is used when a title is not defined the document
    }

    @Test
    public void getModule_getImageFromTheProduct(){
        // Verifies that the default title is used when a title is not defined the document

    }

    @Test
    public void getModule_verifyDefaultImage(){
        //Verifies that when a product is not defined, the default the module returns the default image
        //Verifies that when a product does not have an image, the default the module returns the default image
    }
}
