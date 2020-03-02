package com.visitscotland.brmx.components;


import com.visitscotland.dataobjects.MetadataSearch;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.repository.HippoStdNodeType;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceFacade;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.sf.json.JSONObject;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.util.*;


public abstract class AbstractDMSPicker implements ExternalDocumentServiceFacade<JSONObject> {

    private static Logger log = LoggerFactory.getLogger(AbstractDMSPicker.class);

    protected static final String PARAM_EXTERNAL_DOCS_FIELD_NAME = "example.external.docs.field.name";
    protected static final String PRODUCT_TYPE = "dms.productype";
    protected static final String MULTIPLE_SELECTION = "selection.mode";

    protected JSONArray docArray;

    protected MetadataSearch metadata;



    @Override
    public void setFieldExternalDocuments(ExternalDocumentServiceContext context,
                                          ExternalDocumentCollection<JSONObject> exdocs) {
        final String fieldName = context.getPluginConfig().getString(PARAM_EXTERNAL_DOCS_FIELD_NAME);

        if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("Invalid plugin configuration parameter for '"
                    + PARAM_EXTERNAL_DOCS_FIELD_NAME + "': " + fieldName);
        }

        try {
            final Node contextNode = context.getContextModel().getNode();
            final List<String> docIds = new ArrayList<String>();

            for (Iterator<? extends JSONObject> it = exdocs.iterator(); it.hasNext();) {
                JSONObject doc = it.next();
                docIds.add(doc.getString("id"));
            }

            if (!contextNode.isNodeType(HippoStdNodeType.NT_RELAXED)) {
                contextNode.addMixin(HippoStdNodeType.NT_RELAXED);
            }

            contextNode.setProperty(fieldName, docIds.toArray(new String[docIds.size()]));
        } catch (RepositoryException e) {
            log.error("Failed to set related exdoc array field.", e);
        }
    }
    public ExternalDocumentCollection<JSONObject> getFieldExternalDocuments(ExternalDocumentServiceContext context) {
        final String fieldName = context.getPluginConfig().getString(PARAM_EXTERNAL_DOCS_FIELD_NAME);
        final String multiple = context.getPluginConfig().getString(PARAM_EXTERNAL_DOCS_FIELD_NAME);

        if (StringUtils.isBlank(fieldName)) {
            throw new IllegalArgumentException("Invalid plugin configuration parameter for '"
                    + PARAM_EXTERNAL_DOCS_FIELD_NAME + "': " + fieldName);
        }

        ExternalDocumentCollection<JSONObject> docCollection = new SimpleExternalDocumentCollection<JSONObject>();

        try {
            final Node contextNode = context.getContextModel().getNode();

            if (contextNode.hasProperty(fieldName)) {
                Value[] values = contextNode.getProperty(fieldName).getValues();

                for (Value value : values) {
                    String id = value.getString();
                    JSONObject doc = findDocumentById(id);

                    if (doc != null) {
                        docCollection.add(doc);
                    }
                }
            }
        } catch (RepositoryException e) {
            log.error("Failed to retrieve related exdoc array field.", e);
        }

        return docCollection;
    }


    @Override
    public String getDocumentTitle(ExternalDocumentServiceContext context, JSONObject doc, Locale preferredLocale) {
        if (doc != null && doc.has("id")) {
            return doc.getString("id");
        }

        return "";
    }
    @Override
    public String getDocumentDescription(ExternalDocumentServiceContext context, JSONObject doc,
                                         Locale preferredLocale) {
        if (doc != null && doc.has("name")) {
            return doc.getString("name");
        }

        return "";
    }

    @Override
    public String getDocumentIconLink(ExternalDocumentServiceContext context, JSONObject doc, Locale preferredLocale) {

        return "";
    }

    protected JSONObject findDocumentById(final String id) {
        for (int i = 0; i < docArray.size(); i++) {
            JSONObject doc = docArray.getJSONObject(i);

            if (StringUtils.equals(id, doc.getString("id"))) {
                return doc;
            }
        }

        return null;
    }


    @Override
    public abstract  ExternalDocumentCollection<JSONObject> searchExternalDocuments(ExternalDocumentServiceContext externalDocumentServiceContext, String s);

}