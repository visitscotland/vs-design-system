<#include "../../../include/imports.ftl">
<#include "../../../vs-dotcom-ds/components/itinerary-map.ftl">
<#include "../../../vs-dotcom-ds/components/itinerary-map-marker.ftl">

<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->
<#-- @ftlvariable name="prod" type="com.visitscotland.brmx.beans.mapping.FlatStop" -->

<#macro itineraryMap days>
    <vs-itinerary-map
        slot="map"
        access-token="<@fmt.message key="mapbox.devkey"/>"
        overview-map-longitude="57.81"
        overview-map-latitude="-4.13"
        overview-map-zoom="5"
        :stops='[<#list days as day>
            <#list day.stops as stop>
                <#assign prod = stops[stop.identifier]>
                <#assign image = "" />
                <#if prod.image.cmsImage??>
                    <#assign image>
                        <@hst.link hippobean=prod.image.cmsImage.original/>
                    </#assign>
                <#elseif prod.image.externalImage??>
                    <#assign image = prod.image.externalImage />
                <#else>
                </#if>
                <#if prod.coordinates?? && prod.coordinates.latitude?? && prod.coordinates.latitude?has_content && prod.coordinates.longitude?? && prod.coordinates.longitude?has_content>
                {
                    title: "${prod.title}",
                    latitude: "${prod.coordinates.latitude}",
                    longitude: "${prod.coordinates.longitude}",
                    stopCount: "${prod.index}",
                    imageSrc: "${image}",
                    altText: "${prod.title}"
                },
                </#if>
            </#list>
        </#list>]'
        :labels='{
            stopLabel: "<@fmt.message key="stop.title"/>",
            mapControlsFullscreenOpen: "Show fullscreen",
            mapControlsFullscreenClose: "Exit fullscreen",
            mapControlsCompass: "Reset angle",
            mapControlsZoomIn: "Zoom in",
            mapControlsZoomOut: "Zoom out"
        }'
    >
    </vs-itinerary-map>
</#macro>