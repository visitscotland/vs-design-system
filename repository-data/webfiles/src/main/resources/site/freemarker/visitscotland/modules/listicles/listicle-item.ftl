<#include "../../../include/imports.ftl">
<#include "../../../vs-dotcom-ds/components/listicle-item.ftl">
<#include "../../../vs-dotcom-ds/components/link.ftl">

<#include "../key-facilities.ftl">
<#include "../image-with-caption.ftl">

<#macro listicleItem listItem>
<#-- @ftlvariable name="listItem" type="com.visitscotland.brmx.beans.ListicleItem" -->
<#-- @ftlvariable name="item" type="com.visitscotland.brmx.beans.mapping.FlatListicle" -->
<#-- @ftlvariable name="cta" type="com.visitscotland.brmx.beans.mapping.FlatLink" -->

	<#assign item = items[listItem.identifier]>

	<#assign image = "" />
    <#if item.image.cmsImage??>
        <#assign image>
            <@hst.link hippobean=item.image.cmsImage.original/>
        </#assign>
    <#elseif item.image.externalImage??>
        <#assign image = item.image.externalImage />
    </#if>
	
	<vs-listicle-item
		index="${item.index}"
		title="${item.title}"
		sub-title="${item.subTitle}"
	>
		<div slot="hippo-details" class="has-edit-button">
			<@hst.manageContent hippobean=listItem />
			<#if item.errorMessages?? && item.errorMessages?size gt 0 && editMode>
				<#list item.errorMessages as error>
					<h1 class="text-danger">${error?upper_case}</h1>
				</#list>
			</#if>
		</div>

		<#if image?? && image?has_content>
			<div slot="image-slot">
                <@imageWithCaption imageSrc=image imageDetails=item.image variant="large"/>
			</div>
		</#if>

		<div slot="description-slot">
			<@hst.html hippohtml=item.description />

			<#if item.ctaLinks?has_content>
				<#list item.ctaLinks as cta>
					<#if cta?has_content>
                        <div class="mb-2">
						    <vs-link href="${cta.link}">${cta.label}</vs-link>
                        </div>
					</#if>
				</#list>
			</#if>
		</div>

        <#if item.facilities?? && item.facilities?size gt 1>
			<div slot="facilities-slot">
				<@keyFacilities facilitiesList=item.facilities />
			</div>
		</#if>
	</vs-listicle-item>
</#macro>