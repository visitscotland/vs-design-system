package com.visitscotland.brmx.dms;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.Properties;
import com.visitscotland.utils.Contract;

import java.io.IOException;
import java.util.Locale;

/**
 * TODO Test! Notice tha CommonUtils request must be mocked.
 */
public class DMSDataService {

    /**
     * TODO comment!
     * @param productId
     * @param locale
     * @return
     * @throws IOException
     */
    public JsonNode productCard(String productId, Locale locale) throws IOException {
        if (!Contract.isEmpty(productId)) {
            String dmsUrl = Properties.VS_DMS_SERVICE + "/data/products/card?id=" + productId;
            if (locale != null) {
                dmsUrl += "&locale=" + locale.getLanguage();
            }

            String responseString = CommonUtils.request(dmsUrl);
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
}
