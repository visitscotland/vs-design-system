package com.visitscotland.brmx.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.beans.InstagramImage;
import com.visitscotland.brmx.services.ResourceBundleService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class CommonUtils {

    final static String VS_DMS_ENCODING = "UTF8";

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
            final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream(), VS_DMS_ENCODING));
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

    public static JsonNode getInstagramInformation(InstagramImage instagramLink) throws IOException {
        JsonNode response =  null;
        ResourceBundleService bundle = new ResourceBundleService();
        //TODO add the access token value for VS facebook account
        String accessToken = bundle.getResourceBundle("keys","tagram.accesstoken",  Locale.UK);
        URL instagramInformation  = new URL("https://graph.facebook.com/v9.0/instagram_oembed?url=http://instagr.am/p/" + instagramLink.getId()+"&access_token="+accessToken);
        String responseInstagram =  request(instagramInformation.toString());
        if (responseInstagram != null) {
            ObjectMapper mapper = new ObjectMapper();
            response = mapper.readTree(request(instagramInformation.toString()));
        }
        return response;
    }

    public static String getExtenalDocumentSize(String link){
        String size = null;
        URL url;
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        try {
            url = new URL(link);
            URLConnection con = url.openConnection();

            String ext = con.getContentType();
            if (ext.contains("pdf")){
                double bytes = con.getContentLength();
                size = "PDF " + decimalFormat.format((bytes / 1024) / 1024) + "MB";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
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


}
