package com.visitscotland.brmx.beans.mapping.megalinks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FeaturedLayout extends AbstractLayout<EnhancedLink> {

    private boolean hideTeaser;
    private List<EnhancedLink> featuredLinks;

    public boolean isHideTeaser() {
        return hideTeaser;
    }

    public void setHideTeaser(boolean hideTeaser) {
        this.hideTeaser = hideTeaser;
    }

    public List<EnhancedLink> getFeaturedLinks() {
        return featuredLinks;
    }

    public void setFeaturedLinks(List<EnhancedLink> featuredLinks) {
        this.featuredLinks = featuredLinks;
    }

}
