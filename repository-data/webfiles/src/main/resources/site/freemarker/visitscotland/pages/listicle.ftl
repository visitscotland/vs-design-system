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
    <#list document.items as item>

        <#if prod.image.cmsImage??>
            <#assign image>
                <@hst.href hippobean=prod.image.cmsImage.original/>
            </#assign>
        <#elseif prod.image.externalImage??>
            <#assign image = prod.image.externalImage />
        <#else>
            <#assign image = "" />
        </#if>
        <#assign i = i +1>

        <vs-row class="justify-content-md-between">
            <vs-col cols="12" lg="8" offset-lg="1">
                <div>
                    <vs-heading level="2"> ${i}. ${item.title}</vs-heading>
                    ${item.subTitle}
                    <img src="${image}" width="50%" >
                    <#-- TODO: Copy minimap -->
                    ${item.subTitle}

                    <@hst.html hippohtml=item.description/>

                    <#if item.facilities?? && item.facilities?size gt 1>
                        <vs-icon-description-list>
                            <vs-icon-description-list-term><@fmt.message key="stop.key-facilities"/></vs-icon-description-list-term>
                            <#list item.facilities as facility>
                                <vs-icon-description-list-detail
                                        icon="${facility}"
                                        label="<@fmt.message key="${facility}"/>">
                                </vs-icon-description-list-detail>
                            </#list>
                        </vs-icon-description-list>
                    </#if>

                    <#list item.ctaLinks as cta>
                        <vs-href href="${cta.href}">Find out more</vs-href>
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