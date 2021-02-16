package com.visitscotland.brxm.beans.mapping;


import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.beans.Image;
import com.visitscotland.brxm.beans.InstagramImage;
import com.visitscotland.brxm.beans.dms.LocationObject;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.utils.Properties;

import java.util.Locale;

public class FlatImage extends IssueList {

    public enum Source {
        INSTAGRAM;

        @Override
        public String toString() {
            return name().toLowerCase();
        }
    }
    private Image cmsImage;
    private String externalImage;
    private String altText;
    private String credit;
    private String description;
    private Coordinates coordinates;
    private Source source;
    private String postUrl;
    private String location;



    public FlatImage(){
    }

    /**
     * @deprecated. Use ImageFactory.createImage(cmsModule, locale) instead
     */
    @Deprecated
    public FlatImage (Image cmsImage, Locale locale){
       this.cmsImage = cmsImage;
       this.credit = cmsImage.getCredit();
       if (locale != null){
            switch (locale.getLanguage()) {
                case "fr":
                    if (cmsImage.getFr() != null) {
                        this.altText = cmsImage.getFr().getAltText();
                        this.description = cmsImage.getFr().getCaption();
                    }
                    break;
                case "de":
                    if (cmsImage.getDe() != null) {
                        this.altText = cmsImage.getDe().getAltText();
                        this.description =cmsImage.getDe().getCaption();
                    }
                    break;
                case "es":
                    if (cmsImage.getEs() != null) {
                        this.altText = cmsImage.getEs().getAltText();
                        this.description =cmsImage.getEs().getCaption();
                    }
                    break;
                case "nl":
                    if (cmsImage.getNl() != null) {
                        this.altText = cmsImage.getNl().getAltText();
                        this.description =cmsImage.getNl().getCaption();
                    }
                    break;
                case "it":
                    if (cmsImage.getIt() != null) {
                        this.altText = cmsImage.getIt().getAltText();
                        this.description =cmsImage.getIt().getCaption();
                    }
                    break;
                default:
                    this.altText = cmsImage.getAltText();
                    this.description =cmsImage.getDescription();
            }
        }
    }

    /**
     * @deprecated. Use ImageFactory.createImage(cmsModule, locale) instead
     */
    @Deprecated
    public FlatImage(InstagramImage instagramLink, JsonNode instagram, Locale locale) {
        this.externalImage =  instagram.get("thumbnail_url").asText();
        this.credit = instagram.get("author_name").asText();
        this.altText = instagramLink.getCaption();
        this.description = instagramLink.getCaption();
        this.source = Source.INSTAGRAM;
        this.postUrl = Properties.INSTAGRAM_API  + instagramLink.getId();
        this.coordinates = setInstagramCoordinates(instagramLink,locale);
    }


    /**
     * @deprecated. Use ImageFactory.createImage(dmsProduct) instead
     */
    @Deprecated
    public FlatImage(JsonNode product) {
        final String MEDIA = "mediaUrl";
        final String CREDIT = "copyright";
        final String ALT_TEXT = "altText";
        final String IMAGE = "images";
        final String NAME = "name";
        final String LATITUDE = "latitude";
        final String LONGITUDE = "longitude";
        final String ID = "longitude";

        if (product.has(IMAGE)){
            JsonNode dmsImage = product.get(IMAGE).get(0);
            this.externalImage = (dmsImage.has(MEDIA) ? dmsImage.get(MEDIA).asText() : null);
            this.credit = (dmsImage.has(CREDIT) ? dmsImage.get(CREDIT).asText() : null);
            this.altText = (dmsImage.has(ALT_TEXT) ? dmsImage.get(ALT_TEXT).asText() : product.get(NAME).asText());
            this.description = this.altText;

            if (product.has(LATITUDE) && product.has(LONGITUDE)){
                this.coordinates = new Coordinates(product.get(LATITUDE).asDouble(), product.get(LONGITUDE).asDouble());
            }
        } else {
            addError(String.format("The product %s does not have an image", product.has(ID)?product.get(ID):null));
        }
    }

    /**
     * @deprecated. Use ImageFactory.createImage(dmsProduct) instead
     */
    @Deprecated
    public FlatImage(JsonNode dmsImage, String productName) {
        final String MEDIA = "mediaUrl";
        final String CREDIT = "copyright";
        final String ALT_TEXT = "altText";

        this.externalImage = (dmsImage.has(MEDIA) ? dmsImage.get(MEDIA).asText() : null);
        this.credit = (dmsImage.has(CREDIT) ? dmsImage.get(CREDIT).asText() : null);
        this.description = (dmsImage.has(ALT_TEXT) ? dmsImage.get(ALT_TEXT).asText() : productName);
        this.altText = this.description;
    }

    public String getExternalImage() {
        return externalImage;
    }

    public void setExternalImage(String externalImage) {
        this.externalImage = externalImage;
    }
    public Image getCmsImage() {
        return cmsImage;
    }

    public void setCmsImage(Image cmsImage) {
        this.cmsImage = cmsImage;
    }
    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }


    /**
     * @deprecated to be removed along with the constructor that is using it
     */
    @Deprecated
    private Coordinates setInstagramCoordinates(InstagramImage instagramLink, Locale locale){
        if (instagramLink.getLocation()!= null && !instagramLink.getLocation().isEmpty()){
            LocationObject locationObject = LocationLoader.getInstance().getLocation(instagramLink.getLocation(), locale);
            if (locationObject != null){
               return (new Coordinates(locationObject.getLatitude(),locationObject.getLongitude()));
            }
        }
        return null;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
