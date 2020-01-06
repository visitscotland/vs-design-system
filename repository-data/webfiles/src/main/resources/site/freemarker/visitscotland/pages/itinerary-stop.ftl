<#include "../../include/imports.ftl">

<@hst.setBundle basename="keyFacilities,itinerary"/>

<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->
<#-- @ftlvariable name="prod" type="com.visitscotland.brmx.beans.mapping.FlatStop" -->

<#macro itineraryStop stop lastStop>
    <#assign prod = stops[stop.identifier]>
    <#assign title = prod.title />

    <#if prod.cmsImage??>
        <#assign image>
            <@hst.link hippobean=prod.cmsImage.original/>
        </#assign>
    <#elseif prod.image??>
        <#assign image = prod.image.url />
    <#else>
        <#assign image = "" />
    </#if>
    
    <#assign stopNumber = prod.index>
    <#assign latitude = prod.coordinates.latitude>
    <#assign longitude = prod.coordinates.longitude>
    <#assign href = prod.cta>
    <#assign timeToExplore = prod.timeToExplore>
    <#assign address = prod.address>
    <#assign priceText = prod.priceText>
    <#assign imgAltText = prod.imgAltText>
    <#assign imgCredit = prod.imgCredit>
    <#assign description = prod.description>
    <#assign tipsTitle = prod.tipsTitle>
    <#assign tips = prod.tipsBody>

    <li class="vs-itinerary-stop__list-item has-edit-button" data-stop="${stopNumber}">
      <@hst.manageContent hippobean=stop />
      <div class="d-flex justify-content-between align-items-top">
        <vs-icon name="map-marker-filled" variant="secondary-teal" size="md" :padding="0"></vs-icon>
        <vs-heading 
          level="3" 
          thin 
          class="vs-itinerary-stop__title ml-4 flex-fill">
          <span 
          >Stop ${stopNumber}</span>
          <#if !stop.stopItem?? && editMode>
            <span class="text-danger">The stop doesn't have any product linked to the stop</span>
          <#else>
            ${title}
          </#if>
        </vs-heading>
         <#if href?? && href?has_content>
            <vs-favourites-toggle-button
            href="${href}"
            title="${title}">
            </vs-favourites-toggle-button>
        </#if>
      </div>
      <#if image?? && image?has_content>
        <vs-image-with-caption
          alt-text="${imgAltText}"
          credit="${imgCredit}"
          caption="${imgAltText}"
          image-src="${image}"
          latitude="${latitude}"
          longitude="${longitude}"
          >
        <img 
          class="lazyload" 
          src="${image}"
          srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
          data-srcset="${image}" 
          alt="${imgAltText}"
          data-sizes="auto" />
        </vs-image-with-caption>
      </#if>


      <#if description?? && description?has_content>
        <@hst.html hippohtml=description/>
      </#if>

        <#if timeToExplore?? && timeToExplore?has_content>
        <dl slot="stop-time-to-explore" class="list-inline my-4 mb-0">
            <dt class="list-inline-item mb-0">Time to explore:</dt>
            <dd class="list-inline-item mb-0">${timeToExplore}</dd>
        </dl>
        </#if>
        <#if tips?? && tips?has_content>
          <vs-itinerary-stop-pullout>
            <div slot="text">
              <#if tipsTitle?? && tipsTitle?has_content>
                <strong>${tipsTitle}</strong>
              </#if>
              <@hst.html hippohtml=tips/>
            </div>
            <vs-svg slot="svg" path="highland-cow" />
          </vs-itinerary-stop-pullout>
        </#if>

        <#if prod.facilities?? && prod.facilities?size gt 1>
            <dl class="itinerary-stop__facilities">
                <dt class="list-inline-item"><@fmt.message key="stop.key-facilities"/>:</dt>
                <#list prod.facilities as facility>
                    <dd class="list-inline-item">
                        <vs-icon name="${facility}" variant="dark" size="sm"></vs-icon>
                        <span class="d-block"><@fmt.message key="${facility}"/></span>
                    </dd>
                </#list>
            </dl>
        </#if>

        <#if lastStop=="true" && longitude?? && longitude?has_content && latitude?? && latitude?has_content>
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