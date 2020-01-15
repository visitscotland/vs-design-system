<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-tips.ftl">

<@hst.setBundle basename="keyFacilities,itinerary"/>

<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->
<#-- @ftlvariable name="prod" type="com.visitscotland.brmx.beans.mapping.FlatStop" -->

<#macro itineraryStop stop lastStop>
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
    <#assign href = prod.cta>
    <#assign address = prod.address>
    <#assign priceText = prod.priceText>

    <li class="vs-itinerary-stop__list-item has-edit-button" data-stop="${prod.index}">
      <@hst.manageContent hippobean=stop />
      <div class="d-flex justify-content-between align-items-top">
        <vs-icon name="map-marker-filled" variant="secondary-teal" size="md" :padding="0"></vs-icon>
        <vs-heading 
          level="3" 
          thin 
          class="vs-itinerary-stop__title ml-4 flex-fill">
          <span
          ><@fmt.message key="stop.title"/> ${prod.index}</span>
            ${prod.title}
            <#if !stop.stopItem?? && editMode>
                <span class="text-danger">The stop doesn't have any product linked to the stop</span>
            </#if>
        </vs-heading>
         <#if href?? && href?has_content>
            <vs-favourites-toggle-button
            href="${href}"
            title="${prod.title}">
            </vs-favourites-toggle-button>
        </#if>
      </div>
      <#if image?? && image?has_content>
        <vs-image-with-caption
          alt-text="${prod.image.altText}"
          credit="${prod.image.credit}"
          caption="${prod.image.altText}"
          image-src="${image}"
          latitude="${prod.coordinates.latitude}"
          longitude="${prod.coordinates.longitude}"
          >
        <img 
          class="lazyload" 
          src="${image}"
          srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
          data-srcset="${image}" 
          alt="${prod.image.altText}"
          data-sizes="auto" />
        </vs-image-with-caption>
      </#if>

      <#if prod.description?? && prod.description?has_content>
        <@hst.html hippohtml=prod.description/>
      </#if>

        <#if prod.timeToexplore?? && prod.timeToexplore?has_content>
        <dl slot="stop-time-to-explore" class="list-inline my-4 mb-0">
            <dt class="list-inline-item mb-0"><@fmt.message key="stop.timeToExplore"/>:</dt>
            <dd class="list-inline-item mb-0">${prod.timeToexplore}</dd>
        </dl>
        </#if>
        <#if ((prod.tipsTitle?? && prod.tipsTitle?has_content) || (prod.tipsBody.content?? &&  prod.tipsBody.content.length() gt 1))>
          <vs-itinerary-tips>
            <div slot="text">
              <#if  prod.tipsTitle?? &&  prod.tipsTitle?has_content>
                <strong>${prod.tipsTitle}</strong>
              </#if>
              <@hst.html hippohtml=prod.tipsBody/>
            </div>
            <vs-svg slot="svg" path="highland-cow" />
          </vs-itinerary-tips>
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

        <#if lastStop=="true" && prod.coordinates.longitude?? && prod.coordinates.longitude?has_content && prod.coordinates.latitude?? && prod.coordinates.latitude?has_content>
          <vs-button
            class="d-inline-flex mb-4"
            variant="outline-primary"
            href="https://www.visitscotland.com/info/accommodation/search-results?prodtypes=cate&lat=${prod.coordinates.latitude}&lng=${prod.coordinates.longitude}&locprox=2&areaproxdist=5&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&order=proximityAsc"
          >
              <vs-icon name="food" variant="primary" size="sm"></vs-icon>
              <@fmt.message key="stop.nearby-eat"/>
          </vs-button>
          <br />
          <vs-button
            class="d-inline-flex"
            variant="outline-primary"
            href="https://www.visitscotland.com/info/accommodation/search-results?prodtypes=acco&lat=${prod.coordinates.latitude}&lng=${prod.coordinates.longitude}&locprox=2&areaproxdist=5&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&order=proximityAsc"
          >
              <vs-icon name="product-accommodation" variant="primary" size="sm"></vs-icon>
              <@fmt.message key="stop.nearby-stay"/>
          </vs-button>
      </#if>
    </li>
</#macro>