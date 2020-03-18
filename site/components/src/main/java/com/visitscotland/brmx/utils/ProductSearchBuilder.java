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

    //TODO The only allowed type is accommodation, define rest of them and refactor enum
    enum ProductType {
        ACCOMMODATION("accommodation", "acco"),
        RESTAURANTS("food-drink", "cate");

        final String path;
        final String type;

        ProductType (String path, String type){
            this.path = path;
            this.type = type;
        }

        static ProductType fromValue(String type){
            for (ProductType pt: values()){
                if (pt.type.equals(type)){
                    return pt;
                }
            }
            return null;
        }
    }

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

    static final String PRODUCT_SEARCH = "%s/info/%s/search-results?&prodtypes=%s";
    static final String AVAILABILITY = "avail=off";

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

    private String host = Properties.VS_DMS_SERVICE;

    private ProductType type;
    private Integer proximity = 10;
    private String url;
    private String location;
    private Double longitude;
    private Double latitude;
    private Locale locale;
    private Order order = Order.NONE;

    private Set<String> categories = new TreeSet<>();
    private Set<String> awards = new TreeSet<>();
    private Set<String> facilities = new TreeSet<>();
    private Set<String> ratings = new TreeSet<>();


    public ProductSearchBuilder(){

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

    public ProductSearchBuilder productType(String type){
        ProductType pt = ProductType.fromValue(type);
        if (ProductType.fromValue(type) != null){
            this.type = pt;
        }

        return this;
    }

    public ProductSearchBuilder productType(ProductsSearch ps){
        if (ps.getProductType() != null) {
            ProductSearchBuilder psb = new ProductSearchBuilder();
            psb.productType(ps.getProductType());
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
        if (validNumber(proximity)){
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
        if (type == null){
            throw new RuntimeException(String.format("The type %s has not been defined", type));
        }

        String compose =  String.format(PRODUCT_SEARCH, host==null?"":host, type.path, type.type);

        //Accommotions MUST deactivate avalavility search
        if (type == ProductType.ACCOMMODATION){
            compose = addParam(compose, AVAILABILITY, "off");
        }

        //The list of parameters is different if a location is provided from latitude and longitude
        if (location != null) {
            LocationObject loc = LocationLoader.getLocation(location, locale);

            compose = addParam(compose, "POLYGON".equals(loc.getType())?LOCATION_POLYGON: LOCATION_PLACE, loc.getId());
            compose = addParam(compose, PROXIMITY_LOCATION, proximity.toString());

            try {
                compose = addParam(compose, LOCATION_NAME, URLEncoder.encode(loc.getName(), "UTF-8"));
            } catch (UnsupportedEncodingException e){
                logger.error("Unexpected UnsupportedEncodingException for UTF-8",e);
            }
        } else if (latitude != null && longitude != null){
            compose = addParam(compose, LATITUDE, latitude.toString());
            compose = addParam(compose, LONGITUDE, longitude.toString());
            compose = addParam(compose, PROXIMITY_PIN, proximity.toString());
        }

        //Add categories, awards, facilities and rating
        compose = addParam(compose, CATEGORY, categories);
        compose = addParam(compose, AWARD, awards);
        compose = addParam(compose, FACILITY, facilities);
        compose = addParam(compose, RATING, ratings);

        //Sort the list
        compose = addParam(compose, ORDER, order.value);

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
    private String addParam(String url, String param, String value){
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
    private String addParam(String url, String param, Collection<String> values){
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
