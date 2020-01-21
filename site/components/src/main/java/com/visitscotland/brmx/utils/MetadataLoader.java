package com.visitscotland.brmx.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.visitscotland.brmx.beans.dms.LocationObject;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public abstract class MetadataLoader {

    //TODO Log messages
    private static final Logger logger = null;//LoggerFactory.getLogger(MetadataLoader.class);

    //TODO Calculate environment
    public static final String SITE = "https://www.visitscotland.com"; //ApplicationProperties.getInstance().getString(MAIN_SITE);

    private static final Map<Locale, List<LocationObject>> metadata = new HashMap<>();
    private static final Set<Locale> locales = new HashSet<>();

    private static Collection<Locale> getLocales(){
        if (locales.size() == 0){
            locales.add(Locale.forLanguageTag("fr-fr"));
            locales.add(Locale.forLanguageTag("es-es"));
        }

        return locales;
    }

    static {
        //TODO: This is a bad practice. Turn into a Autorefreshable class (similar to vs-dms-products)
        loadSources();
    }

    private static void loadSources() {
        boolean firstRun = locales.size() == 0;
        synchronized (MetadataLoader.class) {

            for (Locale loc: getLocales()){
                metadata.put(loc, getData(loc, metadata.get(loc)));
            }



            if (firstRun){
//                logger.info("Header and footer cached from {}. The resources will be reloaded every {} seconds", SITE, 600);
            } else {
//                logger.info("Header and footer reloaded successfully");
            }
        }
    }

    public static List<LocationObject> getData(Locale locale, List<LocationObject> currentValue){
        try {
            return deserialize(request(locale));
        } catch (IOException e){
//            logger.warn("Metadata couldn't been loaded");
        }

        return currentValue;
    }

    /**
     * Request the the resource taking into account the language.
     *
     * @param locale: Specific locale for the fragment or null if the locale is English (default locale)
     *
     * @return HTML fragment according to the type and the locale
     */
    private static String request(Locale locale){
        if (locale == null){
            return requestPage(String.format("%s/data/locations",SITE));
        } else {
            return requestPage(String.format("%s/data/locations?locale=%s",SITE, locale.getLanguage()));
        }
    }

    /**
     * Reads a page byte per byte to ensure that the encoding used is the same as the original
     *
     * @param url Requested URL
     *
     * @return HTML Fragment
     */
    private static String requestPage(String url) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = new URL(url).openStream();
            int c;
            while ((c = is.read()) != -1){
                sb.append((char) c);
            }
            return sb.toString();
        } catch (Exception e){
//            logger.error("Error while loading {}: {}", url, e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    private static List<LocationObject> deserialize(String data) throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode dataObject = jsonMapper.readTree(data);
        List<LocationObject> locations = new ArrayList<>();

        if (!dataObject.has("data")){
            throw new IOException("No data field found");
        }

        for (JsonNode elm: dataObject.get("data")){
            locations.add(jsonMapper.readValue(elm.toString(), LocationObject.class));
        }
        return locations;
    }

    public static void main(String[] args) {
        System.out.println("-- INIT");
        loadSources();
        System.out.println("-- LOAD");
        String data = request(Locale.forLanguageTag("es-es"));

        ObjectMapper jsonMapper = new ObjectMapper();

        try {

            System.out.println("-- DESERIALIZING LIST");
            JsonNode dataObject = jsonMapper.readTree(data);
            ArrayNode array = (ArrayNode) dataObject.get("data");


            for (JsonNode elm : array) {
                System.out.println("ELM 1 = " + elm.toString());

                System.out.println(jsonMapper.readValue(elm.toString(), LocationObject.class));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("-- END --->");
        System.out.println(metadata.get(Locale.forLanguageTag("es-es")).size());
        System.out.println("-- END");
    }
}