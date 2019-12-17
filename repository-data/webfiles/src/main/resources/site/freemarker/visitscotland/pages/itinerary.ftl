<#include "../../include/imports.ftl">
<#include "itinerary-stop.ftl">

<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">

<#include "../../vs-dotcom-ds/components/itinerary.ftl">
<#include "../../vs-dotcom-ds/components/hero.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-summary-list.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-summary-list-item.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-highlights-list.ftl">

<#include "../../vs-dotcom-ds/components/itinerary-day.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-map.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-map-marker.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Itinerary" -->
<#-- @ftlvariable name="firstStopLocation" type="java.lang.String" -->
<#-- @ftlvariable name="lastStopLocation" type="java.lang.String" -->

<#-- Template defined objects -->
<#-- @ftlvariable name="day" type="com.visitscotland.brmx.beans.Day" -->
<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<@hst.setBundle basename="itinerary, toomany, keyFacilities"/> <#-- TODO: keyFacilities shouldn't be defined here -->

<#assign heroLat = 0><#-- TODO: "${document.heroImage.latitude}" -->
<#assign heroLon = 0><#-- TODO: ${document.heroImage.longitude}" -->
<#assign mainTransport = "">
<#assign dayNumber = 0>
<#assign stopNumber = 0>
<#assign lastStop = 0>


<#if document.transports?has_content >
    <#assign mainTransport = document.transports[0]>
</#if>



<div class="has-edit-button">
      <@hst.manageContent hippobean=document documentTemplateQuery="new-document" rootPath="site"  />

    <vs-itinerary>
        <#if document.heroImage??>
            <@hst.link var="hero" hippobean=document.heroImage.original/>
        <vs-hero
                altText="${document.heroImage.altText}"
                credit="${document.heroImage.credit}"
                description="${document.heroImage.description}" <#-- TODO: description for the image?? -->
                image-src="${hero}"
                latitude="${heroLat}"
                longitude="${heroLon}"
        >
            <img
                    class="lazyload"
                    src="${hero}"
                    srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                    data-srcset="${hero}"
                    alt="${document.heroImage.altText}"
                    data-sizes="auto"
                    slot="image" />
            <noscript>
                <img class="img-fluid" src="${hero}" alt="${document.heroImage.altText}" />
            </noscript>
        </vs-hero>
        </#if>
        <vs-container>
            <div class="vs-itineraries__intro-wrapper">
                <#-- TODO: BREADCRUMB as part of the main template -->
                <#--<vs-breadcrumb>-->
                    <#--<vs-breadcrumb-item-->
                            <#--v-for="(item, index) in breadcrumb.breadcrumb"-->
                            <#--:key="index"-->
                            <#--:href="item.href"-->
                            <#--:active="item.active"-->
                            <#--:text="item.name"-->
                    <#-->-->
                    <#--</vs-breadcrumb-item>-->
                <#--</vs-breadcrumb>-->
                <vs-row class="justify-content-md-between">
                    <vs-col cols="12" sm="6" lg="7">
                        <vs-heading level="1">
                            ${document.heading}
                        </vs-heading>
                        <div class="lead" > <#-- NOTE: v-html="itinerary.introduction" doesn't work -->
                            <@hst.html hippohtml=document.introduction/>
                        </div>
                        <dl class="list-inline">
                            <dt class="list-inline-item"><@fmt.message key="start-finish"/></dt>
                            <dd class="list-inline-item">${firstStopLocation}/${lastStopLocation}</dd><#-- TODO: Starting point == Finishing ==> remove finish and separator -->
                        </dl>
                    </vs-col>
                    <vs-col cols="12" sm="6" md="5" lg="4">
                        <vs-itinerary-summary-list>
                            <vs-itinerary-summary-list-item>
                                <strong><@fmt.message key="days"/></strong>
                                <span>${document.days?size}</span>
                            </vs-itinerary-summary-list-item>
                            <vs-itinerary-summary-list-item>
                                <strong><@fmt.message key="distance"/><br /><abbr title="miles"><@fmt.message key="miles-abbreviation"/></abbr>/<abbr title="kilometres"><@fmt.message key="kilometres-abbreviation"/></abbr></strong>
                                <span>${document.distance}<span class="divider">/</span>${document.distance*1.6}</span><#-- TODO: #{document.distance*1.609344; M2} -->
                            </vs-itinerary-summary-list-item>
                            <vs-itinerary-summary-list-item>
                                <strong><@fmt.message key="transport"/></strong>
                                <div class="icon-wrapper">
                                    <vs-icon name="${mainTransport}" variant="dark" size="sm" />
                                    <@fmt.message key="${mainTransport}"/>
                                </div>
                            </vs-itinerary-summary-list-item>
                            <vs-itinerary-summary-list-item>
                                <strong><@fmt.message key="themes"/></strong>
                                <div class="icon-wrapper">
                                    <#--<vs-icon name="${document.theme}" variant="dark" size="sm" />--> <#-- TODO: Broken component. Defensive programing??? -->
                                    <@fmt.message key="${document.theme}"/>
                                </div>
                            </vs-itinerary-summary-list-item>
                        </vs-itinerary-summary-list>
                    </vs-col>
                </vs-row>
            </div>
        </vs-container>


        <div class="bg-light vs-itineraries__highlights-wrapper">
            <vs-container>
                <vs-itinerary-highlights-list>
                    <dt>Highlights</dt>
                    <dd class="mb-0">
                        <div style="white-space: pre-wrap">${document.highlights}</div><#-- TODO: Apply pre wrap to the component -->
                    </dd>
                    <dt class="mt-6">Areas Covered</dt>
                    <dd class="mb-0">
                        <div style="white-space: pre-wrap"><#list document.areas as area><@fmt.message key="${area}"/>${"\n"}</#list></div><#-- TODO: Apply pre wrap to the component -->
                    </dd>
                </vs-itinerary-highlights-list>
            </vs-container>
        </div>


        <div class="position-sticky">
            <vs-itinerary-map
                    access-token="pk.eyJ1IjoidmlzaXRzY290bGFuZC1kZXYiLCJhIjoiY2p4MGZwcmtjMDBlczN5bTBnY3pjeHNubCJ9.d3CJWPvX9FfjfSNAW98Q6w"
                    overview-map-longitude="57.81"
                    overview-map-latitude="-4.13"
                    overview-map-zoom="5"
                    :stops="stops"
                    :labels='{
                "mapControlsFullscreenOpen": "Show fullscreen",
                "mapControlsFullscreenClose": "Exit fullscreen",
                "mapControlsCompass": "Reset angle",
                "mapControlsZoomIn": "Zoom in",
                "mapControlsZoomOut": "Zoom out"
            }'
            >
            </vs-itinerary-map>
            <vs-container>
                <vs-row>
                    <vs-col cols="12">
                        <ul class="list-unstyled">
                            <#list document.days as day>
                                <#assign dayNumber++>

                            <vs-itinerary-day
                                    defaultShow="(${(dayNumber < 3)?c}"
                                    key="day-${dayNumber}"
                            >
                                <vs-heading
                                        slot="day-title"
                                        level="2"
                                        thin
                                        class="vs-itinerary-day__title">

                                    <span>Día ${dayNumber}</span>
                                    ${day.title}
                                </vs-heading>

                                <#--<dl v-if="day.dayMiles && day.dayKM" slot="day-distance" class="list-inline text-center">--> <#-- TODO: remove day distance?? -->
                                    <#--<dt class="list-inline-item"><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr>:</dt>-->
                                    <#--<dd class="list-inline-item">{{day.dayMiles}}/{{day.dayKM}}</dd>-->
                                <#--</dl>-->

                                <#-- TODO: TRANSPORT FOR THE DAY -->
                                <#--<dl v-if="day.transport.length" class="list-inline text-center" slot="day-transport">-->
                                    <#--<dt class="list-inline-item">Transport:</dt>-->
                                    <#--<dl class="list-inline-item" v-for="(transportType, transportTypeIndex) in day.transport">-->
                                        <#--<vs-itinerary-transport-type :transportType="transportType">-->
                                            <#--<span class="sr-only">{{transportType.value}}</span>-->
                                        <#--</vs-itinerary-transport-type>-->
                                    <#--</dl>-->
                                <#--</dl>-->
                                <#-- TODO: TRANSPORT FOR THE DAY END -->

                                <div slot="day-introduction" class="has-edit-button"  style="padding-top: 1.6%" ><#-- TODO v-html="day.introduction" doesn't work -->
                                    <@hst.manageContent hippobean=day />
                                    <@hst.html hippohtml=day.introduction/>
                                </div>

                                <!-- STOP STARTS HERE -->
                                <ul slot="stops" class="mt-9 list-unstyled">
                                <#--<ul class="list-unstyled">-->
                                <#assign lastStop = lastStop + day.stops?size>
                                <#list day.stops as stop>
                                    <#assign stopNumber++>
                                    <@itineraryStop stop=stop stopNumber=stopNumber lastStop=stopNumber==lastStop/>
                                </#list>
                                </ul>
                                <!-- STOP ENDS HERE -->
                            </vs-itinerary-day>
                            </#list>
                        </ul>
                    </vs-col>
                </vs-row>
            </vs-container>
        </div>


        <#--<vs-related-content-list>-->
            <#--<h2 slot="header" class="text-warning text-center py-7 m-0">Extend Your Trip</h2>-->
            <#--<vs-related-content-list-item-->
                    <#--v-for="(item, index) in relatedContent.relatedContent"-->
                    <#--:key="index"-->
                    <#--slot="cards"-->
            <#-->-->
                <#--<vs-related-content-card>-->
                    <#--<img-->
                            <#--:src="item.image.imageSrc"-->
                            <#--:alt="item.image.imageAlt"-->
                            <#--class="card-img-top"-->
                    <#-->-->
                    <#--<div class="card-body">-->
                        <#--<h3 class="card-title h5">-->
                            <#--<a class="stretched-link" :href="item.href">-->
                                <#--{{item.title}}-->
                            <#--</a>-->
                        <#--</h3>-->
                        <#--<div class="card-text">-->
                            <#--{{item.description}}-->
                        <#--</div>-->
                    <#--</div>-->
                <#--</vs-related-content-card>-->
            <#--</vs-related-content-list-item>-->
        <#--</vs-related-content-list>-->
    </vs-itinerary>
</div>