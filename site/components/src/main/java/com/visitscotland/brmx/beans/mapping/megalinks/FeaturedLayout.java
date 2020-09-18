package com.visitscotland.brmx.beans.mapping.megalinks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeaturedLayout extends AbstractLayout<EnhancedLink> {

    private boolean teaserVisible;
    private List<EnhancedLink> featuredLinks;

    public boolean isTeaserVisible() {
        return teaserVisible;
    }

    public void setTeaserVisible(boolean teaserVisible) {
        this.teaserVisible = teaserVisible;
    }

    public List<EnhancedLink> getFeaturedLinks() {
        return featuredLinks;
    }

    public void setFeaturedLinks(List<EnhancedLink> featuredLinks) {
        this.featuredLinks = featuredLinks;
    }

}
