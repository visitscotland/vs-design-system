<#include "../../include/imports.ftl">

<#include "../../vs-dotcom-ds/components/itinerary-stop.ftl">
<#include "../../vs-dotcom-ds/components/heading.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-stop-image.ftl">
<#include "../../vs-dotcom-ds/components/favourites-toggle-button.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-stop-pullout.ftl">
<#include "../../vs-dotcom-ds/components/button.ftl">
<#include "../../vs-dotcom-ds/components/icon.ftl">
<#include "../../vs-dotcom-ds/components/svg.ftl">


<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->

<#macro itineraryStop stop stopNumber>

    <#assign title = stop.title />

    <#assign stopsCount = stopNumber>
    <#assign latitude = "0.0">
    <#assign longitude = "0.0">
    <#assign href = "">
    <#assign prod = "">
    <#assign timeToExplore = "">
    <#assign address = "">
    <#assign priceText = "">
    <#assign imgAltText = "">
    <#assign imgCredit = "">

    <#if !stop.stopItem??>
        <#if editMode>
        <#-- TODO: Component Warning-message -->
            <h2 style="color: red">The stop doesn't have any product linked to the stop</h2>
        </#if>
    <#elseif stop.stopItem.product??> <#-- DMSLink TODO: CHECK TYPES  -->
        <#assign prod = productsMap[stop.stopItem.product]>

        <#if prod?has_content >
            <#assign latitude = prod.latitude>
            <#assign longitude = prod.longitude>
            <#assign href = "https://www.visitscotland.com${prod.url}"> <#-- TODO: Depends on the environment -->

            <#-- Override image if defined in the document -->
            <#if stop.stopItem.image??>
                <#-- TODO: This image check can be reused if a macro for images is created -->
                <@hst.link var="image" hippobean=stop.getStopItem().image.original/>
                <#if stop.getStopItem().image.altText??>
                    <#assign imgAltText = stop.getStopItem().image.altText>
                </#if>
                <#if stop.getStopItem().image.credit??>
                    <#assign imgCredit = stop.getStopItem().image.credit>
                </#if>

            <#else>
                <#assign image = prod.image >
            </#if>

            <#if prod.addressLine1??>
                 <#assign address = prod.addressLine1>
            </#if>

            <#if prod.price?? && prod.price!="null" >
                <#assign priceText = prod.multiplePrices?then("Prices from","Price") + " : " + prod.price>
            </#if>

            <#if prod.facilities?has_content >
                <#assign facilities=prod.facilities>
            </#if>

        <#elseif editMode>
        <#-- TODO: Component Warning-message -->
            <h2 style="color: red">The product id doesn't exist in the DMS</h2>
        </#if>
    <#elseif stop.stopItem.link??> <#-- ExternalProductLink TODO: CHECK TYPES -->
        <#assign href = stop.stopItem.link>

        <#if stop.stopItem.image??>
        <#-- TODO: This image check can be reused if a macro for images is created -->
            <@hst.link var="image" hippobean=stop.getStopItem().image.original/>
            <#if stop.getStopItem().image.altText??>
                <#assign imgAltText = stop.getStopItem().image.altText>
            </#if>
            <#if stop.getStopItem().image.credit??>
                <#assign imgCredit = stop.getStopItem().image.credit>
            </#if>
        </#if>

        <#if stop.stopItem.timeToExplore??>
            <#assign timeToExplore= stop.stopItem.timeToExplore>
        </#if>
    </#if>

<#-- INTEGRATION WITH DS STARTS -->
<div class="has-edit-button">
<@hst.manageContent hippobean=stop />

     <vs-itinerary-stop
             v-for='(stop) in [
             {
                "key": "stop-${stopNumber}",
                "count": "${stopNumber}",
                "title": "${stop.title}",
                "href":"${href}",
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
                "facilities" : null <#-- Facilities is a list and it is easier to fill a stot with the data?-->
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
             <span>Parada {{stop.count}}</span>
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

         <div slot="stop-description">
             <@hst.html hippohtml=stop.description/>
         </div>

         <#if timeToExplore?has_content> <#-- TODO: USE v-if -->
         <dl slot="stop-time-to-explore" class="list-inline my-4 mb-0">
             <dt class="list-inline-item mb-0">Tiempo recomendado:</dt>
             <dd class="list-inline-item mb-0">{{stop.timeToExplore}}</dd>
         </dl>
         </#if>

         <a slot="stop-href" class="vs-itinerary__stop-link text-uppercase font-weight-bold d-inline-flex align-items-center"
            :href="stop.href"
         >
             Averigua más informcación
             <vs-icon name="play" variant="primary" size="xxs" :padding=3 />
         </a>

        <#if stop.tips.content?has_content>
         <vs-itinerary-stop-pullout slot="stop-pullout">
             <div slot="text">
                <strong> ${stop.tipsTitle} </strong>
                <p> <@hst.html hippohtml=stop.tips/></p>
             </div>
             <vs-svg slot="svg" path="highland-cow" />
         </vs-itinerary-stop-pullout>
        </#if>

     </vs-itinerary-stop>

<#-- INTEGRATION WITH DS ENDS -->
</div>
</#macro>