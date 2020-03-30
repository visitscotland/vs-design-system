<#include "../../../include/imports.ftl">

<#include "../../../vs-dotcom-ds/components/button-with-icon.ftl">
<#include "../../../vs-dotcom-ds/components/itinerary-tips.ftl">
<#include "../../../vs-dotcom-ds/components/itinerary-stop.ftl">
<#include "../../../vs-dotcom-ds/components/itinerary-border-overlap-wrapper.ftl">
<#include "../../../vs-dotcom-ds/components/image-with-caption.ftl">
<#include "../../../vs-dotcom-ds/components/icon-description-list.ftl">
<#include "../../../vs-dotcom-ds/components/img.ftl">
<#include "../../../vs-dotcom-ds/components/icon-description-list-term.ftl">
<#include "../../../vs-dotcom-ds/components/icon-description-list-detail.ftl">
<#include "../../../vs-dotcom-ds/components/link.ftl">



<#macro itineraryStop stop lastStop>
<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->
<#-- @ftlvariable name="prod" type="com.visitscotland.brmx.beans.mapping.FlatStop" -->

    <#assign prod = stops[stop.identifier]>

    <#if prod.image.cmsImage??>
        <#assign image>
            <@hst.link hippobean=prod.image.cmsImage.original/>
        </#assign>
    <#elseif prod.image.externalImage??>
        <#assign image = prod.image.externalImage />
    <#else>
        <#assign image = "" />
    </#if>

    <#if !stop.stopItem?? && editMode>
        <vs-itinerary-stop
            slot="stops"
            stop-number="${prod.index}"
            stop-label="${prod.title}"
            stop-title="${prod.location}"
        >
            <div slot="stop-details" class="has-edit-button">
                <@hst.manageContent hippobean=stop />
                <#if prod.errorMessage?? && prod.errorMessage?size gt 0>
                    <#list prod.errorMessage as error>
                        <h1 class="text-danger">${error?upper_case}</h1>
                    </#list>
                </#if>
            </div>
        </vs-itinerary-stop>
    <#elseif stop.stopItem??>
        <vs-itinerary-stop
            slot="stops"
            stop-number="${prod.index}"
            stop-label="${prod.title}"
            stop-title="${prod.location}"
        >
            <div slot="stop-details" class="has-edit-button">
                <@hst.manageContent hippobean=stop />
                <#if prod.errorMessage?? && prod.errorMessage?size gt 0 && editMode>
                    <#list prod.errorMessage as error>
                        <h1 class="text-danger">${error?upper_case}</h1>
                    </#list>
                </#if>
                <#if image?? && image?has_content>
                    <vs-image-with-caption
                        alt-text="${(prod.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                        credit="${(prod.image.credit)!'No credit'}"
                        caption="${(prod.image.altText)!''}"
                        image-src="${image}"
                        latitude="${prod.coordinates.latitude}"
                        longitude="${prod.coordinates.longitude}"
                        >
                    <vs-img
                        class="lazyload"
                        src="${image}"
                        srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                        data-srcset="${image}"
                        alt="${(prod.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                        data-sizes="auto">
                    </vs-img>
                    </vs-image-with-caption>
                </#if>

                <#if prod?? && prod.description?? && prod.description?has_content>
                    <@hst.html hippohtml=prod.description/>
                </#if>

                <#if prod.ctaLink?? && prod.ctaLink.link?? && prod.ctaLink.link?has_content>
                    <vs-link href="${prod.ctaLink.link}">
                        ${prod.ctaLink.label}
                    </vs-link>
                </#if>
                  <#--TODO Include Address, Address fields allow null and the fields are:
                    ${prod.address.line1}
                    ${prod.address.line2}
                    ${prod.address.line3}
                    ${prod.address.city}
                    ${prod.address.postCode} -->

                    <#--TODO show open times the field is:
                    ${prod.open}-->
                    </br>
                     <#if prod.openLink?? && prod.openLink.link?? && prod.openLink.link?has_content>
                        <vs-link href="${prod.openLink.link}">
                            ${prod.openLink.label}
                        </vs-link>
                     </#if>
                    <#--TODO show price the field is:
                       ${prod.price} -->
                <#if prod??>
                    <#if prod.timeToexplore?? && prod.timeToexplore?has_content>
                    <vs-description-list class="my-4 mb-0 justify-content-start" inline>
                        <vs-description-list-term class="mb-0 mr-0 col-auto">${label("itinerary", "stop.time-to-explore")}</vs-description-list-term>
                        <vs-description-list-detail class="mb-0 col-auto px-0">${prod.timeToexplore}</vs-description-list-detail>
                    </vs-description-list>
                    </#if>

                    <#if (prod.tipsTitle?? && prod.tipsTitle?has_content)>
                        <vs-itinerary-tips>
                            <div slot="text">
                                <strong>${prod.tipsTitle}</strong>
                                <@hst.html hippohtml=prod.tipsBody/>
                            </div>
                            <vs-svg slot="svg" path="highland-cow" />
                        </vs-itinerary-tips>
                    </#if>

                    <#if prod.facilities?? && prod.facilities?size gt 1>
                        <vs-icon-description-list>
                            <vs-icon-description-list-term>${label("itinerary", "stop.key-facilities")}</vs-icon-description-list-term>
                            <#list prod.facilities as facility>
                                <vs-icon-description-list-detail
                                    icon="${facility.id}"
                                    label="${facility.name}">
                                </vs-icon-description-list-detail>
                            </#list>
                        </vs-icon-description-list>
                    </#if>
                </#if>
        </div>
            <#if lastStop=="true" && prod.coordinates.longitude?? && prod.coordinates.longitude?has_content && prod.coordinates.latitude?? && prod.coordinates.latitude?has_content>
                <vs-itinerary-border-overlap-wrapper slot="nearby-links">
                    <vs-button-with-icon class="mb-3" background="white" variant="outline-primary" icon="food"
                                         href=" ${productSearch(locale, "cate", prod.coordinates.latitude, prod.coordinates.longitude, 5)}" >
                        ${label("itinerary", "stop.nearby-eat")}
                    </vs-button-with-icon>
                    <vs-button-with-icon background="white" variant="outline-primary" icon="product-accommodation"
                                         href=" ${productSearch(locale, "acco", prod.coordinates.latitude, prod.coordinates.longitude, 5)}" >
                        ${label("itinerary", "stop.nearby-stay")}
                    </vs-button-with-icon>
                </vs-itinerary-border-overlap-wrapper>
            </#if>
        </vs-itinerary-stop>
    </#if>
</#macro>