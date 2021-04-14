package com.visitscotland.brxm.model;

public interface ProductCard {

    //TODO: add all necessary setters
    
    void setOpen(String openingMessage);

    FlatLink getCtaLink();

    void setOpenLink(FlatLink flatLink);
}
