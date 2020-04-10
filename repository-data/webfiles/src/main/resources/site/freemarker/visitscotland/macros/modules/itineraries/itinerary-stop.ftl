<#include "../../../../include/imports.ftl">
<#include "../../../../vs-dotcom-ds/components/itinerary-stop.ftl">
<#include "../../../../vs-dotcom-ds/components/itinerary-tips.ftl">
<#include "../../../../vs-dotcom-ds/components/itinerary-border-overlap-wrapper.ftl">
<#include "../../../../vs-dotcom-ds/components/description-list.ftl">
<#include "../../../../vs-dotcom-ds/components/description-list-term.ftl">
<#include "../../../../vs-dotcom-ds/components/description-list-detail.ftl">
<#include "../../../../vs-dotcom-ds/components/link.ftl">
<#include "../../../../vs-dotcom-ds/components/svg.ftl">
<#include "../../../../vs-dotcom-ds/components/button-with-icon.ftl">

<#include "../../global/key-facilities.ftl">
<#include "../../global/image-with-caption.ftl">
<#include "../../global/cms-errors.ftl">

<#macro itineraryStop stop lastStop>
<#-- @ftlvariable name="stop" type="com.visitscotland.brmx.beans.Stop" -->
<#-- @ftlvariable name="prod" type="com.visitscotland.brmx.beans.mapping.FlatStop" -->

    <#assign prod = stops[stop.identifier]>
    <#assign image = "" />

    <#if prod.image??>
        <#if prod.image.cmsImage??>
            <#assign image>
                <@hst.link hippobean=prod.image.cmsImage.original/>
            </#assign>
        <#elseif prod.image.externalImage??>
            <#assign image = prod.image.externalImage />
        </#if>
    </#if>

    <#if !stop.stopItem?? && editMode>
        <vs-itinerary-stop
                slot="stops"
                stop-number="${prod.index}"
                stop-label="${prod.title}"
                stop-title="${prod.subTitle!''}"
        >
            <div slot="stop-details" class="has-edit-button">
                <@hst.manageContent hippobean=stop />
                <@cmsErrors errors=prod.errorMessages!"" editMode=editMode />
            </div>
        </vs-itinerary-stop>
    <#elseif stop.stopItem??>
        <vs-itinerary-stop
                slot="stops"
                stop-number="${prod.index}"
                stop-label="${prod.title}"
                stop-title="${prod.subTitle!''}"
        >
            <div slot="stop-details" class="has-edit-button">
                <@hst.manageContent hippobean=stop />
                <@cmsErrors errors=prod.errorMessages!"" editMode=editMode />
                <#if image?? && image?has_content>
                    <@imageWithCaption imageSrc=image imageDetails=prod.image variant="fullwidth"/>
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
                </br>
                ${prod.open}
                </br>
                 <#if prod.openLink?? && prod.openLink.link?? && prod.openLink.link?has_content>
                    <vs-link href="${prod.openLink.link}">
                        ${prod.openLink.label}
                    </vs-link>
                 </#if>-->
                <#--TODO  price the field is:
            </br>
                ${prod.price}-->

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
                        <@keyFacilities facilitiesList=prod.facilities />
                    </#if>
                </#if>
        </div>
            <#if lastStop == 'true' && prod.coordinates.longitude?? && prod.coordinates.longitude?has_content && prod.coordinates.latitude?? && prod.coordinates.latitude?has_content>
                <#assign nearbyEatsUrl = productSearch(locale, "cate", prod.coordinates.latitude, prod.coordinates.longitude, 5)>
                <#assign nearbyStayUrl = productSearch(locale, "acco", prod.coordinates.latitude, prod.coordinates.longitude, 5)>

                <vs-itinerary-border-overlap-wrapper slot="nearby-links">
                    <vs-button-with-icon class="mb-3" background="white" variant="outline-primary" icon="food" href="${nearbyEatsUrl}" >
                        ${label("itinerary", "stop.nearby-eat")}
                    </vs-button-with-icon>
                    <vs-button-with-icon background="white" variant="outline-primary" icon="product-accommodation" href="${nearbyStayUrl}" >
                        ${label("itinerary", "stop.nearby-stay")}
                    </vs-button-with-icon>
                </vs-itinerary-border-overlap-wrapper>
            </#if>
        </vs-itinerary-stop>
    </#if>
</#macro>