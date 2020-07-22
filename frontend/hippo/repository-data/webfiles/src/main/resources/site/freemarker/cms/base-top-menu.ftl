<#include "../include/imports.ftl">
<#include "../include/vs-dotcom-ds/components/container.ftl">
<#include "../include/vs-dotcom-ds/components/row.ftl">
<#include "../include/vs-dotcom-ds/components/col.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->
<#if menu??>

<vs-container class="has-edit-button">
  <vs-row>
    <vs-col>
      <#if menu.siteMenuItems??>
        <ul class="nav nav-pills">
          <#list menu.siteMenuItems as item>
            <#if !item.hstLink?? && !item.externalLink??>
              <#if item.selected || item.expanded>
                <li class="active">${item.name?html}</li>
              <#else>
                <li><div style="padding: 10px 15px;">${item.name?html}</div></li>
              </#if>
            <#else>
              <#if item.hstLink??>
                <#assign href><@hst.link link=item.hstLink/></#assign>
              <#elseif item.externalLink??>
                <#assign href>${item.externalLink?replace("\"", "")}</#assign>
              </#if>
              <#if  item.selected || item.expanded>
                <li class="active">${item.title?html} (Go to <a href="${href}">${item.cta?html}</a>)</li>
              <#else>
                <li><a href="${href}">${item.title?html}</a></li>
              </#if>
            </#if>
          </#list>
        </ul>
      </#if>
      <@hst.cmseditmenu menu=menu/>
    </vs-col>

  </vs-row>
</vs-container>
</#if>