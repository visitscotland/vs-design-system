package com.visitscotland.brmx.beans.mapping.megalinks;

import java.util.List;

public class MultiImageLinksModule extends LinksModule<EnhancedLink> {

    private boolean teaserVisible;
    private List<EnhancedLink> featuredLinks;
    private Integer linksSize;
    private List<String> errorMessages;

    public MultiImageLinksModule() {

    }

    public MultiImageLinksModule(String title) {
        setTitle(title);
    }

    public boolean isTeaserVisible() {
        return teaserVisible;
    }

    public void setTeaserVisible(boolean teaserVisible) {
        this.teaserVisible = teaserVisible;
    }

    public List<EnhancedLink> getFeaturedLinks() {
        return featuredLinks;
    }

    public Integer getLinksSize() {
        return linksSize;
    }

    public void setLinksSize(Integer linksSize) {
        this.linksSize = linksSize;
    }

    public List<String> getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(List<String> errorMessages) {
        this.errorMessages = errorMessages;
    }

    public void setFeaturedLinks(List<EnhancedLink> featuredLinks) {
        this.featuredLinks = featuredLinks;
    }

}
