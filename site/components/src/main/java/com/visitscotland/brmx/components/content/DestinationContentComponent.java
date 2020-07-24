package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.megalinks.AbstractLayout;
import com.visitscotland.brmx.components.content.factory.LinkModulesFactory;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class DestinationContentComponent extends PageContentComponent<Destination> {

    private static final Logger logger = LoggerFactory.getLogger(DestinationContentComponent.class);

    LinkModulesFactory linksFactory;

    static final String PAGE_ITEMS = "pageItems";
    static final String[] styles = {"style1","style2","style3"};



    public DestinationContentComponent(){
        linksFactory = new LinkModulesFactory();
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addHeroCoordinates(request);
        addModules(request);
    }

    void addModules(HstRequest request){
        List<AbstractLayout> links = new ArrayList<>();
        int styleIndex = 0;

        for (BaseDocument item: getDocument(request).getItems()){
            if (item instanceof Megalinks) {
                AbstractLayout al = linksFactory.getMegalinkModule((Megalinks) item, request.getLocale());

                if (Contract.isEmpty(al.getTitle()) && styleIndex > 0) {
                    styleIndex--;
                }

                al.setStyle(styles[styleIndex++ % styles.length]);
                links.add(al);
            } else if (item instanceof IKnowIcentre){
                IKnowIcentre iKnowIcentre = (IKnowIcentre) item;

                String location = getDocument(request).getLocation();

                //TODO IcentreModule
                //TODO: Get list of icentre
                //icentre.setVICList()
                //ProductSearchBuilder iCentre-> Parse and getList
                // use location
                //https://www.visitscotland.com/info/see-do/search-results?loc=Scotland&locplace=&locprox=0&cat=vics
                List vicList = new ArrayList<>();

                for (Object vic: vicList){
                    FlatLink link = new FlatLink();
                    //link.setLabel(vic.getName());
                    //link.setLink(vic.getUrl());
                }

                if (vicList.size() > 0){


                    //icentre.setTitle()
                    iKnowIcentre.getICentre().getTitle();
                    //if (null) -> label "A tip from ...."

                    //TODO What is happenning here
                    //icentre.setImage()
                    //if (image)
                    FlatImage image = new FlatImage((Image) iKnowIcentre.getICentre().getImage(), request.getLocale());
                    //if else if (quote & quote.cta)

                    // default: CMS -> https://cimg.visitscotland.com/cms-images/about/458950/fort-william-visitscotland-icentre-sign?size=sm

                    if (iKnowIcentre.getICentre().getICentreQuote() != null) {

                        //icentre.setQuoteTitle()
                        iKnowIcentre.getICentre().getICentreQuote().getTitle();

                        //icentre.setQuoteAuthor()
                        iKnowIcentre.getICentre().getICentreQuote().getName();

                        //icentre.setQuote()
                        iKnowIcentre.getICentre().getICentreQuote().getQuote();

                        //icentre.setQuoteImage()
                        if (iKnowIcentre.getICentre().getICentreQuote().getImage() != null) {
                            FlatImage quoteImage = new FlatImage((Image) iKnowIcentre.getICentre().getICentreQuote().getImage(), request.getLocale());
                        }

                        if (iKnowIcentre.getICentre().getICentreQuote().getProductId() != null){
                            // iKnowIcentre.getICentre().getICentreQuote().getChildBeans("hippo:facetselect");
                            //    COMPOSE IMAGE DMS (or SHARED LINK)
                            image = new FlatImage(); //Compose DMS
                        }
                    }
                }

                //TODO IknowModule
                //iknow.setTitle()
                iKnowIcentre.getICentre().getIknowTitle();
                //if (null) -> label "Help and Advice"

                //iknow.setDescription
                iKnowIcentre.getICentre().getIknowDescription();

                //iknow.cta
                // use location
                //https://www.visitscotland.com/info/see-do/search-results?prodtypes=cate%2Cacti%2Cattr%2Creta&src_awards__0=qaiknowscotland&loc=Edinburgh&locplace=4161&locprox=0
                ProductSearchBuilder psr =  new ProductSearchBuilder();

                System.out.println("An IKnowIcentre was found");
            }
        }

        //Note: In the future this listLayout will be compose by different types of module.
        request.setAttribute(PAGE_ITEMS, links);
    }

}
