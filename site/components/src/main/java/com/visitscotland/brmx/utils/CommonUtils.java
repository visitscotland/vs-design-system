package com.visitscotland.brmx.utils;

import org.hippoecm.hst.core.component.HstResponse;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

public class CommonUtils {

    //TODO use utils library instead.
    public static final boolean isEmpty(String value){
        return value == null || value.trim().length() == 0;
    }

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
    public static JSONObject getProduct(String productId, Locale locale) throws IOException {
        String dmsUrl = Properties.VS_DMS_PRODUCTS + "/data/product-search/map?prod_id=" + productId;
        if (locale != null) {
            dmsUrl += "&locale=" + locale.getLanguage();
        }

        String body = request(dmsUrl);

        JSONObject json = new JSONObject(body);

        if (json.has("data")) {
            JSONArray data = (JSONArray) json.get("data");
            return data.getJSONObject(0);
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
}
