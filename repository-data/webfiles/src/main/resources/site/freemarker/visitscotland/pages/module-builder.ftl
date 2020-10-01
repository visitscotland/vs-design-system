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
<#include "../../frontend/components/vs-link.ftl">

<#include "../../frontend/components/vs-heading.ftl">
<#include "../../frontend/components/vs-social-share.ftl">

<#include "../macros/modules/megalinks/megalinks-multi-image.ftl">
<#include "../macros/modules/megalinks/megalinks-single-image.ftl">
<#include "../macros/modules/megalinks/megalinks-list.ftl">
<#include "../macros/global/cms-errors.ftl">
<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brmx.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<#--TODO Control abput colours, change style="background-color:${style}  -->
<#macro moduleBuilder module style>
    <#if module.introduction??>
<p>hola interno ${style} ${module.title} NO</p>
    <#else >
<p> no module</p>
    </#if>

		<div class="has-edit-button" style="background-color:${style}">
        <#--TODO hippoBean-->
        <#--<@hst.manageContent hippobean=module.hippoBean />-->
            <vs-row>
                <vs-col cols="10" lg="8" offset-lg="1">
                    <vs-heading level="1">${module.title}</vs-heading>
                </vs-col>
            </vs-row>
            <vs-row class="mb-6">
                <vs-col cols="12" lg="8" offset-lg="1">
                    <vs-rich-text-wrapper variant="lead">
							<@hst.html hippohtml=module.introduction/>
                    </vs-rich-text-wrapper>
                </vs-col>
            </vs-row>
        </div>

<#-- Macro for Multim Image -->
    <#if item.getType()== "MultiImageLinksModule" >
        <@multiImage item=item />


    <#--Macro for single image-->
    <#elseif item.getType()== "SingleImageLinksModule">
        <@singleImage item=item />

    <#--Macro for list-->
    <#elseif item.getType()== "ListLinksModule">
        <@list item=item />

    <#elseif item.getType()== "ICentreModule">
        <#if item.image.cmsImage??>
            <#assign image>
                <@hst.link hippobean=item.image.cmsImage.original/>
            </#assign>
        <#else>
            <#assign image = item.image.externalImage!'' />
        </#if>

        <#if item.quoteImage??>
            <#assign imageQuote>
                <@hst.link hippobean=item.quoteImage.cmsImage.original/>
            </#assign>
        </#if>
					<vs-col>
                    <#--TODO for links the image does not have caption-->
						<@imageWithCaption imageSrc=image imageDetails=item.image variant="fullwidth"/>

                    </vs-col>

					<vs-row>
                        <vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
							<@imageWithCaption imageSrc=imageQuote imageDetails=item.quoteImage variant="fullwidth"/>
                            "<@hst.html hippohtml=item.quote/>"
                            <vs-heading level="6">${item.quoteAuthorName}</vs-heading>
                            ${item.quoteAuthorTitle}
                        </vs-col>


                        <vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
							<@hst.html hippohtml=item.description/>
                        </vs-col>
                        <vs-col cols="4" lg="4" offset-lg="1">
							<#list item.iCentreList as iCentre>
                                <vs-link href="${iCentre.link}">${iCentre.label}</vs-link>
                                </br>
                            </#list>
                        </vs-col>
                    </vs-row>

    <#elseif item.getType()== "IKnowModule">
        <@hst.manageContent hippobean=item.tourismInformation />
					<vs-row>
                        <vs-col cols="12" md="10" lg="10" xl="10" offset-lg="1">
							<@hst.html hippohtml=item.description/>
                        </vs-col>
                        <vs-col cols="4" lg="4" offset-lg="1">
                            <vs-link href="${item.link.link}">iKnow partners in this area</vs-link>
                            </br>
                        </vs-col>
                    </vs-row>

    </#if>

</#macro>
