package com.visitscotland.brmx.components;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.google.gson.Gson;
import com.visitscotland.api.DataService;
import com.visitscotland.api.DataServiceImpl;
import com.visitscotland.dataobjects.Award;
import com.visitscotland.dataobjects.DataType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

public class DMSAwardPicker extends AbstractDMSPicker {

    /**
     * Plugin parameter name for physical document field name (JCR property name).
     */

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DMSAwardPicker.class);


    public DMSAwardPicker() {
        try {
            docArray = new JSONArray();
            DataService dsi = new DataServiceImpl();
            metadata = dsi.getMetatdata();

            SortedMap<DataType, List<Award>> awards = metadata.getAwardsByType();
            if (awards != null) {
                for (List<Award> award : awards.values()) {
                    String json = new Gson().toJson(award);
                    docArray.addAll(JSONArray.fromObject(json));
                }
            }

        } catch (Exception e) {
            log.error("Failed to load JSON data.", e);
        }

    }

    @Override
    public ExternalDocumentCollection<JSONObject> searchExternalDocuments(ExternalDocumentServiceContext context,
                                                                          String queryString) {

        ExternalDocumentCollection<JSONObject> docCollection = new SimpleExternalDocumentCollection<JSONObject>();
        for (int i = 0; i < docArray.size(); i++) {
            JSONObject doc = docArray.getJSONObject(i);
            if (StringUtils.contains(doc.getString("name").toLowerCase(), queryString.toLowerCase())) {
                docCollection.add(doc);
            }
        }

        return docCollection;
    }

}