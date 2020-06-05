package com.visitscotland.brmx.beans.mapping.megalinks;

public class ListLayout extends AbstractLayout<EnhancedLink> {

    private boolean teaserVisible;

    public boolean isHideTeaser() {
        return teaserVisible;
    }

    public void setTeaserVisible(boolean teaserVisible) {
        this.teaserVisible = teaserVisible;
    }
}
