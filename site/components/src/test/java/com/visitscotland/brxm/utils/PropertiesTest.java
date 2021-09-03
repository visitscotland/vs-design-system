package com.visitscotland.brxm.utils;

import com.visitscotland.brxm.services.ResourceBundleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("Reads an String value from a property")
    void readString(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, "string", Locale.UK)).thenReturn("http://localhost:8181");
        assertEquals("http://localhost:8181", properties.readString("string"));
    }

    @Test
    @DisplayName("Reads an string value from an environment variable")
    void readString_env(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, "string", Locale.UK)).thenReturn("$TEST_VS");
        value = "http://dms.visitscotland.com";
        assertEquals("http://dms.visitscotland.com", properties.readString("string"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "$"})
    @NullSource
    @DisplayName("Empty values return an empty String for String properties")
    void readString_env(String value){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, "string", Locale.UK)).thenReturn(value);
        value = "";
        assertEquals("", properties.readString("string"));
    }

    @Test
    @DisplayName("Reads an integer number from a property")
    void readInteger(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_DATA_TRIES, Locale.UK)).thenReturn("3718");
        assertEquals(3718, properties.getDmsTries());
    }

    @Test
    @DisplayName("Can parse Integers from environment variables")
    void readInteger_env(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_DATA_TRIES, Locale.UK)).thenReturn("$TEST_VS");
        value = "3718";
        assertEquals(3718, properties.getDmsTries());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "$"})
    @NullSource
    @DisplayName("Empty and wrong values return 0 for Numeric properties")
    void readInteger_empty(String value){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_DATA_TRIES, Locale.UK)).thenReturn(value);
        value = "";
        assertEquals(0, properties.getDmsTries());
    }

    @Test
    @DisplayName("Reads an integer number from a property")
    void readDouble(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_MAP_DEFAULT_DISTANCE, Locale.UK)).thenReturn("3.14");
        assertEquals(3.14, properties.getDmsMapDefaultDistance());
    }

    @Test
    @DisplayName("Can parse Integers from environment variables")
    void readDouble_env(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_MAP_DEFAULT_DISTANCE, Locale.UK)).thenReturn("$TEST_VS");
        value = "3.14";
        assertEquals(3.14, properties.getDmsMapDefaultDistance());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "a", "$"})
    @NullSource
    @DisplayName("Empty and wrong values return 0 for Numeric properties")
    void readDouble_empty(String value){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_MAP_DEFAULT_DISTANCE, Locale.UK)).thenReturn(value);
        assertEquals(0.0, properties.getDmsMapDefaultDistance());
    }


    @ParameterizedTest
    @ValueSource(strings = {"true", "TRUE"})
    @DisplayName("Reads true from a property")
    void readBoolean(String value){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, "boolean", Locale.UK)).thenReturn(value);
        assertTrue(properties.readBoolean("boolean"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"false", "", "$", "0", "1", "T"})
    @NullSource
    @DisplayName("false, empty and wrong values return false for Boolean properties")
    void readInteger_false(String value){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, "boolean", Locale.UK)).thenReturn(value);
        assertFalse(properties.readBoolean("boolean"));
    }

    @Test
    @DisplayName("getDmsEncoding - Can parse the value from a String")
    void getDmsEncoding(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_DATA_ENCODING, Locale.UK)).thenReturn("ISO-8859-1");
        assertEquals(StandardCharsets.ISO_8859_1, properties.getDmsEncoding());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "latin","UFT-16"})
    @NullSource
    @DisplayName("getDmsEncoding - non recognized values return UTF-8 encoding")
    void getDmsEncoding_empty(String value){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_DATA_ENCODING, Locale.UK)).thenReturn(value);
        assertEquals(StandardCharsets.UTF_8, properties.getDmsEncoding());
    }

    @Test
    @DisplayName("As requested by WebOps, links to vs-dms-products URLs will be relative when use relative urls is active")
    void getDmsHost(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.DMS_HOST, Locale.UK)).thenReturn("http://test-dms.visitscotland.com");
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.USE_RELATIVE_URLS, Locale.UK)).thenReturn("false");
        assertEquals("http://test-dms.visitscotland.com", properties.getDmsHost());

        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.USE_RELATIVE_URLS, Locale.UK)).thenReturn("true");
        assertEquals("", properties.getDmsHost());
    }

    @Test
    @DisplayName("As requested by WebOps, made up links to the CMS, will be relative when use relative urls is active")
    void getLocalHost(){
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.CMS_BASE_PATH, Locale.UK)).thenReturn("http:/localhost:8080/site");
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.USE_RELATIVE_URLS, Locale.UK)).thenReturn("true");
        assertEquals("", properties.getDmsHost());

        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.USE_RELATIVE_URLS, Locale.UK)).thenReturn("false");
        assertEquals("http:/localhost:8080/site", properties.getCmsBasePath());
    }

    @Test
    @DisplayName("getInstagramURL() Composes the token from the app-id and the client-token (PR-383)")
    void getInstagramURL(){

        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.INSTAGRAM_APP_ID, Locale.UK)).thenReturn("{app-id}");
        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.INSTAGRAM_ACCESS_TOKEN, Locale.UK)).thenReturn("{client-token}");
        assertEquals("{app-id}|{client-token}", properties.getInstagramToken());

        when(bundle.getResourceBundle(Properties.DEFAULT_ID, Properties.INSTAGRAM_ACCESS_TOKEN, Locale.UK)).thenReturn("");
        assertEquals("{app-id}", properties.getInstagramToken());
    }

    @Test
    @DisplayName("Read properties from hosts configuration");
    void propertiesFromConfiguration(){
        // TODO:
    }
}