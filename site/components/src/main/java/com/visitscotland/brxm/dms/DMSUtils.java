package com.visitscotland.brxm.dms;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.dataobjects.DataType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DMSUtils {

    public static final String FACILITIES = "keyFacilities";

    public List<DataType> getFacilities (JsonNode product){
        List<DataType> facilities = null;
        if (product.has(FACILITIES)){
            facilities = new ArrayList<>();
            JsonNode keyFacilitiesList = product.get(FACILITIES);

            if (keyFacilitiesList.isArray()) {
                for (JsonNode facility : keyFacilitiesList) {
                    DataType dataType = new DataType(facility.get("id").asText(),facility.get("name").asText());
                    facilities.add(dataType);
                }
            }
        }
        return facilities;
    }
}
