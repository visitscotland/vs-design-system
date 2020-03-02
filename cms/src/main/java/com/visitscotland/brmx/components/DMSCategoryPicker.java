package com.visitscotland.brmx.components;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.visitscotland.api.DataService;
import com.visitscotland.api.DataServiceImpl;
import com.visitscotland.dataobjects.CategoryGroup;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentCollection;
import org.onehippo.forge.exdocpicker.api.ExternalDocumentServiceContext;
import org.onehippo.forge.exdocpicker.impl.SimpleExternalDocumentCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vs.ase.dms.ProductTypes;

import javax.jcr.RepositoryException;
import java.util.*;

public class DMSCategoryPicker extends AbstractDMSPicker{

    /**
     * Plugin parameter name for physical document field name (JCR property name).
     */
    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DMSCategoryPicker.class);


    public DMSCategoryPicker() {
        try {
            docArray = new JSONArray();
            // TODO: create an exposed endpoint to get categories (similar to locations)
            DataService dsi = new DataServiceImpl();
            metadata = dsi.getMetatdata();
            List<ProductTypes> searchTypes = new ArrayList<>();
            // TODO: define which productTypes content team wants
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
            List<String> productTypes = Arrays.asList(context.getContextModel().getNode().getProperty(fieldName).getValue().getString().split("\\s*,\\s*"));
            if (!productTypes.get(0).isEmpty()) {
                List<CategoryGroup> catGroups = new ArrayList<>();

                for (String productType : productTypes) {
                    catGroups.addAll(metadata.getCategoryGroupsForType(ProductTypes.byId(productType)));
                }

                if (catGroups != null) {
                    JSONArray subCategory = new JSONArray();
                    for (CategoryGroup cat : catGroups) {
                        subCategory.addAll(JSONArray.fromObject(cat.getCategories()));
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



}