package com.visitscotland.brmx.dms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.Properties;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Locale;


public class DMSDataService {

    private static final Logger logger = LoggerFactory.getLogger(DMSDataService.class.getName());

    private CommonUtils utils;

    public DMSDataService() {
        this (new CommonUtils());
    }

    public DMSDataService(CommonUtils utils) {
        this.utils = utils;
    }

    /**
     * Returns a summary of the product as a JsonNode. This is usually use to show product cards
     *
     * @param productId Id of the producct
     * @param locale locale for translated texts
     *
     * @return JsonNode with the product card Information
     *
     *
     */
    public JsonNode productCard(String productId, Locale locale) throws IOException {
        String responseString = null;

        if (!Contract.isEmpty(productId)) {
            String dmsUrl = Properties.VS_DMS_SERVICE + "/data/products/card?id=" + productId;
            if (locale != null) {
                dmsUrl += "&locale=" + locale.getLanguage();
            }

            logger.info("Requesting data to the dms: " + dmsUrl);
            try {
                responseString = utils.requestUrl(dmsUrl);

                if (responseString!=null) {

                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode json = mapper.readTree(responseString);

                    if (json.has("data")) {
                        return json.get("data");
                    }
                }
            } catch (JsonProcessingException e){
                logger.error("The response could not be parsed:\n" + responseString, e);
            } catch (IOException e){
                logger.error("An unexpected error happened while connecting to the DMS", e);
            }
        } else {
            logger.info("productCard data requested but the product id was not provided");
        }
        return null;
    }

    //TODO: Do it right!!!! Use psb instead of locale and query
    public JsonNode searchResults(ProductSearchBuilder psb, Locale locale, String query){

        // TODO: This method is part of POC for iCentre and Iknow modules, The requirements hasn't been signed of and
        // therefore, this method might not be correct. Once this method is completed, Some unit tests must be added
        logger.error("This is an stub method that conver product search parameters into map parameters.");

        String dmsUrl = psb.buildDataMap();

//        String dmsUrl = String.format(DMSConstants.PRODUCT_SEARCH_DATA_MAP, Properties.VS_DMS_SERVICE, query.substring(query.lastIndexOf("?")+1)) ;
        String responseString = null;

        if (locale != null) {
            dmsUrl += "&locale=" + locale.getLanguage();
        }

        logger.info("Requesting data to the dms: " + dmsUrl);
        try {
            responseString = utils.requestUrl(dmsUrl);

            if (responseString!=null) {

                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(responseString);

                if (json.has("features")) {
                    return json.get("features");
                }
            }
        } catch (JsonProcessingException e){
            logger.error("The response could not be parsed:\n" + responseString, e);
        } catch (IOException e){
            logger.error("An unexpected error happened while connecting to the DMS", e);
        }
        return  null;
    }
}
