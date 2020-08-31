package com.visitscotland.brmx.beans.mapping;

import com.visitscotland.brmx.beans.TourismInformation;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class IKnowModule extends Module{

    private String title;
    private HippoHtml description;
    private FlatLink link;
    private TourismInformation tourismInformation;

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

    public FlatLink getLink() {
        return link;
    }

    public void setLink(FlatLink link) {
        this.link = link;
    }

    public TourismInformation getTourismInformation() {
        return tourismInformation;
    }

    public void setTourismInformation(TourismInformation tourismInformation) {
        this.tourismInformation = tourismInformation;
    }
}
