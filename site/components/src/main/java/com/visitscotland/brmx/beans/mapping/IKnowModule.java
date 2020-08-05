package com.visitscotland.brmx.beans.mapping;

import org.hippoecm.hst.content.beans.standard.HippoHtml;

public class IKnowModule {

    private String title;
    private HippoHtml description;
    private FlatLink link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public HippoHtml getDescription() {
        return description;
    }

    public void setDescription(HippoHtml description) {
        this.description = description;
    }

    public FlatLink getLink() {
        return link;
    }

    public void setLink(FlatLink link) {
        this.link = link;
    }
}
