<#include "../../include/imports.ftl">
<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->
----------------------------------------------------------------------------------------------------------</br></br>
<#if enhancedMenu??>
  <div class="has-edit-button">
      <#list enhancedMenu as item>
          <ul class="nav nav-pills">
              <#list item.childMenuItems as children>
                  <#if item.hstLink??>
                      <#assign href><@hst.link link=item.hstLink/></#assign>
                  <#elseif item.externalLink??>
                      <#assign href>${item.externalLink?replace("\"", "")}</#assign>
                  </#if>
                  <div class="mb-2">
                      <vs-link href="${href}">
                          ${children.title?html}
                      </vs-link>
                  </div>
              </#list>
          </ul>
      </#list>
    <@hst.cmseditmenu menu=menu/>
  </div>
</#if>

