<#include "../../include/imports.ftl">

<@hst.setBundle basename="keyFacilities,itinerary"/>

<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->
<#-- @ftlvariable name="prod" type="com.visitscotland.brmx.beans.mapping.FlatStop" -->

<#macro itineraryStop stop stopNumber lastStop>
    <@hst.manageContent hippobean=stop />

    <#assign prod = stops[stop.identifier]>
    <#assign title = prod.title />

    <#if prod.cmsImage??>
        <#assign image>
            <@hst.link hippobean=prod.cmsImage.original/>
        </#assign>
    <#elseif prod.image??>
        <#assign image = prod.image.url />
    <#else >
        <#assign image = "" />
    </#if>

    <#--<#assign facility = "wifi" />-->

    <#assign stopsCount = prod.index>
    <#assign latitude = "0.0">
    <#assign longitude = "0.0">
    <#assign href = "">
    <#assign prod = "">
    <#assign timeToExplore = "">
    <#assign address = "">
    <#assign priceText = "">
    <#assign imgAltText = "">
    <#assign imgCredit = "">



<#-- INTEGRATION WITH DS STARTS -->
<@hst.manageContent hippobean=stop />

    <#assign stopItem = stop.getStopItem()>
    <li class="vs-itinerary-stop__list-item" data-stop="${stopNumber}">
      <div class="d-flex justify-content-between align-items-top">
        <vs-icon name="map-marker-filled" variant="secondary-teal" size="md" :padding="0"></vs-icon>
        <vs-heading
          level="3"
          thin
          class="vs-itinerary-stop__title ml-4 flex-fill">
          <span
          >Stop ${stopNumber}</span>
          ${stop.title}
        </vs-heading>
        <vs-favourites-toggle-button
          href="stop.href"
          title="stop.title">
        </vs-favourites-toggle-button>
      </div>
      <#if stopItem.image??>
        <vs-image-with-caption
          altText="${stopItem.image.altText}"
          credit="${stopItem.image.credit}"
          caption="${stopItem.image.caption}"
          image-src="<@hst.link hippobean=stopItem.image.original/>"
          latitude="${stopItem.image.coordinates.latitude}"
          longitude="${stopItem.image.coordinates.longitude}"
          >
        <img
          class="lazyload"
          src="<@hst.link hippobean=stopItem.image.original/>"
          srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
          data-srcset="<@hst.link hippobean=stopItem.image.original/>"
          alt="${stopItem.image.altText}"
          data-sizes="auto" />
        </vs-image-with-caption>
        <dl>
          <dt>Image altText:</dt>
          <dd>${stopItem.image.altText}</dd>
          <dt>Image credit:</dt>
          <dd>${stopItem.image.credit}</dd>
          <dt>Image caption:</dt>
          <dd>${stopItem.image.caption}</dd>
          <dt>Image latitude:</dt>
          <dd>${stopItem.image.coordinates.latitude}</dd>
          <dt>Image longitude:</dt>
          <dd>${stopItem.image.coordinates.longitude}</dd>
        </dl>
      </#if>
      <#if stop.description?? && stop.description?has_content>
        <@hst.html hippohtml=stop.description/>
      </#if>
        <#if stopItem?? && stopItem.timeToExplore??>
        <dl slot="stop-time-to-explore" class="list-inline my-4 mb-0">
            <dt class="list-inline-item mb-0">Time to explore:</dt>
            <dd class="list-inline-item mb-0">${stopItem.timeToExplore}</dd>
        </dl>
        </#if>
        <#if stop.tips?? && stop.tips?has_content>
          <vs-itinerary-stop-pullout>
            <div slot="text">
              <#if stop.tipsTitle?? && stop.tipsTitle?has_content>
                <strong>${stop.tipsTitle}</strong>
              </#if>
              <@hst.html hippohtml=stop.tips/>
            </div>
            <vs-svg slot="svg" path="highland-cow" />
          </vs-itinerary-stop-pullout>
        </#if>

        <#if lastStop=="true">
          <vs-button
            class="d-inline-flex mb-4"
            variant="outline-primary"
            href="https://www.visitscotland.com/info/accommodation/search-results?prodtypes=cate&lat=${latitude}&lng=${longitude}&locprox=2&areaproxdist=1&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&order=proximityAsc"
          >
              <vs-icon name="food" variant="primary" size="sm"></vs-icon>
              <@fmt.message key="stop.nearby-eat"/>
          </vs-button>
          <br />
          <vs-button
            class="d-inline-flex"
            variant="outline-primary"
            href="https://www.visitscotland.com/info/accommodation/search-results?prodtypes=acco&lat=${latitude}&lng=${longitude}&locprox=2&areaproxdist=1&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&order=proximityAsc"
          >
              <vs-icon name="product-accommodation" variant="primary" size="sm"></vs-icon>
              <@fmt.message key="stop.nearby-stay"/>
          </vs-button>
      </#if>
    </li>
</#macro>