<#include "../../include/imports.ftl">

<#include "../../frontend/components/vs-header-new.ftl">

<#include "../macros/modules/header/mega-nav/header-mega-nav.ftl">
<#include "../macros/modules/header/header-global-menu.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsMenuItem" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->



<#if menu??>
<div class="has-edit-button">
    <vs-header-new>
        <template slot="globalMenu">
            <@headerGlobalMenu />
        </template>
        <template slot="megaNav">
            <@headerMegaNav menu=menu/>
        </template>
    </vs-header-new>

    <@hst.cmseditmenu menu=menu />
</div>
</#if>