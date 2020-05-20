package com.visitscotland.brmx.beans.mapping.megalinks;

public class ListLayout extends AbstractLayout<EnhancedLink> {

    //TODO: Question displayTeaser insteadof hideTeaser?
    private boolean hideTeaser;

    public boolean isHideTeaser() {
        return hideTeaser;
    }

    public void setHideTeaser(boolean hideTeaser) {
        this.hideTeaser = hideTeaser;
    }
}
