<#include "../../include/imports.ftl">
<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenufooter" type="java.util.List" -->
<#-- @ftlvariable name="enhancedMenuutility" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->
</br></br>----------------------------------------------------------------------------------------------------------</br><?br>
<#if enhancedMenuutility??>
  <div class="has-edit-button">
    <ul class="nav nav-pills">
      <#list enhancedMenuutility as item>
          <#if item.hstLink??>
            <#assign href><@hst.link link=item.hstLink/></#assign>
          <#elseif item.externalLink??>
            <#assign href>${item.externalLink?replace("\"", "")}</#assign>
          </#if>
      </#list>
    </ul>
    <@hst.cmseditmenu menu=menu/>
  </div>
</#if>

