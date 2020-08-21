package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.capabilities.Linkable;
import com.visitscotland.brmx.beans.dms.LocationObject;
import com.visitscotland.brmx.beans.mapping.Coordinates;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.megalinks.*;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.LocationLoader;
import com.visitscotland.brmx.services.LinkService;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class LinkModulesFactory {

    private static final Logger logger = LoggerFactory.getLogger(LinkModulesFactory.class);

    public final static int MAX_ITEMS = 6;

    private final static String URL = "url";
    private final static String IMAGE = "images";

    private final HippoUtilsService utils;
    private final DMSDataService dmsData;
    private final LinkService linkService;

    public LinkModulesFactory() {
        this(new HippoUtilsService(), new DMSDataService(), new LinkService());
    }

    public LinkModulesFactory(HippoUtilsService utils, DMSDataService dmsData, LinkService linkService) {
        this.utils = utils;
        this.dmsData = dmsData;
        this.linkService   = linkService;
    }

    public LinksModule getMegalinkModule(Megalinks doc, Locale locale) {
        if (Boolean.TRUE.equals(doc.getListLayout()) || doc.getMegalinkItems().size() > MAX_ITEMS) {
            return listLayout(doc, locale) ;
        } else if (doc.getSingleImageModule() != null) {
            return singleImageLayout(doc, locale);
        } else {
            return multiImageLayout(doc, locale);
        }
    }

    public SingleImageLinksModule singleImageLayout(Megalinks doc, Locale locale) {
        SingleImageLinksModule sil = new SingleImageLinksModule();
        sil.setTitle(doc.getTitle());
        sil.setIntroduction(doc.getIntroduction());

        sil.setInnerTitle(doc.getSingleImageModule().getTitle());
        sil.setInnerIntroduction(doc.getSingleImageModule().getIntroduction());
        sil.setImage(createFlatImage(doc.getSingleImageModule().getImage(), locale));
        sil.setLinks(convertToFlatLinks(doc.getMegalinkItems(), locale));
        sil.setMegalinkItem(doc);

        if (doc.getProductItem()!= null) {
            sil.setCta(linkService.createLink(locale, doc.getProductItem()));
        }
        return sil;
    }

    /**
     * Converts a Megalinks document into a MultiImageLinksModule Object for easier the consumption of the data on the front
     * end.
     * <p>
     * The number of featured items marked on the document might change depending on the following rules:
     * <ul>
     * <li>Megalinks with 2 or less featured items won't have featured items</li>
     * <li>Megalinks with 3 items might have up to one featured items</li>
     * <li>Megalinks with 4 or more items will have between 1 and 2 featured items</li>
     * <li>When the number of featured items exceed what is expected, only the first items will remain as Featured</li>
     * <li>When the number of featured items is inferion to the expected, the first non-featured items will be promoted
     * to featured items</li>
     * </ul>
     *
     * @param doc    Megalink document to be converted.
     * @param locale Locale of the request
     * @return MultiImageLinksModule containing the relevant information from the Megalinks document
     */
    public MultiImageLinksModule multiImageLayout(Megalinks doc, Locale locale) {
        MultiImageLinksModule fl = new MultiImageLinksModule();
        fl.setTitle(doc.getTitle());
        fl.setIntroduction(doc.getIntroduction());
        fl.setTeaserVisible(doc.getTeaserVisible());
        fl.setMegalinkItem(doc);


        if (doc.getProductItem()!= null) {
            fl.setCta(linkService.createLink(locale, doc.getProductItem()));
        }
        fl.setLinksSize(doc.getMegalinkItems().size());
        fl.setLinks(convertToEnhancedLinks(doc.getMegalinkItems(), locale));

        //There is no featured items when the amount of items is inferior to 3
        if (fl.getLinks().size() > 2) {
            //For 3 links the maximum of 1 featured item.  From 4 on the maximum is 2 featured items.
            fl.setFeaturedLinks(fl.getLinks().stream()
                    .filter(link -> link.isFeatured())
                    .limit(fl.getLinks().size() == 3 ? 1 : 2)
                    .collect(Collectors.toCollection(ArrayList::new)));

            //When there is more than 3 items and no featured item the first item is promoted as featured.
            if (fl.getFeaturedLinks().size() == 0 && fl.getLinks().size() > 3) {
                fl.getFeaturedLinks().add(fl.getLinks().get(0));
            }

            //Links added to the Featured list MUST be removed from the original list
            fl.getLinks().removeAll(fl.getFeaturedLinks());
        } else {
            fl.setFeaturedLinks(Collections.EMPTY_LIST);
        }

        return fl;
    }

    public ListLinksModule listLayout(Megalinks doc, Locale locale) {
        ListLinksModule ll = new ListLinksModule();
        ll.setTitle(doc.getTitle());
        ll.setIntroduction(doc.getIntroduction());
        ll.setTeaserVisible(doc.getTeaserVisible());
        ll.setLinks(convertToEnhancedLinks(doc.getMegalinkItems(), locale));
        ll.setMegalinkItem(doc);

        if (doc.getProductItem()!= null) {
            ll.setCta(linkService.createLink(locale, doc.getProductItem()));
        }
        return ll;
    }

    //TODO comment this method
    List<FlatLink> convertToFlatLinks(List<MegalinkItem> items, Locale locale) {
        List<FlatLink> links = new ArrayList<>();
        for (MegalinkItem item : items) {
            if (item.getLink() == null) {
                CommonUtils.contentIssue("The module %s contains a link without any reference", item.getPath());
            } else if (item.getLink() instanceof Page) {
                links.add(new FlatLink(((Page) item.getLink()).getTitle(), utils.createUrl(item.getLink())));
            } else if (item.getLink() instanceof SharedLink) {
                JsonNode node = getNodeFromSharedLink((SharedLink) item.getLink(), locale);
                links.add(new FlatLink(((SharedLink) item.getLink()).getTitle(), linkService.getPlainLink((SharedLink)item.getLink(), node)));
            } else {
                CommonUtils.contentIssue("The module %s is pointing to a document of type %s which cannot be rendered as a page", item.getPath(), item.getLink().getClass().getSimpleName());
            }
        }
        return links;
    }

    //TODO comment this method
    List<EnhancedLink> convertToEnhancedLinks(List<MegalinkItem> items, Locale locale) {
        List<EnhancedLink> links = new ArrayList<>();
        for (MegalinkItem item : items) {

            if (item.getLink() == null) {
                CommonUtils.contentIssue("The module %s contains a link without any reference", item.getPath());
            } else if (item.getLink() instanceof Linkable) {
                EnhancedLink link = new EnhancedLink();
                link.setTeaser(((Linkable) item.getLink()).getTeaser());
                link.setLabel(((Linkable) item.getLink()).getTitle());
                link.setFeatured(item.getFeature());

                if (((Linkable) item.getLink()).getImage() != null) {
                    link.setImage(createFlatImage(((Linkable) item.getLink()).getImage(), locale));
                }

                if (item.getLink() instanceof Page) {
                    link.setLink(utils.createUrl(item.getLink()));
                } else if (item.getLink() instanceof SharedLink) {
                    JsonNode product = getNodeFromSharedLink((SharedLink) item.getLink(), locale);
                    if (link.getImage() == null && product != null && product.has(IMAGE)) {
                        link.setImage(new FlatImage(product));
                    }
                    link.setLink(linkService.getPlainLink((SharedLink) item.getLink(), product));
                } else {
                    logger.warn(String.format("The type %s was not expected and will be skipped", item.getLink().getClass().getSimpleName()));
                    continue;
                }

                //TODO add itineraries days and transport
            /*   if (item.getLink() instanceof Itinerary) {
                    link.setTransport(((Itinerary) item.getLink()).getTransports()[0]);
                    link.setDays(((Itinerary) item.getLink()).getDays().size());
                }*/


                if (link.getImage() == null) {
                    CommonUtils.contentIssue("The link to %s does not have an image but it is expecting one", item.getLink());
                }

                links.add(link);
            } else {
                CommonUtils.contentIssue("The module %s is pointing to a document of type %s which cannot be rendered as a page", item.getPath(), item.getLink().getClass().getSimpleName());
            }
        }
        return links;
    }

    /**
     * Query the DMSDataService and extract the information about the product as a {@code JsonNode}
     * @param link
     * @param locale
     * @return
     */
    private JsonNode getNodeFromSharedLink(SharedLink link, Locale locale) {
        if (link.getLinkType() instanceof DMSLink) {
            try {
                return dmsData.productCard(((DMSLink) link.getLinkType()).getProduct(), locale);
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * Create a localized FlatImage from an Image Object
     * @param img Image Object
     * @param locale User language to localize Image texts such as the caption
     * @return
     */
    private FlatImage createFlatImage(Image img, Locale locale) {
        FlatImage flatImage = new FlatImage(img, locale);
        LocationObject locationObject = getLocation(img.getLocation(), locale);
        if (locationObject != null) {
            flatImage.setCoordinates(new Coordinates(locationObject.getLatitude(), locationObject.getLongitude()));
        }

        return flatImage;
    }

    LocationObject getLocation(String location, Locale locale) {
        return LocationLoader.getLocation(location, locale);
    }
}
