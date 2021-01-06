package com.visitscotland.brxm.beans.mapping;

import com.visitscotland.brxm.beans.mapping.megalinks.EnhancedLink;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class FlatLongContentSection {
    private HippoHtml copy;
    private FlatImage image;

    //TODO Consider to refactor into an Interface
    //TODO Do the same for ICentreModule
    private String quoteAuthorName;
    private String quoteAuthorTitle;
    private HippoHtml quote;
    private FlatImage quoteImage;
    private EnhancedLink quoteLink;

    public HippoHtml getCopy() {
        return copy;
    }

    public void setCopy(HippoHtml copy) {
        this.copy = copy;
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

    public EnhancedLink getQuoteLink() {
        return quoteLink;
    }

    public void setQuoteLink(EnhancedLink quoteLink) {
        this.quoteLink = quoteLink;
    }
}
