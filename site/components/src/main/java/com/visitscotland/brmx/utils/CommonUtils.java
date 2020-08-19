package com.visitscotland.brmx.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.beans.InstagramImage;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.utils.Contract;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CommonUtils {

    private static DMSDataService dmsData = new DMSDataService();
    private static ResourceBundleService resourceBundle = new ResourceBundleService();

    //TODO add message format for other languages
    public static final String contentIssue (String message, Object... parameters){
        return String.format("- [CONTENT] - " + message, parameters);
    }

    /**
     * Request a page and return the body as String
     * @param url
     * @return null if status code not 200 or 300
     * @throws IOException
     */
    @Deprecated
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

    public String requestUrl(String url) throws IOException {
        return CommonUtils.request(url);
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


    //TODO this method returns the current open state and it could be affected by the cache, ask WEBOPS and move it to front end if needed
    //TODO move to DMSDataService once this method need is confirmed
    public static  String currentOpenStatus(String starTime, String endTime, Locale locale){
        ResourceBundleService bundle = new ResourceBundleService();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma");
        LocalTime starts = LocalTime.parse(starTime, formatter);
        LocalTime ends = LocalTime.parse(endTime, formatter);
        LocalTime currentTime = LocalTime.now(ZoneId.of("+1"));
        if (currentTime.isAfter(starts) && currentTime.isBefore(ends)){
            if (currentTime.plusMinutes(30).isAfter(ends)){
                return  bundle.getResourceBundle("itinerary","stop.close.soon",  locale);
            }else{
                return   bundle.getResourceBundle("itinerary","stop.open",  locale);
            }
        }else
        {
            return   bundle.getResourceBundle("itinerary","stop.closed",  locale);
        }
    }

    /**
     * Returns the default CTA label when the manual CTA  is not defined.     *
     *
     * @param manualCta Manual CTA defined in the CMS
     * @param locale Locale
     *
     * @return the manual CTA if provided otherwise the default CTA
     */
    //TODO: Refactor static method. Move to another place?
    public static String getCtaLabel(String manualCta, Locale locale) {
        if (!Contract.isEmpty(manualCta)) {
            return manualCta;
        } else {
            return resourceBundle.getResourceBundle("essentials.global","button.find-out-more",  locale);
        }
    }
}