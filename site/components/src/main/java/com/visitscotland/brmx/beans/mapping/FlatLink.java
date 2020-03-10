package com.visitscotland.brmx.beans.mapping;

public class FlatLink {


    private String label;
    private String link;

    public FlatLink(){

    }

    public FlatLink(String label, String link) {
        this.label = label;
        this.link = link;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
