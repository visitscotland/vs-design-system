<#ftl output_format="XML">
<#include "../../include/imports.ftl">
<#include "../macros/global/cms-errors.ftl">
<#include "../../vs-dotcom-ds/components/page-intro.ftl">
<#include "../../vs-dotcom-ds/components/hero.ftl">
<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">
<#include "../../vs-dotcom-ds/components/rich-text-wrapper.ftl">
<#include "../../vs-dotcom-ds/components/img.ftl">
<#include "../../vs-dotcom-ds/components/image-with-caption.ftl">
<#include "../../vs-dotcom-ds/components/heading.ftl">
<#include "../../vs-dotcom-ds/components/social-share.ftl">

<#include "../macros/modules/itineraries/itinerary-stop.ftl">
<#include "../macros/global/cms-errors.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brmx.beans.MegaLinks" -->
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
					alt-text="${heroImage.altText}"
					credit="${heroImage.credit}"
					caption="${heroImage.description}"
					image-src="${hero}"
					latitude="${(heroCoordinates.latitude)!''}"
					longitude="${(heroCoordinates.longitude)!''}"
			>
				<vs-img
						src="${hero}"
						alt="${document.heroImage.altText}"
				> </vs-img>
			</vs-hero>
		<vs-container slot="upper" class="py-lg-4">
			<vs-row class="justify-content-md-between">
				<vs-col cols="12" lg="8" offset-lg="1">
					<#-- TODO: BREADCRUMB as part of the main template -->
					<@hst.include ref="breadcrumb"/>
				</vs-col>
			</vs-row>

			<vs-row>
				<vs-col cols="10" lg="8" offset-lg="1">
					<vs-heading level="1">${document.title}</vs-heading>
				</vs-col>
				<vs-col cols="2">
					<div class="d-flex justify-content-center justify-content-sm-end">
						<vs-social-share />
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
		<div class="has-edit-button">
			<@hst.manageContent hippobean=item.megaLinkItem />
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


				<#--Macro for Featured-->
				<#if item.featuredLinks?? >
					<#list item.featuredLinks as feature>
						<#assign image>
							<@hst.link hippobean=feature.image.cmsImage.original/>
						</#assign>
						<vs-row>
							<vs-col cols="6" lg="6" offset-lg="1">
								<#--TODO for links the image does not have caption-->
								<@imageWithCaption imageSrc=image imageDetails=feature.image variant="fullwidth"/>
							</vs-col>
							<vs-col cols="3" lg="3" offset-lg="1">
								<vs-heading level="3">${feature.label}</vs-heading>
								<#if item.hideTeaser == false >
									${feature.teaser}
								</#if>
								</br>
								<vs-link href="${feature.link}"> ${feature.label}</vs-link> </br>
							</vs-col>
						</vs-row>
						</br>
						</br>
					</#list>

					<vs-row>
						<#list item.links as megaLink>
							<#assign image>
								<@hst.link hippobean=megaLink.image.cmsImage.original/>
							</#assign>
							<vs-col cols="4" lg="4">
								<#--TODO for links the image does not have caption-->
								<@imageWithCaption imageSrc=image imageDetails=megaLink.image variant="fullwidth"/>
								<vs-heading level="3">${megaLink.label}</vs-heading>
								<#if item.hideTeaser == false >
									${megaLink.teaser}
								</#if>
								</br>
								<vs-link href="${megaLink.link}"> ${megaLink.label}</vs-link>
								</br>
								</br>
							</vs-col>
						</#list>
					</vs-row>
					</br> </br>
				<#--Macro for single image-->
				<#elseif item.image??>
					<#assign image>
						<@hst.link hippobean=item.image.cmsImage.original/>
					</#assign>
					<vs-row>
						<#if item.fullWidth == true>
							<vs-col cols="12" lg="12">
								<#--FOR SIMPLE IMAGE, THE IMAGE HAS CAPTION-->
								<@imageWithCaption imageSrc=image imageDetails=item.image variant="fullwidth"/>
							</vs-col>
							<vs-col cols="12" lg="12">
								<vs-heading level="2">${item.innerTitle}</vs-heading>
							</vs-col>
							</br>
							<vs-col cols="6" lg="6">
								<vs-rich-text-wrapper variant="lead">
									<@hst.html hippohtml=item.innerIntroduction/> </br>
								</vs-rich-text-wrapper>
							</vs-col>
						<#else>
							<vs-col cols="12" lg="12" offset-lg="1">
								<vs-heading level="2">${item.innerTitle}</vs-heading>
							</vs-col>
							<vs-col cols="6" lg="6" offset-lg="1">
								<vs-rich-text-wrapper variant="lead">
									<@hst.html hippohtml=item.innerIntroduction/> </br>
								</vs-rich-text-wrapper>
							</vs-col>
							<vs-col cols="4" lg="4">
								<#--FOR SIMPLE IMAGE, THE IMAGE HAS CAPTION-->
								<@imageWithCaption imageSrc=image imageDetails=item.image variant="fullwidth"/>
							</vs-col>
						</#if>

					</vs-row>
					<ol>
					<#list item.links as megaLink>
						<vs-row>
							<vs-col cols="4" lg="4" offset-lg="1">
								<vs-link href="${megaLink.link}"> ${megaLink.label}</vs-link> </br>
							</vs-col>

						</vs-row>
					</#list>
					</ol>

				<#--Macro for list-->
				<#else>
					<ol>
						<vs-row>
							<#list item.links as megaLink>
								<#assign image>
									<@hst.link hippobean=megaLink.image.cmsImage.original/>
								</#assign>
								<vs-col cols="3" lg="3">
									<#--TODO for links the image does not have caption-->
									<@imageWithCaption imageSrc=image imageDetails=megaLink.image variant="fullwidth"/>
								</vs-col>
								<vs-col cols="2" lg="2">
									<vs-heading level="3">${megaLink.label}</vs-heading>
									<#if item.hideTeaser == false >
										${megaLink.teaser}
									</#if>
									</br>
									<vs-link href="${megaLink.link}"> ${megaLink.label}</vs-link> </br>
									</br>
								</vs-col>
							</#list>
						</vs-row>
					</ol>
				</#if>
			</vs-container>
		</div>
	</#list>
</div>
