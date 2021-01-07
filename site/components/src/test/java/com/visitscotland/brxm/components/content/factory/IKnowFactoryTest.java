package com.visitscotland.brxm.components.content.factory;

import com.visitscotland.brxm.beans.IKnow;
import com.visitscotland.brxm.beans.mapping.IKnowModule;
import com.visitscotland.brxm.dms.DMSConstants;
import com.visitscotland.brxm.mock.TouristInformationMockBuilder;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IKnowFactoryTest {

    @Mock
    HippoUtilsService utils;

    @Mock
    ResourceBundleService bundle;

    IKnowFactory factory;

    TouristInformationMockBuilder mockBuilder;

    @BeforeEach
    void init() {
        factory = new IKnowFactory(utils, bundle);
        mockBuilder = new TouristInformationMockBuilder().addIKnow();
    }

    @Test
    @DisplayName("VS-1661 - iKnow Module General Definition")
    void getModule()  throws UnsupportedEncodingException {
        //Gets a null when there is no location defined
        //Verifies default values
        String location = "Edinburgh";
        when(bundle.getResourceBundle(ICentreFactory.BUNDLE_ID,"iknow.title.default", Locale.UK))
                .thenReturn("default title");
        when(bundle.getResourceBundle(ICentreFactory.BUNDLE_ID,"iknow.description.default", Locale.UK))
                .thenReturn("default description");
        when(bundle.getResourceBundle(ICentreFactory.BUNDLE_ID,"iknow.link.label", Locale.UK))
                .thenReturn("link text");

        IKnowModule module = factory.getIKnowModule(mockBuilder.build().getIKnow(), location, Locale.UK);

        assertEquals("default title", module.getTitle());
        assertEquals("default description", module.getDescription());
        assertEquals("link text", module.getLink().getLabel());
        assertTrue(module.getLink().getLink().contains(location));
        assertTrue(module.getLink().getLink().contains(DMSConstants.AWARD_IKNOW));
        assertTrue(module.getLink().getLink().contains(URLEncoder.encode(DMSConstants.TYPE_SEE_DO, "UTF-8")));
    }


    @Test
    @DisplayName("VS-1661 - iKnow Module General Definition")
    void getModule_checkMapping() {
        // Verifies that the default title is used when a title is not defined the document
        IKnow iknow = mockBuilder.addIKnowTitle("idunno").addIKnowDescription("description").build().getIKnow();
        IKnowModule module = factory.getIKnowModule(iknow, "Edinburgh", Locale.UK);

        assertEquals("idunno", module.getTitle());
        assertEquals("description", module.getDescription());
        assertEquals(iknow.getDescription(), module.getDescription());
    }

}
