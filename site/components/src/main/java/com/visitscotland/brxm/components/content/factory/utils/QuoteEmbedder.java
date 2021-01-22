package com.visitscotland.brxm.components.content.factory.utils;

import com.visitscotland.brxm.beans.Quote;
import com.visitscotland.brxm.beans.capabilities.Linkable;
import com.visitscotland.brxm.beans.mapping.FlatQuote;
import com.visitscotland.brxm.beans.mapping.Module;
import com.visitscotland.brxm.beans.mapping.megalinks.EnhancedLink;
import com.visitscotland.brxm.components.content.factory.ImageFactory;
import com.visitscotland.brxm.components.content.factory.LinkModulesFactory;
import com.visitscotland.brxm.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class QuoteEmbedder {

    private static final Logger logger = LoggerFactory.getLogger(QuoteEmbedder.class);

    ImageFactory imageFactory;
    LinkModulesFactory linkFactory;

    public QuoteEmbedder(){
        this.imageFactory = new ImageFactory();
        this.linkFactory = new LinkModulesFactory();
    }

    public FlatQuote getQuote(Quote doc, Module module, Locale locale){

        if (doc != null) {
            FlatQuote quote = new FlatQuote();
            quote.setQuote(doc.getQuote());
            quote.setAuthorName(doc.getAuthor());
            quote.setAuthorTitle(doc.getRole());

            if (doc.getImage() != null) {
                quote.setImage(imageFactory.createImage(doc.getImage(), module, locale));
            }

            if (doc.getProduct() instanceof Linkable) {
                EnhancedLink link = linkFactory.createEnhancedLink((Linkable) doc.getProduct(), locale, false);
                quote.setLink(link);
            } else if (doc.getProduct() != null){
                CommonUtils.contentIssue("The Product for this iCentre (%s) is not a valid link.", doc.getPath());
                logger.warn("The Product for this iCentre ({})is not a valid link.", doc.getPath());
            }
            return quote;
        }
        return null;

    }

}
