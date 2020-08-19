package com.visitscotland.brmx.beans.mapping.megalinks;

import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class EnhancedLink extends FlatLink {

    private FlatImage image;
    private String teaser;
    private boolean featured;
    //TODO: Itinerary variable?
    private int days;
    private String transport;

    public FlatImage getImage() {
        return image;
    }

    public void setImage(FlatImage image) {
        this.image = image;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}
