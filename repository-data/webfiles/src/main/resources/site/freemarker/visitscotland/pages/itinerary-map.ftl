<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/itinerary.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-map.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-map-marker.ftl">

<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->
<#-- @ftlvariable name="prod" type="com.visitscotland.brmx.beans.mapping.FlatStop" -->

<#macro itineraryMap day>
    <vs-itinerary-map
        access-token="<@fmt.message key="mapbox.devkey"/>"
        overview-map-longitude="57.81"
        overview-map-latitude="-4.13"
        overview-map-zoom="5"
        :stops='[{
            title: "Beach fun at Elie And Earlsferry",
            latitude: "56.18974",
            longitude: "-2.81868",
            stopCount: "1",
            imageSrc: "https://via.placeholder.com/150",
            altText: "Image placeholder"
        },
        {
            title: "Kellie Castle and Garden",
            latitude: "56.24068",
            longitude: "-2.76587",
            stopCount: "2",
            imageSrc: "https://via.placeholder.com/150",
            altText: "Image placeholder"
        },
        {
            title: "Scotlands Secret Bunker",
            latitude: "56.27057",
            longitude: "-2.69826",
            stopCount: "3",
            imageSrc: "https://via.placeholder.com/150",
            altText: "Image placeholder"
        },
        {
            title: "Fish and chips in Anstruther",
            latitude: "56.22448",
            longitude: "-2.70087",
            stopCount: "4",
            imageSrc: "https://via.placeholder.com/150",
            altText: "Image placeholder"
        }]'
        :labels='{
            mapControlsFullscreenOpen: "Show fullscreen",
            mapControlsFullscreenClose: "Exit fullscreen",
            mapControlsCompass: "Reset angle",
            mapControlsZoomIn: "Zoom in",
            mapControlsZoomOut: "Zoom out"
        }'
    >
    </vs-itinerary-map>
</#macro>