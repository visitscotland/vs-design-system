package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.Destination;
import com.visitscotland.brmx.beans.Megalinks;
import com.visitscotland.brmx.beans.mapping.megalinks.LinksModule;
import com.visitscotland.brmx.beans.mapping.megalinks.SingleImageLinksModule;
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
    static final String[] alignment = {"left","right"};

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
        List<LinksModule> links = new ArrayList<>();
        int styleIndex = 0;
        int singleImageindex = 0;

        for (Megalinks mega: getDocument(request).getItems()){
            //TODO: do we need the document for the log? In that case.. update tests
            LinksModule al = linksFactory.getMegalinkModule(mega, request.getLocale());

            if (al.getType().equalsIgnoreCase(SingleImageLinksModule.class.getSimpleName())){
                al.setAlignment(alignment[singleImageindex++ % alignment.length]);
            }
            if (Contract.isEmpty(al.getTitle()) && styleIndex > 0){
                styleIndex--;
            }

            al.setStyle(styles[styleIndex++ % styles.length]);
            links.add(al);
        }

        //Note: In the future this listLayout will be compose by different types of module.
        request.setAttribute(PAGE_ITEMS, links);
    }

}
