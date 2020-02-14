<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/button-with-icon.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-tips.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-stop.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-border-overlap-wrapper.ftl">
<#include "../../vs-dotcom-ds/components/image-with-caption.ftl">
<#include "../../vs-dotcom-ds/components/icon-description-list.ftl">
<#include "../../vs-dotcom-ds/components/img.ftl">
<#include "../../vs-dotcom-ds/components/icon-description-list-term.ftl">
<#include "../../vs-dotcom-ds/components/icon-description-list-detail.ftl">
<#include "../../vs-dotcom-ds/components/link.ftl">

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
    <#if !stop.stopItem?? && editMode>
        <vs-itinerary-stop 
            slot="stops"
            stop-number="${prod.index}"
            stop-label="<@fmt.message key="stop.title"/>"
            stop-title="${prod.title}"
            >
            <div slot="stop-details" class="has-edit-button">
                <@hst.manageContent hippobean=stop />
                <span class="text-danger">The stop doesn't have any product linked to the stop</span>
            </div>
        </vs-itinerary-stop>
    <#elseif stop.stopItem??>
    <vs-itinerary-stop 
        slot="stops"
        stop-number="${prod.index}"
        stop-label="<@fmt.message key="stop.title"/>"
        stop-title="${prod.title}"
		>
        <div slot="stop-details" class="has-edit-button">
            <@hst.manageContent hippobean=stop />
            
            <#if image?? && image?has_content>
                <vs-image-with-caption
                    alt-text="${prod.image.altText}"
                    credit="${prod.image.credit}"
                    caption="${prod.image.altText}"
                    image-src="${image}"
                    latitude="${prod.coordinates.latitude}"
                    longitude="${prod.coordinates.longitude}"
                    >
                <vs-img 
                    class="lazyload" 
                    src="${image}"
                    srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                    data-srcset="${image}" 
                    alt="${prod.image.altText}"
                    data-sizes="auto">
                </vs-img>
                </vs-image-with-caption>
            </#if>

            <#if prod.description?? && prod.description?has_content>
                <@hst.html hippohtml=prod.description/>
            </#if>

            <#if href?? && href?has_content>
            <!-- TODO: add logic to apply external param where needed
                button text should be dynamic and come from CMS once
                the document type is updated -->
                <vs-link href="${href}">Find out more</vs-link>
            </#if>

            <#if prod.timeToexplore?? && prod.timeToexplore?has_content>
            <vs-description-list class="my-4 mb-0 justify-content-start" inline>
                <vs-description-list-term class="mb-0 mr-0 col-auto"><@fmt.message key="stop.timeToExplore"/></vs-description-list-term>
                <vs-description-list-detail class="mb-0 col-auto px-0">${prod.timeToexplore}</vs-description-list-detail>
            </vs-description-list>
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
                <vs-icon-description-list>
                    <vs-icon-description-list-term><@fmt.message key="stop.key-facilities"/></vs-icon-description-list-term>
                    <#list prod.facilities as facility>
                        <vs-icon-description-list-detail 
                            icon="${facility}"
                            label="<@fmt.message key="${facility}"/>">
                        </vs-icon-description-list-detail>
                    </#list>
                </vs-icon-description-list>
            </#if>
      </div>
        <#if lastStop=="true" && prod.coordinates.longitude?? && prod.coordinates.longitude?has_content && prod.coordinates.latitude?? && prod.coordinates.latitude?has_content>
            <vs-itinerary-border-overlap-wrapper slot="nearby-links">
                <vs-button-with-icon class="mb-3" background="white" variant="outline-primary" href="https://www.visitscotland.com/info/accommodation/search-results?prodtypes=cate&lat=${prod.coordinates.latitude}&lng=${prod.coordinates.longitude}&locprox=2&areaproxdist=5&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&order=proximityAsc" icon="food">
                    <@fmt.message key="stop.nearby-eat"/>
                </vs-button-with-icon>
                <vs-button-with-icon background="white" variant="outline-primary" href="https://www.visitscotland.com/info/accommodation/search-results?prodtypes=acco&lat=${prod.coordinates.latitude}&lng=${prod.coordinates.longitude}&locprox=2&areaproxdist=5&stay=&endDate=&r1a=2&r1children=0&r1infants=0&r1c=0&avail=off&order=proximityAsc" icon="product-accommodation">
                    <@fmt.message key="stop.nearby-stay"/>
                </vs-button-with-icon>
            </vs-itinerary-border-overlap-wrapper>
        </#if>
    </vs-itinerary-stop>
    </#if>
</#macro>