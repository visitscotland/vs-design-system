package com.visitscotland.brxm.dms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brxm.utils.CommonUtils;
import com.visitscotland.brxm.utils.Properties;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Locale;

//TODO: Test changing the name
//@Component("dmsDataService")
@Component
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
            String dmsUrl = String.format(DMSConstants.VS_DMS_PRODUCT_CARD,Properties.VS_DMS_SERVICE);
            dmsUrl += "id=" + productId;
            if (locale != null) {
                dmsUrl += "&locale=" + locale.getLanguage();
            }

            logger.info("Requesting data to the dms: %s", dmsUrl);
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
                logger.error("The response could not be parsed:\n %s", responseString, e);
            } catch (IOException e){
                logger.error("An unexpected error happened while connecting to the DMS", e);
            }
        } else {
            logger.info("productCard data requested but the product id was not provided");
        }
        return null;
    }

    /**
     * This method invokes the legacy data Map endpoint from a ProductSearchBuilder
     *
     * @param psb ProductSearchBuilder
     *
     * @return
     */
    public JsonNode legacyMapSearch(ProductSearchBuilder psb){

        String responseString = null;
        String dmsUrl = psb.buildDataMap();

        logger.info("Requesting data to the dms: %s", dmsUrl);
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
            logger.error("The response could not be parsed:\n %s", responseString, e);
        } catch (IOException e){
            logger.error("An unexpected error happened while connecting to the DMS", e);
        }
        return  null;
    }
}
