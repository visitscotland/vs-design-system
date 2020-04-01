<#ftl output_format="XML">
<#include "../../include/imports.ftl">

<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">
<#include "../../vs-dotcom-ds/components/icon.ftl">
<#include "../../vs-dotcom-ds/components/social-share.ftl">
<#include "../../vs-dotcom-ds/components/heading.ftl">
<#include "../../vs-dotcom-ds/components/img.ftl">
<#include "../../vs-dotcom-ds/components/link.ftl">
<#include "../../vs-dotcom-ds/components/rich-text-wrapper.ftl">

<#include "../modules/listicles/listicle-item.ftl">
<#include "../../vs-dotcom-ds/components/listicle-item.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Listicle" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="item" type="com.visitscotland.brmx.beans.mapping.FlatListicle" -->
<#-- @ftlvariable name="cta" type="com.visitscotland.brmx.beans.mapping.FlatLink" -->

<div class="has-edit-button">
	<@hst.manageContent hippobean=document documentTemplateQuery="new-document" rootPath="site" defaultPath="${path}" />
	<#if alerts?? && alerts?size gt 0>
		<#list alerts as error>
			<h1 class="text-danger">${error?upper_case}</h1>
		</#list>
	</#if>

	<vs-container slot="upper" class="py-lg-4">
		<vs-row class="justify-content-md-between">
			<vs-col cols="12" lg="8">
				<@hst.include ref="breadcrumb"/>
			</vs-col>
		</vs-row>

		<vs-row class="justify-content-md-between">
			<vs-col cols="10" lg="8">
				<vs-heading level="1">${document.title}</vs-heading>
			</vs-col>
			<vs-col cols="2">
				<div class="d-flex justify-content-center justify-content-sm-end">
					<vs-social-share />
				</div>
			</vs-col>
		</vs-row>

		<vs-row class="justify-content-md-between mb-6">
			<vs-col cols="12" lg="8">
                <vs-rich-text-wrapper variant="lead"><@hst.html hippohtml=document.introduction/></vs-rich-text-wrapper>
			</vs-col>
		</vs-row>

		<ol style="list-style:none; margin:0; padding:0;">
			<#list document.items as listItem>
				<@listicleItem listItem=listItem />
			</#list>
		</ol>

	</vs-container>
</div>
