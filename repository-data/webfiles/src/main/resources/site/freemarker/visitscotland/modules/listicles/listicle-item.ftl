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


<#macro listicleItem listItem descOrder>
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Listicle" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="listItem" type="com.visitscotland.brmx.beans.ListicleItem" -->
<#-- @ftlvariable name="item" type="com.visitscotland.brmx.beans.mapping.FlatListicle" -->
<#-- @ftlvariable name="cta" type="com.visitscotland.brmx.beans.mapping.FlatLink" -->

	<#assign item = items[listItem.identifier]>
    <#if descOrder>
        <#assign i = i - 1>
    <#else >
        <#assign i = i + 1>
    </#if>

    <#if item.image.cmsImage??>
        <#assign image>
            <@hst.link hippobean=item.image.cmsImage.original/>
        </#assign>
    <#elseif item.image.externalImage??>
        <#assign image = item.image.externalImage />
    <#else>
        <#assign image = "" />
    </#if>

	<vs-listicle-item
		index="${i}"
		title="${item.title}"
		sub-title="${item.subTitle}"
	>

		<div slot="hippo-details" class="has-edit-button">
			<@hst.manageContent hippobean=listItem />
			<#if item.errorMessage?? && editMode>
				<h1 class="text-danger">${item.errorMessage?upper_case}</h1>
			</#if>
		</div>

		<div slot="image-slot">
			<vs-image-with-caption
				alt-text="${(item.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
				credit="${(item.image.credit)!'No credit'}"
				caption="${(item.image.altText)!''}"
				image-src="${image}"
				latitude="${(item.image.coordinates.latitude)!''}"
				longitude="${(item.image.coordinates.longitude)!''}"
			>
				<vs-img
					class="lazyload"
					src="${image}"
					srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
					data-srcset="${image}"
					alt="${(item.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
					data-sizes="auto">
				</vs-img>
			</vs-image-with-caption>
		</div>

		<div slot="description-slot">
			<@hst.html hippohtml=item.description />
			<#if  item.ctaLinks?has_content>
				<#list item.ctaLinks as cta>
					<#if  cta?has_content>
						<vs-link href="${cta.link}">${cta.label}</vs-link>
						</br>
					</#if>
				</#list>
			</#if>
		</div>

		<#if item.facilities?? && item.facilities?size gt 1>
			<div slot="facilities-slot" class="facilities">
				<vs-icon-description-list>
					<#list item.facilities as facility>
						<vs-icon-description-list-detail
							icon="${facility}"
							label="${label("keyFacilities", "${facility}")}">
						</vs-icon-description-list-detail>
					</#list>
				</vs-icon-description-list>
			</div>
		</#if>


	</vs-listicle-item>
</#macro>