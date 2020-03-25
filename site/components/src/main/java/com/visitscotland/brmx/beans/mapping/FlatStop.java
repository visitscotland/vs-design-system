package com.visitscotland.brmx.beans.mapping;

import com.visitscotland.brmx.beans.Stop;
import org.hippoecm.hst.content.beans.standard.HippoHtml;
import org.json.JSONObject;

import java.util.List;

public class FlatStop {

    private String identifier;

    private Integer index;
    private String title;
    private HippoHtml description;
    private FlatImage image;
    private String timeToexplore;
    private String tipsTitle;
    private FlatLink ctaLink;
    private HippoHtml tipsBody;
    private List<JSONObject> facilities;
    private JSONObject address;
    private String location;
    private String price;
    private String open;

    private Coordinates coordinates;
    private String errorMessage;

    public FlatStop() {

    }

    public FlatStop(Stop stop) {
        this.identifier = stop.getIdentifier();

        this.title = stop.getTitle();
        this.description = stop.getDescription();
        this.tipsTitle = stop.getTipsTitle();
        this.tipsBody = stop.getTips();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HippoHtml getDescription() {
        return description;
    }

    public void setDescription(HippoHtml description) {
        this.description = description;
    }

    public FlatImage getImage() {
        return image;
    }

    public void setImage(FlatImage image) {
        this.image = image;
    }

    public String getTimeToexplore() {
        return timeToexplore;
    }

    public void setTimeToexplore(String timeToexplore) {
        this.timeToexplore = timeToexplore;
    }

    public String getTipsTitle() {
        return tipsTitle;
    }

    public void setTipsTitle(String tipsTitle) {
        this.tipsTitle = tipsTitle;
    }

    public HippoHtml getTipsBody() {
        return tipsBody;
    }

    public void setTipsBody(HippoHtml tipsBody) {
        this.tipsBody = tipsBody;
    }

    public List<JSONObject> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<JSONObject> facilities) {
        this.facilities = facilities;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public FlatLink getCtaLink() {
        return ctaLink;
    }

    public void setCtaLink(FlatLink ctaLink) {
        this.ctaLink = ctaLink;
    }

    public JSONObject getAddress() {
        return address;
    }

    public void setAddress(JSONObject address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
}
