package com.visitscotland.brxm.model;

import com.visitscotland.brxm.dms.DMSConstants.PSType;
import com.visitscotland.brxm.dms.model.LocationObject;

import java.util.Map;

public class PSModule {

    private String title;
    private String description;
    private PSType category;
    private LocationObject location;
    private String searchUrl;
    private Map<String,String> supportingURLs;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PSType getCategory() {
        return category;
    }

    public void setCategory(PSType category) {
        this.category = category;
    }

    public LocationObject getLocation() {
        return location;
    }

    public void setLocation(LocationObject location) {
        this.location = location;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public Map<String, String> getSupportingURLs() {
        return supportingURLs;
    }

    public void setSupportingURLs(Map<String, String> supportingURLs) {
        this.supportingURLs = supportingURLs;
    }
}
