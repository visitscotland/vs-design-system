package com.visitscotland.brmx.utils;

import com.visitscotland.brmx.beans.MegaLinkItem;
import com.visitscotland.brmx.beans.MegaLinks;
import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.megalinks.AbstractLayout;
import com.visitscotland.brmx.beans.mapping.megalinks.ListLayout;
import com.visitscotland.brmx.beans.mapping.megalinks.SingleImageLayout;
import com.visitscotland.brmx.beans.mapping.megalinks.FeaturedLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LinkModulesFactory {

    private final static int MAX_ITEMS = 6;

    public AbstractLayout getMegalinkModule(MegaLinks doc, Locale locale){
        if (doc.getSingleImageModule() != null){
            return singleImageLayout(doc, locale);
        } else if (doc.getList() || doc.getSingleImageLinks().size() > MAX_ITEMS){
            return list(doc, locale);
        } else {
            return featuredLayout(doc, locale);
        }
    }

    public SingleImageLayout singleImageLayout(MegaLinks doc, Locale locale){
        SingleImageLayout sil = new SingleImageLayout ();
        sil.setTitle(doc.getTitle());
        sil.setIntroduction(doc.getIntroduction());
        sil.setImage(new FlatImage(doc.getSingleImageModule().getImage(), locale));
        sil.setFullWidth(doc.getSingleImageModule().getFullWidth());
        //TODO: Question: Featured links first?
        sil.setLinks(convertoToFlatLinks(doc.getMegaLinkItems()));



        //TODO cta?
        //sil.setCta(doc.getCta());

        return sil;
    }

    public FeaturedLayout featuredLayout(MegaLinks doc, Locale locale){
        //TODO
        return new FeaturedLayout();
    }

    public ListLayout list(MegaLinks doc, Locale locale){
        ListLayout l = new ListLayout();
        l.setHideTeaser(doc.getHideTeaser());

        return new ListLayout();
    }

    private List<FlatLink> convertoToFlatLinks(List<MegaLinkItem> items){
        List<FlatLink> links = new ArrayList<>();
        for (MegaLinkItem item : items){
            if (item.getLink() == null) {
                //TODO "The module %s contains a link without any reference"
            } else if (item.getLink() instanceof Page){
                //TODO HippoUtils.getUrl(HippoLink)
                links.add(new FlatLink(((Page) item.getLink()).getTitle(), "TODO"));
            } else {
                //TODO "The module %s is pointing to a document of type %s which cannot be rendered as a page " item.getLink().getClass().getSimpleName()
            }
        }
        return links;
    }

    private <L> List<L> convertToEnhancedLinks(){
        //TODO
        return null;
    }





}
