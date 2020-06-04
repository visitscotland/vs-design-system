<#include "../../include/imports.ftl">
<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->
<#if enhancedMenu??>
    <div class="has-edit-button">
        <#list enhancedMenu as item>
            <ul class="nav nav-pills">
                <#list item.childMenuItems as children>
                    <#if children.hstLink??>
                        <#assign href><@hst.link link=children.hstLink/></#assign>
                        <div class="mb-2">
                            <vs-link href="${href}">
                                ${children.title?html}
                            </vs-link>
                        </div>
                    <#elseif children.externalLink??>
                        <#assign href>${children.externalLink?replace("\"", "")}</#assign>
                        <div class="mb-2">
                            <vs-link href="${href}">
                                ${children.title?html}
                            </vs-link>
                        </div>
                    </#if>

                </#list>
            </ul>
        </#list>
        <@hst.cmseditmenu menu=menu/>
    </div>
</#if>


