package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.*;
import com.visitscotland.brmx.beans.capabilities.Linkable;
import com.visitscotland.brmx.beans.dms.LocationObject;
import com.visitscotland.brmx.beans.mapping.Coordinates;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.LinkType;
import com.visitscotland.brmx.beans.mapping.megalinks.*;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.LocationLoader;
import com.visitscotland.brmx.services.LinkService;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.CommonUtils;
import com.visitscotland.brmx.utils.HippoUtilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.stream.Collectors;
import java.net.URL;

public class LinkModulesFactory {

    private static final Logger logger = LoggerFactory.getLogger(LinkModulesFactory.class);

    public final static int MAX_ITEMS = 6;

    private final static String IMAGE = "images";

    private final HippoUtilsService utils;
    private final DMSDataService dmsData;
    private final LinkService linkService;
    private ResourceBundleService bundle;

    public LinkModulesFactory() {
        this(new HippoUtilsService(), new DMSDataService(), new LinkService());
    }

    public LinkModulesFactory(HippoUtilsService utils, DMSDataService dmsData, LinkService linkService) {
        this.utils = utils;
        this.dmsData = dmsData;
        this.linkService = linkService;
        this.bundle= new ResourceBundleService();
    }

    public LinksModule getMegalinkModule(Megalinks doc, Locale locale) {
        if (doc.getLayout()!= null && doc.getLayout().equalsIgnoreCase("list") || doc.getMegalinkItems().size() > MAX_ITEMS) {
            return listLayout(doc, locale) ;
        } else if (doc.getLayout()!= null && doc.getLayout().contains("Horizontal")) {
            return horizontalListLayout (doc, locale);
        }else if (doc.getSingleImageModule() != null) {
            return singleImageLayout(doc, locale);
        } else {
            return multiImageLayout(doc, locale);
        }
    }

    /**
     * Converts a Megalinks document into a {@code ListLinksModule} Object for easier the consumption of the data on the front
     * end.
     *
     * @param doc    Megalink document to be converted.
     * @param locale Locale of the request
     * @return {@code ListLinksModule} containing the relevant information from the Megalinks document
     */
    public ListLinksModule listLayout(Megalinks doc, Locale locale) {
        ListLinksModule ll = new ListLinksModule();
        populateCommonFields(ll, doc, locale);

        ll.setTeaserVisible(doc.getTeaserVisible());
        ll.setLinks(convertToEnhancedLinks(doc.getMegalinkItems(), locale));

        return ll;
    }


    public HorizontalListLinksModule horizontalListLayout(Megalinks doc, Locale locale) {
        HorizontalListLinksModule ll = new HorizontalListLinksModule();
        populateCommonFields(ll, doc, locale);

        ll.setTeaserVisible(doc.getTeaserVisible());
        ll.setLinks(convertToEnhancedLinks(doc.getMegalinkItems(), locale));

        return ll;
    }

    public HorizontalListLinksModule horizontalListLayout(Page page, Locale locale) {
        HorizontalListLinksModule target = new HorizontalListLinksModule();
        OTYML doc = page.getOtherThings();
        target.setTitle(doc.getTitle().isEmpty()? (bundle.getResourceBundle("otyml", "title", locale ,true)): doc.getTitle());
        target.setIntroduction(doc.getIntroduction());
        target.setLinks(convertToEnhancedLinks(doc.getMegalinkItems(), locale));

        return target;
    }

    /**
     * Converts a Megalinks document into a {@code SingleImageLinksModule} Object for easier the consumption of the data on the front
     * end.
     *
     * @param doc    Megalink document to be converted.
     * @param locale Locale of the request
     * @return {@code SingleImageLinksModule} containing the relevant information from the Megalinks document
     */
    public SingleImageLinksModule singleImageLayout(Megalinks doc, Locale locale) {
        SingleImageLinksModule sil = new SingleImageLinksModule();
        populateCommonFields(sil, doc, locale);

        sil.setInnerTitle(doc.getSingleImageModule().getTitle());
        sil.setInnerIntroduction(doc.getSingleImageModule().getIntroduction());
        sil.setImage(createFlatImage(doc.getSingleImageModule().getImage(), locale));
        sil.setLinks(convertToFlatLinks(doc.getMegalinkItems(), locale));

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
        populateCommonFields(fl, doc, locale);
        fl.setTeaserVisible(doc.getTeaserVisible());

        fl.setLinksSize(doc.getMegalinkItems().size());
        fl.setLinks(convertToEnhancedLinks(doc.getMegalinkItems(), locale));

        if (fl.getLinks().size() == 1) {
            //If the megalinks only have one item, that one is featured
            fl.setFeaturedLinks(fl.getLinks());
            fl.setLinks(Collections.EMPTY_LIST);
        } else if (fl.getLinks().size() > 2) {
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

    /**
     * Populate the common fields among all layouts
     *
     * @param target target module to be populated
     * @param doc    Megalinks document with the data source
     * @param locale consumer language.
     */
    private void populateCommonFields(LinksModule<?> target, Megalinks doc, Locale locale) {
        target.setMegalinkItem(doc);
        target.setTitle(doc.getTitle());
        target.setIntroduction(doc.getIntroduction());

        if (doc.getProductItem() != null) {
            target.setCta(linkService.createLink(locale, doc.getProductItem()));
        }
    }

    /**
     * Converts the list of {@code MegalinksItem} into  a list of plain {@code FlatLink }
     */
    List<FlatLink> convertToFlatLinks(List<MegalinkItem> items, Locale locale) {
        List<FlatLink> links = new ArrayList<>();
        for (MegalinkItem item : items) {
            if (item.getLink() == null) {
                CommonUtils.contentIssue("The module %s contains a link without any reference", item.getPath());
            } else if (item.getLink() instanceof Page) {
                links.add(new FlatLink(((Page) item.getLink()).getTitle(), utils.createUrl(item.getLink()), LinkType.INTERNAL));
            } else if (item.getLink() instanceof SharedLink) {
                JsonNode node = getNodeFromSharedLink((SharedLink) item.getLink(), locale);
                FlatLink link = new FlatLink();
                link.setLabel(((SharedLink) item.getLink()).getTitle());
                link.setLink(linkService.getPlainLink((SharedLink) item.getLink(), node));
                link.setType(linkService.getType(link.getLink()));
                links.add(link);
            } else {
                CommonUtils.contentIssue("The module %s is pointing to a document of type %s which cannot be rendered as a page", item.getPath(), item.getLink().getClass().getSimpleName());
            }
        }
        return links;
    }

    /**
     * Converts the list of {@code MegalinksItem} into  a list of {@code EnhancedLink }
     */
    List<EnhancedLink> convertToEnhancedLinks(List<MegalinkItem> items, Locale locale) {
        List<EnhancedLink> links = new ArrayList<>();
        for (MegalinkItem item : items) {

            if (item.getLink() == null) {
                CommonUtils.contentIssue("The module %s contains a link without any reference", item.getPath());
            } else if (item.getLink() instanceof Linkable) {
                EnhancedLink link = createEnhancedLink((Linkable) item.getLink(), locale);
                if (link != null) {
                    link.setFeatured(item.getFeature());
                    if (this.getLinkCategory(item.getLink().getPath(),locale)!=null){
                        link.setCategory(this.getLinkCategory(item.getLink().getPath(),locale));
                    }else{
                        try {
                            URL url = new URL(link.getLink());
                            link.setCategory(url.getHost().substring(4));
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                    }

                    links.add(link);
                }
            } else {
                CommonUtils.contentIssue("The module %s is pointing to a document of type %s which cannot be rendered as a page", item.getPath(), item.getLink().getClass().getSimpleName());
            }
        }
        return links;
    }

    public EnhancedLink createEnhancedLink(Linkable linkable, Locale locale) {
        EnhancedLink link = new EnhancedLink();
        link.setTeaser(linkable.getTeaser());
        link.setLabel(linkable.getTitle());

        if (linkable.getImage() != null) {
            link.setImage(createFlatImage(linkable.getImage(), locale));
        }

        if (linkable instanceof Page) {
            //TODO add itineraries days and transport
            if (linkable instanceof Itinerary) {
                link.setItineraryTransport(((Itinerary) linkable).getTransports()[0]);
                link.setItineraryDays(((Itinerary) linkable).getDays().size());
            }
            link.setLink(utils.createUrl((Page) linkable));
        } else if (linkable instanceof SharedLink) {
            JsonNode product = getNodeFromSharedLink((SharedLink) linkable, locale);
            if (link.getImage() == null && product != null && product.has(IMAGE)) {
                link.setImage(new FlatImage(product));
            }
            link.setLink(linkService.getPlainLink((SharedLink) linkable, product));
        } else {
            logger.warn(String.format("The type %s was not expected and will be skipped", linkable.getClass().getSimpleName()));
            return null;
        }

        if (link.getImage() == null) {
            CommonUtils.contentIssue("The link to %s does not have an image but it is expecting one", linkable);
        }

        return link;
    }


    /**
     * Query the DMSDataService and extract the information about the product as a {@code JsonNode}
     *
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
     *
     * @param img    Image Object
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

    private String getLinkCategory(String path, Locale locale) {
        if (path.contains("ebook")){
            return bundle.getResourceBundle("otyml", "category.ebooks", locale ,true);
        }else if(path.contains("blog")){
            return bundle.getResourceBundle("otyml", "category.blog", locale ,true);
        }else if(path.contains("see-do")||path.contains("events")||path.contains("tours")){
            return bundle.getResourceBundle("otyml", "category.see-do", locale ,true);
        }else if(path.contains("accommodation")){
            return bundle.getResourceBundle("otyml", "category.accommodation", locale ,true);
        }else if(path.contains("destination")){
            return bundle.getResourceBundle("otyml", "category.destination", locale ,true);
        }else if(path.contains("travel")||path.contains("holidays")){
            return bundle.getResourceBundle("otyml", "category.travel-plan", locale ,true);
        }else if(path.contains("brochures")){
            return bundle.getResourceBundle("otyml", "category.inspiration", locale ,true);
        }else if(path.contains("about")||path.contains("contact")||path.contains("policies")) {
            return bundle.getResourceBundle("otyml", "category.information", locale ,true);
        }
        else if(path.contains("sandbox") && !path.contains("shared")) {
            return "sandbox";
        }
        return null;
    }

    //TODO convert into a service
    LocationObject getLocation(String location, Locale locale) {
        return LocationLoader.getLocation(location, locale);
    }
}
