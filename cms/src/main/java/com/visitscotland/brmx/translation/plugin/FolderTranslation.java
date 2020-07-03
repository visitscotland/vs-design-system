package com.visitscotland.brmx.translation.plugin;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FolderTranslation implements Serializable {

    private static final long serialVersionUID = 1L;

    static final Logger log = LoggerFactory.getLogger(FolderTranslation.class);

    private final String id;
    private String name;
    private String url;

    private String namefr = "";
    private String urlfr = "";
    private boolean mutable = true;
    private boolean hasSameNameSibling = false;

    public FolderTranslation(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public String getUrlfr() {
        return urlfr;
    }

    public void setUrlfr(String url) {
        if (!mutable) {
            throw new UnsupportedOperationException("Translation is immutable");
        }
        this.urlfr = url;
    }

    public String getNamefr() {
        return namefr;
    }

    public void setNamefr(String name) {
        if (!mutable) {
            throw new UnsupportedOperationException("Translation is immutable");
        }
        this.namefr = name;
    }

    public void setEditable(boolean mutable) {
        this.mutable = mutable;
    }

    public boolean isEditable() {
        return mutable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHasSameNameSibling(boolean hasSameNameSibling) {
        this.hasSameNameSibling = hasSameNameSibling;
    }

    public boolean hasSameNameSibling() {
        return hasSameNameSibling;
    }
}
