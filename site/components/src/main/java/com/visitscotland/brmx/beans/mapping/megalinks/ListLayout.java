package com.visitscotland.brmx.beans.mapping.megalinks;

import java.util.List;

public class ListLayout extends AbstractLayout<EnhancedLink> {

    private boolean teaserVisible;

    public boolean isTeaserVisible() {
        return teaserVisible;
    }

    public void setTeaserVisible(boolean teaserVisible) {
        this.teaserVisible = teaserVisible;
    }
}
