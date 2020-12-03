package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.Destination;
import com.visitscotland.brmx.beans.mapping.LocalizedURL;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.brmx.utils.Language;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class InternalContentComponentTest {

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    InternalContentComponent component;

    @Mock
    HippoUtilsService utils;

    @Mock
    ResourceBundleService bundle;

    /**
     * Note: We usually prefer to use the out-of-the-box class MockRequest, however, in this class we need to mock
     * the request context with is not possible to do with MockRequest
     */
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    HstRequest request;

    @BeforeEach
    void init() {
        component.utils = this.utils;
        component.bundle = this.bundle;
    }

    @Test
    @DisplayName("VS-443 - processParameters - No query parameters defined")
    void processParameters(){
        ArgumentCaptor<String> ssoUrl = ArgumentCaptor.forClass(String.class);

        when(request.setModel(eq(InternalContentComponent.SSO_URL), ssoUrl.capture())).thenReturn(null);

        component.processParameters(request);

        Assertions.assertEquals("/"+ InternalContentComponent.PATH_PLACEHOLDER, ssoUrl.getValue());
    }

    @Test
    @DisplayName("VS-2357 - processParameters - external query parameter specification")
    void processParameters_external(){
        //Verify tha populates the fullQualifiedUrls attribute on the request CONTEXT
        //When there is no parameters the SSO_URL is host + /[PATH-PLACEHOLDER]
        ArgumentCaptor<String> fullyQualified = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> ssoUrl = ArgumentCaptor.forClass(String.class);

        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_EXTERNAL)).thenReturn("true");
        when(request.getRequestContext().setModel(eq(InternalContentComponent.FULLY_QUALIFIED_URLS), fullyQualified.capture())).thenReturn(null);
        when(request.getRequestContext().getBaseURL().getHostName()).thenReturn("http://visitscotland.com");
        when(request.getRequestContext().getBaseURL().getContextPath()).thenReturn("/site");
        when(request.getLocale()).thenReturn(Locale.UK);
        when(request.setModel(eq(InternalContentComponent.SSO_URL), ssoUrl.capture())).thenReturn(null);


        component.processParameters(request);

        Assertions.assertEquals("true", fullyQualified.getValue());
        Assertions.assertEquals("http://visitscotland.com/site/[PATH-PLACEHOLDER]", ssoUrl.getValue());
    }


    @Test
    @DisplayName("VS-2357 - processParameters - root-path query parameter specification")
    void processParameters_rootPath(){
        //Verify tha populates the fullQualifiedUrls attribute on the request CONTEXT
        //When there is no parameters the SSO_URL is rootpath + /[PATH-PLACEHOLDER]
        ArgumentCaptor<String> fullyQualified = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> ssoUrl = ArgumentCaptor.forClass(String.class);

        when(utils.getParameterFromUrl(eq(request), any())).thenReturn(null);
        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_ROOT_PATH)).thenReturn("http://visitscotlan.com/ws/tours");
        when(request.getRequestContext().setModel(eq(InternalContentComponent.FULLY_QUALIFIED_URLS), fullyQualified.capture())).thenReturn(null);
        when(request.setModel(eq(InternalContentComponent.SSO_URL), ssoUrl.capture())).thenReturn(null);

        component.processParameters(request);

        Assertions.assertEquals("true", fullyQualified.getValue());
        Assertions.assertEquals("http://visitscotlan.com/ws/tours/[PATH-PLACEHOLDER]", ssoUrl.getValue());
    }

    @Test
    @DisplayName("VS-2358 - processParameters - sso query parameter specification")
    void processParameters_sso(){
        //Verify that DOES NOT populate the fullQualifiedUrls attribute on the request CONTEXT
        //When there is no parameters the SSO_URL is /[PATH-PLACEHOLDER]?id={sso}
        ArgumentCaptor<String> ssoUrl = ArgumentCaptor.forClass(String.class);

        when(utils.getParameterFromUrl(eq(request), any())).thenReturn(null);
        when(utils.getParameterFromUrl(request, InternalContentComponent.PARAM_SSO)).thenReturn("jcalcines");

        when(request.setModel(eq(InternalContentComponent.SSO_URL), ssoUrl.capture())).thenReturn(null);

        component.processParameters(request);

        verify(request.getRequestContext(), never()).setModel(eq(InternalContentComponent.FULLY_QUALIFIED_URLS), any());
        Assertions.assertEquals("/[PATH-PLACEHOLDER]?id=jcalcines", ssoUrl.getValue());
    }

    @Test
    @DisplayName("VS-2360 - addlocalizedUrls - verify that localized URLs have the placeholders")
    void addLocalizedUrls(){
        ArgumentCaptor<List<LocalizedURL>> urls = ArgumentCaptor.forClass(List.class);

        when(request.getRequestContext().setModel(eq(InternalContentComponent.GLOBAL_MENU_URLS), urls.capture())).thenReturn(null);

        component.addLocalizedURLs(request);

        Assertions.assertEquals(Language.values().length, urls.getValue().size());
        Assertions.assertTrue(urls.getValue().get(0).getUrl().contains(InternalContentComponent.PATH_PLACEHOLDER));

    }
}
