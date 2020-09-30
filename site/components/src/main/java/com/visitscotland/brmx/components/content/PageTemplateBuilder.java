package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;
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

import java.util.ArrayList;
import java.util.List;

public class PageTemplateBuilder<T extends Page> {

    private final LinkModulesFactory linksFactory;
    private final ICentreFactory iCentreFactory;
    private final IKnowFactory iKnowFactory;


    static final String PAGE_ITEMS = "pageItems";
    static final String[] styles = {"style1","style2","style3"};
    static final String[] alignment = {"left","right"};

    public PageTemplateBuilder(){
        this(new LinkModulesFactory(), new ICentreFactory(), new IKnowFactory());
    }

    public PageTemplateBuilder(LinkModulesFactory linksFactory,ICentreFactory iCentre, IKnowFactory iKnow ){
        this.linksFactory = linksFactory;
        this.iCentreFactory = iCentre;
        this.iKnowFactory = iKnow;
    }


    private Page getDocument(HstRequest request){
        return (Page) request.getAttribute("document");
    }

    public void addModules(HstRequest request){
        List<Module> links = new ArrayList<>();
        int styleIndex = 0;
        int singleImageindex = 0;

        for (BaseDocument item: getDocument(request).getModules()){
            if (item instanceof Megalinks) {
                //TODO: do we need the document for the log? In that case.. update tests
                LinksModule al = linksFactory.getMegalinkModule((Megalinks) item, request.getLocale());

                if (al.getType().equalsIgnoreCase(SingleImageLinksModule.class.getSimpleName())){
                    al.setAlignment(alignment[singleImageindex++ % alignment.length]);
                }
                if (Contract.isEmpty(al.getTitle()) && styleIndex > 0){
                    styleIndex--;
                }

                al.setStyle(styles[styleIndex++ % styles.length]);
                links.add(al);


            } else if (item instanceof TourismInformation){
                TourismInformation touristInfo = (TourismInformation) item;
                //TODO send as parameter
//                String location = getDocument(request).getLocation();
                String location = null;

                //TODO IcentreModule
                ICentreModule iCentreModule = iCentreFactory.getModule(touristInfo.getICentre(),request.getLocale(), location);
                if (iCentreModule != null) {
                    links.add(iCentreModule);
                }

                IKnowModule iKnowModule = iKnowFactory.getModule(touristInfo.getIKnow(),location);
                iKnowModule.setTourismInformation(touristInfo);
                links.add(iKnowModule);

                System.out.println("A TourismInformation was found");

            }
        }

       //Note: In the future this listLayout will be compose by different types of module.
        request.setAttribute(PAGE_ITEMS, links);
    }
}
