package com.visitscotland.brxm.components.content.factory;

import com.visitscotland.brxm.beans.Article;
import com.visitscotland.brxm.beans.ArticleSection;
import com.visitscotland.brxm.beans.mapping.ArticleModule;
import com.visitscotland.brxm.beans.mapping.ArticleModuleSection;
import com.visitscotland.brxm.components.content.factory.utils.QuoteEmbedder;
import org.hippoecm.hst.core.component.HstRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ArticleFactory {

//    @Autowired
    private ImageFactory imageFactory;

//    @Autowired
    private QuoteEmbedder quoteEmbedder;

//    public ArticleFactory(){
//        imageFactory = new ImageFactory();
//        quoteEmbedder = new QuoteEmbedder();
//    }

    public ArticleFactory(ImageFactory imageFactory, QuoteEmbedder quoteEmbedder){
        this.imageFactory = imageFactory;
        this.quoteEmbedder = quoteEmbedder;
    }

    public ArticleModule getModule(HstRequest request, Article doc){
        ArticleModule module = new ArticleModule();
        List<ArticleModuleSection> sections = new ArrayList<>();
        if (doc.getImage() != null) {
            module.setImage(imageFactory.createImage(doc.getImage(), module, Locale.UK));
        }
        module.setTitle(doc.getTitle());
        module.setIntroduction(doc.getCopy());
        module.setHippoBean(doc);
        module.setAnchor(doc.getAnchor());

        for (ArticleSection paragraph: doc.getParagraph()){
            ArticleModuleSection section = new ArticleModuleSection();
            section.setCopy(paragraph.getCopy());

            if (paragraph.getMediaItem() != null) {
                section.setImage(imageFactory.getImage(paragraph.getMediaItem(), module, request.getLocale()));
            }

            section.setQuote(quoteEmbedder.getQuote(paragraph.getQuote(), module, request.getLocale()));

            sections.add(section);
        }
        module.setSections(sections);

        return module;
    }
}
