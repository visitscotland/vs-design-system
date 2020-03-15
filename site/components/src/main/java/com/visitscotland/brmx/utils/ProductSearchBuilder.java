package com.visitscotland.brmx.utils;

import com.visitscotland.brmx.beans.ProductsSearch;

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

        static ProductType fromType(String type){
            for (ProductType pt: values()){
                if (pt.type.equals(type)){
                    return pt;
                }
            }
            return null;
        }
    }

    static final String PRODUCT_SEARCH = "/info/%s/search-results?&prodtypes=%s";

    static final String LOCATION = "locplace";
    static final String CATEGORY = "cat";
    static final String AWARD = "src_awards__0";
    static final String FACILITY = "fac_id";
    static final String RATING = "grade";
    static final String LATITUDE = "lat";
    static final String LONGITUDE = "lng";
    static final String PROXIMITY_LOCATION = "locprox";
    static final String PROXIMITY_PIN = "areaproxdist";

    private String host = Properties.VS_DMS_SERVICE;

    private Integer proximity = 0;
    private String url;
    private String location;
    private Double longitude;
    private Double latitude;
    private Locale locale;

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

    public ProductSearchBuilder createProductSearch(String type){
        if  (url!=null){
            throw new RuntimeException("A different query has been defined");
        } else if (ProductType.fromType(type) == null){
            throw new RuntimeException(String.format("The type %s has not been defined", type));
        }

        url =  String.format(PRODUCT_SEARCH, ProductType.fromType(type).path, type);
        return this;
    }

    public ProductSearchBuilder createProductSearch(ProductsSearch ps){
        if (ps.getProductType() != null) {
            ProductSearchBuilder psb = new ProductSearchBuilder().createProductSearch(ps.getProductType());
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
            compose = addParam(compose, LOCATION, LocationLoader.getLocation(location, locale).getName());
            compose = addParam(compose, PROXIMITY_LOCATION, proximity.toString());
        } else if (latitude != null && longitude != null){
            compose = addParam(compose, LATITUDE, latitude.toString());
            compose = addParam(compose, LONGITUDE, longitude.toString());
            compose = addParam(compose, PROXIMITY_PIN, proximity.toString());
        } else {
            //TODO: Should we default to Scotland?
            throw new RuntimeException("No location defined");
        }


        compose = addParam(compose, CATEGORY, categories);
        compose = addParam(compose, AWARD, awards);
        compose = addParam(compose, FACILITY, facilities);
        compose = addParam(compose, RATING, ratings);


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
