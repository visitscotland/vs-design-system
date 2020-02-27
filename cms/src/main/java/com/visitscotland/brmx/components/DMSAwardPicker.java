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
import com.visitscotland.dataobjects.FacilityGroup;
import com.visitscotland.dataobjects.MetadataSearch;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hippoecm.repository.HippoStdNodeType;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceFacade;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vs.ase.dms.ProductTypes;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.util.*;

// TODO: refactor
public class DMSAwardPicker implements ExternalDocumentServiceFacade<JSONObject> {

    /**
     * Plugin parameter name for physical document field name (JCR property name).
     */
    public static final String PARAM_EXTERNAL_DOCS_FIELD_NAME = "example.external.docs.field.name";
    public static final String PRODUCT_TYPE = "dms.productype";
    public static final String MULTIPLE_SELECTION = "selection.mode";

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DMSAwardPicker.class);

    private JSONArray docArray;

    private MetadataSearch metadata;

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
        final String fieldName = context.getPluginConfig().getString(PRODUCT_TYPE);
        try {
            List<String> productTypes = Arrays.asList(context.getContextModel().getNode().getProperty(fieldName).getValue().getString().split("\\s*,\\s*"));
            if (!productTypes.get(0).isEmpty()) {
                List<FacilityGroup> facGroups = new ArrayList<>();

                for (String productType : productTypes) {
                    facGroups.addAll(metadata.getFacilityGroupsForType(ProductTypes.byId(productType)));
                }

                if (facGroups != null) {
                    JSONArray subCategory = new JSONArray();
                    for (FacilityGroup fac : facGroups) {
                        subCategory.addAll(JSONArray.fromObject(fac.getFacilities()));
                    }
                    docArray = subCategory;
                }

            }
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        ExternalDocumentCollection<JSONObject> docCollection =
                new SimpleExternalDocumentCollection<JSONObject>();
        for (int i = 0; i < docArray.size(); i++) {
            JSONObject doc = docArray.getJSONObject(i);
            if (StringUtils.contains(doc.getString("name").toLowerCase(), queryString.toLowerCase())) {
                docCollection.add(doc);
            }
        }

        return docCollection;
    }

    //TODO : common method
    @Override
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

    //TODO : common method
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
    //TODO : common method
    @Override
    public String getDocumentTitle(ExternalDocumentServiceContext context, JSONObject doc, Locale preferredLocale) {
        if (doc != null && doc.has("id")) {
            return doc.getString("id");
        }

        return "";
    }
    //TODO : common method
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
      /*  if (doc != null && doc.has("name")) {
            return doc.getString("name");
        }*/

        return "";
    }
    //TODO : common method
    private JSONObject findDocumentById(final String id) {
        for (int i = 0; i < docArray.size(); i++) {
            JSONObject doc = docArray.getJSONObject(i);

            if (StringUtils.equals(id, doc.getString("id"))) {
                return doc;
            }
        }

        return null;
    }



}