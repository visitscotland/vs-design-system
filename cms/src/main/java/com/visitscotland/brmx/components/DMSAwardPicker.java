package com.visitscotland.brmx.components;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DMSAwardPicker extends AbstractDMSPicker {

    /**
     * Plugin parameter name for physical document field name (JCR property name).
     */

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DMSAwardPicker.class);
    private static final String TYPE = "award";


    public DMSAwardPicker() {
        super (TYPE);
    }

    @Override
    public ExternalDocumentCollection<JSONObject> searchExternalDocuments(ExternalDocumentServiceContext context, String queryString) {

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