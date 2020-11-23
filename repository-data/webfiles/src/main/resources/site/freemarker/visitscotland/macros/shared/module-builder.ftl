<#include "../../../include/imports.ftl">
<#include "../global/cms-errors.ftl">
<#include "../modules/megalinks/megalinks.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brmx.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<#--TODO Control abput colours, change style="background-color:${style}  -->
<#macro moduleBuilder module theme>
    <#if theme="theme1">
        <#assign themeName = "dark">
    <#elseif theme="theme2">
        <#assign themeName = "light">
    </#if>

    <div class="has-edit-button theme-${themeName}">
       
        <#-- all Megalinks modules -->
        <#if module.getType() == "MultiImageLinksModule" ||  module.getType() == "SingleImageLinksModule" || module.getType()== "ListLinksModule">
            <@megalinks item=module type=module.getType() theme=themeName />

        <#--  <#elseif module.getType()== "ICentreModule">
            <@icentre module/>

        <#elseif module.getType()== "IKnowModule">
            <@iknow module/>  -->

        </#if>
    </div>
</#macro>
