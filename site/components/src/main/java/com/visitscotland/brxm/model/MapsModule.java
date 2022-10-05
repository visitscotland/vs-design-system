package com.visitscotland.brxm.model;

import com.visitscotland.brxm.hippobeans.MapCategory;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.List;

public class MapsModule extends Module {
    private String title;
    private HippoHtml introduction;
    private String tabTitle;
    private List<MapCategory> feauredPlaces;

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

    public List<MapCategory> getFeauredPlaces() {
        return feauredPlaces;
    }

    public void setFeauredPlaces(List<MapCategory> feauredPlaces) {
        this.feauredPlaces = feauredPlaces;
    }
}
