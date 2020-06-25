<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/footer-utility-list.ftl">
<#include "../../vs-dotcom-ds/components/footer-nav-list-item.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->

<#if enhancedMenu??>
    <div class="has-edit-button">
        <#list enhancedMenu as item>
            <vs-footer-utility-list>
                <#list item.childMenuItems as childItem>
                    <#if childItem.title?has_content>
                        <#assign href = "">
                        <#assign external = false>

                        <#if childItem.hstLink??>
                            <#assign href><@hst.link link=childItem.hstLink/></#assign>
                        <#elseif childItem.externalLink??>
                            <#assign href>${childItem.externalLink}</#assign>
                            <#assign external = true>
                        </#if>

                        <vs-footer-nav-list-item
                            href="${href}"
                            link-text="${childItem.title}"
                            :external="<#if external>true<#else>false</#if>"
                        ></vs-footer-nav-list-item>
                    </#if>
                </#list>
            </vs-footer-utility-list>
        </#list>

        <@hst.cmseditmenu menu=menu/>
    </div>
</#if>


