package com.visitscotland.brxm.components.content.factory;

import com.visitscotland.brxm.beans.Article;
import com.visitscotland.brxm.beans.ArticleSection;
import com.visitscotland.brxm.beans.Quote;
import com.visitscotland.brxm.beans.capabilities.Linkable;
import com.visitscotland.brxm.beans.mapping.FlatImage;
import com.visitscotland.brxm.beans.mapping.FlatLongContentSection;
import com.visitscotland.brxm.beans.mapping.LongContentModule;
import com.visitscotland.brxm.beans.mapping.megalinks.EnhancedLink;
import com.visitscotland.brxm.dms.LocationLoader;
import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ArticleFactory {

    private static final Logger logger = LoggerFactory.getLogger(ArticleFactory.class);

    LocationLoader locationLoader;
    ImageFactory imageFactory;

    public ArticleFactory(){
        locationLoader = LocationLoader.getInstance();
        imageFactory = new ImageFactory();
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

            Quote quote = section.getQuote();
            Locale locale = request.getLocale();
            // TODO: --> Refactor
            
            if (quote != null) {
                flcs.setQuote(quote.getQuote());
                flcs.setQuoteAuthorName(quote.getAuthor());
                flcs.setQuoteAuthorTitle(quote.getRole());

                if (quote.getImage() != null) {
                    flcs.setQuoteImage(new FlatImage(quote.getImage(), locale));
                }

                if (quote.getProduct() instanceof Linkable) {
                    //TODO Global
                    LinkModulesFactory linkFactory = new LinkModulesFactory();
                    EnhancedLink link = linkFactory.createEnhancedLink((Linkable) quote.getProduct(), locale, false);
                    flcs.setQuoteLink(link);
                    if (module.getImage() == null) {
                        module.setImage(link.getImage());
                    }
                } else if (quote.getProduct() != null){
                    //TODO: Content issue
                    logger.warn("The Product for this iCentre ({})is not a valid link.", doc.getPath());
                }
            }
            //TODO: --> Refactor
            sections.add(flcs);
        }
        module.setSections(sections);

        return module;
    }

}
