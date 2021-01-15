package com.visitscotland.brxm.dms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brxm.beans.dms.LocationObject;
import com.visitscotland.brxm.utils.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

//TODO Test?
//TOTO convert to Service
public class LocationLoader {

    private static final Logger logger = LoggerFactory.getLogger(LocationLoader.class);

    private final Map<Locale, Map<String, LocationObject>> locations = new HashMap<>();

    private final Map<String, String> locationToId = new HashMap<>();

    private static LocationLoader instance;

    private LocationLoader(){
        init();
    }

    public static LocationLoader getInstance(){
        if (instance == null){
            instance = new LocationLoader();
        }
        return instance;
    }

    public LocationObject getLocation(String location, Locale locale){

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
    public List<LocationObject> getLocationsByLevel(String... levels){
        List<LocationObject> locationList = new ArrayList<>();
        for (LocationObject obj : locations.get(null).values()){
            if (levels!=null && levels.length>0){
                for (String level : levels){
                    if (obj.getTypes().contains(level)){
                        locationList.add(obj);
                        break;
                    }
                }
            }else{
                locationList.add(obj);
            }
        }
        if (locationList.size() == 0){
            logger.warn("No objects matched with the types. It is possible that the types weren't loaded from the endpoint.");
        }

        Collections.sort(locationList, Comparator.comparing(LocationObject::getName));

        return  locationList;
    }

    private void clear(){
        locationToId.clear();
        locations.clear();
    }
    /**
     * Initialize maps
     */
    private void init() {
        synchronized (LocationLoader.class) {
            if (locationToId.size() == 0) {
                for (Locale locale : com.visitscotland.brxm.utils.Properties.locales) {
                    Map<String, LocationObject> locationsMap = new HashMap<>();
                    try {
                        List<LocationObject> locationList = deserialize(request(locale));

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

                    } catch (IOException e) {
                        logger.warn("Location List couldn't been loaded for the locale {}", locale);
                    } catch (Exception e) {
                        logger.error("Unexpected exception ", e);
                    }

                    locations.put(locale, locationsMap);
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
    private String request(Locale locale){
        //TODO Change the level to add polygon (for destinations pages)
        if (locale == null){
            return requestPage(String.format("%s/data/meta/location/list?full", com.visitscotland.brxm.utils.Properties.VS_DMS_SERVICE));
        } else {
            return requestPage(String.format("%s/data/meta/location/list?full&locale=%s", Properties.VS_DMS_SERVICE, locale.getLanguage()));
        }
    }

    /**
     * Reads a page byte per byte to ensure that the encoding used is the same as the original
     *
     * @param url Requested URL
     *
     * @return HTML Fragment
     */
    private String requestPage(String url) {
        StringBuilder sb = new StringBuilder();
        try (InputStream is = new URL(url).openStream()) {
            int c;
            while ((c = is.read()) != -1) {
                sb.append((char) c);
            }
            return sb.toString();
        } catch (Exception e) {
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
    private List<LocationObject> deserialize(String data) throws IOException {
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
}