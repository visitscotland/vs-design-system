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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ICentreFactory {

    private final HippoUtilsService utils;
    private final ProductSearchBuilder psBuilder;
    private final DMSDataService dmsData;
    private final LinkModulesFactory linkFactory;

    public ICentreFactory(){
        utils = new HippoUtilsService();
        psBuilder = new ProductSearchBuilder();
        dmsData = new DMSDataService();
        linkFactory  = new LinkModulesFactory();
    }

    public ICentreModule getModule(ICentre doc, Locale locale, String location) {
        ICentreModule module = new ICentreModule();

        //TODO: This is a POC. It might need to be tidied up/refactor
        ProductSearchBuilder psBuilder = new ProductSearchBuilder();
        //https://www.visitscotland.com/info/see-do/search-results?loc=Scotland&locplace=&locprox=0&cat=vics
        List<FlatLink> vicList = new ArrayList<>();
        if (location != null){
            psBuilder.location(location);

            psBuilder.productTypes("serv");
            psBuilder.category("vics");
            psBuilder.sortBy("alpha");
            String dmsQuery = psBuilder.build();

            JsonNode node = dmsData.searchResults(psBuilder, null, dmsQuery);


            for (JsonNode child  :node) {
                String label = child.get("properties").get("name").asText();
                //TODO: create url to the DMS product page
                String url = child.get("properties").get("id").asText();

                vicList.add(new FlatLink(label, url));
            }

            module.setiCentreList(vicList);
        }else{
            //TODO Flatlink to https://www.visitscotland.com/about/practical-information/vic/  with label "Find a tourist information centre"
        }
        //The module disappears when there is no iCentres in the area
        if (vicList.size() == 0) {
            return null;
        } else {
            if (Contract.isEmpty(doc.getTitle())){
                //TODO: Resource Bundle
                module.setTitle("A tip from your local expert");
            } else {
                module.setTitle(doc.getTitle());
            }

            //TODO: add description before list iCentres

            if (doc.getImage() != null){
                module.setImage(new FlatImage(doc.getImage(), locale));
            }

            if (doc.getQuote() != null) {

                //TODO remove index
                module.setQuote(doc.getQuote().getQuote());
                module.setQuoteAuthorName(doc.getQuote().getAuthor());
                module.setQuoteAuthorTitle(doc.getQuote().getRole());

                if (doc.getQuote().getImage() != null) {
                    module.setQuoteImage(new FlatImage(doc.getQuote().getImage() , locale));
                }
                //TODO the quote could link to any shared document
                if (doc.getQuote().getProduct()!=null){
                   EnhancedLink link = linkFactory.createEnhancedLink((Linkable) doc.getQuote().getProduct(),locale);
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
