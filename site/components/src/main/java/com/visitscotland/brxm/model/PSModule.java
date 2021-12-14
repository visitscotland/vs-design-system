package com.visitscotland.brxm.model;

import com.visitscotland.brxm.dms.DMSConstants.PSType;

public class PSModule {

    private String title;
    private String description;
    private String location;
    private PSType category;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public PSType getCategory() {
        return category;
    }

    public void setCategory(PSType category) {
        this.category = category;
    }
}
