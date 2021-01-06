package com.visitscotland.brxm.components.content;

import com.visitscotland.brxm.beans.*;
import com.visitscotland.brxm.beans.mapping.*;
import com.visitscotland.brxm.beans.mapping.megalinks.LinksModule;
import com.visitscotland.brxm.beans.mapping.megalinks.SingleImageLinksModule;
import com.visitscotland.brxm.components.content.factory.ICentreFactory;
import com.visitscotland.brxm.components.content.factory.IKnowFactory;
import com.visitscotland.brxm.components.content.factory.LinkModulesFactory;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;

public class PageTemplateBuilder {

    private static final Logger logger = LoggerFactory.getLogger(PageTemplateBuilder.class);

    private final LinkModulesFactory linksFactory;
    private final ICentreFactory iCentreFactory;
    private final IKnowFactory iKnowFactory;

    static final String PAGE_ITEMS = "pageItems";
    static final String[] themes = {"theme1", "theme2", "theme3"};
    static final String[] alignment = {"right", "left"};

    public PageTemplateBuilder() {
        this(new LinkModulesFactory(), new ICentreFactory(), new IKnowFactory());
    }

    public PageTemplateBuilder(LinkModulesFactory linksFactory, ICentreFactory iCentre, IKnowFactory iKnow) {
        this.linksFactory = linksFactory;
        this.iCentreFactory = iCentre;
        this.iKnowFactory = iKnow;
    }


    private Page getDocument(HstRequest request) {
        return (Page) request.getAttribute("document");
    }


    public void addModules(HstRequest request) {
        addModules(request, null);
    }

    public void addModules(HstRequest request, String location) {
        List<Module> links = new ArrayList<>();
        int styleIndex = 0;
        int singleImageindex = 0;

        for (BaseDocument item : getDocument(request).getModules()) {
            try {
                logger.info("A {} module was found. Type {}", item.getClass(), item.getPath());
                if (item instanceof Megalinks) {
                    LinksModule<?> al = linksFactory.getMegalinkModule((Megalinks) item, request.getLocale());

                    if (al.getType().equalsIgnoreCase(SingleImageLinksModule.class.getSimpleName())) {
                        al.setAlignment(alignment[singleImageindex++ % alignment.length]);
                    }
                    if (Contract.isEmpty(al.getTitle()) && styleIndex > 0) {
                        styleIndex--;
                    }

                    al.setTheme(themes[styleIndex++ % themes.length]);
                    al.setHippoBean(item);

                    links.add(al);
                } else if (item instanceof TourismInformation) {
                    TourismInformation touristInfo = (TourismInformation) item;

                    ICentreModule iCentreModule = iCentreFactory.getModule(touristInfo.getICentre(), request.getLocale(), location);

                    IKnowModule iKnowModule = iKnowFactory.getIKnowModule(touristInfo.getIKnow(), location, request.getLocale());

                    if (iCentreModule != null) {
                        iCentreModule.setHippoBean(item);
                        links.add(iCentreModule);
                    }
                    iKnowModule.setHippoBean(item);
                    links.add(iKnowModule);
                } else if (item instanceof LongContent){
                    links.add(createLongContent(request, (LongContent) item));
                } else if (item instanceof Article){
                    links.add(createLongContent(request, (Article) item));
                }
            } catch (MissingResourceException e){
                logger.error("The module for {} couldn't be built because some labels do not exist", item.getPath(), e);
            } catch (RuntimeException e){
                logger.error("An unexpected exception happened while building the module for {}", item.getPath(), e);
            }
        }
        OTYML otyml = getDocument(request).getOtherThings();
        if(otyml!=null) {
            LinksModule al = linksFactory.horizontalListLayout(otyml, request.getLocale());
            al.setTheme(themes[0]);
            links.add(al);
        }

        request.setAttribute(PAGE_ITEMS, links);
    }

    //TODO convert into factory
    private Module createLongContent(HstRequest request, Article doc){
        LongContentModule module = new LongContentModule();
        List<FlatLongContentSection> sections = new ArrayList<>();
        //TODO add media
        if (doc.getImage() != null) {
            module.setImage(new FlatImage(doc.getImage(), Locale.UK));
        }
        module.setTitle(doc.getTitle());
        module.setIntroduction(doc.getCopy());
        module.setHippoBean(doc);
        module.setAnchor(doc.getAnchor());

        for (ArticleSection section: doc.getParagraph()){
            FlatLongContentSection flcs = new FlatLongContentSection();
            flcs.setCopy(section.getCopy());
            //TODO Convert MediaItem into image
           //flcs.setImage(new FlatImage(section.getMediaItem(), request.getLocale()));

            if (section.getMediaItem() instanceof Image) {
                Image cmsImage = (Image) section.getMediaItem();
                if (cmsImage != null) {
                    FlatImage flatImage = new FlatImage(cmsImage,request.getLocale());
                   flcs.setImage(flatImage);
                }
            }

            // TODO Are we going to include Quotes?
            if (section.getQuote()!= null){
                flcs.setQuote(section.getQuote().getQuote());
                flcs.setQuoteAuthorName(section.getQuote().getAuthor());
                flcs.setQuoteAuthorTitle(section.getQuote().getRole());
                if (section.getQuote().getImage() != null) {
                    flcs.setQuoteImage(new FlatImage(section.getQuote().getImage(), request.getLocale()));
                }
                //TODO ADD Rethink about CTA
//                flcs.setQuoteLink(section.getQuote().getProduct());
            }
            sections.add(flcs);
        }
        module.setSections(sections);

        return module;
    }

    //TODO convert into factory
    private Module createLongContent(HstRequest request, LongContent doc){
        LongContentModule module = new LongContentModule();
        List<FlatLongContentSection> sections = new ArrayList<>();
        //TODO add media
        if (doc.getImage() != null) {
            module.setImage(new FlatImage(doc.getImage(), Locale.UK));
        }
        module.setTitle(doc.getTitle());
        module.setIntroduction(doc.getIntroduction());
        module.setHippoBean(doc);

        for (LongContentSection section: doc.getparagraphs()){
            FlatLongContentSection flcs = new FlatLongContentSection();
            flcs.setCopy(section.getCopy());
            //TODO Convert MediaItem into image
            //flcs.setImage(new FlatImage(section.getMediaItem(), request.getLocale()));

            if (section.getMediaItem() instanceof Image) {
                Image cmsImage = (Image) section.getMediaItem();
                if (cmsImage != null) {
                    FlatImage flatImage = new FlatImage(cmsImage,request.getLocale());
                    flcs.setImage(flatImage);
                }
            }

            // TODO Are we going to include Quotes?
            if (section.getQuote()!= null){
                flcs.setQuote(section.getQuote().getQuote());
                flcs.setQuoteAuthorName(section.getQuote().getAuthor());
                flcs.setQuoteAuthorTitle(section.getQuote().getRole());
                if (section.getQuote().getImage() != null) {
                    flcs.setQuoteImage(new FlatImage(section.getQuote().getImage(), request.getLocale()));
                }
                //TODO ADD Rethink about CTA
//                flcs.setQuoteLink(section.getQuote().getProduct());
            }
            sections.add(flcs);
        }
        module.setSections(sections);

        return module;
    }
}
