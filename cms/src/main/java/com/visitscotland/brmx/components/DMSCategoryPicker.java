package com.visitscotland.brmx.components;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.visitscotland.api.DataService;
import com.visitscotland.api.DataServiceImpl;
import com.visitscotland.dataobjects.CategoryGroup;
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
import java.io.IOException;
import java.io.Reader;
import java.util.*;


public class DMSCategoryPicker implements ExternalDocumentServiceFacade<JSONObject> {

    /**
     * Plugin parameter name for physical document field name (JCR property name).
     */
    public static final String PARAM_EXTERNAL_DOCS_FIELD_NAME = "example.external.docs.field.name";
    public static final String PRODUCT_TYPE = "dms.productype";
    public static final String MULTIPLE_SELECTION = "selection.mode";

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DMSCategoryPicker.class);

    private JSONArray docArray;

    private MetadataSearch metadata;

    public DMSCategoryPicker() {
        try {
            docArray = new JSONArray();

            DataService dsi = new DataServiceImpl();
            metadata = dsi.getMetatdata();
            List<ProductTypes> searchTypes = new ArrayList<>();
            searchTypes.add(ProductTypes.ACCOMMODATION);
            searchTypes.add(ProductTypes.ACTIVITY);
            searchTypes.add(ProductTypes.ATTRACTION);
            searchTypes.add(ProductTypes.EVENT);
            searchTypes.add(ProductTypes.SHOPPING);
            searchTypes.add(ProductTypes.FOOD_DRINK);

            for (ProductTypes productTypes : searchTypes) {
                List<CategoryGroup> catGroups = metadata.getCategoryGroupsForType(productTypes);
                if (catGroups != null) {
                    for (CategoryGroup cat : catGroups) {
                        docArray.addAll(JSONArray.fromObject(cat.getCategories()));
                    }
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

            String productType= context.getContextModel().getNode().getProperty(fieldName).getValue().getString();
            List<String> items = Arrays.asList(productType.split("\\s*,\\s*"));
            List<CategoryGroup> catGroups = new ArrayList<>();

            for (String productT : items){
                catGroups.addAll( metadata.getCategoryGroupsForType(ProductTypes.byId(productT)));
            }

            if (catGroups != null) {
                JSONArray subCategory  = new JSONArray();
                for (CategoryGroup cat : catGroups) {
                    subCategory.addAll(JSONArray.fromObject(cat.getCategories()));
                }
                docArray = subCategory;
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
      /*  if (doc != null && doc.has("name")) {
            return doc.getString("name");
        }*/

        return "";
    }

    private JSONObject findDocumentById(final String id) {
        for (int i = 0; i < docArray.size(); i++) {
            JSONObject doc = docArray.getJSONObject(i);

            if (StringUtils.equals(id, doc.getString("id"))) {
                return doc;
            }
        }

        return null;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

}