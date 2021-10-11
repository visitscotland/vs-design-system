package com.visitscotland.brxm.model;

import com.visitscotland.brxm.hippobeans.CannedSearchTours;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class CannedSearchToursModule extends Module<CannedSearchTours> {

    public CannedSearchToursModule() {
        this.productType = "tours";
    }

    private String title;
    private String dmsApiUrl;
    private FlatLink viewAllLink;
    private HippoHtml copy;
    private String productType;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDmsApiUrl() {
        return dmsApiUrl;
    }

    public void setDmsApiUrl(String dmsApiUrl) {
        this.dmsApiUrl = dmsApiUrl;
    }

    public FlatLink getViewAllLink() {
        return viewAllLink;
    }

    public void setViewAll(FlatLink viewAll) {
        this.viewAllLink = viewAll;
    }

    public HippoHtml getCopy() {
        return copy;
    }

    public void setCopy(HippoHtml copy) {
        this.copy = copy;
    }

    public String getProductType() {
        return productType;
    }
}
