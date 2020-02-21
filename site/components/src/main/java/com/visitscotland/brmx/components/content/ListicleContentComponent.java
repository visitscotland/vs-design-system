package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.FlatListicle;
import com.visitscotland.brmx.utils.CommonUtils;
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
            List<FlatLink> links = new ArrayList<>();

            //Set the image
            if (listicleItem.getListicleItemImage()!=null){
                if (listicleItem.getListicleItemImage() instanceof InstagramImage){
                    InstagramImage instagramLink = (InstagramImage) listicleItem.getListicleItemImage();
                    try {
                        URL instagramInformation = new URL("https://api.instagram.com/oembed/?url=http://instagr.am/p/"+instagramLink.getId());

                        String response = CommonUtils.request(instagramInformation.toString());
                        if (response!=null){
                            JSONObject json = new JSONObject(response);
                            String credit = json.has("author_name")?json.getString("author_name"):"";
                            String link = "https://www.instagram.com/p/"+instagramLink.getId()+"/media";
                            model.setImage(new FlatImage(link, instagramLink.getCaption(), credit, instagramLink.getCaption(), FlatImage.Source.INSTAGRAM));
                        } else {
                            model.setErrorMessage("The Instagram id is not valid");
                            logger.warn(CommonUtils.contentIssue("The Instagram id {} is not valid, Listicle = {} - {}",
                                    instagramLink.getId(), listicle.getPath(), listicleItem.getTitle()));
                        }

                    } catch (IOException e) {
                        model.setErrorMessage("Error while accessing Instagram: " + e.getMessage());
                        logger.error("Error while accessing Instagram", e);
                    }
                }else{
                    if (listicleItem.getListicleItemImage() instanceof Image){
                        Image cmsImage = (Image)listicleItem.getListicleItemImage();
                        if (cmsImage != null) {
                            FlatImage image = new FlatImage();

                            model.setImage(new FlatImage(cmsImage, cmsImage.getAltText(), cmsImage.getCredit(), cmsImage.getDescription()));
                        }
                    }
                }
             }

             //Set the main product
            if (listicleItem.getListicleItem()!=null){
                //TODO create a case?
                if (listicleItem.getListicleItem() instanceof DMSLink){
                    DMSLink dmsLink = (DMSLink) listicleItem.getListicleItem();
                    JSONObject product = null;
                    try {
                        product = CommonUtils.getProduct(dmsLink.getProduct(), request.getLocale());
                    } catch (IOException e) {
                        model.setErrorMessage("Error while querying the DMS: " + e.getMessage());
                        logger.error(String.format("Error while querying the DMS for {}, Listicle item {}: {}",
                                listicle.getName(), model.getIndex(), e.getMessage()));

                    }
                    if (product == null){
                        model.setErrorMessage("The product id does not exists in the DMS");
                        logger.warn(CommonUtils.contentIssue("The product's id  wasn't provided for {}, Listicle = {} - {}",
                                dmsLink.getProduct(), listicle.getPath(), listicleItem.getTitle()));
                    } else {

                        List<String> facilities = new ArrayList<>();
                        FlatLink cta = new FlatLink(dmsLink.getLabel(), product.getString(URL));
                        links.add(cta);

                        model.setLocation(product.getString(LOCATION));

                      if (model.getImage()!=null){
                          FlatImage image = new FlatImage();
                          image.setExternalImage(product.getString(IMAGE));
                          //TODO: SET ALT-TEXT, CREDITS AND DESCRIPTION
                          model.setImage(image);
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
                        FlatLink cta = new FlatLink(productSearchLink.getLabel(), productSearchLink.getLabel());
                        links.add(cta);
                    }else{
                        if (listicleItem.getListicleItem() instanceof ExternalLink){
                            ExternalLink externalLink = (ExternalLink) listicleItem.getListicleItem();
                            FlatLink cta = new FlatLink(externalLink.getLabel(), externalLink.getLabel());
                            links.add(cta);
                        }
                        else{
                            if (listicleItem.getListicleItem() instanceof CMSLink){
                                CMSLink cmsLink = (CMSLink) listicleItem.getListicleItem();
                                FlatLink cta = new FlatLink(cmsLink.getLabel(), cmsLink.getLabel());
                                links.add(cta);
                            }

                        }
                    }

                }
                model.setCtaLinks(links);
            }

            }
        }
}