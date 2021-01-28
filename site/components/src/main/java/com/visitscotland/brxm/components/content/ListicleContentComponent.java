package com.visitscotland.brxm.components.content;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.beans.*;
import com.visitscotland.brxm.beans.dms.LocationObject;
import com.visitscotland.brxm.beans.mapping.Coordinates;
import com.visitscotland.brxm.beans.mapping.FlatImage;
import com.visitscotland.brxm.beans.mapping.FlatLink;
import com.visitscotland.brxm.beans.mapping.FlatListicle;
import com.visitscotland.brxm.cfg.SpringContext;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.utils.CommonUtils;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ListicleContentComponent extends PageContentComponent<Listicle> {

    private static final Logger logger = LoggerFactory.getLogger(ListicleContentComponent.class);

    private LinkService linksService;
    private LocationLoader locationLoader;
    private DMSDataService dmsData;

    public ListicleContentComponent(){
        logger.debug("ListicleContentComponent initialized");

        linksService = SpringContext.getLinkService();
        locationLoader = SpringContext.getLocationLoader();
        dmsData = SpringContext.getDMSDataService();
    }

    @Override
    public void doBeforeRender(HstRequest request, HstResponse response) {
        super.doBeforeRender(request, response);
        generateItems(request, getDocument(request));
    }

    /**
     * @param request HstRequest
     * @param listicle listicle document
     */
    private void generateItems(HstRequest request, Listicle listicle) {
        final String LISTICLE_ITEMS = "items";
        final String OTYML = "otyml";
        final String ADDRESS = "address";
        final String LOCATION = "city";
        final String LATITUDE = "latitude";
        final String LONGITUDE = "longitude";
        final List<FlatListicle> items =  new ArrayList<>();

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
            //TODO Use ImageFactory
            if (listicleItem.getListicleItemImage() != null) {
                if (listicleItem.getListicleItemImage() instanceof InstagramImage) {
                    InstagramImage instagramLink = (InstagramImage) listicleItem.getListicleItemImage();
                    location = instagramLink.getLocation();
                    try {
                        JsonNode instagramInfo = CommonUtils.getInstagramInformation(instagramLink);
                        if (instagramInfo != null) {
                            flatImage = new FlatImage(instagramLink,instagramInfo,request.getLocale());
                        } else {
                            errors.add("The Instagram id is no longer valid");
                            logger.warn(CommonUtils.contentIssue("The Instagram id %s is no longer, Listicle = %s - %s",
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
                            flatImage = new FlatImage(cmsImage,request.getLocale());
                            checkImageErrors(flatImage,request.getLocale(),errors);
                            LocationObject locationObject = locationLoader.getLocation(cmsImage.getLocation(), request.getLocale());
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
                    JsonNode product;
                    try {
                        product = dmsData.productCard(dmsLink.getProduct(), request.getLocale());
                        if (product == null) {
                            errors.add("The product id does not match in the DMS");
                            logger.warn(CommonUtils.contentIssue("The product's id  wasn't provided for %s, Listicle = %s - %s",
                                    dmsLink.getProduct(), listicle.getPath(), listicleItem.getTitle()));
                        } else {
                            if (product.has(ADDRESS)){
                                JsonNode address = product.get(ADDRESS);
                                if (location== null && address.has(LOCATION)) {
                                    location = address.get(LOCATION).asText();
                                }
                            }

                            if (flatImage == null) {
                                flatImage = new FlatImage(product);
                            } else {
                                if (flatImage.getCoordinates() == null) {
                                    Coordinates coordinates = new Coordinates(product.get(LATITUDE).asDouble(), product.get(LONGITUDE).asDouble());
                                    flatImage.setCoordinates(coordinates);
                                }
                            }

                             model.setFacilities(getFacilities(product));

                        }
                    } catch (IOException e) {
                        errors.add("Error while querying the DMS: " + e.getMessage());
                        logger.error(String.format("Error while querying the DMS for %s, Listicle item %s: 5s",
                                listicle.getName(), model.getIndex(), e.getMessage()));
                    }
                }

                links.add(linksService.createLink(request.getLocale(), listicleItem.getListicleItem()));

            }

            //Set Extra Links
            for (HippoCompound compound : listicleItem.getExtraLinks()) {
                links.add(linksService.createLink(request.getLocale(), compound));
            }
            if (listicleItem.getSubtitle() == null || listicleItem.getSubtitle().isEmpty()) {
                model.setSubTitle(location);
            }else{
                model.setSubTitle(listicleItem.getSubtitle());
            }
            model.setImage(flatImage);
            model.setLinks(links);
            model.setErrorMessages(errors);
            items.add(model);
        }

        request.setAttribute(LISTICLE_ITEMS, items);
        request.setAttribute(OTYML, addOTYML(listicle, request.getLocale()));
    }
}
