<#include "../../../include/imports.ftl">
<#include "../global/cms-errors.ftl">
<#include "../modules/megalinks/megalinks.ftl">
<#include "../modules/megalinks/megalinks-horizontal-list.ftl">
<#include "../modules/long-content/long-content.ftl">
<#include "../modules/tourism-information/tourisminformation-iknow.ftl">
<#include "../modules/tourism-information/tourisminformation-icentre.ftl">
<#include "themeCalculator.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brxm.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brxm.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brxm.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brxm.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brxm.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brxm.beans.Image" -->

<#--TODO Control abput colours, change style="background-color:${style}  -->
<#macro moduleBuilder module colourScheme=[]>


    <#if colourScheme?size gt 0>
        <#assign theme = themeCalculator(module, colourScheme)>
    <#elseif breadcrumbs?? && breadcrumbs.items?size == 2>
        <#assign theme = themeCalculator(module, ["light", "light", "dark"])>
    <#else>
        <#assign theme = themeCalculator(module, ["dark", "light", "light"])>
    </#if>
    <#--<p>Calculated = ${theme}</p>-->

    <div class="has-edit-button" style="background-color:${theme}">
        <#-- all Megalinks modules -->
        <#if module.getType() == "MultiImageLinksModule" ||  module.getType() == "SingleImageLinksModule" || module.getType()== "ListLinksModule">
            <@megalinks item=module type=module.getType() />

        <#elseif module.getType()== "HorizontalListLinksModule">
            <@horizontalList module/>

        <#elseif module.getType()== "ICentreModule">
            <@icentre module/>

        <#elseif module.getType()== "IKnowModule">
            <@iknow module/>

        <#elseif module.getType()== "LongContentModule">
            <@longContent module/>
        </#if>
    </div>
</#macro>
