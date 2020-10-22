package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.visitscotland.brmx.beans.IKnow;
import com.visitscotland.brmx.beans.mapping.IKnowModule;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IKnowFactoryTest {

    @Mock
    HippoUtilsService utils;

    IKnowFactory factory;

    @BeforeEach
    void init() {
        factory = new IKnowFactory(utils);
    }

    private IKnow iKnowMock(){
        return mock(IKnow.class);
    }

   @Test
    public void getModule() throws JsonProcessingException {
        //Gets a null when there is no location defined
        String location = "Edinburgh";

        IKnowModule module = factory.getModule(iKnowMock(), location);

        assertNotNull(module);
        assertTrue(module.getLink().getLink().contains(location));
    }


    @Test
    public void getModule_defaultTitle(){
        // Verifies that the default title is used when a title is not defined the document
    }


}
