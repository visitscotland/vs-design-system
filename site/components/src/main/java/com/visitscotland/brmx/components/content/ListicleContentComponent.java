package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.FlatListicle;
import com.visitscotland.brmx.utils.Properties;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.onehippo.cms7.essentials.components.EssentialsContentComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ListicleContentComponent extends EssentialsContentComponent {

    private static final Logger logger = LoggerFactory.getLogger(ListicleContentComponent.class);
    private final String ROOT_SITE = "/site/";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        generateItems(request, (Listicle) request.getAttribute("document"));
    }


    /**
     *
     * @param request
     * @param listicle
     */
    private void generateItems(HstRequest request, Listicle listicle){
        String path= listicle.getPath().substring(listicle.getPath().indexOf(ROOT_SITE),listicle.getPath().indexOf("/content/content")).replace(ROOT_SITE,"");
        request.setAttribute("path", path);

        final String LOCATION = "locationName";
        final String URL = "url";
        final String FACILITIES = "facilities";
        final String IMAGE = "image";

        //TODO:separate image, main product and optional cta in different methods ?
        for (ListicleItem listicleItem : listicle.getItems()) {
            FlatListicle model = new FlatListicle(listicleItem);
            FlatImage image = new FlatImage();
            List<FlatLink> links = new ArrayList<>();
            if (listicleItem.getListicleItemImage()!=null){
                if (listicleItem.getListicleItemImage() instanceof InstagramImage){
                    InstagramImage instagramLink = (InstagramImage) listicleItem.getListicleItemImage();
                    try {
                        //TODO add instagram ID to the url
                        URL instagramInformation = new URL("https://api.instagram.com/oembed/?url=http://instagr.am/p/B7i9Ne7gsUR&hidecaption=true");

                        JSONObject json = new JSONObject(request(instagramInformation.toString()));
                        //TODO create a constructor to fill the image for listicle and itineraries and check if author alwasy come form instagram
                        image.setCredit(json.getString("author_name"));
                        image.setExternalImage("https://www.instagram.com/p/"+instagramLink.getId()+"/media");
                        image.setDescription(instagramLink.getCaption());
                        model.setImage(image);

                    } catch (IOException e) {
                        model.setErrorMessage("Error while querying the DMS: " + e.getMessage());
                        logger.error("Error while querying the DMS for " + listicle.getName() + ", Listicle item " + model.getIndex() + ": " + e.getMessage());

                    }
                }else{
                    if (listicleItem.getListicleItemImage() instanceof Image){
                        Image cmsImage = (Image)listicleItem.getListicleItemImage();
                        if (cmsImage != null) {
                            //TODO create a method to fill the image for listicle and itineraries
                            image.setCmsImage(cmsImage);
                            image.setAltText(cmsImage.getAltText());
                            image.setCredit(cmsImage.getCredit());
                            image.setDescription(cmsImage.getDescription());
                            model.setImage(image);
                        }
                    }
                }
             }

             //main product
            if (listicleItem.getListicleItem()!=null){
                //TODO create a case?
                if (listicleItem.getListicleItem() instanceof DMSLink){
                    DMSLink dmsLink = (DMSLink) listicleItem.getListicleItem();
                    JSONObject product = null;
                    try {
                        product = getProduct(dmsLink.getProduct(), request.getLocale());
                    } catch (IOException e) {
                        model.setErrorMessage("Error while querying the DMS: " + e.getMessage());
                        logger.error("Error while querying the DMS for " + listicle.getName() + ", Listicle item " + model.getIndex() + ": " + e.getMessage());

                    }
                    if (product == null){
                        model.setErrorMessage("The product id does not exists in the DMS");
                        logger.warn("CONTENT The product's id  wasn't provided for " + listicleItem.getName() + ", Stop " + model.getIndex());
                    } else {

                        List<String> facilities = new ArrayList<>();
                        FlatLink cta = new FlatLink(dmsLink.getLabel(), product.getString(URL));
                        links.add(cta);
                        model.setCtaLinks(links);

                        model.setLocation(product.getString(LOCATION));

                      if (image!=null){
                          image.setExternalImage(product.getString(IMAGE));
                          //TODO: SET ALT-TEXT, CREDITS AND DESCRIPTION
                      }


                        for (Object facility:  product.getString(FACILITIES).split(",")) {
                            facilities.add(facility.toString());
                        }

                        model.setFacilities(facilities);

                    }
                }else{
                    if (listicleItem.getListicleItem() instanceof ProductSearchLink){
                        ProductSearchLink productSearchLink = (ProductSearchLink) listicleItem.getListicleItem();
                        String psLink = "";
                        //TODO build the PSR url for the CTA in a reusable class
                        String productType= productSearchLink.getSearch().getProducttype();
                        String[] categories= productSearchLink.getSearch().getDmscategories();
                        Long distance= productSearchLink.getSearch().getDistance();

                       /* model.setCta(psLink);
                        model.setCtaLabel(productSearchLink.getLabel());*/

                    }else{
                        if (listicleItem.getListicleItem() instanceof ExternalLink){
                            ExternalLink externalLink = (ExternalLink) listicleItem.getListicleItem();
                      /*      model.setCtaLabel(externalLink.getLabel());
                            model.setCta(externalLink.getLink());*/
                        }
                        else{
                            if (listicleItem.getListicleItem() instanceof CMSLink){
                                CMSLink cmsLink = (CMSLink) listicleItem.getListicleItem();
                                /*model.setCtaLabel(cmsLink.getLabel());*/
                            }

                        }
                    }

                }

            }

            }
        }

    //TODO this method has already been defined in ItinerariesContentComponet - refactor, utils class?
    private JSONObject getProduct(String productId, Locale locale) throws IOException {

        String body = request(Properties.VS_DMS_PRODUCTS + "/data/product-search/map?prod_id=" + productId+ "&locale="+locale.getLanguage());
        JSONObject json = new JSONObject(body);
        JSONArray data = (JSONArray) json.get("data");

        return data.getJSONObject(0);
        }

    /**
     * Request a page and return the body as String
     */
    //TODO this method has already been defined in ItinerariesContentComponet - refactor, utils class?
    private static String request(String url) throws IOException {
        final BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        final StringBuilder sb = new StringBuilder();
        int cp;

        while ((cp = br.read()) != -1) {
            sb.append((char) cp);
        }

        return sb.toString();
    }
}