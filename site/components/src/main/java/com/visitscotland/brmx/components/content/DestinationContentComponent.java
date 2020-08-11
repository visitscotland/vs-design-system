package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.ICentreModule;
import com.visitscotland.brmx.beans.mapping.IKnowModule;
import com.visitscotland.brmx.beans.mapping.megalinks.AbstractLayout;
import com.visitscotland.brmx.components.content.factory.ICentreFactory;
import com.visitscotland.brmx.components.content.factory.IKnowFactory;
import com.visitscotland.brmx.components.content.factory.LinkModulesFactory;
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
    ICentreFactory iCentreFactory;
    IKnowFactory iKnowFactory;

    static final String PAGE_ITEMS = "pageItems";
    static final String[] styles = {"style1","style2","style3"};



    public DestinationContentComponent(){
        linksFactory = new LinkModulesFactory();
        iCentreFactory = new ICentreFactory();
        iKnowFactory = new IKnowFactory();
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        addHeroCoordinates(request);
        addModules(request);
    }

    void addModules(HstRequest request){
        //TODO create a parent class for modules in a destination page and inherit from it
        List<Object> links = new ArrayList<>();
        int styleIndex = 0;

        for (BaseDocument item: getDocument(request).getItems()){
            if (item instanceof Megalinks) {
                AbstractLayout layout = linksFactory.getMegalinksModule((Megalinks) item, request.getLocale());

                if (Contract.isEmpty(layout.getTitle()) && styleIndex > 0) {
                    styleIndex--;
                }

                layout.setStyle(styles[styleIndex++ % styles.length]);
                links.add(layout);
            } else if (item instanceof TourismInformation){
                TourismInformation touristInfo = (TourismInformation) item;
                String location = getDocument(request).getLocation();

                //TODO IcentreModule
                ICentreModule iCentreModule = iCentreFactory.getModule(touristInfo.getICentre(),request.getLocale(), location);
                if (iCentreModule != null) {
                    links.add(iCentreModule);
                }

                IKnowModule iKnowModule = iKnowFactory.getModule(touristInfo.getIKnow(),location);
                links.add(iKnowModule);

                System.out.println("A TourismInformation was found");

            }
        }

        //Note: In the future this listLayout will be compose by different types of module.
        request.setAttribute(PAGE_ITEMS, links);
    }

}
