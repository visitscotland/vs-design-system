<#ftl output_format="XML">
<#include "../../include/imports.ftl">

<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">
<#include "../../vs-dotcom-ds/components/icon.ftl">
<#include "../../vs-dotcom-ds/components/social-share.ftl">
<#include "../../vs-dotcom-ds/components/lead-paragraph.ftl">
<#include "../../vs-dotcom-ds/components/heading.ftl">
<#include "../../vs-dotcom-ds/components/img.ftl">
<#include "../../vs-dotcom-ds/components/link.ftl">

<#include "../modules/listicles/listicle-item.ftl">
<#include "../../vs-dotcom-ds/components/listicle-item.ftl">



<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Listicle" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="item" type="com.visitscotland.brmx.beans.mapping.FlatListicle" -->
<#-- @ftlvariable name="cta" type="com.visitscotland.brmx.beans.mapping.FlatLink" -->

<div class="has-edit-button">
      <@hst.manageContent hippobean=document documentTemplateQuery="new-document" rootPath="site" defaultPath="${path}" />
</div>
<vs-container slot="upper" class="py-lg-4">
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
				<vs-social-share />
			</div>
		</vs-col>
	</vs-row>
	<vs-row class="justify-content-md-between">
		<vs-col cols="12" lg="8" offset-lg="1">
			<@hst.html hippohtml=document.introduction/>
		</vs-col>
	</vs-row>

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

		<#if document.descOrder>
			<#assign i = i - 1>
		<#else >
			<#assign i = i + 1>
		</#if>

		<#assign itemDescription>
			<@hst.html hippohtml=item.description />
		</#assign>

		<#assign listicle_item = {
			"name": "${item.title}",
			"subTitle": "${item.subTitle!''}",
			"description": 'Description Test'
		}>

		<#assign listicle_image = '{
			"source": "image",
			"imageSrc": "http://localhost:8080/site/_cmsinternal/binaries/content/gallery/visitscotland/default/visitscotland_49386435364-1.jpg",
			"altText": "Child playing on Elie Beach",
			"caption": "Elie beach",
			"credit": "Test Credit",
			"longitude": "-2.8243733",
			"latitude": "56.1896033"
		}'>

		<vs-row class="justify-content-md-between">
			<vs-listicle-item
				intro="${(listicle_item.description)}"
				index=1
				name="${(listicle_item.name)}"
				place="${(listicle_item.subTitle)}"
				image='${listicle_image}'
				hasEditButton
				errorMessage="${errorMessage}"
			>
				<template v-slot:description>
					<div>
						<@hst.html hippohtml=item.description />
					</div>
				</template>
			</vs-listicle-item>
		</vs-row>

	</#list>

    </ol>
</vs-container>
