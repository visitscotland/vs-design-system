<#include "../include/imports.ftl">
<#include "./header/navigation-item.ftl">

<#include "../vs-dotcom-ds/components/header.ftl">
<#include "../vs-dotcom-ds/components/header-drawer-toggle.ftl">
<#include "../vs-dotcom-ds/components/header-login-button.ftl">
<#include "../vs-dotcom-ds/components/drawer-content.ftl">
<#include "../vs-dotcom-ds/components/list-group.ftl">
<#include "../vs-dotcom-ds/components/logo.ftl">
<#include "../vs-dotcom-ds/components/site-search.ftl">
<#include "../vs-dotcom-ds/components/favourites-list.ftl">
<#include "../vs-dotcom-ds/components/site-search-toggle-button.ftl">
<#include "../vs-dotcom-ds/components/favourites-button.ftl">
<#include "../vs-dotcom-ds/components/header-button.ftl">
<#include "../vs-dotcom-ds/components/site-nav-list-item.ftl">

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
        <#list enhancedMenu as item>
          <@navItem menuItem=item/>
        </#list>
      </#if>
      <@hst.cmseditmenu menu=menu/>
    </template>
</vs-header>

</#if>