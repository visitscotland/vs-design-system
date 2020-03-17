package com.visitscotland.brmx.utils;

import com.visitscotland.brmx.beans.ProductsSearch;
import com.visitscotland.brmx.beans.dms.LocationObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * @author jose.calcines
 */
public class ProductSearchBuilder {

    //TODO The only allowed type is accomodation, define rest of them and refactor enum
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

    static final String PRODUCT_SEARCH = "/info/%s/search-results?&prodtypes=%s";
    static final String NO_AVAILABILITY_PARAM = "avail=off";

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
        if  (url!=null){
            throw new RuntimeException("A different query has been defined");
        } else if (ProductType.fromValue(type) == null){
            throw new RuntimeException(String.format("The type %s has not been defined", type));
        }
        ProductType pt = ProductType.fromValue(type);

        //TODO: dejar de ser un chungo y hacer las cosas bien que ya tenemos una edad
        //TODO test;
        url =  String.format(PRODUCT_SEARCH, pt.path, type);

        if (pt == ProductType.ACCOMMODATION){
            url += "&" + NO_AVAILABILITY_PARAM;
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
     * Builds the URL
     * @return
     */
    public String build(){
        if (url == null){
            throw new RuntimeException("the main query has not been defined");
        }

        String compose = host + url;

        if (location != null) {
            LocationObject loc = LocationLoader.getLocation(location, locale);

            compose = addParam(compose, "POLYGON".equals(loc.getType())?LOCATION_POLYGON: LOCATION_PLACE, loc.getId());
            compose = addParam(compose, PROXIMITY_LOCATION, proximity.toString());

            try {
                compose = addParam(compose, LOCATION_NAME, URLEncoder.encode(loc.getName(), "UTF-8"));
            } catch (UnsupportedEncodingException e){
                //TODO logger.error("Unexpected UnsupportedEncodingException for UTF-8",e)
            }
        } else if (latitude != null && longitude != null){
            compose = addParam(compose, LATITUDE, latitude.toString());
            compose = addParam(compose, LONGITUDE, longitude.toString());
            compose = addParam(compose, PROXIMITY_PIN, proximity.toString());
        }


        compose = addParam(compose, CATEGORY, categories);
        compose = addParam(compose, AWARD, awards);
        compose = addParam(compose, FACILITY, facilities);
        compose = addParam(compose, RATING, ratings);

        compose = addParam(compose, ORDER, order.value);


        return compose;
    }

    private String addParam(String compose, String param, String var){
        if (var == null) {
            return compose;
        } else if (compose.endsWith("?")){
            return compose + param + "=" + var;
        } else {
            return compose + "&" + param + "=" + var;
        }
    }

    private String addParam(String compose, String param, Collection<String> vars){
        if (vars == null || vars.size() == 0) {
            return compose;
        } else {
            String aux = compose;

            for (String var: vars){
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
