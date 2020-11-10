package com.visitscotland.brmx.beans.mapping;

public enum LinkType {
    INTERNAL, EXTERNAL, DOWNLOAD;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
