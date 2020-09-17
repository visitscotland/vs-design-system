<#include "../../include/imports.ftl">
<#include "../../frontend/components/vs-global-menu.ftl">
<#include "../../frontend/components/vs-global-menu-language.ftl">
<#include "../../frontend/components/vs-global-menu-language-item.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsMenuItem" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#-- @ftlvariable name="language" type="com.visitscotland.brmx.beans.mapping.LocalizedURL"-->

<#assign currentLocale=hstRequest.requestContext.resolvedMount.mount.locale>
<#assign languages = ["en-gb","de-de","es-es","fr-fr","it-it","nl-nl"]>

<#if menu??>
<div class="has-edit-button">

    <vs-global-menu
        dropdown-label="${label('navigation.static', 'uninav.our-sites')}"
        active-site="https://www.visitscotland.com/"
    >
        <template slot="third-menu-item">
            <vs-global-menu-language current-locale="${currentLocale}" language-label="${label('navigation.static', 'universal.language')}">
                <#list localizedURLs as language>
                    <vs-global-menu-language-item
                        key="${language.language}"
                        language-link="${language.url}"
                        language-name="${language.displayName}<#-- (${language.isExists()?c}) -->"
                    >
                    </vs-global-menu-language-item>
                </#list>
            </vs-global-menu-language>
        </template>
    </vs-global-menu>

    <ul class="nav nav-pills">
      <#list menu.siteMenuItems as item>
          <#if item.title?has_content>
            <#if !item.hstLink?? && !item.externalLink??>
                <#if item.selected || item.expanded>
                <li class="active">${item.title?html}</li>
                <#else>
                <li>${item.title?html}</li>
                </#if>
            <#else>
                <#if item.hstLink??>
                    <#assign href><@hst.link link=item.hstLink/></#assign>
                <#elseif item.externalLink??>
                    <#assign href>${item.externalLink?replace("\"", "")}</#assign>
                </#if>
                <#if item.widget??>
                    <li><a style="background: lightcyan;">Widget (${item.widget.component})</a></li>
                <#elseif  item.selected || item.expanded>
                    <li class="active">${item.title?html}
                        <#if item.cta??><a href="${href}">(${item.cta?html})</a></#if>
                    </li>
                <#else>
                    <li>${item.title?html}
                        <#if item.cta??><a href="${href}">(${item.cta?html})</a></#if>
                    </li>
                </#if>
            </#if>
          </#if>
      </#list>
    </ul>
    <@hst.cmseditmenu menu=menu />
</div>
</#if>