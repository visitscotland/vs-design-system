<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->

<#macro itineraryStop stop number>
    <#include "../../include/imports.ftl">

<#-- INTEGRATION WITH DS STARTS -->

 <vs-itinerary-stop
         stop="Parada"
         key=${number}
 >
     Hello
     <#--<vs-heading-->
             <#--slot="stop-title"-->
             <#--level="3"-->
             <#--thin-->
             <#--class="vs-itinerary-stop__title ml-4">-->
                    <#--<span-->
                    <#-->Stop {{stop.stopCount}}</span>-->
         <#--{{stop.title}}-->
     <#--</vs-heading>-->

     <#--<vs-favourites-toggle-button-->
             <#--slot="stop-favourite"-->
             <#--:href="stop.href"-->
             <#--:title="stop.title"-->
     <#--/>-->
     <#--<vs-itinerary-stop-image-->
             <#--:altText="stop.image.altText"-->
             <#--:credit="stop.image.credit"-->
             <#--:description="stop.image.description"-->
             <#--:image-src="stop.image.imageSrc"-->
             <#--:latitude="stop.image.latitude"-->
             <#--:longitude="stop.image.longitude"-->
             <#--slot="stop-image"-->
     <#-->-->
         <#--<img-->
                 <#--class="lazyload"-->
                 <#--:src="stop.image.imageSrc"-->
                 <#--srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="-->
                 <#--:data-srcset="stop.image.imageSrc"-->
                 <#--:alt="stop.image.altText"-->
                 <#--data-sizes="auto"-->
                 <#--slot="image" />-->
         <#--<noscript>-->
             <#--<img class="img-fluid" :src="stop.image.imageSrc" alt="item.image.altText" />-->
         <#--</noscript>-->
     <#--</vs-itinerary-stop-image>-->
     <#--<div slot="stop-description" v-html="stop.description"></div>-->
     <#--<dl slot="stop-time-to-explore" class="list-inline my-4 mb-0">-->
         <#--<dt class="list-inline-item mb-0">Time to explore:</dt>-->
         <#--<dd class="list-inline-item mb-0">{{stop.timeToExplore}}</dd>-->
     <#--</dl>-->
     <#--<vs-itinerary-stop-pullout slot="stop-pullout" v-if="stop.pullOut.description.length">-->
         <#--<div slot="text">-->
             <#--<strong>{{stop.pullOut.title}}</strong>-->
             <#--<div v-html="stop.pullOut.description"></div>-->
         <#--</div>-->
         <#--<vs-svg slot="svg" path="highland-cow" />-->
     <#--</vs-itinerary-stop-pullout>-->
     <#--<a slot="stop-href" class="vs-itinerary__stop-link text-uppercase font-weight-bold d-inline-flex align-items-center"-->
        <#--:href="stop.href"-->
     <#-->-->
         <#--Find out more-->
         <#--<vs-icon name="play-filled" variant="primary" size="xxs" :padding=3 />-->
     <#--</a>-->
     <#--<dl v-if="stop.facilities.length" class="itinerary-stop__facilities" slot="stop-facilities">-->
         <#--<dt>Key facilities</dt>-->
         <#--<vs-itinerary-stop-facility-->
                 <#--v-for="(facility, facilitiesIndex) in stop.facilities"-->
                 <#--:key="facilitiesIndex"-->
                 <#--:facility="facility"-->
         <#-->-->
             <#--{{facility.value}}-->
         <#--</vs-itinerary-stop-facility>-->
     <#--</dl>-->
 </vs-itinerary-stop>

<#-- INTEGRATION WITH DS ENDS -->



    <#assign stopsCount = number>
    <#assign latitude = "">
    <#assign longitude = "">
    <#assign prod = "">
                <div class="has-edit-button" style="border-style: solid; border-width: thin; padding:1%">
                <@hst.manageContent hippobean=stop />
                <#if stop.stopItem.product??>
                    <#assign prod = productsMap[stop.stopItem.product]>

                    <#if editMode && !prod?has_content >
                        <h2 style="color: red">The product does not exist in the DMS, please add a valid DMS id</h2>
                    <#elseif prod?has_content >
                        <#assign latitude = prod.latitude>
                        <#assign longitude = prod.longitude>
                    </#if>
                </#if>

                    <h3 style="padding-top: 1%">Stop ${stopsCount}. ${stop.title}</h3>

                    <div class="col-sm-12" style="text-align: center">
                    <#if stop.stopItem.image??>
                        <@hst.link var="image" hippobean=stop.getStopItem().image.original/>
                        <img src="${image}" width="50%">
                    <#else>
                             <img src="${prod.image}" width="50%">
                    </#if>
                    </div>

                    <div style="padding-top: 1%"><@hst.html hippohtml=stop.description/></div>

                    <#if stop.stopItem?? && stop.stopItem.timeToExplore??>
                 <h4> Time to explore: ${stop.stopItem.timeToExplore}</h4>
                    </#if>

                <#if prod?has_content && prod.name??>
                     <a target="_blank" href="https://www.visitscotland.com${prod.url}">
                         FIND OUT MORE</a>
                </#if>


                <#if stop.tips.content?has_content>
                    <div style="background: lightpink">
                        <h3> ${stop.tipsTitle} </h3>
                        <p> <@hst.html hippohtml=stop.tips/></p>
                    </div>
                </#if>
                    </br>
                    <#if prod?has_content>
                        <p class="glyphicon glyphicon-map-marker" style="padding-top: 1%"> Coordinates: ${prod.latitude}
                            ,${prod.longitude}</p>
                    </#if>

                    <div style="position:relative">
                        <img
                                id="image${stopsCount}"
                                src="https://www.csp.org.uk/sites/default/files/scotlandx.gif"
                                data-lat-start="60.566850"
                                data-lat-end="53.622142"
                                data-lon-start="-7.151292"
                                data-lon-end="-0.124849"
                        />
                        <#if prod?has_content>
                        <svg
                                class="target"
                                style="position:absolute"
                                data-lat="${prod.latitude}"
                                data-lon="${prod.longitude}"
                                data-image-id="image${stopsCount}"
                        >
                        </#if>
                        <circle
                                r="10"
                                fill="green"
                                cx="20"
                                cy="20"
                        />
                    </svg>
                    </div>


                <#if prod?has_content && prod.addressLine1??>
                   </br>
                    <div class="glyphicon glyphicon-map-marker" style="padding-top: 1%"> ${prod.addressLine1}</div>
                </#if>


                <#if prod?has_content && prod.price?? && prod.price!="null" >
                    </br>
                    <div class="glyphicon glyphicon-info-sign"
                         style="padding-top: 1%"> ${prod.multiplePrices?then("Prices from","Price")}
                        : ${prod.price}</div>
                </#if>

                <#if prod?has_content && prod.facilities?has_content >
                    <h4> Facilities: </h4>
                         <ul>
                             <#list prod.facilities?split(",") as facility>
                                 <li><@fmt.message key="${facility}" /></li>
                             </#list>
                         </ul>
                </#if>
                </div>



</#macro>