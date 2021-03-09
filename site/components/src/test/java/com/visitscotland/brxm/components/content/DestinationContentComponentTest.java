package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.hippobeans.Destination;
import com.visitscotland.brxm.utils.PageTemplateBuilder;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.mock.core.component.MockHstRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DestinationContentComponentTest {

    private HstRequest request;

    @Mock
    Destination document;

    @Mock()
    PageTemplateBuilder templateBuilder;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    DestinationContentComponent component;

    @BeforeEach
    void init() {
        request = new MockHstRequest();

        document = mock(Destination.class);

        //Adds a mock document to the Request
        request.setAttribute("document", document);

        //The following method has to be initialized in order to avoid a NullPointerException
//        component.builder = templateBuilder;
    }


    /**
     * Verifies that some methods that add information on the request are called
     *
     * TODO: Analyze the usefulness of this test and fix it if it is useful
     */
    @Test
    @Disabled("This test fails because of the Dependency Injection. Does this test have any real value?")
    void addAttributesToRequest() {
        //PageContentComponent should verify the functionality of this method
        when(document.getLocation()).thenReturn("edinburgh");
        doNothing().when(component).addHeroCoordinates(request);
        doNothing().when(templateBuilder).addModules(request, "edinburgh");

        component.addAttributesToRequest(request);
    }
}
