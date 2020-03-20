<#include "../../../include/imports.ftl">

<#include "../../../vs-dotcom-ds/components/button-with-icon.ftl">
<#include "../../../vs-dotcom-ds/components/itinerary-tips.ftl">
<#include "../../../vs-dotcom-ds/components/itinerary-stop.ftl">
<#include "../../../vs-dotcom-ds/components/itinerary-border-overlap-wrapper.ftl">
<#include "../../../vs-dotcom-ds/components/image-with-caption.ftl">
<#include "../../../vs-dotcom-ds/components/icon-description-list.ftl">
<#include "../../../vs-dotcom-ds/components/img.ftl">
<#include "../../../vs-dotcom-ds/components/icon.ftl">
<#include "../../../vs-dotcom-ds/components/icon-description-list-term.ftl">
<#include "../../../vs-dotcom-ds/components/icon-description-list-detail.ftl">
<#include "../../../vs-dotcom-ds/components/link.ftl">
<#include "../../../vs-dotcom-ds/components/social-credit-link.ftl">


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

	<#assign image = "" />
    <#if item.image.cmsImage??>
        <#assign image>
            <@hst.link hippobean=item.image.cmsImage.original/>
        </#assign>
    <#elseif item.image.externalImage??>
        <#assign image = item.image.externalImage />
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
			${item.address.line1}
			${item.address.line2}

		</div>

		<#if image?has_content>
			<div slot="image-slot">
				<vs-image-with-caption
					alt-text="${(item.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
					alt="${(item.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
					image-src="${image}"
					latitude="${(item.image.coordinates.latitude)!''}"
					longitude="${(item.image.coordinates.longitude)!''}"
					variant="large"
				>
					<span slot="caption">
						${(item.image.description)!''}
					</span>
					
					<#if !item.image.source?has_content && item.image.credit?has_content>
						<span slot="credit">
							&copy; ${item.image.credit}
						</span>
					</#if>

					<#if item.image.source?has_content>
						<vs-icon
							slot="toggle-icon"
							name="${item.image.source}"
							variant="light"
							size="sm"
						></vs-icon>

						<vs-social-credit-link
							slot="social-link"
							credit="${(item.image.credit)!'No credit'}"
							social-post-url="${item.image.postUrl}"
							source="${item.image.source}"
						></vs-social-credit-link>
					</#if>

				</vs-image-with-caption>
			</div>
		</#if>

		<div slot="description-slot">
			<@hst.html hippohtml=item.description />
			<#if item.links?has_content>
				<#list item.links as cta>
					<#if cta?has_content>
						<vs-link href="${cta.link}">${cta.label}</vs-link>
						</br>
					</#if>
				</#list>
			</#if>
		</div>

		<#if item.facilities?? && item.facilities?size gt 1>
			<div slot="facilities-slot">
				<vs-icon-description-list>
					<#list item.facilities as facility>
						<vs-icon-description-list-detail
							icon="${facility.id}"
							label="${facility.name}">
						</vs-icon-description-list-detail>
					</#list>
				</vs-icon-description-list>
			</div>
		</#if>


	</vs-listicle-item>
</#macro>