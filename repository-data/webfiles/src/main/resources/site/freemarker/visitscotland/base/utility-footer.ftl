<#compress>
<#include "../../include/imports.ftl">
<#include "../../frontend/components/vs-footer-utility-list.ftl">
<#include "../../frontend/components/vs-footer-nav-list-item.ftl">

<#-- @ftlvariable name="menu" type="com.visitscotland.brxm.components.navigation.RootMenuItem" -->
<#-- @ftlvariable name="item" type="com.visitscotland.brxm.components.navigation.MenuItem" -->

</#compress>
<#if menu??>
    <div class="has-edit-button">
        <#list menu.siteMenuItems as item>
            <vs-footer-utility-list>
                <#list item.childMenuItems as childItem>
                    <#if childItem.title?has_content>
                        <#assign href = "">
                        <#assign external = false>

                        <#if childItem.hstLink??>
                            <#assign href><@hst.link fullyQualified=fullyQualifiedURLs link=childItem.hstLink/></#assign>
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


