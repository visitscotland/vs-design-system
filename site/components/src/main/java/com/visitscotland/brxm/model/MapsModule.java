package com.visitscotland.brxm.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.visitscotland.brxm.hippobeans.MapCategory;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.List;

public class MapsModule extends Module {
    private String title;
    private HippoHtml introduction;
    private String tabTitle;
    private JsonArray mapFilters;
    private JsonObject geoJson;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HippoHtml getIntroduction() {
        return introduction;
    }

    public void setIntroduction(HippoHtml introduction) {
        this.introduction = introduction;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public void setTabTitle(String tabTitle) {
        this.tabTitle = tabTitle;
    }

    public JsonArray getMapFilters() {
        return mapFilters;
    }

    public void setMapFilters(JsonArray mapFilters) {
        this.mapFilters = mapFilters;
    }

    public JsonObject getGeoJson() {
        return geoJson;
    }

    public void setGeoJson(JsonObject geoJson) {
        this.geoJson = geoJson;
    }

    public JsonObject getMapControls() {
        return mapControls;
    }

    public void setMapControls(JsonObject mapControls) {
        this.mapControls = mapControls;
    }
}
