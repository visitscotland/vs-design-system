package com.visitscotland.brmx.components.content;

import com.visitscotland.brmx.beans.*;

import com.visitscotland.brmx.beans.dms.LocationObject;
import com.visitscotland.brmx.beans.mapping.*;
import com.visitscotland.brmx.beans.mapping.Coordinates;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.ProductSearchBuilder;
import com.visitscotland.brmx.utils.Properties;
import com.visitscotland.brmx.utils.LocationLoader;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.*;
import java.io.*;
import java.util.*;

public class ListicleContentComponent extends PageContentComponent<Listicle> {

    private static final Logger logger = LoggerFactory.getLogger(ListicleContentComponent.class);

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        generateIndexPage(request);
        generateItems(request, getDocument(request));
    }

    /**
     * @param request HstRequest
     * @param listicle listicle document
     */
    private void generateItems(HstRequest request, Listicle listicle) {
        final String LISTICLE_ITEMS = "items";
        final String ADDRESS = "address";
        final String LOCATION = "city";
        final String LATITUDE = "latitude";
        final String LONGITUDE = "longitude";
        final String FACILITIES = "keyFacilities";
        final String NAME = "name";
        final String IMAGE = "images";
        final Map<String ,FlatListicle> items =  new LinkedHashMap<>();

        int index = listicle.getDescOrder()?listicle.getItems().size():1;


        //TODO:separate image, main product and optional cta in different methods ?
        for (ListicleItem listicleItem : listicle.getItems()) {
            List<String> errors = new ArrayList<>();
            FlatListicle model = new FlatListicle(listicleItem);
            List<FlatLink> links = new ArrayList<>();
            FlatImage flatImage = null;
            String location = null;

            model.setIndex(listicle.getDescOrder()?index--:index++);

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
                            String instagramImage = "https://www.instagram.com/p/" + instagramLink.getId() + "/media?size=l";
                            flatImage = new FlatImage(instagramImage, instagramLink.getCaption(), credit, instagramLink.getCaption(), FlatImage.Source.INSTAGRAM, link);
                            if (instagramLink.getLocation()!= null && !instagramLink.getLocation().isEmpty() && !(listicleItem.getListicleItem() instanceof DMSLink)){
                                location = instagramLink.getLocation();
                                LocationObject locationObject = LocationLoader.getLocation(instagramLink.getLocation(), request.getLocale());
                                if (locationObject != null){
                                    flatImage.setCoordinates(new Coordinates(locationObject.getLatitude(),locationObject.getLongitude()));
                                }
                            }
                        } else {
                            errors.add("The Instagram id is not valid");
                            logger.warn(CommonUtils.contentIssue("The Instagram id %s is not valid, Listicle = %s - %s",
                                    instagramLink.getId(), listicle.getPath(), listicleItem.getTitle()));
                        }

                    } catch (IOException e) {
                        errors.add("Error while accessing Instagram: " + e.getMessage());
                        logger.error("Error while accessing Instagram", e);
                    }
                } else {
                    if (listicleItem.getListicleItemImage() instanceof Image) {
                        Image cmsImage = (Image) listicleItem.getListicleItemImage();
                        if (cmsImage != null) {
                            flatImage = CommonUtils.getTranslatedImage(cmsImage,request.getLocale());
                            CommonUtils.checkImageErrors(flatImage,request.getLocale(),logger,errors);
                            LocationObject locationObject = LocationLoader.getLocation(cmsImage.getLocation(), request.getLocale());
                            if (locationObject!=null) {
                                flatImage.setCoordinates(new Coordinates(locationObject.getLatitude(), locationObject.getLongitude()));
                                if (listicleItem.getListicleItem() != null && !(listicleItem.getListicleItem() instanceof DMSLink)){
                                    location = locationObject.getName();
                                }
                            }
                        }
                    }
                }
            }

            //Set the main product
            if (listicleItem.getListicleItem() != null) {
                if (listicleItem.getListicleItem() instanceof DMSLink) {
                    DMSLink dmsLink = (DMSLink) listicleItem.getListicleItem();
                    JSONObject product;
                    try {
                        product = CommonUtils.getProduct(dmsLink.getProduct(), request.getLocale());
                        if (product == null) {
                            errors.add("The product id does not exists in the DMS");
                            logger.warn(CommonUtils.contentIssue("The product's id  wasn't provided for %s, Listicle = %s - %s",
                                    dmsLink.getProduct(), listicle.getPath(), listicleItem.getTitle()));
                        } else {

                            List<JSONObject> facilities = new ArrayList<>();
                            if (product.has(ADDRESS)){
                                JSONObject address = product.getJSONObject(ADDRESS);
                                if (location== null && address.has(LOCATION)) {
                                    location = address.getString(LOCATION);
                                }
                            }

                            if (model.getImage() == null &&  product.has(IMAGE)) {
                                JSONArray dmsImageList = product.getJSONArray(IMAGE);
                                flatImage = new FlatImage( dmsImageList.getJSONObject(0),product.getString(NAME));
                                if (product.has(LATITUDE) && product.has(LONGITUDE)){
                                    Coordinates coordinates = new Coordinates(product.getDouble(LATITUDE), product.getDouble(LONGITUDE));
                                    flatImage.setCoordinates(coordinates);
                                }

                            }else{
                                if (model.getImage().getCoordinates()==null){
                                    Coordinates coordinates = new Coordinates(product.getDouble(LATITUDE),product.getDouble(LONGITUDE));
                                    model.getImage().setCoordinates(coordinates);
                                }
                            }

                            if (product.has(FACILITIES)){
                                JSONArray keyFacilitiesList = product.getJSONArray(FACILITIES);
                                if (keyFacilitiesList!=null){
                                    for (int i = 0; i < keyFacilitiesList.length(); i++) {
                                        facilities.add(keyFacilitiesList.getJSONObject(i));
                                     }
                                 }
                                model.setFacilities(facilities);
                            }

                        }
                    } catch (IOException e) {
                        errors.add("Error while querying the DMS: " + e.getMessage());
                        logger.error(String.format("Error while querying the DMS for %s, Listicle item %s: 5s",
                                listicle.getName(), model.getIndex(), e.getMessage()));
                    }
                }

                links.add(createLink(request, listicleItem.getListicleItem()));

            }

            //Set Extra Links
            for (HippoCompound compound : listicleItem.getExtraLinks()) {
                links.add(createLink(request, compound));
            }
            if (listicleItem.getSubtitle() == null || listicleItem.getSubtitle().isEmpty()) {
                model.setSubTitle(location);
            }else{
                model.setSubTitle(listicleItem.getSubtitle());
            }
            model.setImage(flatImage);
            model.setLinks(links);
            model.setErrorMessages(errors);
            items.put(model.getIdentifier(), model);
        }

        request.setAttribute(LISTICLE_ITEMS, items);
    }

    /**
     * @param request HstRequest
     * @param item Compound for DMSLink, PSRLink , External Link or CMS link
     * @return FlatLink
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
                    return new FlatLink(this.getCtaLabel(dmsLink.getLabel(), request.getLocale()), Properties.VS_DMS_SERVICE + product.getString(URL));
                }
            } catch (IOException e) {
                logger.error(String.format("Error while querying the DMS for '%s', (%s)",
                        dmsLink.getProduct(), getDocument(request).getPath()));
            }
        } else if (item instanceof ProductSearchLink) {
            ProductSearchLink productSearchLink = (ProductSearchLink) item;
            ProductSearchBuilder psb = new ProductSearchBuilder()
                    .productType(productSearchLink.getSearch()).locale(request.getLocale());

            return new FlatLink(this.getCtaLabel(productSearchLink.getLabel(), request.getLocale()), psb.build());

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
