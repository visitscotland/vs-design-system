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
<div class="has-edit-button">
<@hst.manageContent hippobean=stop />

     <vs-itinerary-stop
             v-for='(stop) in [
             {
                "key": "stop-${stopNumber}",
                "count": "${stopNumber}",
                "title": "${title}", <#-- There is need to escape single quote characters-->
                "href": "${href}",
                "timeToExplore": "${timeToExplore}",
                "description": null, <#-- Note: we couln't escape Hippo HTML -->
                "image": {
                    "imageSrc": "${image}",
                    "altText":"${imgAltText}",
                    "credit":"${imgCredit}",
                    "description":"${stop.title}",
                    "latitude":"${latitude}",
                    "longitude":"${longitude}"
                },
                "pullOut": {
                    "title": null, <#-- Note: Not in use -->
                    "description": null, <#-- Note: we couln't escape Hippo HTML -->
                },
                "facilities" : null <#-- Facilities is a list and it is easier to fill a slot with the data?--> <!-- Furthermore, the description is not part of the list since it needs to be translated -->
            }
    ]'
             :stop="stop"
             :key="stop.key"
     >
         <vs-heading
                 slot="stop-title"
                 level="3"
                 thin
                 class="vs-itinerary-stop__title ml-4"
         >
             <span><@fmt.message key="stop.title"/> {{stop.count}}</span>
             {{stop.title}}
         </vs-heading>

         <#-- TODO : This component doesn't work because of BootStrap
         <vs-favourites-toggle-button
         slot="stop-favourite"
         :href="stop.href"
         :title="stop.title"
         />
     -->

         <vs-itinerary-stop-image
             :altText="stop.image.altText"
             :credit="stop.image.credit"
             :description="stop.image.description"
             :image-src="stop.image.imageSrc"
             :latitude="stop.image.latitude"
             :longitude="stop.image.longitude"
             slot="stop-image"
     >
         <img
                 class="lazyload"
                 :src="stop.image.imageSrc"
                 srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                 :data-srcset="stop.image.imageSrc"
                 :alt="stop.image.altText"
                 data-sizes="auto"
                 slot="image"
         />

     </vs-itinerary-stop-image>



         <#if timeToExplore?has_content> <#-- TODO: USE v-if -->
         <dl slot="stop-time-to-explore" class="list-inline my-4 mb-0">
             <dt class="list-inline-item mb-0"><@fmt.message key="stop.timeToExplore"/></dt>
             <dd class="list-inline-item mb-0">{{stop.timeToExplore}}</dd>
         </dl>
         </#if>

        <#if stop.tips.content?has_content>
         <vs-itinerary-stop-pullout slot="stop-pullout">
             <div slot="text">
                <strong> ${stop.tipsTitle} </strong>
                <p> <@hst.html hippohtml=stop.tips/></p>
             </div>
             <vs-svg slot="svg" path="highland-cow" />
         </vs-itinerary-stop-pullout>

        <a slot="stop-href" class="vs-itinerary__stop-link text-uppercase font-weight-bold d-inline-flex align-items-center"
           :href="stop.href">
            <@fmt.message key="stop.cta"/>
            <vs-icon name="play" variant="primary" size="xxs" :padding=3 />
        </a>

            <#if facilities??>
        <dl class="itinerary-stop__facilities" slot="stop-facilities">
            <dt>Key facilities</dt>
            <#list facilities?split(",") as facility>
                <vs-itinerary-stop-facility
                        key="${facility}"
                        facility="${facility}"
                ><#-- TODO: KEY and FACILITY has always the same value -->
                   <@fmt.message key="${facility}"/>
                </vs-itinerary-stop-facility>
            </#list>
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