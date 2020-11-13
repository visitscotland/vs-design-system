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
<#include "../macros/modules/tourism-information/tourisminformation-icentre.ftl">
<#include "../macros/modules/tourism-information/tourisminformation-iknow.ftl">
<#include "../macros/modules/megalinks/megalinks-horizontal-list.ftl">
<#include "../macros/global/cms-errors.ftl">
<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brmx.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<#--TODO Control abput colours, change style="background-color:${style}  -->
<#macro moduleBuilder module theme>
		<div class="has-edit-button" style="background-color:${theme}">
        <#--TODO hippoBean-->
        <@hst.manageContent hippobean=module.hippoBean />
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
    <#if module.getType()== "MultiImageLinksModule" >
        <@multiImage module />


    <#--Macro for single image-->
    <#elseif module.getType()== "SingleImageLinksModule">
        <@singleImage module />

    <#--Macro for list-->
    <#elseif module.getType()== "ListLinksModule">
        <@list module />
    <#--Macro for horizontal list-->
    <#elseif module.getType()== "HorizontalListLinksModule">
        <@horizontalList module />

    <#elseif module.getType()== "ICentreModule">
      <@icentre module/>

    <#elseif module.getType()== "IKnowModule">
    <@iknow module/>

    </#if>

</#macro>
