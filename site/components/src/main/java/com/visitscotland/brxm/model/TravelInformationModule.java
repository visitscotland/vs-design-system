package com.visitscotland.brxm.model;

import com.visitscotland.brxm.hippobeans.TravelInformation;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class TravelInformationModule extends Module<TravelInformation> {

    private String title;
    private HippoHtml copy;
    private TravelInformationTabModule gettingTo;
    private TravelInformationTabModule gettingAround;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HippoHtml getCopy() {
        return copy;
    }

    public void setCopy(HippoHtml copy) {
        this.copy = copy;
    }

    public TravelInformationTabModule getGettingTo() {
        return gettingTo;
    }

    public void setGettingTo(TravelInformationTabModule gettingTo) {
        this.gettingTo = gettingTo;
    }

    public TravelInformationTabModule getGettingAround() {
        return gettingAround;
    }

    public void setGettingAround(TravelInformationTabModule gettingAround) {
        this.gettingAround = gettingAround;
    }
}
