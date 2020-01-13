<#include "../../include/imports.ftl">

<#include "../../vs-dotcom-ds/stores/itineraries-store.ftl">
<#include "../../vs-dotcom-ds/components/page-intro.ftl">
<#include "../../vs-dotcom-ds/components/hero.ftl">
<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">
<#include "../../vs-dotcom-ds/components/icon.ftl">
<#include "../../vs-dotcom-ds/components/favourites-toggle-button.ftl">
<#include "../../vs-dotcom-ds/components/image-with-caption.ftl">
<#include "../../vs-dotcom-ds/components/image-location-map.ftl">
<#include "../../vs-dotcom-ds/components/button.ftl">
<#include "../../vs-dotcom-ds/components/heading.ftl">
<#include "../../vs-dotcom-ds/components/tooltip.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-list.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-list-item.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-day.ftl">
<#include "../../vs-dotcom-ds/components/itinerary.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-highlights-list.ftl">
<#include "../../vs-dotcom-ds/components/svg.ftl">
<#include "../../vs-dotcom-ds/components/related-content-list.ftl">
<#include "../../vs-dotcom-ds/components/related-content-list-item.ftl">
<#include "../../vs-dotcom-ds/components/related-content-card.ftl">

<#include "itinerary-stop.ftl">
<#include "itinerary-map.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Itinerary" -->
<#-- @ftlvariable name="firstStopLocation" type="java.lang.String" -->
<#-- @ftlvariable name="lastStopLocation" type="java.lang.String" -->

<#-- Template defined objects -->
<#-- @ftlvariable name="day" type="com.visitscotland.brmx.beans.Day" -->
<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<@hst.setBundle basename="itinerary, toomany, keyFacilities, keys"/> <#-- TODO: keyFacilities shouldn't be defined here -->

<#assign heroLat = 0><#-- TODO: "${document.heroImage.latitude}" -->
<#assign heroLon = 0><#-- TODO: ${document.heroImage.longitude}" -->
<#assign mainTransport = "">
<#assign dayNumber = 0>
<#assign stopNumber = 0>
<#assign lastStop = 0>

<#if document.start?has_content>
   <#assign firstStopLocation = document.start>
</#if>
<#if document.finish?has_content>
    <#assign lastStopLocation = document.finish>
</#if>

<#if document.transports?has_content >
    <#assign mainTransport = document.transports[0]>
</#if>



<div class="has-edit-button">
      <@hst.manageContent hippobean=document documentTemplateQuery="new-document" rootPath="site" defaultPath="${path}" />

    <vs-page-intro>
        <#if document.heroImage??>
            <@hst.link var="hero" hippobean=document.heroImage.original/>
            <vs-hero
                alt-text="${document.heroImage.altText}"
                credit="${document.heroImage.credit}"
                caption="${document.heroImage.caption}"
                image-src="${hero}"
                latitude="${document.heroImage.coordinates.latitude}"
                longitude="${document.heroImage.coordinates.longitude}"
            >
            <img
                class="lazyload"
                src="${hero}"
                srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                data-srcset="${hero}"
                alt="${document.heroImage.altText}"
                data-sizes="auto"
                    />
        </vs-hero>
        </#if>
        <vs-container>
            <div class="vs-page-intro__wrapper--inner">
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
                            ${document.title}
                        </vs-heading>
                    </vs-col>
                </vs-row>
                <vs-row class="justify-content-md-between">
                    <vs-col cols="12" sm="6" lg="7">
                        <div class="lead">
                            <@hst.html hippohtml=document.introduction/>
                        </div>
                        <dl class="list-inline">
                            <dt class="list-inline-item"><@fmt.message key="start-finish"/></dt>
                            <dd class="list-inline-item">${firstStopLocation} / ${lastStopLocation}</dd>
                        </dl>
                    </vs-col>
                    <vs-col cols="12" sm="6" md="5" lg="4">
                        <vs-summary-box-list class="bg-warning">
                            <vs-summary-box-list-item>
                                <strong><@fmt.message key="days"/></strong>
                                <span>${document.days?size}</span>
                            </vs-summary-box-list-item>
                            <vs-summary-box-list-item>
                                <strong><@fmt.message key="distance"/><br /><abbr title="<@fmt.message key="miles"/>"><@fmt.message key="miles-abbreviation"/></abbr>/<abbr title="<@fmt.message key="kilometres"/>"><@fmt.message key="kilometres-abbreviation"/></abbr></strong>
                                <span>${document.distance}<span class="divider">/</span>${document.distance*1.6}</span><#-- TODO: #{document.distance*1.609344; M2} talk to team regarding rounding the display-->
                            </vs-summary-box-list-item>
                            <vs-summary-box-list-item>
                                <strong><@fmt.message key="transport"/></strong>
                                <div class="icon-wrapper">
                                    <vs-icon name="${mainTransport}" variant="dark" size="sm"></vs-icon>
                                    <@fmt.message key="${mainTransport}"/>
                                </div>
                            </vs-summary-box-list-item>
                            <vs-summary-box-list-item>
                                <strong><@fmt.message key="themes"/></strong>
                                <div class="icon-wrapper">
                                    <vs-icon name="${document.theme}" variant="dark" size="sm"></vs-icon>
                                    <@fmt.message key="${document.theme}"/>
                                </div>
                            </vs-summary-box-list-item>
                        </vs-summary-box-list>
                    </vs-col>
                </vs-row>
            </div>
            <vs-itinerary-highlights-list>
                <dt><@fmt.message key="highlights"/></dt>
                <#-- TODO: each ${document.highlight} should render a new dd element -->
                <dd class="mb-0">
                    <div style="white-space: pre-wrap">${document.highlights}</div>
                </dd>
                <dt class="mt-6"><@fmt.message key="areas-covered"/></dt>
                <#list document.areas as area>
                    <dd class="mb-0">
                        <@fmt.message key="${area}"/>${"\n"}
                    </dd>
                </#list>
            </vs-itinerary-highlights-list>
        </vs-container>
    </vs-page-intro>
    <vs-itinerary>
        <@itineraryMap days=document.days />
        <vs-container slot="list">
            <vs-row>
                <vs-col cols="12" tag="ul" class="list-unstyled">
                    <#list document.days as day>
                        <#assign dayNumber++>
                        <#assign dayTransport = "">
                        <vs-itinerary-day :default-show=${(dayNumber < 3)?c}>
                            <vs-heading
                                slot="day-title"
                                level="2"
                                thin
                                class="vs-itinerary-day__title">
                                <span class="text-secondary-teal"><@fmt.message key="day"/> ${dayNumber}</span>
                                ${day.title}
                            </vs-heading>

                            <vs-summary-box-list-item>
                                <strong><@fmt.message key="transport"/></strong>
                                <div class="icon-wrapper">
                                    <vs-icon name="${mainTransport}" variant="dark" size="sm"></vs-icon>
                                    <span><@fmt.message key="${mainTransport}"/></span>
                                </div>
                            </vs-summary-box-list-item>

                            <#if day.transports?has_content>
                                <#assign dayTransport = day.transports[0]>
                                <dl class="list-inline text-center" slot="day-transport">
                                    <dt class="list-inline-item"><@fmt.message key="transport"/>:</dt>
                                    <#list day.transports as transport>
                                        <dd class="list-inline-item">
                                            <#-- TODO: Tooltip title and sr-only should spit out the transport value, not the key -->
                                            <vs-tooltip title="<@fmt.message key="${transport}"/>">
                                                <vs-icon name="${transport}" variant="dark" size="sm"></vs-icon>
                                            </vs-tooltip>
                                            <#-- TODO: Tooltip title and sr-only should spit out the transport value, not the key -->
                                            <span class="sr-only"><@fmt.message key="${transport}"/></span>
                                        </dd>
                                    </#list>
                                </dl>
                            </#if>

                            <div slot="day-introduction">
                                <@hst.html hippohtml=day.introduction/>
                            </div>

                            <!-- STOP STARTS HERE -->
                            <ul slot="stops" class="mt-9 list-unstyled">
                                <#assign lastStop = lastStop + day.stops?size>
                                <#list day.stops as stop>
                                    <#assign stopNumber++>
                                    <@itineraryStop stop=stop lastStop=(stopNumber==lastStop)?c/>
                                </#list>
                            </ul>
                            <!-- STOP ENDS HERE -->
                        </vs-itinerary-day>
                    </#list>
                </vs-col>
            </vs-row>
        </vs-container>
    </vs-itinerary>


    <vs-related-content-list>
        <h2 slot="header" class="text-warning text-center py-7 m-0">Extend Your Trip</h2>
        <vs-related-content-list-item slot="cards">
            <vs-related-content-card>
                <img
                    src="/site/binaries/content/gallery/visitscotland/default/vector-illustration-of-cartoon-hippos-in-hot-weather-drawing_csp36829792.jpg"
                    alt="Test Alt Text"
                    class="card-img-top"
                />
                <div class="card-body">
                    <h3 class="card-title h5">
                        <a class="stretched-link" href="www.google.com">
                            Unhappy Hippo 1
                        </a>
                    </h3>
                    <div class="card-text">
                        Lorem ipsum dolor sit amet. Test description.
                    </div>
                </div>
            </vs-related-content-card>
        </vs-related-content-list-item>
        <vs-related-content-list-item slot="cards">
            <vs-related-content-card>
                <img
                    src="/site/binaries/content/gallery/visitscotland/default/vector-illustration-of-cartoon-hippos-in-hot-weather-drawing_csp36829792.jpg"
                    alt="Test Alt Text"
                    class="card-img-top"
                />
                <div class="card-body">
                    <h3 class="card-title h5">
                        <a class="stretched-link" href="www.google.com">
                            Unhappy Hippo 2
                        </a>
                    </h3>
                    <div class="card-text">
                        Lorem ipsum dolor sit amet. Test description.
                    </div>
                </div>
            </vs-related-content-card>
        </vs-related-content-list-item>
        <vs-related-content-list-item slot="cards">
            <vs-related-content-card>
                <img
                    src="/site/binaries/content/gallery/visitscotland/default/vector-illustration-of-cartoon-hippos-in-hot-weather-drawing_csp36829792.jpg"
                    alt="Test Alt Text"
                    class="card-img-top"
                />
                <div class="card-body">
                    <h3 class="card-title h5">
                        <a class="stretched-link" href="www.google.com">
                            Unhappy Hippo 3
                        </a>
                    </h3>
                    <div class="card-text">
                        Lorem ipsum dolor sit amet. Test description.
                    </div>
                </div>
            </vs-related-content-card>
        </vs-related-content-list-item>
        <vs-related-content-list-item slot="cards">
            <vs-related-content-card>
                <img
                    src="/site/binaries/content/gallery/visitscotland/default/vector-illustration-of-cartoon-hippos-in-hot-weather-drawing_csp36829792.jpg"
                    alt="Test Alt Text"
                    class="card-img-top"
                />
                <div class="card-body">
                    <h3 class="card-title h5">
                        <a class="stretched-link" href="www.google.com">
                            Unhappy Hippo 4
                        </a>
                    </h3>
                    <div class="card-text">
                        Lorem ipsum dolor sit amet. Test description.
                    </div>
                </div>
            </vs-related-content-card>
        </vs-related-content-list-item>
    </vs-related-content-list>
</div>