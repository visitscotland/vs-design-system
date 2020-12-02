package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.Destination;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ResourceBundle;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InternalContentComponentTest {

    HstRequest request;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    InternalContentComponent component;

    @Mock
    HippoUtilsService utils;

    @Mock
    ResourceBundleService bundle;

    @BeforeEach
    void init() {
        request = new MockHstRequest();
        component.utils = this.utils;
        component.bundle = this.bundle;
    }


    /**
     * Verifies that some methods that add information on the request are called
     */
    @Test
    void addAttributesToRequest() {
//        //PageContentComponent should verify the functionality of this method
//        when(document.getLocation()).thenReturn("edinburgh");
//        doNothing().when(component).addHeroCoordinates(request);
//        doNothing().when(templateBuilder).addModules(request, "edinburgh");
//
//        component.addAttributesToRequest(request);
    }


    @Test
    @DisplayName("processParameters - No parameters defined ")
    void processParameters(){
        //When there is no parameters the SSO_URL is /[PATH-PLACEHOLDER]
//        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_EXTERNAL)).thenReturn()
//        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_SSO)).thenReturn("sso")
//        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_ROOT_PATH)).thenReturn();

        component.processParameters(request);

        Assertions.assertEquals("/"+ InternalContentComponent.PATH_PLACEHOLDER, request.getModel(InternalContentComponent.SSO_URL));
    }

    @Test
    @DisplayName("processParameters - No parameters defined ")
    void processParameters_external(){
        //When there is no parameters the SSO_URL is /[PATH-PLACEHOLDER]
        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_EXTERNAL)).thenReturn("true");

        component.processParameters(request);

        Assertions.assertEquals("/[PATH-PLACEHOLDER]", request.getModel(InternalContentComponent.SSO_URL));
    }

    @Test
    @DisplayName("processParameters - No parameters defined ")
    void processParameters_rootPath(){
        //When there is no parameters the SSO_URL is /[PATH-PLACEHOLDER]
        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_SSO)).thenReturn("sso-id");

        component.processParameters(request);

        Assertions.assertEquals("/[PATH-PLACEHOLDER]?id=sso-id", request.getModel(InternalContentComponent.SSO_URL));
    }

    @Test
    @DisplayName("processParameters - No parameters defined ")
    void processParameters_sso(){
        //When there is no parameters the SSO_URL is /[PATH-PLACEHOLDER]
        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_ROOT_PATH)).thenReturn("http://vs.com");

        component.processParameters(request);

        Assertions.assertEquals("http://vs.com/[PATH-PLACEHOLDER]", request.getModel(InternalContentComponent.SSO_URL));
    }
}
