<#include "../../include/imports.ftl">

<#include "../../vs-dotcom-ds/stores/itineraries-store.ftl">
<#include "../../vs-dotcom-ds/components/page-intro.ftl">
<#include "../../vs-dotcom-ds/components/hero.ftl">
<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">
<#include "../../vs-dotcom-ds/components/icon.ftl">
<#include "../../vs-dotcom-ds/components/lead-paragraph.ftl">
<#include "../../vs-dotcom-ds/components/image-with-caption.ftl">
<#include "../../vs-dotcom-ds/components/image-location-map.ftl">
<#include "../../vs-dotcom-ds/components/button.ftl">
<#include "../../vs-dotcom-ds/components/heading.ftl">
<#include "../../vs-dotcom-ds/components/img.ftl">
<#include "../../vs-dotcom-ds/components/tooltip.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-list.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-list-item.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-display.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-label.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-distance-display.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-distance-label.ftl">
<#include "../../vs-dotcom-ds/components/summary-box-icon-with-label.ftl">
<#include "../../vs-dotcom-ds/components/description-list.ftl">
<#include "../../vs-dotcom-ds/components/description-list-term.ftl">
<#include "../../vs-dotcom-ds/components/description-list-detail.ftl">
<#include "../../vs-dotcom-ds/components/itinerary-day.ftl">
<#include "../../vs-dotcom-ds/components/itinerary.ftl">
<#include "../../vs-dotcom-ds/components/svg.ftl">
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


<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Listicle" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="item" type="com.visitscotland.brmx.beans.mapping.FlatListicle" -->
<#-- @ftlvariable name="cta" type="com.visitscotland.brmx.beans.mapping.FlatLink" -->

NO HERO WILL BE ALLOWED IN THIS REALM!

<vs-page-intro>
    <vs-container slot="upper" class="py-lg-4">
        <!-- TODO Remove this row it is just here for adding some padding to the top. The overlay of the hero image stays even when there is no HERO image-->
        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                <vs-heading level="1">${document.title}</vs-heading>
            </vs-col>
        </vs-row>
        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                 <@hst.include ref="breadcrumb"/>
            </vs-col>
        </vs-row>
        <vs-row class="justify-content-md-between">
            <vs-col cols="10" lg="8" offset-lg="1">
                <vs-heading level="1">${document.title}</vs-heading>
            </vs-col>
            <vs-col cols="2">
                <div class="d-flex justify-content-center justify-content-sm-end">
                    <!-- TODO - Below icon is FPO. Replace with icon with text component and a share component -->
                    <vs-icon name="share" variant="dark" size="sm" />
                </div>
            </vs-col>
        </vs-row>
        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                <@hst.html hippohtml=document.introduction/>
            </vs-col>
        </vs-row>

    <#assign i = 0>
    <#list items as item>

        <#assign image = ""/>
        <#if item.image??>
            <#if item.image.cmsImage??>
                <#assign image>
                    <@hst.link hippobean=item.image.cmsImage.original/>
                </#assign>
            <#elseif item.image.externalImage??>
                <#assign image = item.image.externalImage />
            </#if>
        </#if>

        <#assign i = i +1>

        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                <div>
                    <vs-heading level="2"> ${i}. ${item.title}</vs-heading>
                    <#if item.subtitle??>${item.subTitle} </#if>
                </div>
            </vs-col>
        </vs-row>
        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                <div>
                    <img src="${image}" width="50%" >
                    <#-- TODO: Copy minimap -->
                </div>
            </vs-col>
        </vs-row>
        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                <div>
                    <@hst.html hippohtml=item.description/>
                </div>
            </vs-col>
        </vs-row>
        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                <div>
                    <#if item.facilities?? && item.facilities?size gt 1>
                        There are ${item.facilities?size} facilities</br>
                        <#list item.facilities as facility>
                            facility = ${facility}</br>
                        </#list>
                    </#if>
                </div>
            </vs-col>
        </vs-row>
        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                <div>
                    <#list item.ctaLinks as cta>
                        <a href="${cta.link}">GO TO: ${label}</a>
                        <vs-link href="${cta.link}">GO TO: ${label}</vs-link>
                    </#list>
                </div>
            </vs-col>
        </vs-row>
    </#list>
    </vs-container>
</vs-page-intro>
This is a listicle! Yay!!





    <#--<@hst.include ref="breadcrumb"/>-->
<#if message??>
    Also
<#else>
    but the component is not ready
</#if>