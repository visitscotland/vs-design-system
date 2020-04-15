<#include "../../include/imports.ftl">
<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenufooter" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->
<#if enhancedMenufooter??>
  <div class="has-edit-button">
    <ul class="nav nav-pills">
      <#list enhancedMenufooter as item>
        <#if !item.hstLink?? && !item.externalLink??>
            <li><div style="padding: 10px 15px;">${item.title?html}</div></li>
        <#else>
          <#if item.hstLink??>
            <#assign href><@hst.link link=item.hstLink/></#assign>
          <#elseif item.externalLink??>
            <#assign href>${item.externalLink?replace("\"", "")}</#assign>
          </#if>
        </#if>
        <ul class="nav">
            <#list item.childMenuItems as children>
            <div class="mb-2">
                <vs-link href="${href}">
                    ${children.title?html}
                </vs-link>
            </div>
            </#list>
        </ul>
      </#list>
    </ul>
    <@hst.cmseditmenu menu=menu/>
  </div>
</#if>