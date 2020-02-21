package com.visitscotland.brmx.beans.mapping;

public class FlatLink {


    private String label;
    private String href;

    private String errorMessage;

    public FlatLink(){

    }

    public FlatLink(String label, String link) {
        this.label = label;
        this.href = link;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
