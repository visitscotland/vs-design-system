<#include "../../../include/imports.ftl">
<#include "../global/cms-errors.ftl">
<#include "../modules/megalinks/megalinks.ftl">
<#include "../modules/long-content/long-content.ftl">
<#include "../modules/tourism-information/tourisminformation-iknow.ftl">
<#include "../modules/tourism-information/tourisminformation-icentre.ftl">
<#include "../modules/otyml/otyml.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brxm.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brxm.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brxm.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brxm.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brxm.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brxm.beans.Image" -->

<#macro moduleBuilder module theme>

    <div class="has-edit-button" style="background-color:${theme}">
        <#-- all Megalinks modules -->
        <#if module.getType() == "MultiImageLinksModule" ||  module.getType() == "SingleImageLinksModule" || module.getType()== "ListLinksModule">
            <@megalinks item=module type=module.getType() />

        <#elseif module.getType()== "HorizontalListLinksModule">
            <@otyml module/>

        <#elseif module.getType()== "ICentreModule">
            <@icentre module/>

        <#elseif module.getType()== "IKnowModule">
            <@iknow module/>

        <#elseif module.getType()== "LongContentModule">
            <@longContent module/>
        </#if>
    </div>
</#macro>
