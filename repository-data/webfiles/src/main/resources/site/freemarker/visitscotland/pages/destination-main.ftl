<#ftl output_format="XML">
<#include "../../include/imports.ftl">
<#include "../macros/global/cms-errors.ftl">
<#include "../../frontend/components/vs-page-intro.ftl">
<#include "../../frontend/components/vs-hero.ftl">
<#include "../../frontend/components/vs-container.ftl">
<#include "../../frontend/components/vs-row.ftl">
<#include "../../frontend/components/vs-col.ftl">
<#include "../../frontend/components/vs-rich-text-wrapper.ftl">
<#include "../../frontend/components/vs-img.ftl">
<#include "../../frontend/components/vs-button.ftl">

<#include "../../frontend/components/vs-heading.ftl">
<#include "../../frontend/components/vs-social-share.ftl">

<#include "../macros/modules/megalinks/multi-image.ftl">
<#include "../macros/modules/megalinks/single-image.ftl">
<#include "../macros/modules/megalinks/list.ftl">
<#include "../macros/global/cms-errors.ftl">
<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brmx.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<div class="has-edit-button">
	<@hst.manageContent hippobean=document documentTemplateQuery="new-document" rootPath="site" defaultPath="${path}" />
    <@cmsErrors errors=alerts!"" editMode=editMode />

	<vs-page-intro>
			<@hst.link var="hero" hippobean=document.heroImage.original/>
			<vs-hero
					slot="hero"
					alt-text="${heroImage.altText!''}"
					credit="${heroImage.credit!''}"
					caption="${heroImage.description!''}"
					image-src="${hero}"
					latitude="${(heroCoordinates.latitude)!''}"
					longitude="${(heroCoordinates.longitude)!''}"
			>
				<vs-img
						src="${hero}"
						alt="${heroImage.altText!''}"
				> </vs-img>
			</vs-hero>
		<vs-container slot="upper" class="py-lg-4">
			<vs-row class="justify-content-md-between">
				<vs-col cols="12" lg="8" offset-lg="1">
					<@hst.include ref="breadcrumb"/>
				</vs-col>
			</vs-row>

			<vs-row>
				<vs-col cols="10" lg="8" offset-lg="1">
					<vs-heading level="1">${document.title}</vs-heading>
				</vs-col>
				<vs-col cols="2">
					<div class="d-flex justify-content-center justify-content-sm-end">
						<vs-social-share></vs-social-share>
					</div>
				</vs-col>
			</vs-row>
			<vs-row>
				<vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
					<vs-rich-text-wrapper variant="lead">
						<@hst.html hippohtml=document.introduction/>
					</vs-rich-text-wrapper>
				</vs-col>
			</vs-row>
		</vs-container>
	</vs-page-intro>

  <#--TODO SPlit in macros-->
	<#list pageItems as item>
		<#if item.style="style3">
			<#assign style = "lightgray" />
		<#else>
			<#assign style = "white" />
		</#if>
		<div class="has-edit-button" style="background-color:${style}">
			<@hst.manageContent hippobean=item.megalinkItem />
			<vs-container slot="upper" class="py-lg-4" >
				<vs-row>
					<vs-col cols="10" lg="8" offset-lg="1">
						<vs-heading level="1">${item.title}</vs-heading>
					</vs-col>
				</vs-row>
				<vs-row class="mb-6">
					<vs-col cols="12" lg="8" offset-lg="1">
						<vs-rich-text-wrapper variant="lead">
							<@hst.html hippohtml=item.introduction/>
						</vs-rich-text-wrapper>
					</vs-col>
				</vs-row>


				<#-- Macro for Multim Image -->
				<#if item.getType()== "MultiImageLinksModule" >
					<@multiImage item=item />


				<#--Macro for single image-->
				<#elseif item.getType()== "SingleImageLinksModule">
					<@singleImage item=item />

				<#--Macro for list-->
				<#else>
					<@list item=item />
				</#if>
			</vs-container>
		</div>
	</#list>
</div>
