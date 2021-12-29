package com.visitscotland.brxm.dms;

public enum PSType {
    ACCOMMODATION(DMSConstants.PATH_ACCOMMODATION, DMSConstants.TYPE_ACCOMMODATION),
    EVENTS(DMSConstants.PATH_EVENTS, DMSConstants.TYPE_EVENTS),
    FOOD_DRINK(DMSConstants.PATH_FOOD_DRINK, DMSConstants.TYPE_FOOD_DRINK),
    TOURS(DMSConstants.PATH_TOURS, DMSConstants.TYPE_TOURS),
    SEE_DO(DMSConstants.PATH_SEE_DO, DMSConstants.TYPE_SEE_DO);

    private final String pathVariable;
    private final String productTypes;

    PSType(String pathVariable, String productTypes) {
        this.pathVariable = pathVariable;
        this.productTypes = productTypes;
    }

    public String getPathVariable() {
        return pathVariable;
    }

    public String getProductTypes() {
        return productTypes;
    }

    public static PSType getType(String url) {
        String[] path = url.split("/");
        for (String segment : path) {
            for (PSType type : PSType.values()) {
                if (segment.equals(type.pathVariable)) {
                    return type;
                }
            }
        }

        return SEE_DO;
    }
}
