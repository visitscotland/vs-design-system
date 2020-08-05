package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.ICentre;
import com.visitscotland.brmx.beans.Image;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.ICentreModule;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.utils.Contract;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ICentreFactory {

    private final HippoUtilsService utils;
    private final ProductSearchBuilder psBuilder;
    private final DMSDataService dmsData;

    public ICentreFactory(){
        utils = new HippoUtilsService();
        psBuilder = new ProductSearchBuilder();
        dmsData = new DMSDataService();
    }

    public ICentreModule getModule(ICentre doc, Locale locale, String location) {
        ICentreModule module = new ICentreModule();

        //TODO: This is a POC. It might need to be tidied up/refactor
        ProductSearchBuilder psBuilder = new ProductSearchBuilder();
        //https://www.visitscotland.com/info/see-do/search-results?loc=Scotland&locplace=&locprox=0&cat=vics

        if (location != null){
            psBuilder.location(location);
        }
        psBuilder.category("vics");
        psBuilder.sortBy("alpha");
        String dmsQuery = psBuilder.build();

        JsonNode node = dmsData.searchResults(psBuilder, null);

        List<FlatLink> vicList = new ArrayList<>();
        for (JsonNode child  :node) {
            String label = child.get("properties").get("name").asText();
            String url = child.get("properties").get("id").asText();

            vicList.add(new FlatLink(label, url));
        }

        module.setiCentreList(vicList);

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

                if (doc.getQuote().getProductId() != null){
                    try {
                        JsonNode product = dmsData.productCard(doc.getQuote().getProductId(), locale);
                        if (product != null) {
                            module.setImage(new FlatImage(product));
                        }
                    } catch (IOException e) {
                        //TODO: Capture properly: logger and stuff
                        e.printStackTrace();
                    }
                } else {
                    //TODO: Decide on one of the options
//                    List<HippoBean> links = (List<HippoBean>) doc.getICentreQuote().getChildBeans("hippo:facetselect");
                    //TODO: Option 2 = ;
                }
            }

            if (module.getImage() == null) {
                FlatImage image = new FlatImage();
                //TODO: Use an image from the CMS
                image.setExternalImage("https://cimg.visitscotland.com/cms-images/about/458950/fort-william-visitscotland-icentre-sign?size=sm");
                module.setImage(image);
            }

        }

        return module;
    }
}
