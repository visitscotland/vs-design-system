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

<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Listicle" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="item" type="com.visitscotland.brmx.beans.mapping.FlatListicle" -->
<#-- @ftlvariable name="cta" type="com.visitscotland.brmx.beans.mapping.FlatLink" -->

<#macro listicleItem item index>

    <#if item.image.cmsImage??>
        <#assign image>
            <@hst.link hippobean=item.image.cmsImage.original/>
        </#assign>
    <#elseif item.image.externalImage??>
        <#assign image = item.image.externalImage />
    <#else>
        <#assign image = "" />
    </#if>
${index}
    <#--<div slot="item-details" class="has-edit-button">-->
        <@hst.manageContent hippobean=item />

        <#if item.facilities?? && item.facilities?size gt 1>
            <vs-icon-description-list>
                <vs-icon-description-list-term>${label("itinerary", "stop.key-facilities")}</vs-icon-description-list-term>
                <#list item.facilities as facility>
                    <vs-icon-description-list-detail
                        icon="${facility}"
                        label="${label("keyFacilities", "${facility}")}">
                    </vs-icon-description-list-detail>
                </#list>
            </vs-icon-description-list>
        </#if>
</#macro>