<#compress>
<#include "../../include/imports.ftl">

<#include "../../frontend/components/vs-header.ftl">

<#include "../macros/modules/header/mega-nav/header-mega-nav.ftl">
<#include "../macros/modules/header/header-global-menu.ftl">
<#include "../macros/global/dev-env-menu.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsMenuItem" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
</#compress>

<#-- TODO: Remove the following lines-->
<#assign ciBranch = "feature/VS-1234-this-is-a-very-long-branch-name-that-jose-is-using-as-a-demo">
<#assign ciCommitAuthor = "Jose Calcines">
<#assign ciPrID = "PR-231">
<#if ciBranch??>
<#-- TODO: Remove the following lines-->
<#--<div class="vs-global-menu">-->
<#--    <div fluid="lg" class="px-1 px-sm-3 container-lg">-->
<#--        <div class="row">-->
<#--            <div cols="12" class="vs-global-menu__wrapper col-12">-->
<#--                <div class="dropdown b-dropdown vs-dropdown vs-global-menu__dropdown d-lg-none btn-group" id="__BVID__12">-->
<#--                    <span>branch: ${ciBranch} author: ${ciCommitAuthor} PR: ${ciPrID}</span>-->
<#--                </div>-->
<#--                <ul class="vs-list vs-global-menu__list d-none d-lg-flex vs-list--unstyled vs-list--inline">-->
<#--                    <li class="vs-global-menu__list_item vs-global-menu__list_item--active d-none d-lg-block">-->
<#--                        <span>branch: ${branch} author: ${commitAuthor} PR: ${ciPrID}</span>-->
<#--                    </li>-->
<#--                </ul>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--</div>-->

    <@devEnvMenu ciBranch ciCommitAuthor ciPrID />
   
</#if>

<#if menu??>
    <div class="has-edit-button">
        <vs-header>
            <template slot="globalMenu">
                <@headerGlobalMenu />
            </template>
            <template slot="megaNav">
                <@headerMegaNav menu=menu/>
            </template>
        </vs-header>

        <@hst.cmseditmenu menu=menu />
    </div>
</#if>