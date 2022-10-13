package com.visitscotland.brxm.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.model.FlatImage;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.MapsModule;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.content.beans.query.HstQuery;
import org.hippoecm.hst.content.beans.query.HstQueryResult;
import org.hippoecm.hst.content.beans.query.builder.HstQueryBuilder;
import org.hippoecm.hst.content.beans.query.exceptions.QueryException;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.content.beans.standard.HippoBeanIterator;
import org.hippoecm.hst.content.beans.standard.HippoMirror;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.request.HstRequestContext;
import org.hippoecm.hst.site.HstServices;
import org.onehippo.taxonomy.api.Category;
import org.onehippo.taxonomy.api.Taxonomy;
import org.onehippo.taxonomy.api.TaxonomyManager;
import org.springframework.stereotype.Component;

import java.util.Locale;

import static com.visitscotland.brxm.dms.DMSConstants.DMSProduct.*;
import static org.hippoecm.hst.content.beans.query.builder.ConstraintBuilder.constraint;

@Component
public class MapFactory {

    private final LinkService linkService;
    private final LocationLoader locationLoader;
    private final DMSDataService dmsDataService;
    private final ImageFactory imageFactory;
    private final ResourceBundleService bundle;

    public MapFactory(LinkService linkService, LocationLoader locationLoader, DMSDataService dmsDataService, ImageFactory imageFactory , ResourceBundleService bundle) {

        this.linkService = linkService;
        this.locationLoader = locationLoader;
        this.dmsDataService = dmsDataService;
        this.imageFactory = imageFactory;
        this.bundle = bundle;
    }

    public MapsModule getModule(HstRequest request, MapModule doc , Page page) {
        MapsModule module = new MapsModule();

        module.setTitle(doc.getTitle());
        module.setIntroduction(doc.getCopy());
        module.setTabTitle(doc.getTabTitle());
        module.setFeauredPlaces(doc.getCategories());

        JsonObject featureCollection = new JsonObject();
        featureCollection.addProperty("type", "FeatureCollection");
        JsonArray features = new JsonArray();
        JsonArray keysMain = new JsonArray();
        JsonObject jsonParameters = new JsonObject();
        if (page instanceof General) {
            for (String taxonomy : doc.getKeys()) {
                JsonObject jsonFilters = new JsonObject();
                JsonArray keys = new JsonArray();
                //get all the Taxonomy information
                TaxonomyManager taxonomyManager = HstServices.getComponentManager().getComponent("TaxonomyManager", "org.onehippo.taxonomy.contentbean");
                Taxonomy vsTaxonomyTree = taxonomyManager.getTaxonomies().getTaxonomy("Visitscotland-categories");
                for (Category child : vsTaxonomyTree.getCategoryByKey(taxonomy).getChildren()) {
                    keys.add(getFilterNode(child, request.getLocale()));
                    //find all the documents with a taxonomy/category
                    HstQueryResult result = getMapDocumentsByTaxonomy(request, child);
                    if (result != null) {
                        final HippoBeanIterator it = result.getHippoBeans();

                        while (it.hasNext()) {
                            JsonObject feature = new JsonObject();
                            features.add(getMapDocuments(request, child, module, feature, it));
                        }
                    }
                }
                //TODO implement featured places and move it to a different method
                if (doc.getFeaturedPlacesItem() != null) {
                    for (MapCategory featuredPlaces : doc.getCategories()) {
                        JsonObject filter = new JsonObject();
                        filter.addProperty("id", "map.feature-default-title");
                        if (Contract.isEmpty(featuredPlaces.getTitle())) {
                            filter.addProperty("label", bundle.getResourceBundle("map", "map.feature-default-title", request.getLocale()));
                        } else {
                            filter.addProperty("label", featuredPlaces.getTitle());
                        }
                        keys.add(filter);
                        for(HippoBean link:featuredPlaces.getMapPins()) {
                            JsonObject filter2 = new JsonObject();
                            if (link instanceof HippoMirror) {
                                //filter.addProperty("title", featuredPlaces.getTitle());
                                features.add(filter2);
                            }
                        }
                    }
                }

                jsonFilters.add("filters", keys);
                keysMain.add(jsonFilters);
            }
        }else{
            //TODO Maps for Cities and Region pages
        }

        featureCollection.add("features", features);
        jsonParameters.add("map", keysMain);
        module.setFilters(jsonParameters);
        module.setGeoJson(featureCollection);
        module.setHippoBean(doc);
        return module;
    }


    /** Method to build the property section for the GeoJson file generated for maps
     *
     * @param title Mapcard title
     * @param description Mapcard description
     * @param image Mapcard image
     * @param category Mapcard category (id and label)
     * @param link Mapcard link to the page
     * @return JsonObject with the right format to be consumed by the front end team
     */
    private JsonObject getPropertyNode(String title, String description, FlatImage image, JsonObject category, FlatLink link, String id) {
        JsonObject properties = new JsonObject();
        properties.add("category", category);
        properties.addProperty("id", id);
        properties.addProperty("title", title);
        properties.addProperty("description", description);
        if (image != null) {
            properties.addProperty("image", image.getCmsImage()!=null? image.getCmsImage().getOriginal().getPath() : image.getExternalImage());
        }

        if (link != null) {
            JsonObject linkNode = new JsonObject();
            linkNode.addProperty("label", link.getLabel());
            linkNode.addProperty("link", link.getLink());
            linkNode.addProperty("type", link.getType().name());
            properties.add("link", linkNode);
        }


        return properties;
    }

    /**
     * Method to build the geometry node for the GeoJson file generated for maps
     * @param latitude latitude value for the pin/mapcard
     * @param longitude longitude value for the pin/mapcard
     * @return JsonObject with the right format to be consumed by the front end team
     */
    private JsonObject getGeometryNode(Double latitude, Double longitude) {
        JsonObject geometry = new JsonObject();
        JsonArray coordinates = new JsonArray();
        coordinates.add(longitude);
        coordinates.add(latitude);
        geometry.addProperty("type", "Point");
        geometry.add("coordinates", coordinates);

        return geometry;
    }

    private JsonObject getCategoryNode(Category child, Locale locale) {
        JsonObject filter = new JsonObject();
        filter.addProperty("id", child.getKey());
        filter.addProperty("label", child.getInfo(locale).getName());

        return filter;
    }

    /**
     * Method to build a normal Json with parameters for the maps, in particular for the filters to use in the map
     * @param child Category or taxonomy to use as a filter, adding id and label
     * @param locale locale to bring the label in the right language
     * @return JsonObject with the filters to be used
     */
    private JsonObject getFilterNode(Category child, Locale locale) {
        JsonObject filter = this.getCategoryNode(child,locale);
        if (!child.getChildren().isEmpty()){
            JsonArray childrenArray = new JsonArray();
            for (Category children : child.getChildren()) {
                childrenArray.add(this.getCategoryNode(children, locale));
            }
            filter.add("subCategory",childrenArray);
        }


        return filter;
    }

    /**
     * Get all the Destinations and stops categorised with the taxonomy wanted in alphabetic order
     *
     * @param request the request
     * @param category the category or taxonomy wanted
     * @return all the destinations and stop with that category selected
     */
    private HstQueryResult getMapDocumentsByTaxonomy(HstRequest request, Category category) {
        HstRequestContext requestContext = request.getRequestContext();
        HippoBean scope = requestContext.getSiteContentBaseBean();
        HstQuery hstQuery = HstQueryBuilder.create(scope)
                .ofTypes(Destination.class, Stop.class)
                .where(constraint("@hippotaxonomy:keys").contains(category.getKey())).orderByAscending("visitscotland:title").build();

        try {
            return hstQuery.execute();
        } catch (QueryException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Method that build properties and geometry nodes for the GeoJson file to be consumed by feds
     * for each destination and stop with the category/taxonomy selected
     *
     * @param request the request
     * @param category the category/taxonomy selected
     * @param module the map module
     * @param feature jsonobject to keep adding destinations or stops
     * @param it iterator to iterate the list of destinations or stops
     * @return JsonObject with the right format to be sent to FEDs
     */
    private JsonObject getMapDocuments(HstRequest request, Category category, MapsModule module, JsonObject feature, HippoBeanIterator it){
        //find all the documents with a taxonomy
        final HippoBean bean = it.nextHippoBean();
        if (bean != null) {
            feature.addProperty("type", "Feature");
            if (bean instanceof Destination) {
                Destination destination = ((Destination) bean);
                feature.add("properties", getPropertyNode(destination.getTitle(), destination.getTeaser(),
                        imageFactory.createImage(destination.getImage(), module, request.getLocale()), getCategoryNode(category, request.getLocale()),
                        linkService.createFindOutMoreLink(module, request.getLocale(), destination), destination.getCanonicalUUID()));

                LocationObject location = locationLoader.getLocation(destination.getLocation(), Locale.UK);
                feature.add("geometry", getGeometryNode(location.getLatitude(), location.getLongitude()));
            } else {
               buildStopNode(request.getLocale(),category,module, ((Stop) bean), feature);
            }
        }
        return feature;
    }

    /**
     * Method to build nodes when the document selected is a Stop
     *
     * @param locale locale to bring labels in the right language
     * @param category category or taxonomy for the mapcard (id and label)
     * @param module Mapsmodule needed for images and links
     * @param stop stop document information
     * @param feature JsonObject to add the Stop information
     */
    private void buildStopNode(Locale locale, Category category, MapsModule module,Stop stop, JsonObject feature){
        Double latitude = null;
        Double longitude = null;

        HippoBean item = stop.getStopItem();
        FlatImage image = imageFactory.createImage(stop.getImage(), module, locale);
        if (item instanceof DMSLink) {
            JsonNode dmsNode = dmsDataService.productCard(((DMSLink) item).getProduct(), Locale.UK);
            if (stop.getImage() == null && dmsNode.has(IMAGE)) {
               image = imageFactory.createImage(dmsNode, module, locale);
            }
            if (dmsNode.has(LATITUDE) && dmsNode.has(LONGITUDE)) {
                latitude = dmsNode.get(LATITUDE).asDouble();
                longitude = dmsNode.get(LONGITUDE).asDouble();
            }
        } else if (item instanceof ItineraryExternalLink) {
            latitude = ((ItineraryExternalLink) item).getCoordinates().getLatitude();
            longitude = ((ItineraryExternalLink) item).getCoordinates().getLongitude();
        }
        feature.add("properties", getPropertyNode(stop.getTitle(),stop.getDescription().getContent(),
               image, getCategoryNode(category, locale),linkService.createFindOutMoreLink(module, locale, item),stop.getCanonicalUUID()));
        feature.add("geometry", getGeometryNode(latitude, longitude));
    }
}

