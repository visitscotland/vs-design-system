package com.visitscotland.brmx.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.InstagramImage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.utils.Contract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CommonUtils {
    //TODO add message format for other languages
    public static final String contentIssue (String message, Object... parameters){
        return String.format("- [CONTENT] - " + message, parameters);
    }

    /**
     * TODO comment!
     * @param productId
     * @param locale
     * @return
     * @throws IOException
     */
    public static JsonNode getProduct(String productId, Locale locale) throws IOException {
        if (!Contract.isEmpty(productId)) {
            String dmsUrl = Properties.VS_DMS_SERVICE + "/data/products/card?id=" + productId;
            if (locale != null) {
                dmsUrl += "&locale=" + locale.getLanguage();
            }

            String responseString = request(dmsUrl);
            if (responseString!=null) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(responseString);

                if (json.has("data")) {
                    return json.get("data");
                }
            }
        }
        return null;
    }

    /**
     * Request a page and return the body as String
     * @param url
     * @return null if status code not 200 or 300
     * @throws IOException
     */
    public static String request(String url) throws IOException {
        // TODO comment
        if (((HttpURLConnection) new URL(url).openConnection()).getResponseCode() < 400){
            final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            final StringBuilder sb = new StringBuilder();
            int cp;

            while ((cp = br.read()) != -1) {
                sb.append((char) cp);
            }

            return sb.toString();
        }
        return null;
    }

    public static String getInstagramCaption(InstagramImage instagramLink) throws IOException {
        String response =  null;
        URL instagramInformation  = new URL("https://api.instagram.com/oembed/?url=http://instagr.am/p/" + instagramLink.getId());
        String responseInstagram =  request(instagramInformation.toString());
        if (responseInstagram != null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(request(instagramInformation.toString()));
           response = json.has("author_name") ? json.get("author_name").asText() : "";
        }
        return response;
    }

    //TODO this method returns the current open state and it coud be affected by the cache, ask WEBOPS and move it to front end if needed
    public static  String currentOpenStatus(String starTime, String endTime, Locale locale){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
        LocalTime starts = LocalTime.parse(starTime, formatter);
        LocalTime ends = LocalTime.parse(endTime, formatter);
        LocalTime currentTime = LocalTime.now(ZoneId.of("+1"));
        if (currentTime.isAfter(starts) && currentTime.isBefore(ends)){
            if (currentTime.plusMinutes(30).isAfter(ends)){
                return  HippoUtils.getResourceBundle("stop.close.soon", "itinerary", locale);
            }else{
                return   HippoUtils.getResourceBundle("stop.open", "itinerary", locale);
            }
        }else
        {
            return   HippoUtils.getResourceBundle("stop.closed", "itinerary", locale);
        }
    }
}