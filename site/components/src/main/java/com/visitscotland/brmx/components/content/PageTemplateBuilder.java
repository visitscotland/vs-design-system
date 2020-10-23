package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.BaseDocument;
import com.visitscotland.brmx.beans.Megalinks;
import com.visitscotland.brmx.beans.Page;
import com.visitscotland.brmx.beans.TourismInformation;
import com.visitscotland.brmx.beans.mapping.ICentreModule;
import com.visitscotland.brmx.beans.mapping.IKnowModule;
import com.visitscotland.brmx.beans.mapping.Module;
import com.visitscotland.brmx.beans.mapping.megalinks.LinksModule;
import com.visitscotland.brmx.beans.mapping.megalinks.SingleImageLinksModule;
import com.visitscotland.brmx.components.content.factory.ICentreFactory;
import com.visitscotland.brmx.components.content.factory.IKnowFactory;
import com.visitscotland.brmx.components.content.factory.LinkModulesFactory;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.core.component.HstRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

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

                logger.info("A Megalinks module was found. Type " + al.getType());
            } else if (item instanceof TourismInformation) {
                TourismInformation touristInfo = (TourismInformation) item;

                //TODO IcentreModule
                ICentreModule iCentreModule = iCentreFactory.getModule(touristInfo.getICentre(), request.getLocale(), location);

                IKnowModule iKnowModule = iKnowFactory.getModule(touristInfo.getIKnow(), location);

                if (iCentreModule != null) {
                    iCentreModule.setHippoBean(item);
                    links.add(iCentreModule);
                } else {
                    iKnowModule.setHippoBean(item);
                }

                links.add(iKnowModule);

                logger.info("A TourismInformation was found");
            }
        }
        if(getDocument(request).getOtherThings()!=null) {
            LinksModule al = linksFactory.horizontalListLayout(getDocument(request), request.getLocale());
            al.setTheme(themes[0]);
            links.add(al);
        }

        request.setAttribute(PAGE_ITEMS, links);
    }
}
