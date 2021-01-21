package com.visitscotland.brxm.components.content.factory;

import com.visitscotland.brxm.beans.Article;
import com.visitscotland.brxm.beans.ArticleSection;
import com.visitscotland.brxm.beans.mapping.FlatLongContentSection;
import com.visitscotland.brxm.beans.mapping.LongContentModule;
import com.visitscotland.brxm.components.content.factory.utils.QuoteEmbedder;
import com.visitscotland.brxm.dms.LocationLoader;
import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArticleFactory {

    LocationLoader locationLoader;
    ImageFactory imageFactory;
    LinkModulesFactory linkFactory;
    QuoteEmbedder quoteEmbedder;

    public ArticleFactory(){
        locationLoader = LocationLoader.getInstance();
        imageFactory = new ImageFactory();
        linkFactory = new LinkModulesFactory();
        quoteEmbedder = new QuoteEmbedder();
    }

    public LongContentModule getModule(HstRequest request, Article doc){
        LongContentModule module = new LongContentModule();
        List<FlatLongContentSection> sections = new ArrayList<>();
        if (doc.getImage() != null) {
            module.setImage(imageFactory.createImage(doc.getImage(), module, Locale.UK));
        }
        module.setTitle(doc.getTitle());
        module.setIntroduction(doc.getCopy());
        module.setHippoBean(doc);
        module.setAnchor(doc.getAnchor());

        for (ArticleSection section: doc.getParagraph()){
            FlatLongContentSection flcs = new FlatLongContentSection();
            flcs.setCopy(section.getCopy());

            if (section.getMediaItem() != null) {
                flcs.setImage(imageFactory.getImage(section.getMediaItem(), module, request.getLocale()));
            }

            flcs.setQuote(quoteEmbedder.getQuote(section.getQuote(), module, request.getLocale()));

            sections.add(flcs);
        }
        module.setSections(sections);

        return module;
    }



}
