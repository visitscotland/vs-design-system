package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.ICentre;
import com.visitscotland.brmx.beans.capabilities.Linkable;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.ICentreModule;
import com.visitscotland.brmx.beans.mapping.megalinks.EnhancedLink;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ICentreFactory {

    private static final Logger logger = LoggerFactory.getLogger(ICentreFactory.class);

    private final HippoUtilsService utils;
    private final DMSDataService dmsData;
    private final LinkModulesFactory linkFactory;


    public ICentreFactory() {
        this.utils = new HippoUtilsService();
        this.dmsData = new DMSDataService();
        this.linkFactory = new LinkModulesFactory();
    }

    public ICentreFactory(HippoUtilsService utils, DMSDataService dmsData, LinkModulesFactory linksFactory) {
        this.utils = utils;
        this.dmsData = dmsData;
        this.linkFactory = linksFactory;
    }


    /**
     * Builds an iCentre module when there is enough data to build it or null when there is not.
     *
     * @param doc
     * @param locale
     * @param location
     * @return ICentreModule w
     */
    public ICentreModule getModule(ICentre doc, Locale locale, String location) {
        //TODO: This Content Modelling added many improvements but in order to verify the improvements some classes
        // needed to be stubbed. In order of progressing with the work, this code was committed to develop but marked
        // as unfinished. The main reason for not finishing the work was that the requirements were completely defined.
        // TODO: Parting from the previous statement, we couldn't complete the Unit Testing, so they must be added once
        // the requirements are confirmed
        logger.error("The implementation of this module is just a POC. Please correct and complete the implementation");

        ICentreModule module = new ICentreModule();
        ProductSearchBuilder psBuilder = new ProductSearchBuilder();

        List<FlatLink> vicList = new ArrayList<>();
        if (!Contract.isEmpty(location)) {
            psBuilder.location(location);

            psBuilder.productTypes("serv");
            psBuilder.category("vics");
            psBuilder.sortBy("alpha");
            String dmsQuery = psBuilder.build();

            JsonNode node = dmsData.searchResults(null, null, dmsQuery);

            for (JsonNode child : node) {
                String label = child.get("properties").get("name").asText();
                //TODO: create url to the DMS product page
                String url = child.get("properties").get("id").asText();

                vicList.add(new FlatLink(label, url));
            }

            module.setiCentreList(vicList);
        } else {
            //TODO Flatlink to https://www.visitscotland.com/about/practical-information/vic/  with label "Find a tourist information centre"
        }
        //The module disappears when there is no iCentres in the area
        if (vicList.size() == 0) {
            return null;
        } else {
            if (Contract.isEmpty(doc.getTitle())) {
                //TODO: Resource Bundle
                module.setTitle("A tip from your local expert");
            } else {
                module.setTitle(doc.getTitle());
            }

            //TODO: add description before list iCentres
            if (doc.getImage() != null) {
                module.setImage(new FlatImage(doc.getImage(), locale));
            }

            if (doc.getQuote() != null) {

                //TODO remove index
                module.setQuote(doc.getQuote().getQuote());
                module.setQuoteAuthorName(doc.getQuote().getAuthor());
                module.setQuoteAuthorTitle(doc.getQuote().getRole());

                if (doc.getQuote().getImage() != null) {
                    module.setQuoteImage(new FlatImage(doc.getQuote().getImage(), locale));
                }

                if (doc.getQuote().getProduct() != null) {
                    EnhancedLink link = linkFactory.createEnhancedLink((Linkable) doc.getQuote().getProduct(), locale);
                    module.setQuoteLink(link);
                    if (module.getImage() == null) {
                        module.setImage(link.getImage());
                    }
                }

                if (module.getImage() == null) {
                    FlatImage image = new FlatImage();
                    //TODO: Use an image from the CMS
                    image.setExternalImage("https://cimg.visitscotland.com/cms-images/about/458950/fort-william-visitscotland-icentre-sign?size=sm");
                    module.setImage(image);
                }
            }
        }

        return module;
    }
}
