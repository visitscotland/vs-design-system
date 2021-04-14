package com.visitscotland.brxm.factory;

import com.fasterxml.jackson.databind.JsonNode;
import com.visitscotland.brxm.dms.DMSDataService;
import com.visitscotland.brxm.dms.DMSUtils;
import com.visitscotland.brxm.dms.LocationLoader;
import com.visitscotland.brxm.hippobeans.DMSLink;
import com.visitscotland.brxm.hippobeans.Itinerary;
import com.visitscotland.brxm.model.Coordinates;
import com.visitscotland.brxm.model.FlatLink;
import com.visitscotland.brxm.model.ItineraryStopModule;
import com.visitscotland.brxm.model.LinkType;
import com.visitscotland.brxm.services.CommonUtilsService;
import com.visitscotland.brxm.services.ResourceBundleService;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ItinerayFactory {


    private ResourceBundleService bundle;
    private LocationLoader locationLoader;
    private DMSDataService dmsData;
    private ImageFactory imageFactory;
    private DMSUtils utils;

    public ItinerayFactory(ResourceBundleService bundle, LocationLoader locationLoader, DMSDataService dmsData, ImageFactory imageFactory, DMSUtils utils) {
        this.bundle = bundle;
        this.locationLoader = locationLoader;
        this.dmsData = dmsData;
        this.imageFactory = imageFactory;
        this.utils = utils;
    }
}
