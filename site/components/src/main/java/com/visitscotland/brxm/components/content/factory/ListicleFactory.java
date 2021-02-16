package com.visitscotland.brxm.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.beans.*;
import com.visitscotland.brxm.beans.mapping.Coordinates;
import com.visitscotland.brxm.beans.mapping.FlatLink;
import com.visitscotland.brxm.beans.mapping.FlatListicle;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.DMSUtils;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.utils.CommonUtils;
import com.visitscotland.brxm.utils.DocumentUtils;
import org.hippoecm.hst.content.beans.standard.HippoCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.visitscotland.brxm.dms.DMSConstants.DMSProduct.*;

@Component
public class ListicleFactory {

    private static final Logger logger = LoggerFactory.getLogger(ListicleFactory.class);

    private final LinkService linksService;
    private final DMSDataService dmsData;
    private final ImageFactory imageFactory;
    private final DMSUtils dmsUtils;
    private final DocumentUtils documentUtils;

    public ListicleFactory(LinkService linksService, DMSDataService dmsData, ImageFactory imageFactory, DMSUtils dmsUtils, DocumentUtils documentUtils) {
        this.linksService = linksService;
        this.dmsData = dmsData;
        this.imageFactory = imageFactory;
        this.dmsUtils = dmsUtils;
        this.documentUtils = documentUtils;
    }

    /**
     * TODO Comment!
     *
     * @param listicleItem
     * @param index
     * @return
     */
    public FlatListicle getListicleItem(Locale locale, ListicleItem listicleItem, Integer index) {

        List<FlatLink> links = new ArrayList<>();
        FlatLink link;

        //TODO Rename to Module
        FlatListicle module = new FlatListicle();
        module.setIndex(index);
        module.setHippoBean(listicleItem);
        module.setTitle(listicleItem.getTitle());
        module.setDescription(listicleItem.getDescription());
        module.setSubtitle(listicleItem.getSubtitle());

        //Set the image
        //TODO Use ImageFactory
        if (listicleItem.getListicleItemImage() != null) {
            module.setImage(imageFactory.getImage(listicleItem.getListicleItemImage(), module, locale));
        }

        //Set the main product
        link = processMainProduct(locale, listicleItem.getListicleItem(), module);

        if (link != null) {
            links.add(link);
        }


        //Set Extra Links
        //TODO Can we have more that one?
        for (HippoCompound compound : listicleItem.getExtraLinks()) {
            link = linksService.createLink(locale, compound);
            if (link != null) {
                links.add(link);
            }
        }

        if (module.getSubtitle() == null && module.getImage() != null) {
            module.setSubtitle(module.getImage().getLocation());
        }

        module.setLinks(links);

        return module;
    }

    private FlatLink processMainProduct(Locale locale, HippoCompound link, FlatListicle module){
        if (link == null) {
            //TODO If possible on CMS add warning:  module.addErrorMessage();
            String issue = CommonUtils.contentIssue("The ListicleItem %s doesn't contain a main product", module.getHippoBean().getPath());
            logger.warn(issue);
            return null;
        } else if (link instanceof DMSLink) {
            processDMSMainProduct(locale, module, (DMSLink) link);
        } else if (link instanceof CMSLink) {
            if (((CMSLink) link).getLink() instanceof Page) {
                if (module.getImage() == null) {
                    Page cmsLink = (Page) ((CMSLink) link).getLink();
                    module.setImage(imageFactory.getImage(cmsLink.getHeroImage(), module, locale));
                }
            } else {
                //TODO If possible on CMS add warning:  module.addErrorMessage();
                String issue = CommonUtils.contentIssue("The ListicleItem %s is pointing to a document that is not a page ", module.getHippoBean().getPath());
                logger.warn(issue);
                return null;
            }
        }

        return  linksService.createLink(locale, link);
    }

    /**
     * Loads as much information as it can from the DMS Prodcut data:
     *
     * Facilities are loaded from the dmsItem. Subtitle, Image and Coordinates are set only when the listicle item has
     * not defined the values
     *
     *
     * @param item
     * @param dmsLink
     */
    private void processDMSMainProduct(Locale locale, FlatListicle item, DMSLink dmsLink) {

        JsonNode product = dmsData.productCard(dmsLink.getProduct(), locale);

        if (product == null) {
            item.addErrorMessage("The product id does not match in the DMS");
            String message = CommonUtils.contentIssue("The product id was not provided or the product was not found (id=%s), Listicle = %s - %s",
                    dmsLink.getProduct(), item.getHippoBean(), item.getHippoBean().getTitle());
            logger.warn(message);
        } else {
            if (item.getSubtitle() == null && product.has(ADDRESS) && product.get(ADDRESS).has(LOCATION)) {
                item.setSubtitle(product.get(ADDRESS).get(LOCATION).asText());
            }

            if (item.getImage() == null) {
                item.setImage(imageFactory.createImage(product, item));
            } else if (item.getImage().getCoordinates() == null && product.has(LATITUDE)) {
                Coordinates coordinates = new Coordinates(product.get(LATITUDE).asDouble(), product.get(LONGITUDE).asDouble());
                item.getImage().setCoordinates(coordinates);
            }

            item.setFacilities(dmsUtils.getFacilities(product));
        }
    }

    /**
     * TODO Comment!
     *
     * @param request
     * @param listicle
     * @return
     */
    public List<FlatListicle> generateItems(Locale locale, Listicle listicle) {
        final List<ListicleItem> listicleItems = documentUtils.getAllowedDocuments(listicle, ListicleItem.class);
        final List<FlatListicle> items = new ArrayList<>();

        boolean descOrder = Boolean.TRUE.equals(listicle.getDescOrder());
        int index = descOrder ? listicleItems.size() : 1;

        for (ListicleItem listicleItem : listicleItems) {
            Integer itemNumber = descOrder ? index-- : index++;
            items.add(getListicleItem(locale, listicleItem, itemNumber));
        }
        return items;
    }
}
