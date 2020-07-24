package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.BaseDocument;
import com.visitscotland.brmx.beans.Destination;
import com.visitscotland.brmx.beans.Megalinks;
import com.visitscotland.brmx.beans.Stop;
import com.visitscotland.brmx.beans.mapping.megalinks.AbstractLayout;
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
            } else if (item instanceof Stop){
                System.out.println("An stop was found");
            }
        }

        //Note: In the future this listLayout will be compose by different types of module.
        request.setAttribute(PAGE_ITEMS, links);
    }

}
