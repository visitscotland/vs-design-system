package com.visitscotland.brmx.components;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.RepositoryException;
import java.io.IOException;
import java.util.*;

public class DMSFacilityPicker extends AbstractDMSPicker {

    /**
     * Plugin parameter name for physical document field name (JCR property name).
     */

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DMSFacilityPicker.class);

    private static final String TYPE = "facility";

    public DMSFacilityPicker() {
        super (TYPE);
    }

    @Override
    public ExternalDocumentCollection<JSONObject> searchExternalDocuments(ExternalDocumentServiceContext context,String queryString) {
        final String fieldName = context.getPluginConfig().getString(PRODUCT_TYPE);
        try {
            List<String> productTypes = Arrays.asList(context.getContextModel().getNode().getProperty(fieldName).getValue().getString().split("\\s*,\\s*"));
            if (!productTypes.get(0).isEmpty()) {
                JSONArray subCategory = new JSONArray();

                subCategory.addAll(JSONArray.fromObject(deserialize(TYPE,request(TYPE,null, productTypes))));

                docArray=subCategory;

            }
        } catch (RepositoryException | IOException e) {
            e.printStackTrace();
        }

        ExternalDocumentCollection<JSONObject> docCollection =  new SimpleExternalDocumentCollection<JSONObject>();
        for (int i = 0; i < docArray.size(); i++) {
            JSONObject doc = docArray.getJSONObject(i);
            if (StringUtils.contains(doc.getString("name").toLowerCase(), queryString.toLowerCase())) {
                docCollection.add(doc);
            }
        }

        return docCollection;
    }


}