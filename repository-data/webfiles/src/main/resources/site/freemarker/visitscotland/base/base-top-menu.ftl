<#compress>
<#include "../../include/imports.ftl">
<#include "../macros/modules/header/mega-nav/header-mega-nav.ftl">
<#include "../macros/modules/header/header-global-menu.ftl">
<#include "../macros/modules/header/banner.ftl">
<#include "../macros/global/dev-env-menu.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="banner" type="com.visitscotland.brxm.model.BannerModule" -->
<#-- @ftlvariable name="navigationWidget" type="com.visitscotland.brxm.model.navigation.FeaturedItem" -->
<#-- @ftlvariable name="navigationWidget2" type="com.visitscotland.brxm.model.navigation.FeaturedItem" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsMenuItem" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
</#compress>

<#if ciBranch??>
    <@devEnvMenu />
</#if>

<#if menu??>
    <div class="has-edit-button">
        <header class="position-relative zindex-fixed">
            <@headerGlobalMenu />
            <@headerMegaNav menu=menu/>
        </header>
        <@hst.cmseditmenu menu=menu />
    </div>
    <#if navigationWidget??>
        <@previewWarning editMode navigationWidget navigationWidget.errorMessages/>
    </#if>
    <#if navigationWidget2??>
        <@previewWarning editMode navigationWidget2 navigationWidget2.errorMessages/>
    </#if>
</#if>




<#if banner??>
    <@emergencyBanner module=banner/>
</#if>



