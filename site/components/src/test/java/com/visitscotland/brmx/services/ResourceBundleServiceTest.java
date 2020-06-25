package com.visitscotland.brmx.services;

import org.easymock.cglib.core.Local;
import org.hippoecm.hst.resourcebundle.ResourceBundleRegistry;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;
import java.util.ResourceBundle;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ResourceBundleServiceTest {

    static String BUNDLE = "bundle-file";

    private ResourceBundleService service;

    @Mock
    private ResourceBundleRegistry registry;

    @Mock
    ResourceBundle bundle;
    @Mock
    ResourceBundle fbBundle;

    @BeforeEach
    public void initialize(){

        // No Locale will simulate the fallback to English
        lenient().when(registry.getBundle(BUNDLE)).thenReturn(fbBundle);
        lenient().when(registry.getBundle(BUNDLE, Locale.UK)).thenReturn(bundle);

        service = new ResourceBundleService(registry);

    }

    @Test
    public void getResourceBundle_nonExistingBundleRegisterIssue(){
        service = spy(service);

        String value = service.getResourceBundle("Non-existing", "key", Locale.UK);

        verify(service).logIssue(anyString());
    }

    @Test
    public void getResourceBundle_existingBundleDoNotRegisterIssue(){
        service = spy(service);

        String value = service.getResourceBundle(BUNDLE, "key", Locale.UK);

        verify(service, times(0)).logIssue(anyString());
    }

    @Test
    public void keyExistsInTheLocale(){
        when(bundle.containsKey("key")).thenReturn(true);
        when(bundle.getString("key")).thenReturn("value");

        String value = service.getResourceBundle(BUNDLE, "key", Locale.UK);

        Assert.assertEquals("value", value);
    }

    @Test
    public void fallbackLocaleToDefault(){
        service = spy(service);
        when(bundle.containsKey("key")).thenReturn(true);
        when(bundle.getString("key")).thenReturn("");
        when(bundle.getLocale()).thenReturn(Locale.UK);
        when(fbBundle.containsKey("key")).thenReturn(true);
        when(fbBundle.getString("key")).thenReturn("value");

        String value = service.getResourceBundle(BUNDLE, "key", Locale.UK);

        verify(service).logContentIssue(anyString(), any());
        Assert.assertEquals("value", value);
    }

    @Test
    public void optional_DoNotFallbackLocaleToDefault(){
        service = spy(service);
        when(bundle.containsKey("key")).thenReturn(true);
        when(bundle.getString("key")).thenReturn("");
        when(bundle.getLocale()).thenReturn(Locale.UK);

        String value = service.getResourceBundle(BUNDLE, "key", Locale.UK, true);

        verify(service, times(0)).logContentIssue(anyString(), any());
        Assert.assertEquals("", value);
    }


    @Test
    public void nonExistingKey(){
        service = spy(service);
        when(bundle.containsKey("key")).thenReturn(false);

        String value = service.getResourceBundle(BUNDLE, "key", Locale.UK);

        verify(service).logContentIssue(anyString(), any());
        Assert.assertNull(value);
    }

}
