package com.visitscotland.brmx.utils;

import com.visitscotland.brmx.beans.ProductsSearch;
import com.visitscotland.brmx.beans.dms.LocationObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author jose.calcines
 */
public class ProductSearchBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ProductSearchBuilder.class.getName());

    enum Order {
        DISTANCE("proximityAsc"), NONE(null);

        final String value;

        Order  (String value){
            this.value = value;
        }

        static Order fromValue(String value){
            try {
                 Order order = valueOf(value);
                 if (order != null){
                     return order;
                 }
            } catch (Exception e){
                //logger.warn ("Incorrect value for Order + ", value)
            }
            return Order.NONE;
        }
    }

    static final String PRODUCT_SEARCH = "%s/info/%s/search-results?";
    static final String AVAILABILITY = "avail";
    static final Integer DEFAULT_PROXIMITY = 10;

    static final String PRODUCT_TYPE = "prodtypes";
    static final String LOCATION_NAME = "loc";
    static final String LOCATION_PLACE = "locplace";
    static final String LOCATION_POLYGON = "locpoly";
    static final String CATEGORY = "cat";
    static final String AWARD = "src_awards__0";
    static final String FACILITY = "fac_id";
    static final String RATING = "grade";
    static final String LATITUDE = "lat";
    static final String LONGITUDE = "lng";
    static final String PROXIMITY_LOCATION = "locprox";
    static final String PROXIMITY_PIN = "areaproxdist";
    static final String ORDER = "order";

    //TODO This path should come from DMS?
    static final String PATH_SEE_DO = "see-do";
    static final String PATH_ACCOMMODATION = "accommodation";
    static final String PATH_FOOD_DRINK = "food-drink";
    static final String PATH_EVENTS = "events";

    private String host = Properties.VS_DMS_SERVICE;

    private String productTypes;
    private String path;
    private Integer proximity;
    private String location;
    private Double longitude;
    private Double latitude;
    private Locale locale;
    private Order order;

    private Set<String> categories = new TreeSet<>();
    private Set<String> awards = new TreeSet<>();
    private Set<String> facilities = new TreeSet<>();
    private Set<String> ratings = new TreeSet<>();

    public ProductSearchBuilder(){
        this.proximity = DEFAULT_PROXIMITY;
        this.order = Order.NONE;
    }

    /**
     * Allows new instances from FreeMarker
     *
     * @return
     */
    public static ProductSearchBuilder newInstance(){
        return new ProductSearchBuilder();
    }

    public ProductSearchBuilder locale(Locale locale){
        if (locale != null) {
            for (Locale loc : Properties.locales) {
                if (locale.equals(loc)){
                    this.locale = loc;
                    return this;
                }
            }
        }
        this.locale = null;
        return this;
    }

    public ProductSearchBuilder locale(String locale){
        if (locale != null) {
            locale(Locale.forLanguageTag(locale));
        }
        return this;
    }

    private void path(String types){
        if (types.equals("acco")) {
            path = PATH_ACCOMMODATION;
        } else if (types.equals("cate")) {
            path = PATH_FOOD_DRINK;
        } else if (types.equals("even")){
            path = PATH_EVENTS;
        } else {
            //Note: if category is not acti,attr,reta (in that other) the category "Thing to see and do" is not selected on the Product Search
            path = PATH_SEE_DO;
        }
    }

    public ProductSearchBuilder productTypes(String types){
        try {
            this.productTypes = URLEncoder.encode(types, "UTF-8");
            path(types);
        } catch (UnsupportedEncodingException e){
            logger.error(String.format("Unexpected UnsupportedEncodingException for UTF-8 with the types %s", types),e);
        }

        return this;
    }

    public ProductSearchBuilder productType(ProductsSearch ps){
        if (ps.getProductType() != null) {
            ProductSearchBuilder psb = new ProductSearchBuilder();
            psb.productTypes(ps.getProductType());
            psb.location(ps.getLocation());
            psb.category(ps.getDmsCategories());
            psb.facility(ps.getDmsFacilities());
            psb.award(ps.getDmsAwards());
            psb.rating(ps.getOfficialrating());

            return psb;
        }
        return null;
    }

    public ProductSearchBuilder category(String... categories){
        if (categories != null) {
            for (String category : categories) {
                if (valid(category)) {
                    this.categories.add(category);
                }
            }
        }
        return this;
    }

    public ProductSearchBuilder award(String... awards){
        if (awards != null) {
            for (String category : awards) {
                if (valid(category)) {
                    this.awards.add(category);
                }
            }
        }
        return this;
    }

    public ProductSearchBuilder facility(String... facilities){
        if (facilities != null) {
            for (String category : facilities) {
                if (valid(category)) {
                    this.facilities.add(category);
                }
            }
        }
        return this;
    }

    public ProductSearchBuilder rating(String... ratings){
        if (ratings != null) {
            for (String category : ratings) {
                if (valid(category)) {
                    this.ratings.add(category);
                }
            }
        }
        return this;
    }

    public ProductSearchBuilder location(String location){
        if (valid(location) && LocationLoader.getLocation(location, null) != null){
            this.location = location;
        }
        return this;
    }

    public ProductSearchBuilder proximity(Number proximity){
        if (validNumber(proximity) && proximity.intValue() > 0){
            this.proximity = proximity.intValue();
        }
        return this;
    }

    public ProductSearchBuilder coordinates(Number lat, Number lon){
        if (validNumber(lat) && validNumber(lon)){
            this.latitude = lat.doubleValue();
            this.longitude = lon.doubleValue();
        }
        return this;
    }

    //TODO test
    public ProductSearchBuilder sortBy(String order){
        this.order = Order.fromValue(order);
        return this;
    }

    private boolean valid(String s){
        return  s != null && s.trim().length() > 0 && !s.contains("&");
    }

    private boolean validNumber(Number s){
        return  s != null;
    }

    /**
     * Builds the URL based on the configuration defined.
     *
     * It will return an exception if
     *
     * @return
     */
    public String build(){
        if (productTypes == null){
            throw new RuntimeException(String.format("No types have been defined for this search"));
        }

        String compose =  String.format(PRODUCT_SEARCH, host==null?"":host, path);

        compose = addParams(compose, PRODUCT_TYPE, productTypes);
        //Accommotions MUST deactivate availavility search
        if (path.equals(PATH_ACCOMMODATION)) {
            compose = addParams(compose, AVAILABILITY, "off");
        }

        //The list of parameters is different if a location is provided from latitude and longitude
        if (location != null) {
            LocationObject loc = LocationLoader.getLocation(location, locale);

            compose = addParams(compose, "POLYGON".equals(loc.getType())?LOCATION_POLYGON: LOCATION_PLACE, loc.getId());
            compose = addParams(compose, PROXIMITY_LOCATION, proximity.toString());

            try {
                compose = addParams(compose, LOCATION_NAME, URLEncoder.encode(loc.getName(), "UTF-8"));
            } catch (UnsupportedEncodingException e){
                logger.error("Unexpected UnsupportedEncodingException for UTF-8",e);
            }
        } else if (latitude != null && longitude != null){
            compose = addParams(compose, LATITUDE, latitude.toString());
            compose = addParams(compose, LONGITUDE, longitude.toString());
            compose = addParams(compose, PROXIMITY_PIN, proximity.toString());
        }

        //Add categories, awards, facilities and rating
        compose = addParams(compose, CATEGORY, categories);
        compose = addParams(compose, AWARD, awards);
        compose = addParams(compose, FACILITY, facilities);
        compose = addParams(compose, RATING, ratings);

        //Sort the list
        compose = addParams(compose, ORDER, order.value);

        return compose;
    }

    /**
     * Add one parameter to a url, provided the parameter has a value
     *
     * @param url URL where the parameter will be added to
     * @param param name of the parameter to be added
     * @param value value of the parameter
     *
     * @return composed URL with the parameter
     */
    private String addParams(String url, String param, String value){
        if (value == null) {
            return url;
        } else if (url.endsWith("?")){
            return url + param + "=" + value;
        } else {
            return url + "&" + param + "=" + value;
        }
    }

    /**
     * Add a parameter with multiple values to a url, provided the parameter has at least one value
     *
     * @param url URL where the parameter will be added to
     * @param param name of the parameter to be added
     * @param values list of values for the parameter
     *
     * @return composed URL with the parameter
     */
    private String addParams(String url, String param, Collection<String> values){
        if (values == null || values.size() == 0) {
            return url;
        } else {
            String aux = url;

            for (String var: values){
                if (aux.endsWith("?")){
                    aux += param + "=" + var;
                } else {
                    aux += "&" + param + "=" + var;
                }
            }

            return aux;
        }
    }
}
