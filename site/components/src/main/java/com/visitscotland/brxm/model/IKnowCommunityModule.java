package com.visitscotland.brxm.model;

import java.util.List;

public class IKnowCommunityModule extends Module {

    private String title;
    private String copy;
    private List<FlatLink> tags;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCopy() {
        return copy;
    }

    public void setCopy(String copy) {
        this.copy = copy;
    }

    public List<FlatLink> getTags() {
        return tags;
    }

    public void setTags(List<FlatLink> tags) {
        this.tags = tags;
    }
}
