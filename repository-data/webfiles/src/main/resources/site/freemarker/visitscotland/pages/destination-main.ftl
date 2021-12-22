<#ftl output_format="XML">
<#include "../../include/imports.ftl">
<#include "../macros/global/cms-errors.ftl">
<#include "../macros/modules/page-intro/social-share.ftl">
<#include "../macros/modules/page-intro/page-intro.ftl">
<#include "../macros/modules/signpost/signpost.ftl">
<#include "../macros/shared/module-builder.ftl">
<#include "../../frontend/components/vs-psr-module.ftl">

<#-- Implicit Request Objects -->
<#-- @ftlvariable name="document" type="com.visitscotland.brxm.hippobeans.Destination" -->
<#-- @ftlvariable name="pageItems" type="com.visitscotland.brxm.hippobeans.Megalinks" -->
<#-- @ftlvariable name="image" type="com.visitscotland.brxm.model.FlatImage" -->

<#-- @ftlvariable name="heroImage" type="com.visitscotland.brxm.model.FlatImage" -->
<#-- @ftlvariable name="heroCoordinates" type="com.visitscotland.brxm.model.Coordinates" -->

<div class="has-edit-button">
	<@hst.manageContent hippobean=document/>
    <@cmsErrors errors=alerts!"" editMode=editMode />

    <@pageIntro content=document heroDetails=heroImage />

    <br>
    <#-- search results link = ${lang}/info/${psrWidget.category.pathVariable}/search-results?parameters -->
    <br>
    product types = ${psrWidget.category.productTypes}
    <br>
    location = ${psrWidget.location.type}

    <#if psrWidget.location??>
        <#if psrWidget.location.type == "POLYGON">
            <#assign configArray = "[
                    {'subSearchType': '${psrWidget.category.productTypes}'},
                    {'locpoly': '${psrWidget.location.key}'},
                    {'lang':'${document.locale.language}'},
                ]"
            >
        <#elseif  psrWidget.location.type == "SUBREGION">
            <#assign configArray = "[
                    {'subSearchType': '${psrWidget.category.productTypes}'},
                    {'loc': '${psrWidget.location.key}'},
                    {'lang':'${document.locale.language}'},
                ]"
            >
        </#if>
    </#if>

    ${psrWidget.location.key}

    <vs-psr-module
        :config-arr="[
            {'subSearchType': '${psrWidget.category.productTypes}'},
            {'locPoly': '${psrWidget.location.key}'},
            {'lang':'${document.locale.language}'},
        ]"
    >
        <template slot="vsModuleHeading">
            ${psrWidget.title}
        </template>

        <template slot="vsModuleIntro">
            ${psrWidget.description}
        </template>
    </vs-psr-module>

	<#list pageItems as item>
        <@moduleBuilder item />
	</#list>

    <@socialShare nojs=true/>

    <#if otyml??>
        <@horizontalList otyml themeName />
    </#if>

    <#if newsletterSignpost??>
		<@signpost module=newsletterSignpost imgSrc="assets/images/illustrations/newsletter.svg"/>
	</#if>
</div>
