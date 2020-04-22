<#include "../../include/imports.ftl">
<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->
<#if enhancedMenu??>
    <div class="has-edit-button">
        <ul class="nav nav-pills">
            <#list enhancedMenu as item>
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
                    <#--TODO this code is the same for utility-footer, macro?-->
                    <#list item.childMenuItems as children>
                        <#if children.hstLink?? && children.title?has_content>
                            <#assign href><@hst.link link=children.hstLink/></#assign>
                            <#--TODO fix this look,  just need to control if the link is internal or external-->
                            <div class="mb-2">
                                <vs-link  href="${href}">
                                    ${children.title?html}
                                </vs-link>
                            </div>
                        <#elseif children.externalLink??>
                            <#assign href>${children.externalLink?replace("\"", "")}</#assign>
                            <#assign external>true</#assign>
                            <div class="mb-2">
                                <vs-link external href="${href}">
                                    ${children.title?html}
                                </vs-link>
                            </div>
                        </#if>
                    </#list>
                </ul>
            </#list>
        </ul>
        <@hst.cmseditmenu menu=menu/>
        <@hst.include ref="utility"/>
    </div>
</#if>