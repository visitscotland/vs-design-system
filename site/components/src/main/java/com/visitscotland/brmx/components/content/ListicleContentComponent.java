package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;

import com.visitscotland.brmx.beans.dms.LocationObject;
import com.visitscotland.brmx.beans.mapping.*;
import com.visitscotland.brmx.beans.mapping.Coordinates;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.Properties;
import com.visitscotland.brmx.utils.LocationLoader;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;
import java.util.*;

public class ListicleContentComponent extends PageContentComponent<Listicle> {

    private static final Logger logger = LoggerFactory.getLogger(ListicleContentComponent.class);

    private final String LISTICLE_ITEMS = "items";

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);

        generateItems(request, getDocument(request));
    }

    /**
     * @param request
     * @param listicle
     */
    private void generateItems(HstRequest request, Listicle listicle) {
        final String LOCATION = "locationName";
        final String LATITUDE = "latitude";
        final String LONGITUDE = "longitude";
        final String FACILITIES = "facilities";
        final String IMAGE = "image";
        final Map<String ,FlatListicle> items =  new LinkedHashMap<>();


        //TODO:separate image, main product and optional cta in different methods ?
        for (ListicleItem listicleItem : listicle.getItems()) {
            FlatListicle model = new FlatListicle(listicleItem);
            List<FlatLink> links = new ArrayList<>();

            //Set the image
            if (listicleItem.getListicleItemImage() != null) {
                if (listicleItem.getListicleItemImage() instanceof InstagramImage) {
                    InstagramImage instagramLink = (InstagramImage) listicleItem.getListicleItemImage();
                    try {
                        URL instagramInformation = new URL("https://api.instagram.com/oembed/?url=http://instagr.am/p/" + instagramLink.getId());

                        String response = CommonUtils.request(instagramInformation.toString());
                        if (response != null) {
                            JSONObject json = new JSONObject(response);
                            String credit = json.has("author_name") ? json.getString("author_name") : "";
                            String link = "https://www.instagram.com/p/" + instagramLink.getId();
                            //TODO: This causes a 301 (redirect). Find the way of fixing this.
                            //TODO size for Instagram is large for the showcase but we need to fix that large for desktop, medium tablet and small mobile
                            String image = "https://www.instagram.com/p/" + instagramLink.getId() + "/media?size=l";
                            model.setImage(new FlatImage(image, instagramLink.getCaption(), credit, instagramLink.getCaption(), FlatImage.Source.INSTAGRAM, link));
                        } else {
                            model.setErrorMessage("The Instagram id is not valid");
                            logger.warn(CommonUtils.contentIssue("The Instagram id %s is not valid, Listicle = %s - %s",
                                    instagramLink.getId(), listicle.getPath(), listicleItem.getTitle()));
                        }

                    } catch (IOException e) {
                        model.setErrorMessage("Error while accessing Instagram: " + e.getMessage());
                        logger.error("Error while accessing Instagram", e);
                    }
                } else {
                    if (listicleItem.getListicleItemImage() instanceof Image) {
                        Image cmsImage = (Image) listicleItem.getListicleItemImage();
                        if (cmsImage != null) {
                            FlatImage image = new FlatImage(cmsImage, cmsImage.getAltText(), cmsImage.getCredit(), cmsImage.getDescription());
                            LocationObject location = LocationLoader.getLocation(cmsImage.getLocation(), request.getLocale());
                            image.setCoordinates(new Coordinates(location.getLatitude(),location.getLongitude()));
                            model.setImage(image);
                        }
                    }
                }
            }

            //Set the main product
            if (listicleItem.getListicleItem() != null) {
                if (listicleItem.getListicleItem() instanceof DMSLink) {
                    DMSLink dmsLink = (DMSLink) listicleItem.getListicleItem();
                    JSONObject product = null;
                    try {
                        product = CommonUtils.getProduct(dmsLink.getProduct(), request.getLocale());
                        if (product == null) {
                            model.setErrorMessage("The product id does not exists in the DMS");
                            logger.warn(CommonUtils.contentIssue("The product's id  wasn't provided for %s, Listicle = %s - %s",
                                    dmsLink.getProduct(), listicle.getPath(), listicleItem.getTitle()));
                        } else {

                            List<String> facilities = new ArrayList<>();
                            model.setLocation(product.getString(LOCATION));

                            if (model.getImage() == null) {
                                FlatImage image = new FlatImage();
                                image.setExternalImage(product.getString(IMAGE));
                                Coordinates coordinates = new Coordinates(product.getDouble(LATITUDE),product.getDouble(LONGITUDE));
                                image.setCoordinates(coordinates);
                                //TODO: SET ALT-TEXT, CREDITS AND DESCRIPTION
                                model.setImage(image);
                            }else{
                                if (model.getImage().getSource().equals(FlatImage.Source.INSTAGRAM)){
                                    Coordinates coordinates = new Coordinates(product.getDouble(LATITUDE),product.getDouble(LONGITUDE));
                                    model.getImage().setCoordinates(coordinates);
                                }
                            }

                            for (Object facility : product.getString(FACILITIES).split(",")) {
                                facilities.add(facility.toString());
                            }
                            model.setFacilities(facilities);
                        }
                    } catch (IOException e) {
                        model.setErrorMessage("Error while querying the DMS: " + e.getMessage());
                        logger.error(String.format("Error while querying the DMS for %s, Listicle item %s: 5s",
                                listicle.getName(), model.getIndex(), e.getMessage()));
                    }
                }

                links.add(createLink(request, listicleItem.getListicleItem()));
            }

            //Set Extra Links
            for (HippoCompound compound: listicleItem.getExtraLinks()) {
                links.add(createLink(request, compound));
            }

            model.setCtaLinks(links);
            items.put(model.getIdentifier(), model);
        }

        request.setAttribute(LISTICLE_ITEMS, items);
    }

    /**
     *
     * @param request
     * @param item
     * @return
     */
    private FlatLink createLink(HstRequest request, HippoCompound item) {
        final String URL = "url";

        if (item instanceof DMSLink) {
            DMSLink dmsLink = (DMSLink) item;
            try {
                JSONObject product = CommonUtils.getProduct(dmsLink.getProduct(), request.getLocale());
                if (product == null) {
                    logger.warn(CommonUtils.contentIssue("There is no product with the id '%s', (%s) ",
                            dmsLink.getProduct(), getDocument(request).getPath()));
                } else {
                    //TODO build the link for the DMS product properly
                    return new FlatLink(this.getCtaLabel(dmsLink.getLabel(), request.getLocale()), Properties.VS_DMS_PRODUCTS+product.getString(URL));
                }
            } catch (IOException e) {
                logger.error(String.format("Error while querying the DMS for '%s', (%s)",
                        dmsLink.getProduct(), getDocument(request).getPath()));
            }
        } else if (item instanceof ProductSearchLink) {
            ProductSearchLink productSearchLink = (ProductSearchLink) item;
            //TODO build the PSR url for the CTA in a reusable class
            String productType = productSearchLink.getSearch().getProductType();
            String categoriesParameter="";
            String facilitiesParameter="";
            String awardssParameter="";
            String starsParameter="";
            if (productSearchLink.getSearch().getDmsCategories()!=null){
                for (String category : productSearchLink.getSearch().getDmsCategories()){
                    categoriesParameter=categoriesParameter+"&cat="+category;
                 }
            }
            if (productSearchLink.getSearch().getDmsFacilities()!=null){
                for (String fac : productSearchLink.getSearch().getDmsFacilities()){
                    facilitiesParameter=facilitiesParameter+"&fac_id="+fac;
                }
            }
            if (productSearchLink.getSearch().getDmsAwards()!=null){
                for (String aw : productSearchLink.getSearch().getDmsAwards()){
                    awardssParameter=awardssParameter+"&src_awards__0="+aw;
                }
            }
            if (productSearchLink.getSearch().getOfficialrating()!=null){
                for (String star : productSearchLink.getSearch().getOfficialrating()){
                    starsParameter=starsParameter+"&grade="+star;
                }
            }

            String psr = Properties.VS_DMS_PRODUCTS + "/info/accommodation/search-results?debug&locplace="
                    +productSearchLink.getSearch().getLocation()+"&locprox=0&prodtypes="
                    +productType+categoriesParameter+facilitiesParameter+awardssParameter+starsParameter;

            return  new FlatLink(this.getCtaLabel(productSearchLink.getLabel(), request.getLocale()), psr);

        } else if (item instanceof ExternalLink) {
            ExternalLink externalLink = (ExternalLink) item;
            return new FlatLink(this.getCtaLabel(externalLink.getLabel(), request.getLocale()), externalLink.getLabel());

        } else if (item instanceof CMSLink) {
            CMSLink cmsLink = (CMSLink) item;
            return new FlatLink(this.getCtaLabel(cmsLink.getLabel(), request.getLocale()), cmsLink.getLabel());
        }

        return null;
    }
}