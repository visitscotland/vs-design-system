package com.visitscotland.brmx.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brmx.beans.ICentre;
import com.visitscotland.brmx.beans.capabilities.Linkable;
import com.visitscotland.brmx.beans.mapping.FlatImage;
import com.visitscotland.brmx.beans.mapping.FlatLink;
import com.visitscotland.brmx.beans.mapping.ICentreModule;
import com.visitscotland.brmx.beans.mapping.LinkType;
import com.visitscotland.brmx.beans.mapping.megalinks.EnhancedLink;
import com.visitscotland.brmx.dms.DMSConstants;
import com.visitscotland.brmx.dms.DMSDataService;
import com.visitscotland.brmx.dms.ProductSearchBuilder;
import com.visitscotland.brmx.services.ResourceBundleService;
import com.visitscotland.brmx.utils.HippoUtilsService;
import com.visitscotland.brmx.utils.Properties;
import com.visitscotland.utils.Contract;
import com.visitscotland.utils.DataServiceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ICentreFactory {

    private static final Logger logger = LoggerFactory.getLogger(ICentreFactory.class);

    static final String BUNDLE_ID = "modules";

    private final HippoUtilsService utils;
    private final DMSDataService dmsData;
    private final LinkModulesFactory linkFactory;
    private final ResourceBundleService bundle;


    public ICentreFactory() {
        this(new HippoUtilsService(), new DMSDataService(), new LinkModulesFactory(), new ResourceBundleService());
    }

    public ICentreFactory(HippoUtilsService utils, DMSDataService dmsData, LinkModulesFactory linksFactory, ResourceBundleService bundle) {
        this.utils = utils;
        this.dmsData = dmsData;
        this.linkFactory = linksFactory;
        this.bundle = bundle;
    }


    /**
     * Builds an iCentre module when there is enough data to build it or null when there is not.
     */
    public ICentreModule getModule(ICentre doc, Locale locale, String location) {
        logger.info("Creating iCentreModule for {}", doc.getPath());

        ICentreModule module = new ICentreModule();
        module.setLinks(getLinks(location, locale));

        if (module.getLinks().isEmpty()) {
            //The module disappears when there is no iCentres in the area
            return null;
        }

        //Populate Title
        if (Contract.isEmpty(doc.getTitle())) {
            //TODO: Create labels
            module.setTitle(bundle.getResourceBundle("icentre.title.default", BUNDLE_ID, locale));
        } else {
            module.setTitle(doc.getTitle());
        }

        //Populate Description
        if (module.getLinks().size() == 1) {
            //TODO: Create labels
            module.setDescription(bundle.getResourceBundle("icentre.description.singleVic", BUNDLE_ID, locale));
        } else {
            module.setDescription(bundle.getResourceBundle("icentre.description.multipleVic", BUNDLE_ID, locale));
        }

        //Populate Image
        if (doc.getImage() != null) {
            module.setImage(new FlatImage(doc.getImage(), locale));
        }

        //Quote
        if (doc.getQuote() != null) {
            module.setQuote(doc.getQuote().getQuote());
            module.setQuoteAuthorName(doc.getQuote().getAuthor());
            module.setQuoteAuthorTitle(doc.getQuote().getRole());

            if (doc.getQuote().getImage() != null) {
                module.setQuoteImage(new FlatImage(doc.getQuote().getImage(), locale));
            }

            if (doc.getQuote().getProduct() instanceof Linkable) {
                EnhancedLink link = linkFactory.createEnhancedLink((Linkable) doc.getQuote().getProduct(), locale);
                module.setQuoteLink(link);
                if (module.getImage() == null) {
                    module.setImage(link.getImage());
                }
            }
        }

        //Default the Image if hasn't be set
        if (module.getImage() == null) {
            FlatImage image = new FlatImage();

            //TODO: Create labels
            image.setExternalImage(bundle.getResourceBundle("icentre.image.default", BUNDLE_ID, locale));
            module.setImage(image);
        }

        return module;
    }

    /**
     * Get the list of links for a location and a locale. When the location is not provided a link to the iCentres page
     * would be returned
     */
    private List<FlatLink> getLinks (String location, Locale locale){
        if (!Contract.isEmpty(location)) {
            return getVicList(location, locale);
        } else {
            //TODO: Create labels
            String url = bundle.getResourceBundle("icentre.description.link", BUNDLE_ID, locale);
            String text = bundle.getResourceBundle("icentre.description.link.text", BUNDLE_ID, locale);

            return Collections.singletonList(new FlatLink(text, url, LinkType.INTERNAL));
        }
    }

    /**
     * Queries the DMS and return a list of iCentre for the specific location
     *
     * The location must be specified
     */
    private List<FlatLink> getVicList (String location, Locale locale){
        if (Contract.isNull(location)){
            return Collections.emptyList();
        }

        logger.info("Calulating list of iCentres for the location {} (locale {})", location, locale);
        List<FlatLink> vicList = new ArrayList<>();

        ProductSearchBuilder dmsQuery = new ProductSearchBuilder().location(location)
                .productTypes(DMSConstants.TYPE_SERVICES).category(DMSConstants.CAT_ICENTRE)
                .sortBy(DMSConstants.SORT_ALPHA);

        JsonNode node = dmsData.legacyMapSearch(dmsQuery);

        for (JsonNode child : node) {
            if (child.has(DMSConstants.MapSearch.PROPERTIES) &&
                    child.get(DMSConstants.MapSearch.PROPERTIES).has(DMSConstants.MapSearch.NAME) &&
                    child.get(DMSConstants.MapSearch.PROPERTIES).has(DMSConstants.MapSearch.ID)){
                String label = child.get(DMSConstants.MapSearch.PROPERTIES).get(DMSConstants.MapSearch.NAME).asText();
                String id = child.get(DMSConstants.MapSearch.PROPERTIES).get(DMSConstants.MapSearch.ID).asText();
                String languagePath = Properties.Language.getLanguageForLocale(locale).getDMSPathVariable();
                String url = Properties.VS_DMS_SERVICE + DataServiceUtils.getProductURL(label, id, DMSConstants.TYPE_SERVICES, languagePath);
                vicList.add(new FlatLink(label, url, LinkType.INTERNAL));
            }
        }

        return vicList;
    }
}
