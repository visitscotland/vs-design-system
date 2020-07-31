package com.visitscotland.brmx.components.content.factory;

import com.visitscotland.brmx.beans.ICentre;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.IKnowModule;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.utils.Contract;

public class IKnowFactory {

    private final HippoUtilsService utils;
    private final ProductSearchBuilder psBuilder;

    public IKnowFactory(){
        utils = new HippoUtilsService();
        psBuilder = new ProductSearchBuilder();
    }

    //TODO Decide if we need this method
    public IKnowModule getModule(ICentre doc, String location) {
        return getIKnowModule(doc, location);
    }

    //TODO: Change iCentre to IKnow
    public IKnowModule getIKnowModule(ICentre document, String location){
        IKnowModule module = new IKnowModule ();
        //TODO IknowModule

        if (!Contract.isEmpty(document.getIknowTitle())){
            //TODO bundle
            module.setTitle("Help and Advice");
        } else {
            module.setTitle(document.getIknowTitle());
        }

        module.setDescription(document.getIknowDescription());

        FlatLink link = new FlatLink();

        //TODO get prodTypes from Labels
        //https://www.visitscotland.com/info/see-do/search-results?prodtypes=cate%2Cacti%2Cattr%2Creta&src_awards__0=qaiknowscotland&loc=Edinburgh&locplace=4161&locprox=0
        link.setLink(psBuilder.productTypes("cate,acti,attr,reta").award("qaiknowscotland").location(location).build());
        //TODO bundle
        link.setLabel("iKnow Partners in this area");

        module.setLink(link);

        return module;
    }
}
