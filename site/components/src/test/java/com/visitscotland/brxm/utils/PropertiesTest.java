package com.visitscotland.brxm.utils;

import com.visitscotland.brxm.services.ResourceBundleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropertiesTest {

    @Mock
    ResourceBundleService bundle;

    @InjectMocks
    Properties properties;

    String value;

    @BeforeEach
    void init(){
        properties = new Properties(bundle){

            @Override
            String getEnvironmentVariable(String name) {
                return value;
            }
        };
    }

    @Test
    void readString(){
        when(bundle.getResourceBundle(Properties.BUNDLE_ID, Properties.DMS_HOST, Locale.UK)).thenReturn("http://localhost:8181");
        assertEquals("http://localhost:8181", properties.getDmsHost());
    }

    @Test
    void readString_env(){
        when(bundle.getResourceBundle(Properties.BUNDLE_ID, Properties.DMS_HOST, Locale.UK)).thenReturn("$TEST_VS");
        value = "http://dms.visitscotland.com";
        assertEquals("http://dms.visitscotland.com", properties.getDmsHost());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "$"})
    @NullSource
    void readString_env(String value){
        when(bundle.getResourceBundle(Properties.BUNDLE_ID, Properties.DMS_HOST, Locale.UK)).thenReturn(value);
        value = "";
        assertEquals("", properties.getDmsHost());
    }

    @Test
    void readInteger(){
        when(bundle.getResourceBundle(Properties.BUNDLE_ID, Properties.DMS_DATA_TRIES, Locale.UK)).thenReturn("3718");
        assertEquals(3718, properties.getDmsTries());
    }

    @Test
    void readInteger_env(){
        when(bundle.getResourceBundle(Properties.BUNDLE_ID, Properties.DMS_DATA_TRIES, Locale.UK)).thenReturn("$TEST_VS");
        value = "3718";
        assertEquals(3718, properties.getDmsTries());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "$"})
    @NullSource
    void readInteger_env(String value){
        when(bundle.getResourceBundle(Properties.BUNDLE_ID, Properties.DMS_DATA_TRIES, Locale.UK)).thenReturn(value);
        value = "";
        assertEquals(0, properties.getDmsTries());
    }

    @Test
    void getDmsEncoding(){
        when(bundle.getResourceBundle(Properties.BUNDLE_ID, Properties.DMS_DATA_ENCODING, Locale.UK)).thenReturn("ISO-8859-1");
        assertEquals(StandardCharsets.ISO_8859_1, properties.getDmsEncoding());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "latin","UFT-16"})
    @NullSource
    void getDmsEncoding_empty(String value){
        when(bundle.getResourceBundle(Properties.BUNDLE_ID, Properties.DMS_DATA_ENCODING, Locale.UK)).thenReturn(value);
        assertEquals(StandardCharsets.UTF_8, properties.getDmsEncoding());
    }
}