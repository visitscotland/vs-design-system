package com.visitscotland.brmx.beans.mapping.megalinks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StandardLayout extends AbstractLayout<EnhancedLink> {

    private boolean displayTeaser;

    public boolean isDisplayTeaser() {
        return displayTeaser;
    }

    public void setDisplayTeaser(boolean displayTeaser) {
        this.displayTeaser = displayTeaser;
    }

    private List<EnhancedLink> getSortedLinks(){
        return getLinks().stream()
                .sorted((a,b) -> b.isFeatured()?0:1)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
