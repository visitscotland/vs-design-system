<#include "../include/imports.ftl">

<#include "../include/vs-dotcom-ds/components/header.ftl">
<#include "../include/vs-dotcom-ds/components/header-drawer-toggle.ftl">
<#include "../include/vs-dotcom-ds/components/header-login-button.ftl">
<#include "../include/vs-dotcom-ds/components/drawer-content.ftl">
<#include "../include/vs-dotcom-ds/components/list-group.ftl">
<#include "../include/vs-dotcom-ds/components/logo.ftl">
<#include "../include/vs-dotcom-ds/components/site-search.ftl">
<#include "../include/vs-dotcom-ds/components/favourites-list.ftl">
<#include "../include/vs-dotcom-ds/components/site-search-toggle-button.ftl">
<#include "../include/vs-dotcom-ds/components/favourites-button.ftl">
<#include "../include/vs-dotcom-ds/components/header-button.ftl">
<#include "../include/vs-dotcom-ds/components/site-nav-list-item.ftl">

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

        <vs-header-drawer-toggle
                section="top"
                class="d-lg-none"
                content-key="language-list"
                drawer-key="header-top"
        >
            ENG
        </vs-header-drawer-toggle>

    <#include "./header/language-list.ftl">
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

  <#if menu.siteMenuItems??>
    <template #site-navigation>
      <#list enhancedMenu as item>

        <#if item.hstLink??>
            <#assign href><@hst.link link=item.hstLink/></#assign>
        <#elseif item.externalLink??>
            <#assign href>${item.externalLink?replace("\"", "")}</#assign>
        <#else>
            <#assign href></#assign>
        </#if>

          <vs-site-nav-list-item
                  href="${href}"
                  tracking-id="dummy-id"
                  key="${item.name}"
          >
              <#--TODO: Remove dummy code to check the widget -->
              <#if item.widget??>

                  <vs-site-nav-list-promo-item>
                      Widget (${item.widget.component})
                  </vs-site-nav-list-promo-item>

              <#else>
              ${item.title?html}
              </#if>

          <#if item.childMenuItems??>
            <template #subnav>
              <#list item.childMenuItems as itemLevel2>

                <#if itemLevel2.hstLink??>
                    <#assign hrefLevel2><@hst.link link=itemLevel2.hstLink/></#assign>
                <#elseif itemLevel2.externalLink??>
                    <#assign hrefLevel2>${itemLevel2.externalLink?replace("\"", "")}</#assign>
                <#else>
                    <#assign hrefLevel2></#assign>
                </#if>

                <#if itemLevel2.widget??>

                  <vs-site-nav-list-promo-item>
                      Widget (${item.widget.component})
                  </vs-site-nav-list-promo-item>

                <#else>

                  <vs-site-nav-list-item
                          href="${hrefLevel2}"
                          tracking-id="dummy-tracking-id"
                          key="${itemLevel2.name}"
                  >

                      ${itemLevel2.name?html}

                    <#if itemLevel2.childMenuItems??>
                      <template #subnav>
                        <#list itemLevel2.childMenuItems as itemLevel3>

                          <#if itemLevel3.hstLink??>
                              <#assign hrefLevel3><@hst.link link=itemLevel3.hstLink/></#assign>
                          <#elseif itemLevel3.externalLink??>
                              <#assign hrefLevel3>${itemLevel3.externalLink?replace("\"", "")}</#assign>
                          <#else>
                              <#assign hrefLevel3></#assign>
                          </#if>


                            <vs-site-nav-list-item
                                    href="${hrefLevel3}"
                                    tracking-id="dummy-tracking-id"
                                    key="${itemLevel3.name}"
                            >
                                ${itemLevel3.name?html}
                            </vs-site-nav-list-item>
                        </#list>
                      </template>
                    </#if>

                  </vs-site-nav-list-item>

                </#if>

              </#list>
            </template>
          </#if>
          </vs-site-nav-list-item>

      </#list>
    </template>
  </#if>

</vs-header>
    <@hst.cmseditmenu menu=menu/>
</#if>