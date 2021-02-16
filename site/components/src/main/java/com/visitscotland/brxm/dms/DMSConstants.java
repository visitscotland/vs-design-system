package com.visitscotland.brxm.dms;

public class DMSConstants {

    private DMSConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String TYPE_SERVICES = "serv";
    public static final String TYPE_SEE_DO = "cate,acti,attr,reta";

    public static final String CAT_ICENTRE = "vics";

    public static final String AWARD_IKNOW = "qaiknowscotland";

    public static final String SORT_ALPHA = "alpha";

    /**
     * Unique constants for the Map Search functionallity.
     *
     * Note: This interface is marked by SonarQube as a Critical issue, however since those constant are translated
     * into parameters later on AND they really belong to the dms project, this is a perfectly reasonable use for a
     * constants interface.
     */
    public interface MapSearch {
        String PROPERTIES = "properties";
        String ID = "id";
        String NAME = "name";
    }

    /**
     * Note: This interface is marked by SonarQube as a Critical issue, however since those constant are translated
     * into parameters later on AND they really belong to the dms project, this is a perfectly reasonable use for a
     * constants interface.
     */
    public interface ProductSearch {
        String PRODUCT_TYPE_PARAM = "prodtypes";
        String LOCATION_NAME_PARAM = "loc";
        String LOCATION_PLACE_PARAM = "locplace";
        String LOCATION_POLYGON_PARAM = "locpoly";
        String CATEGORY_PARAM = "cat";
        String AWARD_PARAM = "src_awards__0";
        String FACILITY_PARAM = "fac_id";
        String RATING_PARAM = "grade";
        String LATITUDE_PARAM = "lat";
        String LONGITUDE_PARAM = "lng";
        String PROXIMITY_LOCATION_PARAM = "locprox";
        String PROXIMITY_PIN_PARAM = "areaproxdist";
        String ORDER_PARAM = "c";
        String AVAILABILITY = "avail";
    }

    /**
     * Note: This interface is marked by SonarQube as a Critical issue, however since those constant are translated
     * into parameters later on AND they really belong to the dms project, this is a perfectly reasonable use for a
     * constants interface.
     */
    public interface DMSProduct {
        String MEDIA = "mediaUrl";
        String CREDIT = "copyright";
        String ALT_TEXT = "altText";
        String IMAGE = "images";
        String NAME = "name";
        String LATITUDE = "latitude";
        String LONGITUDE = "longitude";
        String ID = "longitude";
        String ADDRESS = "address";
        String LOCATION = "city";
    }

    //Endpoints
    //TODO Remove %s at the beginning of the constants
    public static final String PRODUCT_SEARCH = "/info/%s/search-results?";
    public static final String PRODUCT_SEARCH_DATA_MAP = "/data/product-search/map?";
    public static final String VS_DMS_PRODUCT_CARD = "%s/data/products/card?";
    public static final String META_PRODUCT_LIST = "/data/meta/%s/list?%s";
    public static final String META_LOCATIONS = "/data/meta/location/list?full";

}
