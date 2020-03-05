package com.visitscotland.brmx.components;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.api.DataService;
import com.visitscotland.api.DataServiceImpl;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.Properties;
import com.visitscotland.dataobjects.Award;
import com.visitscotland.dataobjects.Category;
import com.visitscotland.dataobjects.Facility;
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
import vs.ase.dms.AwardTypes;
import vs.ase.dms.ProductTypes;

import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.Value;
import java.io.IOException;
import java.net.URL;
import java.util.*;


public abstract class AbstractDMSPicker implements ExternalDocumentServiceFacade<JSONObject> {

    private static Logger log = LoggerFactory.getLogger(AbstractDMSPicker.class);

    protected static final String PARAM_EXTERNAL_DOCS_FIELD_NAME = "example.external.docs.field.name";
    protected static final String PRODUCT_TYPE = "dms.productype";
    protected static final String MULTIPLE_SELECTION = "selection.mode";

    private static final String TYPE_AWARD = "award";
    private static final String TYPE_CATEGORY = "category";
    private static final String TYPE_FACILITY = "facility";

    protected JSONArray docArray;
    protected MetadataSearch metadata;

    public AbstractDMSPicker(String type) {
        try {
            docArray = new JSONArray();
            DataService dsi = new DataServiceImpl();
            metadata = dsi.getMetatdata();

            docArray.addAll(JSONArray.fromObject(deserialize(type,
                    request(type,null, productTypesForPSR(type)))));

        } catch (Exception e) {
            log.error("Failed to load JSON data.", e);
        }
    }

    @Override
    public void setFieldExternalDocuments(ExternalDocumentServiceContext context, ExternalDocumentCollection<JSONObject> exdocs) {
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
    public String getDocumentDescription(ExternalDocumentServiceContext context, JSONObject doc, Locale preferredLocale) {
        if (doc != null && doc.has("name")) {
            return doc.getString("name");
        }

        return "";
    }

    @Override
    public String getDocumentIconLink(ExternalDocumentServiceContext context, JSONObject doc, Locale preferredLocale) {
        return "";
    }

    /**
     * Get the DMS metadata by the DMS id.
     *
     * @param id
     * @return
     */
    protected JSONObject findDocumentById(final String id) {
        for (int i = 0; i < docArray.size(); i++) {
            JSONObject doc = docArray.getJSONObject(i);

            if (StringUtils.equals(id, doc.getString("id"))) {
                return doc;
            }
        }

        return null;
    }

    /**
     * Method to retrieve only the metadata (categories and facilities) for Product types used in the product search
     *
     * @return List<ProductTypes>
     */

    protected List<String> productTypesForPSR(String productType){
        List<String> searchTypes = new ArrayList<>();
        if (!productType.equalsIgnoreCase(TYPE_AWARD)) {
            searchTypes.add(ProductTypes.ACCOMMODATION.getId());
            searchTypes.add(ProductTypes.ACTIVITY.getId());
            searchTypes.add(ProductTypes.ATTRACTION.getId());
            searchTypes.add(ProductTypes.EVENT.getId());
            searchTypes.add(ProductTypes.SHOPPING.getId());
            searchTypes.add(ProductTypes.FOOD_DRINK.getId());
        }
        return searchTypes;
    }


    @Override
    public abstract  ExternalDocumentCollection<JSONObject> searchExternalDocuments(ExternalDocumentServiceContext externalDocumentServiceContext, String s);


    /**
     * Request the the resource taking into account the language.
     *
     * @param locale: Specific locale for the fragment or null if the locale is English (default locale)
     *
     * @return HTML fragment according to the type and the locale
     */
    protected static String request(String productType, Locale locale, List<String> productTypeParameterList) throws IOException {
        String parameters ="";
        if (productTypeParameterList!=null){
            for (String productTypeParameter: productTypeParameterList) {
                if (parameters.isEmpty()) {
                    parameters = "?prodtypes=" + productTypeParameter;
                } else {
                    parameters = parameters + "&prodtypes=" + productTypeParameter;
                }
            }
        }

        if (locale == null){
            return  CommonUtils.request(String.format("%s/data/meta/"+productType+"/list"+parameters, com.visitscotland.brmx.utils.Properties.VS_DMS_PRODUCTS));
        } else {
            return  CommonUtils.request(String.format("%s/data/meta/"+productType+"/list"+parameters+"&locale=%s", Properties.VS_DMS_PRODUCTS, locale.getLanguage()));
        }
    }


    protected static Collection<?> deserialize(String type,String data) throws IOException {
        ObjectMapper jsonMapper = new ObjectMapper();
        JsonNode dataObject = jsonMapper.readTree(data);

        switch(type) {
            case TYPE_AWARD:
                List<Award> awards = new ArrayList<>();
                for (JsonNode elm: dataObject.get("data")){
                    Award category = new Award(elm.get("id").toString(),elm.get("name").toString(),null,null);
                    awards.add(category);
                }
                return awards;
            case TYPE_FACILITY:
                Set<Facility> facilities = new TreeSet<>();
                for (JsonNode elm: dataObject.get("data")){
                    Facility facility = new Facility(elm.get("id").toString(),elm.get("name").toString(),null);
                    facilities.add(facility);
                }
                return facilities;
            case TYPE_CATEGORY:
                Set<Category> categories = new TreeSet<>();
                for (JsonNode elm: dataObject.get("data")){
                    Category category = new Category(elm.get("id").toString(),elm.get("name").toString());
                    categories.add(category);
                }
                return categories;
            }
            return null;
    }


}