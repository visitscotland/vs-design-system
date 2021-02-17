package com.visitscotland.brxm.components.content.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visitscotland.brxm.beans.ExternalLink;
import com.visitscotland.brxm.beans.Image;
import com.visitscotland.brxm.beans.ImageData;
import com.visitscotland.brxm.beans.InstagramImage;
import com.visitscotland.brxm.beans.dms.LocationObject;
import com.visitscotland.brxm.beans.mapping.Coordinates;
import com.visitscotland.brxm.beans.mapping.FlatImage;
import com.visitscotland.brxm.beans.mapping.Module;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.services.ResourceBundleService;
import com.visitscotland.brxm.utils.CommonUtils;
import com.visitscotland.brxm.utils.Language;
import com.visitscotland.brxm.utils.Properties;
import com.visitscotland.utils.Contract;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import static com.visitscotland.brxm.dms.DMSConstants.DMSProduct.*;

@Component
public class ImageFactory {

    private static final Logger logger = LoggerFactory.getLogger(ImageFactory.class);

    private LocationLoader locationLoader;

    private ResourceBundleService bundle;

    private CommonUtils utils;

    public ImageFactory(LocationLoader locationLoader, ResourceBundleService bundle, CommonUtils utils) {
        this.locationLoader = locationLoader;
        this.bundle = bundle;
        this.utils = utils;
    }

    public FlatImage getImage(HippoBean image, Module<?> module, Locale locale) {
        if (image instanceof InstagramImage) {
            return createImage((InstagramImage) image, module, locale);
        } else if (image instanceof Image) {
            return createImage((Image) image, module, locale);
        } else if (image instanceof ExternalLink) {
            FlatImage flat = new FlatImage();
            // TODO: No alt text & no description: Double check the requirements <-- Review Again
            flat.setExternalImage(((ExternalLink) image).getLink());
        }
        return null;
    }

    /**
     * Creates an Image from an Instagram compound (InstagramLink)
     *
     * @param cmsImage Compound with the information for the image
     * @param module   Module where potential issues will be logged
     * @param locale   Locale for translated texts (i.e. location)
     * @return FlatImage Object
     */
    public FlatImage createImage(Image cmsImage, Module<?> module, Locale locale) {
        FlatImage image = new FlatImage();
        ImageData data = null;

        image.setCmsImage(cmsImage);

        Language language = Language.getLanguageForLocale(locale);
        //Populate altText and Caption
        if (Language.FRENCH.equals(language)) {
            data = cmsImage.getFr();
        } else if (Language.GERMAN.equals(language)) {
            data = cmsImage.getDe();
        } else if (Language.SPANISH.equals(language)) {
            data = cmsImage.getEs();
        } else if (Language.DUTCH.equals(language)) {
            data = cmsImage.getNl();
        } else if (Language.ITALIAN.equals(language)) {
            data = cmsImage.getIt();
        }

        if (data == null) {
            image.setAltText(cmsImage.getAltText());
            image.setDescription(cmsImage.getDescription());
        } else {
            image.setAltText(data.getAltText());
            image.setDescription(data.getCaption());
        }

        if (Contract.isEmpty(image.getAltText())) {
            String message = "The image does not have an Alternative Text for the language " + locale;
            module.addErrorMessage(message);
            CommonUtils.contentIssue(message);
            logger.warn(message);
            image.setAltText(cmsImage.getAltText());
        }

        if (Contract.isEmpty(image.getDescription())) {
            String message = "The image does not have a description for the locale " + locale;
            module.addErrorMessage(message);
            CommonUtils.contentIssue(message);
            logger.warn(message);
            image.setDescription(cmsImage.getDescription());
        }

        //Populates the location
        populateLocation(image, cmsImage.getLocation(), locale);

        return image;
    }

    /**
     * Creates an Image from an Instagram compound (InstagramLink)
     *
     * @param document Compound with the information for the image
     * @param module   Module where potential issues will be logged
     * @param locale   Locale for translated texts (i.e. location)
     * @return FlatImage Object
     */
    public FlatImage createImage(InstagramImage document, Module<?> module, Locale locale) {
        try {
            JsonNode instagramInfo = requestInstagramImageData(document);

            if (instagramInfo != null) {
                FlatImage image = new FlatImage();
                image.setExternalImage(instagramInfo.get("thumbnail_url").asText());
                image.setCredit(instagramInfo.get("author_name").asText());
                image.setAltText(document.getCaption());
                image.setDescription(document.getCaption());
                image.setSource(FlatImage.Source.INSTAGRAM);
                image.setPostUrl(Properties.INSTAGRAM_API + document.getId());

                populateLocation(image, document.getLocation(), locale);

                return image;
            } else {
                module.addErrorMessage("The Instagram id is no longer valid");
                String issue = CommonUtils.contentIssue("The Instagram id %s is no longer, Listicle = %s ",
                        document.getId(), document.getPath());
                logger.warn(issue);
            }
        } catch (Exception e) {
            module.addErrorMessage("Error while accessing Instagram: " + e.getMessage());
            logger.error("Error while accessing Instagram", e);
        }

        return null;
    }

    /**
     * TODO Comment!
     *
     * @param dmsProduct
     * @param module
     * @return
     */
    public FlatImage createImage(JsonNode dmsProduct, Module<?> module) {

        if (dmsProduct.has(IMAGE)) {

            JsonNode dmsImage = dmsProduct.get(IMAGE).get(0);
            if (dmsImage.has(MEDIA)) {
                FlatImage image = new FlatImage();

                image.setExternalImage(get(dmsImage, MEDIA, null));
                image.setCredit(get(dmsImage, CREDIT, null));
                image.setAltText(get(dmsImage, ALT_TEXT, get(dmsProduct, NAME, null)));
                image.setDescription(image.getAltText());

                if (dmsProduct.has(LATITUDE) && dmsProduct.has(LONGITUDE)) {
                    image.setCoordinates(new Coordinates(dmsProduct.get(LATITUDE).asDouble(), dmsProduct.get(LONGITUDE).asDouble()));
                }
                return image;
            }
        }

        module.addErrorMessage(String.format("The dmsProduct '%s' does not have an image", get(dmsProduct, ID, "unknown")));
        return null;
    }

    private String get(JsonNode node, String field, String defaultValue) {
        return node.has(field) ? node.get(field).asText(defaultValue) : defaultValue;
    }

    /**
     * Request the main image to instagram, so we can embed it in the site.
     *
     * @throws IOException when the response is not readable or the image server is not accessible
     */
    private JsonNode requestInstagramImageData(InstagramImage instagramLink) throws IOException {
        String accessToken = bundle.getResourceBundle("config.cms", "instagram.accesstoken", Locale.UK);
        String url = bundle.getResourceBundle("config.cms", "instagram.api", Locale.UK);
        URL instagramInformation = new URL(url + instagramLink.getId() + "&access_token=" + accessToken);
        String responseInstagram = utils.requestUrl(instagramInformation.toString());
        if (responseInstagram != null) {
            return new ObjectMapper().readTree(responseInstagram);
        }
        return null;
    }

    /**
     * Populates the fields Location and Coordinates from the DMS information providing translated locations for
     * all images.
     */
    private void populateLocation(FlatImage image, String location, Locale locale) {
        LocationObject locationObject = locationLoader.getLocation(location, locale);
        if (locationObject != null) {
            image.setCoordinates(new Coordinates(locationObject.getLatitude(), locationObject.getLongitude()));

            image.setLocation(locationObject.getName());
        }
    }
}
