package com.visitscotland.brxm.beans.mapping;

import com.visitscotland.brxm.beans.mapping.megalinks.EnhancedLink;
import org.hippoecm.hst.content.beans.standard.HippoHtml;

import java.util.List;

public class ICentreModule extends Module{

    private String title;
    private FlatImage image;
    private String quoteAuthorName;
    private String quoteAuthorTitle;
    private HippoHtml quote;
    private FlatImage quoteImage;
    private EnhancedLink quoteLink;
    private String description;
    private List<FlatLink> links;

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

    public EnhancedLink getQuoteLink() {
        return quoteLink;
    }

    public void setQuoteLink(EnhancedLink quoteLink) {
        this.quoteLink = quoteLink;
    }

    public List<FlatLink> getLinks() {
        return links;
    }

    public void setLinks(List<FlatLink> links) {
        this.links = links;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
