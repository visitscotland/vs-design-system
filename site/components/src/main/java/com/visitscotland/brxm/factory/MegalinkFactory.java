package com.visitscotland.brxm.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.hippobeans.*;
import com.visitscotland.brxm.hippobeans.capabilities.Linkable;
import com.visitscotland.brxm.dms.model.LocationObject;
import com.visitscotland.brxm.model.*;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.model.megalinks.*;
import com.visitscotland.brxm.services.DocumentUtilsService;
import com.visitscotland.brxm.services.LinkService;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.services.CommonUtilsService;
import com.visitscotland.brxm.utils.HippoUtilsService;
import com.visitscotland.utils.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MegalinkFactory {

    private static final Logger logger = LoggerFactory.getLogger(MegalinkFactory.class);
    private static final Logger contentLogger = LoggerFactory.getLogger("content");

    public final static int MAX_ITEMS = 6;
    public final static String HORIZONTAL_LAYOUT = "Horizontal";

    private final static String IMAGE = "images";

    private final HippoUtilsService utils;
    private final DMSDataService dmsData;
    private final LinkService linkService;
    private final ResourceBundleService bundle;
    private final LocationLoader locationLoader;
    private final ImageFactory imageFactory;
    private final CommonUtilsService commonUtils;
    private final DocumentUtilsService documentUtilsService;

    public MegalinkFactory(HippoUtilsService utils, DMSDataService dmsData, LinkService linkService, ResourceBundleService bundle, LocationLoader locationLoader, ImageFactory imageFactory, CommonUtilsService commonUtils, DocumentUtilsService documentUtilsService) {
        this.utils = utils;
        this.dmsData = dmsData;
        this.linkService = linkService;
        this.bundle = bundle;
        this.locationLoader = locationLoader;
        this.imageFactory = imageFactory;
        this.commonUtils = commonUtils;
        this.documentUtilsService = documentUtilsService;
    }

    public LinksModule<EnhancedLink> getMegalinkModule(Megalinks doc, Locale locale) {
        if (doc.getLayout()!= null && doc.getLayout().equalsIgnoreCase("list") || doc.getMegalinkItems().size() > MAX_ITEMS) {
            return listLayout(doc, locale) ;
        } else if (doc.getLayout()!= null && doc.getLayout().contains(HORIZONTAL_LAYOUT)) {
            return horizontalListLayout (doc, locale);
        }else if (doc.getSingleImageModule() != null) {
            return singleImageLayout(doc, locale);
        } else {
            return multiImageLayout(doc, locale);
        }
    }

    /**
     * Converts a Megalinks module into a {@code ListLinksModule} Object for easier the consumption of the data on the front
     * end.
     *
     * @param doc    Megalink module to be converted.
     * @param locale Locale of the request
     * @return {@code ListLinksModule} containing the relevant information from the Megalinks module
     */
    public ListLinksModule listLayout(Megalinks doc, Locale locale) {
        ListLinksModule ll = new ListLinksModule();
        populateCommonFields(ll, doc, locale);

        ll.setTeaserVisible(doc.getTeaserVisible());
        ll.setLinks(convertToEnhancedLinks(ll, doc.getMegalinkItems(), locale, false));
        return ll;
    }

    public HorizontalListLinksModule horizontalListLayout(Megalinks doc, Locale locale) {
        HorizontalListLinksModule hll = new HorizontalListLinksModule();
        populateCommonFields(hll, doc, locale);

        hll.setTeaserVisible(doc.getTeaserVisible());
        hll.setLinks(convertToEnhancedLinks(hll, doc.getMegalinkItems(), locale, false));

        return hll;
    }

    public HorizontalListLinksModule horizontalListLayout(OTYML doc, Locale locale) {
        HorizontalListLinksModule hll = new HorizontalListLinksModule();
        hll.setTitle(Contract.isEmpty(doc.getTitle())? (bundle.getResourceBundle("otyml", "otyml.title.default", locale ,true)): doc.getTitle());
        hll.setIntroduction(doc.getIntroduction());
        hll.setLinks(convertToEnhancedLinks(hll, doc.getMegalinkItems(), locale,true));

        return hll;
    }

    /**
     * Converts a Megalinks module into a {@code SingleImageLinksModule} Object for easier the consumption of the data on the front
     * end.
     *
     * @param doc    Megalink module to be converted.
     * @param locale Locale of the request
     * @return {@code SingleImageLinksModule} containing the relevant information from the Megalinks module
     */
    public SingleImageLinksModule singleImageLayout(Megalinks doc, Locale locale) {
        SingleImageLinksModule sil = new SingleImageLinksModule();
        populateCommonFields(sil, doc, locale);

        sil.setInnerTitle(doc.getSingleImageModule().getTitle());
        sil.setInnerIntroduction(doc.getSingleImageModule().getIntroduction());
        sil.setImage(createFlatImage(doc.getSingleImageModule().getImage(), locale));
        sil.setLinks(convertToEnhancedLinks(sil, doc.getMegalinkItems(), locale, false));

        return sil;
    }

    /**
     * Converts a Megalinks module into a MultiImageLinksModule Object for easier the consumption of the data on the front
     * end.
     * <p>
     * The number of featured items marked on the module might change depending on the following rules:
     * <ul>
     * <li>Megalinks with 2 or less featured items won't have featured items</li>
     * <li>Megalinks with 3 items might have up to one featured items</li>
     * <li>Megalinks with 4 or more items will have between 1 and 2 featured items</li>
     * <li>When the number of featured items exceed what is expected, only the first items will remain as Featured</li>
     * <li>When the number of featured items is inferior to the expected, the first non-featured items will be promoted
     * to featured items</li>
     * </ul>
     *
     * @param doc    Megalink module to be converted.
     * @param locale Locale of the request
     * @return MultiImageLinksModule containing the relevant information from the Megalinks module
     */
    public MultiImageLinksModule multiImageLayout(Megalinks doc, Locale locale) {
        MultiImageLinksModule fl = new MultiImageLinksModule();
        populateCommonFields(fl, doc, locale);
        fl.setTeaserVisible(doc.getTeaserVisible());

        fl.setLinksSize(doc.getMegalinkItems().size());
        fl.setLinks(convertToEnhancedLinks(fl, doc.getMegalinkItems(), locale,false));

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
     * @param doc    Megalinks module with the data source
     * @param locale consumer language.
     */
    private void populateCommonFields(LinksModule<?> target, Megalinks doc, Locale locale) {
        target.setHippoBean(doc);
        target.setTitle(doc.getTitle());
        target.setIntroduction(doc.getIntroduction());

        if (doc.getProductItem() != null) {
            target.setCta(linkService.createLink(locale, doc.getProductItem()));
        }
    }

    /**
     * Converts the list of {@code MegalinksItem} into  a list of {@code EnhancedLink }
     */
    List<EnhancedLink> convertToEnhancedLinks(Module module, List<MegalinkItem> items, Locale locale, boolean addCategory) {
        List<EnhancedLink> links = new ArrayList<>();
        for (MegalinkItem item : items) {
            if (item.getLink() == null) {
                addError(module, "One of the Megalinks items contains an invalid reference");
                contentLogger.warn("The module {} contains a link without any reference", item.getPath());
            } else {
                EnhancedLink link = null;
                if (item.getLink() instanceof Linkable) {
                    link = linkService.createEnhancedLink((Linkable) item.getLink(), module, locale, addCategory);
                }

                if (link != null) {
                    link.setFeatured(item.getFeature());
                    links.add(link);
                } else {
                    addError(module, "One of the Megalink items cannot be recognized and will not be included in the page.");
                    contentLogger.warn("The module {} is pointing to a module of type {} which cannot be rendered as a page", item.getPath(), item.getLink().getClass().getSimpleName());
                }
            }
        }
        return links;
    }

    private void addError(Module module, String message){
        if (module != null) {
            module.addErrorMessage(message);
        } else {
            logger.warn("The error message cannot be displayed in preview");
        }
    }

    /**
     * Query the DMSDataService and extract the information about the product as a {@code JsonNode}
     *
     * @param link SharedLink where the DMS product (ID) is defined
     * @param locale User language to consume DMS texts such a category, location, facilities...
     * @return JSON with DMS product information to create the card or null if the product does not exist
     */
    private JsonNode getNodeFromSharedLink(SharedLink link, Locale locale) {
        if (link.getLinkType() instanceof DMSLink) {
            return dmsData.productCard(((DMSLink) link.getLinkType()).getProduct(), locale);
        }
        return null;
    }

    /**
     * Create a localized FlatImage from an Image Object
     *
     * @param img    Image Object
     * @param locale User language to localize Image texts such as the caption
     * @return flat image to be consumed by FED team
     */
    private FlatImage createFlatImage(Image img, Locale locale) {
        FlatImage flatImage = new FlatImage(img, locale);
        LocationObject locationObject = getLocation(img.getLocation(), locale);
        if (locationObject != null) {
            flatImage.setCoordinates(new Coordinates(locationObject.getLatitude(), locationObject.getLongitude()));
        }

        return flatImage;
    }

    //TODO convert into a service
    LocationObject getLocation(String location, Locale locale) {
        return locationLoader.getLocation(location, locale);
    }

}
