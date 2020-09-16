<#include "../../include/imports.ftl">
<#-- @ftlvariable name="menu" type="com.visitscotland.brmx.components.navigation.RootMenuItem" -->
<#-- @ftlvariable name="item" type="com.visitscotland.brmx.components.navigation.MenuItem" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->

<#include "../../frontend/components/vs-header-new.ftl">

<#include "../macros/modules/header/mega-nav/header-mega-nav.ftl">
<#include "../macros/modules/header/header-global-menu.ftl">

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