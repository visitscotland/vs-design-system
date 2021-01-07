<#ftl output_format="XML">
<#include "../../include/imports.ftl">
<#include "../macros/global/cms-errors.ftl">

<#include "../macros/modules/megalinks/megalinks.ftl">
<#include "../macros/modules/page-intro/page-intro.ftl">
<#include "../macros/shared/module-builder.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brxm.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brxm.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brxm.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brxm.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brxm.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brxm.beans.Image" -->

<div class="has-edit-button">
	<@hst.manageContent hippobean=document documentTemplateQuery="new-module" rootPath="site" defaultPath="${path}" />
    <@cmsErrors errors=alerts!"" editMode=editMode />

    <@hst.link var="hero" hippobean=document.heroImage.original/>

    <@pageIntro content=document heroImage=heroImage heroCoordinates=heroCoordinates hero=heroImage hero=hero theme=introTheme areas="" days="" firstStop="" lastStop="" />	

	<#list pageItems as item>
        <#if item.theme??>
            <#assign theme = item.theme />
        <#else>
            <#assign theme = "theme2" />
        </#if>

        <@moduleBuilder module=item theme=theme />
	</#list>
</div>
