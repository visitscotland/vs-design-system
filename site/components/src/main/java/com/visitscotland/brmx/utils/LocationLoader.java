package com.visitscotland.brmx.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.visitscotland.brmx.beans.dms.LocationObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public abstract class LocationLoader {

    private static final Logger logger = null; //LoggerFactory.getLogger(LocationLoader.class.getName());

    private static final Map<Locale, Map<String, LocationObject>> locations = new HashMap<>();

    private static final Map<String, String> locationToId = new HashMap<>();

    static {
        init();
    }

    public static LocationObject getLocation(String location, Locale locale){

        if (Properties.locales.contains(locale)){
            return locations.get(locale).get(locationToId.get(location));
        } else {
            return locations.get(null).get(locationToId.get(location));
        }

    }

    /**
     *
     * @param levels
     * @return
     */
    public static List<LocationObject> getLocationsByLevel(String... levels){
        List<LocationObject> locationList = new ArrayList<>();
        for (LocationObject obj : locations.get(null).values()){
            for (String level : levels){
                if (obj.getTypes().contains(level)){
                    locationList.add(obj);
                    break;
                }
            }
        }
        if (locationList.size() == 0){
            logger.warn("No objects matched with the types. It is possible that the types weren't loaded from the endpoint.");
        }

        return  locationList;
    }

    private static void clear(){
        locationToId.clear();
        locations.clear();
    }
    /**
     * Initialize maps
     */
    private static void init() {
        synchronized (LocationLoader.class) {
            if (locationToId.size() == 0) {
                for (Locale locale : Properties.locales) {
                    try {
                        List<LocationObject> locationList = deserialize(request(locale));
                        Map<String, LocationObject> locationsMap = new HashMap<>();

                        //if the locationToId map is empty and the locale is null. Both lists are populated simultaneously
                        if (locationToId.size() == 0 && locale == null) {
                            for (LocationObject location : locationList) {
                                locationToId.put(location.getName(), location.getId());
                                locationsMap.put(location.getId(), location);
                            }
                        } else {
                            for (LocationObject location : locationList) {
                                locationsMap.put(location.getId(), location);
                            }
                        }

                        locations.put(locale, locationsMap);

                    } catch (IOException e) {
                        logger.warn("Location List couldn't been loaded for the locale {}", locale);
                    }
                }
            }
        }
    }

    /**
     * Request the the resource taking into account the language.
     *
     * @param locale: Specific locale for the fragment or null if the locale is English (default locale)
     *
     * @return HTML fragment according to the type and the locale
     */
    private static String request(Locale locale){
        //TODO Change the level to add polygon (for destinations pages)
        if (locale == null){
            return requestPage(String.format("%s/data/location/list?full",Properties.VS_DMS_PRODUCTS));
        } else {
            return requestPage(String.format("%s/data/location/list?full&locale=%s",Properties.VS_DMS_PRODUCTS, locale.getLanguage()));
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
            logger.error("Error while loading {}: {}", url, e.getMessage(), e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deserialize the List of elements in a list of Locations
     * @param data
     * @return
     * @throws IOException
     */
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
        init();
        System.out.println("-- LOAD");
        String data = request(Locale.forLanguageTag("es-es"));

        ObjectMapper jsonMapper = new ObjectMapper();

        System.out.println("Edinburgh for ES = " + LocationLoader.getLocation("Edinburgh", Locale.forLanguageTag("es-es")).getName());

        System.out.println("DIST AND DEST =" +  getLocationsByLevel("DISTRICT", "DESTINATION").size());
        System.out.println();

        System.out.println("-- END --->");
        System.out.println(locations.get(Locale.forLanguageTag("es-es")).size());
        System.out.println("-- END");
    }
}