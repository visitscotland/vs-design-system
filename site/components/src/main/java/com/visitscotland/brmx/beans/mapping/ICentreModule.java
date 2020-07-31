package com.visitscotland.brmx.beans.mapping;

import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.List;

public class ICentreModule {

    private String title;
    private FlatImage image;
    private String quoteAuthorName;
    private String quoteAuthorTitle;
    private HippoHtml quote;
    private FlatImage quoteImage;
    private List<FlatLink> iCentreList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FlatImage getImage() {
        return image;
    }

    public void setImage(FlatImage image) {
        this.image = image;
    }

    public String getQuoteAuthorName() {
        return quoteAuthorName;
    }

    public void setQuoteAuthorName(String quoteAuthorName) {
        this.quoteAuthorName = quoteAuthorName;
    }

    public String getQuoteAuthorTitle() {
        return quoteAuthorTitle;
    }

    public void setQuoteAuthorTitle(String quoteAuthorTitle) {
        this.quoteAuthorTitle = quoteAuthorTitle;
    }

    public HippoHtml getQuote() {
        return quote;
    }

    public void setQuote(HippoHtml quote) {
        this.quote = quote;
    }

    public FlatImage getQuoteImage() {
        return quoteImage;
    }

    public void setQuoteImage(FlatImage quoteImage) {
        this.quoteImage = quoteImage;
    }

    public List<FlatLink> getiCentreList() {
        return iCentreList;
    }

    public void setiCentreList(List<FlatLink> iCentreList) {
        this.iCentreList = iCentreList;
    }
}
