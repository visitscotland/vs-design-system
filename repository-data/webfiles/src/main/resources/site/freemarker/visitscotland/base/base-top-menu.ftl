<#include "../../include/imports.ftl">
<#include "../../frontend/components/vs-global-menu.ftl">

<@hst.setBundle basename="navigation"/>

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsMenuItem" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->

<#if menu??>
<div class="has-edit-button">
    <vs-global-menu
        dropdown-label='<@fmt.message key="global-menu.our-websites" />'
        active-site="https://www.visitscotland.com/"
    ></vs-global-menu>
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