<#ftl output_format="XML">
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

<#include "../macros/modules/megalinks/megalinks.ftl">
<#include "../macros/modules/page-intro/page-intro.ftl">
<#include "../macros/shared/module-builder.ftl">
<#--  <#include "../macros/modules/megalinks/multi-image/megalinks-multi-image.ftl">  -->
<#--  <#include "../macros/modules/megalinks/megalinks-single-image.ftl">
<#include "../macros/modules/megalinks/megalinks-list.ftl">
<#include "../macros/global/cms-errors.ftl">

<#include "./module-builder.ftl">
<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brmx.beans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brmx.beans.mapping.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brmx.beans.mapping.Coordinates" -->

<#-- @ftlvariable name="hero" type="com.visitscotland.brmx.beans.Image" -->

<div class="has-edit-button">
	<@hst.manageContent hippobean=document documentTemplateQuery="new-module" rootPath="site" defaultPath="${path}" />
    <@cmsErrors errors=alerts!"" editMode=editMode />
	
    <@hst.link var="hero" hippobean=document.heroImage.original/>

    <@heroModule content=document heroImage=heroImage heroCoordinates=heroCoordinates hero=heroImage hero=hero />	

	<#list pageItems as item>
        <#if item.theme?? && item.theme = "theme3">
            <#assign theme = "#292929" />
        <#else>
            <#assign theme = "#FFFFFF" />
        </#if>

        <@moduleBuilder module=item theme=item.theme />
	</#list>
</div>
