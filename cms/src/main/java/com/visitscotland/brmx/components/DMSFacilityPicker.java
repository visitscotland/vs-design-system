package com.visitscotland.brmx.components;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.visitscotland.api.DataService;
import com.visitscotland.api.DataServiceImpl;
import com.visitscotland.dataobjects.CategoryGroup;
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
import java.io.IOException;
import java.io.Reader;
import java.util.*;

// TODO: refactor
public class DMSFacilityPicker extends AbstractDMSPicker {

    /**
     * Plugin parameter name for physical document field name (JCR property name).
     */

    private static final long serialVersionUID = 1L;

    private static Logger log = LoggerFactory.getLogger(DMSFacilityPicker.class);


    public DMSFacilityPicker() {
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
                List<FacilityGroup> facGroups = metadata.getFacilityGroupsForType(productTypes);
                if (facGroups != null) {
                    for (FacilityGroup cat : facGroups) {
                        docArray.addAll(JSONArray.fromObject(cat.getFacilities()));
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

}