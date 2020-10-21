package com.visitscotland.brmx.components.content.factory;

import com.visitscotland.brmx.beans.IKnow;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.IKnowModule;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IKnowFactory {

    private static final Logger logger = LoggerFactory.getLogger(ICentreFactory.class);

    private final HippoUtilsService utils;

    public IKnowFactory(){
        this(new HippoUtilsService());
    }

    public IKnowFactory(HippoUtilsService utils){
        this.utils = utils;
    }

    //TODO Decide if we need this method
    public IKnowModule getModule(IKnow doc, String location) {
        return getIKnowModule(doc, location);
    }

    public IKnowModule getIKnowModule(IKnow document, String location){
        //TODO: This Content Modelling added many improvements but in order to verify the improvements some classes
        // needed to be stubbed. In order of progressing with the work, this code was committed to develop but marked
        // as unfinished. The main reason for not finishing the work was that the requirements were completely defined.
        // TODO: Parting from the previous statement, we couldn't complete the Unit Testing, so they must be added once
        // the requirements are confirmed
        logger.error("The implementation of this module is just a POC. Please correct and complete the implementation");

        IKnowModule module = new IKnowModule ();

        if (Contract.isEmpty(document.getTitle())){
            //TODO bundle
            module.setTitle("Help and Advice");
        } else {
            module.setTitle(document.getTitle());
        }

        module.setDescription(document.getDescription());

        FlatLink link = new FlatLink();

        //TODO get prodTypes from Labels (Configuration)
        //https://www.visitscotland.com/info/see-do/search-results?prodtypes=cate%2Cacti%2Cattr%2Creta&src_awards__0=qaiknowscotland&loc=Edinburgh&locplace=4161&locprox=0
        link.setLink(new ProductSearchBuilder().productTypes("cate,acti,attr,reta").award("qaiknowscotland").location(location).build());
        //TODO bundle
        link.setLabel("iKnow Partners in this area");

        module.setLink(link);

        return module;
    }
}
