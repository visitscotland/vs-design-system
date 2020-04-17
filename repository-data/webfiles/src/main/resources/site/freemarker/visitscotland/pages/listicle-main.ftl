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
<#include "../../vs-dotcom-ds/components/listicle-item.ftl">
<#include "../../vs-dotcom-ds/components/panel.ftl">

<#include "../modules/listicles/listicle-item.ftl">
<#include "../modules/cms-errors.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Listicle" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->
<#-- @ftlvariable name="item" type="com.visitscotland.brmx.beans.mapping.FlatListicle" -->
<#-- @ftlvariable name="cta" type="com.visitscotland.brmx.beans.mapping.FlatLink" -->

<div class="has-edit-button">
	<@hst.manageContent hippobean=document documentTemplateQuery="new-document" rootPath="site" defaultPath="${path}" />
    <@cmsErrors errors=alerts!"" editMode=editMode />
    
    
	<vs-container slot="upper" class="py-lg-4">
		<vs-row>
			<vs-col cols="12" lg="8">
				<@hst.include ref="breadcrumb"/>
			</vs-col>
		</vs-row>

		<vs-row>
			<vs-col cols="10" lg="8">
				<vs-heading level="1">${document.title}</vs-heading>
			</vs-col>
			<vs-col cols="2">
				<div class="d-flex justify-content-center justify-content-sm-end">
					<vs-social-share />
				</div>
			</vs-col>
		</vs-row>

		<vs-row class="mb-6">
			<vs-col cols="12" lg="8">
                <vs-rich-text-wrapper variant="lead">
                    <@hst.html hippohtml=document.introduction/>
                </vs-rich-text-wrapper>
			</vs-col>
		</vs-row>

		<ol style="list-style:none; margin:0; padding:0;">
			<#list items as listItem>
				<@listicleItem item=listItem />
			</#list>
		</ol>

        <#if document.summary?? && document.summary?has_content>
            <vs-row class="mb-6">
                <vs-col cols="12">
                    <vs-panel>
                        <#if document.summaryTitle??>
                            <vs-heading thin level="4" slot="vs-panel-title">${document.summaryTitle}</vs-heading>
                        </#if>

                        <#if document.summary??>
                            <vs-rich-text-wrapper variant="lead">
                                <@hst.html hippohtml=document.summary/>
                            </vs-rich-text-wrapper>
                        </#if>
                    </vs-panel>
                </vs-col>
            </vs-row>
        </#if>
	</vs-container>
</div>
