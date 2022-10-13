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

import java.util.List;
import java.util.Locale;

import static com.visitscotland.brxm.dms.DMSConstants.DMSProduct.*;
import static org.hippoecm.hst.content.beans.query.builder.ConstraintBuilder.constraint;

@Component
public class MapFactory {

    static final String GEOMETRY = "geometry";
    static final String LABEL = "label";
    static final String FEATURE_PLACES_LABEL = "map.feature-default-title";

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

    public MapsModule getModule(HstRequest request, MapModule mapModuleDocument , Page page) {
        MapsModule module = new MapsModule();

        module.setTitle(mapModuleDocument.getTitle());
        module.setIntroduction(mapModuleDocument.getCopy());
        module.setTabTitle(mapModuleDocument.getTabTitle());

        JsonObject featureCollectionGeoJson = new JsonObject();
        featureCollectionGeoJson.addProperty("type", "FeatureCollection");
        JsonArray features = new JsonArray();
        JsonArray mapControlsArray = new JsonArray();
        JsonArray keys = new JsonArray();
        if (!(page instanceof General)) {
            buildMapDestinationPages(request, mapModuleDocument, module, keys, features);
        } else {
            buildMapGeneralPages(request, mapModuleDocument, module, keys, features);
        }
        //add first Json for map controls (include filters) to the module
        JsonObject mapControlFilters = new JsonObject();
        mapControlFilters.add("filters", keys);
        mapControlsArray.add(mapControlFilters);
        JsonObject jsonParameters = new JsonObject();
        jsonParameters.add("map", mapControlsArray);
        module.setFilters(jsonParameters);

        //add second json (geoJson) to the module
        featureCollectionGeoJson.add("features", features);
        module.setGeoJson(featureCollectionGeoJson);
        module.setHippoBean(mapModuleDocument);
        return module;
    }

    private void buildMapGeneralPages (HstRequest request, MapModule mapModuleDocument, MapsModule module, JsonArray keys, JsonArray features){
        for (String taxonomy : mapModuleDocument.getKeys()) {
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
                        features.add(getMapDocuments(request.getLocale(), child, module, feature, it));
                    }
                }
            }
            if (mapModuleDocument.getFeaturedPlacesItem() != null) {
                addFeaturePlacesNode(module, mapModuleDocument.getCategories(), request.getLocale() , keys, features);
            }
        }
    }

    private void buildMapDestinationPages (HstRequest request, MapModule mapModuleDocument, MapsModule module, JsonArray keys , JsonArray features){
        //TODO differenciate between cities and regions and bring the right filters for each of them.
        if (mapModuleDocument.getFeaturedPlacesItem() != null) {
            addFeaturePlacesNode(module, mapModuleDocument.getCategories(), request.getLocale() , keys, features);
        }
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
            linkNode.addProperty(LABEL, link.getLabel());
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
        filter.addProperty(LABEL, child.getInfo(locale).getName());

        return filter;
    }

    private void addFeaturePlacesNode(MapsModule module, List<MapCategory> categories, Locale locale , JsonArray keys, JsonArray features ) {
        for (MapCategory featuredPlaces : categories) {
            JsonObject filter = new JsonObject();
            filter.addProperty("id", FEATURE_PLACES_LABEL);
            if (Contract.isEmpty(featuredPlaces.getTitle())) {
                filter.addProperty(LABEL, bundle.getResourceBundle("map", FEATURE_PLACES_LABEL, locale));
            } else {
                filter.addProperty(LABEL, featuredPlaces.getTitle());
            }
            keys.add(filter);
            for(HippoBean link:featuredPlaces.getMapPins()){
                JsonObject feat = new JsonObject();
                if (link instanceof HippoMirror) {
                    if (((HippoMirror) link).getReferencedBean() instanceof Destination){
                        Destination destination = (Destination)((HippoMirror) link).getReferencedBean();
                        buildPageNode(locale, filter, module,destination,feat);
                    }else{
                        buildStopNode(locale,filter,module, (Stop) ((HippoMirror) link).getReferencedBean(), feat);
                    }

                }else{
                    SpecialLinkCoordinates linkCoordinates = ((SpecialLinkCoordinates) link);
                    Page otherPage = (Page)(linkCoordinates).getLink();
                    buildPageNode(locale, filter, module,otherPage,feat);
                    feat.add(GEOMETRY, getGeometryNode(((SpecialLinkCoordinates)link).getCoordinates().getLatitude(), ((SpecialLinkCoordinates)link).getCoordinates().getLongitude()));
                }
                features.add(feat);
            }
        }
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
     * @param locale the locale/language
     * @param category the category/taxonomy selected
     * @param module the map module
     * @param feature jsonobject to keep adding destinations or stops
     * @param it iterator to iterate the list of destinations or stops
     * @return JsonObject with the right format to be sent to FEDs
     */
    private JsonObject getMapDocuments(Locale locale, Category category, MapsModule module, JsonObject feature, HippoBeanIterator it){
        //find all the documents with a taxonomy
        final HippoBean bean = it.nextHippoBean();
        if (bean != null) {
            feature.addProperty("type", "Feature");
            if (bean instanceof Destination) {
                buildPageNode(locale, getCategoryNode(category, locale), module,((Destination) bean), feature);
            } else {
                buildStopNode(locale,getCategoryNode(category, locale),module, ((Stop) bean), feature);
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
    private void buildStopNode(Locale locale, JsonObject category, MapsModule module,Stop stop, JsonObject feature){
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
               image, category,linkService.createFindOutMoreLink(module, locale, item),stop.getCanonicalUUID()));
        feature.add(GEOMETRY, getGeometryNode(latitude, longitude));
    }

    /**
     * Method to build nodes when the document selected is a Destination
     *
     * @param locale language of the destination
     * @param category the category/taxonomy for the destination
     * @param module map module
     * @param page the destination or other pages
     * @param feature json to build the features and geometry nodes
     */
    private void buildPageNode(Locale locale, JsonObject category, MapsModule module,Page page, JsonObject feature){
        feature.add("properties", getPropertyNode(page.getTitle(), page.getTeaser(),
                imageFactory.createImage(page.getImage(), module, locale), category,
                linkService.createFindOutMoreLink(module, locale, page), page.getCanonicalUUID()));
        if (page instanceof Destination){
            LocationObject location = locationLoader.getLocation(((Destination)page).getLocation(), Locale.UK);
            feature.add(GEOMETRY, getGeometryNode(location.getLatitude(), location.getLongitude()));
        }
    }
}

