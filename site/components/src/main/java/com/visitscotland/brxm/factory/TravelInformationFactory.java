package com.visitscotland.brxm.factory;

import com.visitscotland.brxm.hippobeans.TravelInformation;
import com.visitscotland.brxm.hippobeans.TravelInformationTab;
import com.visitscotland.brxm.hippobeans.TravelInformationTransportRow;
import com.visitscotland.brxm.model.TransportType;
import com.visitscotland.brxm.model.TravelInformationModule;
import com.visitscotland.brxm.model.TravelInformationModuleTab;
import com.visitscotland.brxm.model.TravelInformationTransportRowModule;
import com.visitscotland.brxm.utils.HippoUtilsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TravelInformationFactory {

    private final String TRAVEL_INFO_TRANSPORTS_OPTIONS = "travel-information-transports";
    private final HippoUtilsService utils;
    private static final Logger logger = LoggerFactory.getLogger(TravelInformationFactory.class);

    public TravelInformationFactory(HippoUtilsService utils) {
        this.utils = utils;
    }

    public TravelInformationModule getTravelInformation(TravelInformation document) {
        TravelInformationModule module = new TravelInformationModule();
        module.setTitle(document.getTitle());
        module.setCopy(document.getCopy());
        module.setGettingTo(getTravelInformationModuleTab(document.getGettingTo()));
        module.setGettingAround(getTravelInformationModuleTab(document.getGettingAround()));
        module.setHippoBean(document);
        return module;
    }

    private TravelInformationModuleTab getTravelInformationModuleTab(TravelInformationTab document) {
        TravelInformationModuleTab module = new TravelInformationModuleTab();
        module.setTitle(document.getTitle());
        module.setTravelInformationTransportRows(document.getAccordion()
                .stream().map(this::getTravelInformationTransportRowModule).collect(Collectors.toList()));
        module.setHippoBean(document);
        return module;
    }

    private TravelInformationTransportRowModule getTravelInformationTransportRowModule(TravelInformationTransportRow document) {
        Map<String, String> transportMap = utils.getValueMap(TRAVEL_INFO_TRANSPORTS_OPTIONS);
        TravelInformationTransportRowModule module = new TravelInformationTransportRowModule();
        module.setCopy(document.getCopy());
        module.setHippoBean(document);
        TransportType transportType = new TransportType();
        transportType.setKey(document.getTransport());
        if (!transportMap.containsKey(document.getTransport())) {
            logger.error("Transport options {} does not contain key {}", TRAVEL_INFO_TRANSPORTS_OPTIONS, document.getTransport());
        }
        transportType.setLabel(transportMap.getOrDefault(document.getTransport(), document.getTransport()));
        module.setTransport(transportType);
        return module;
    }

}
