<#include "../../include/imports.ftl">
<#include "./base-header/navigation-item.ftl">

<#include "../../frontend/components/vs-header.ftl">
<#include "../../frontend/components/vs-header-drawer-toggle.ftl">
<#include "../../frontend/components/vs-header-login-button.ftl">
<#include "../../frontend/components/vs-drawer-content.ftl">
<#include "../../frontend/components/vs-list-group.ftl">
<#include "../../frontend/components/vs-logo.ftl">
<#include "../../frontend/components/vs-site-search.ftl">
<#include "../../frontend/components/vs-favourites-list.ftl">
<#include "../../frontend/components/vs-site-search-toggle-button.ftl">
<#include "../../frontend/components/vs-favourites-button.ftl">
<#include "../../frontend/components/vs-header-button.ftl">
<#include "../../frontend/components/vs-site-nav-list-item.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->

<#if menu??>

<vs-header>
    <template #top-left>
        <vs-header-drawer-toggle
                section="top"
                class="d-lg-none"
                content-key="universal-nav"
                drawer-key="header-top"
        >
            Our sites
        </vs-header-drawer-toggle>

    <#include "./header/universal-nav-desktop.ftl">

    </template>

    <template #top-right>
        <vs-header-login-button>
            <template v-slot:logged-in-content>
                <span class="d-none">Log out</span>
                <span>Hi Boudicca... (not you?)</span>
            </template>
            <template v-slot:logged-out-content>
                <span>Login</span>
            </template>
        </vs-header-login-button>

        <#include "./header/language-selector.ftl">
    </template>

    <template #top-drawer>
        <vs-drawer-content content-key="universal-nav" open-focus="content">
        <#include "./header/universal-nav-mobile.ftl">
        </vs-drawer-content>
        <vs-drawer-content content-key="language-list" open-focus="content">

            <vs-list-group class="d-lg-none" tabindex="-1">
            </vs-list-group>
        </vs-drawer-content>
    </template>

    <template #logo>
        <vs-logo />
    </template>

    <template #bottom-right>
        <vs-header-drawer-toggle
                drawer-key="header-bottom"
                content-key="site-search"
                tag="vs-site-search-toggle-button"
                section="bottom"
        >
            Search
        </vs-header-drawer-toggle>

        <vs-header-drawer-toggle
                drawer-key="header-bottom"
                content-key="favourites-list"
                href="/"
                title="Home"
                tag="vs-favourites-button"
                section="bottom"
        />
    </template>

    <template #bottom-drawer>
        <vs-drawer-content content-key="site-search" ref="siteSearch" open-focus="content">
            <vs-site-search />
        </vs-drawer-content>
        <vs-drawer-content content-key="favourites-list" open-focus="close">
            <vs-favourites-list />
        </vs-drawer-content>
    </template>


    <template #site-navigation>
      <#if menu.siteMenuItems??>
        <#list menu.siteMenuItems as item>
          <@navItem menuItem=item/>
        </#list>
      </#if>
      <@hst.cmseditmenu menu=menu/>
    </template>
</vs-header>

</#if>