<#include "../../../include/imports.ftl">
<#include "../global/cms-errors.ftl">
<#include "../modules/megalinks/megalinks.ftl">
<#include "../modules/megalinks/megalinks-horizontal-list.ftl">
<#include "../modules/tourism-information/tourisminformation-iknow.ftl">
<#include "../modules/tourism-information/tourisminformation-icentre.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brmx.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<#macro moduleBuilder module theme>
    <#if theme="theme1">
        <#assign themeName = "dark">
    <#elseif theme="theme2">
        <#assign themeName = "light">
    </#if>

    <div 
        class="has-edit-button <#if module.getType() == 'MultiImageLinksModule' ||  module.getType() == 'SingleImageLinksModule' || module.getType()== 'ListLinksModule'>theme-${themeName}</#if>">
       
        <#-- all Megalinks modules -->
        <#if module.getType() == "MultiImageLinksModule" ||  module.getType() == "SingleImageLinksModule" || module.getType()== "ListLinksModule">
            <@megalinks item=module type=module.getType() theme=themeName />

        <#elseif module.getType()== "HorizontalListLinksModule">
            <@horizontalList module/>

        <#elseif module.getType()== "ICentreModule">
            <@icentre module/>

        <#elseif module.getType()== "IKnowModule">
            <@iknow module/>

        </#if>
    </div>
</#macro>
