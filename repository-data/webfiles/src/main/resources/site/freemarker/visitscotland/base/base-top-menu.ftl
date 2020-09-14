<#include "../../include/imports.ftl">
<#-- @ftlvariable name="menu" type="com.visitscotland.brmx.components.navigation.RootMenuItem" -->
<#-- @ftlvariable name="item" type="com.visitscotland.brmx.components.navigation.MenuItem" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->

<#include "../../frontend/components/vs-global-menu.ftl">

<#include "../../frontend/components/vs-header-new.ftl">
<#include "../../frontend/components/vs-mega-nav.ftl">
<#include "../../frontend/components/vs-mega-nav-top-menu-item.ftl">
<#include "../../frontend/components/vs-mega-nav-mobile-toggle.ftl">


<#if menu??>
<div class="has-edit-button">
    <vs-header-new>
        <template slot="globalNav">
            <vs-global-menu
                dropdown-label='<@fmt.message key="global-menu.our-websites" />'
                active-site="https://www.visitscotland.com/"
            ></vs-global-menu>
        </template>
        <template slot="megaNav">
            <vs-mega-nav href="<@hst.link siteMapItemRefId='root'/>">
                <template slot="megaNavTopMenuItems">
                    <#list menu.siteMenuItems as item>
                        <#if item.title?has_content>

                            <#assign href="#">
                            <#if item.hstLink??>
                                <#assign href><@hst.link link=item.hstLink/></#assign>
                            <#elseif item.externalLink??>
                                <#assign href>${item.externalLink?replace("\"", "")}</#assign>
                            </#if>

                            <vs-mega-nav-top-menu-item
                                href="${href}"
                                cta-text="<#if item.cta??>${item.cta}<#else></#if>"
                            >
                                <template slot="buttonContent">
                                    ${item.title?html}
                                </template>
                                <template slot="dropdownContent">
                                    <#list item.childMenuItems as childItem>
                                    
                                    <#if !childItem.hstLink?? && !childItem.externalLink??>
                                        <li>${childItem.title?html}</li>
                                    <#else>
                                        <#if childItem.hstLink??>
                                            <#assign href><@hst.link link=childItem.hstLink/></#assign>
                                        <#elseif item.externalLink??>
                                            <#assign href>${childItem.externalLink?replace("\"", "")}</#assign>
                                        </#if>
                                        <li><a href="${href}">${childItem.title?html}</a></li>
                                    </#if>

                                    <ul class="nav nav-pills">
                                    <#list childItem.childMenuItems as thirdChildItem>
                                        <#if !thirdChildItem.hstLink?? && !thirdChildItem.externalLink??>
                                            <li>${thirdChildItem.title?html}</li>
                                        <#else>
                                            <#if thirdChildItem.hstLink??>
                                                <#assign href><@hst.link link=thirdChildItem.hstLink/></#assign>
                                            <#elseif item.externalLink??>
                                                <#assign href>${thirdChildItem.externalLink?replace("\"", "")}</#assign>
                                            </#if>
                                            <li><a href="${href}">${thirdChildItem.title?html}</a></li>
                                        </#if>
                                    </#list>
                                    </ul>
                                </#list>
                                </template>
                            </vs-mega-nav-top-menu-item>
                        </#if>
                    </#list>
                </template>

                <template slot="megaNavMobileItems">
                    <#list menu.siteMenuItems as item>
                        <#if item.title?has_content>

                            <#assign href="#">
                            <#if item.hstLink??>
                                <#assign href><@hst.link link=item.hstLink/></#assign>
                            <#elseif item.externalLink??>
                                <#assign href>${item.externalLink?replace("\"", "")}</#assign>
                            </#if>

                            <vs-mega-nav-mobile-toggle
                                href="${href}"
                                cta-text="<#if item.cta??>${item.cta}<#else></#if>"
                            >
                                ${item.title?html}                                
                            </vs-mega-nav-mobile-toggle>

                            <ul>
                                <#list item.childMenuItems as childItem>
                                    <#if childItem.title??>
                                        <#if !childItem.hstLink?? && !childItem.externalLink??>
                                            <li>${childItem.title?html}</li>
                                        <#else>
                                            <#if childItem.hstLink??>
                                                <#assign href><@hst.link link=childItem.hstLink/></#assign>
                                            <#elseif item.externalLink??>
                                                <#assign href>${childItem.externalLink?replace("\"", "")}</#assign>
                                            </#if>
                                            <li><a href="${href}">${childItem.title?html}</a></li>
                                        </#if>
                                    </#if>

                                    <ul class="nav nav-pills">
                                    <#list childItem.childMenuItems as thirdChildItem>
                                        <#if thirdChildItem.title??>
                                            <#if !thirdChildItem.hstLink?? && !thirdChildItem.externalLink??>
                                                <li>${thirdChildItem.title?html}</li>
                                            <#else>
                                                <#if thirdChildItem.hstLink??>
                                                    <#assign href><@hst.link link=thirdChildItem.hstLink/></#assign>
                                                <#elseif item.externalLink??>
                                                    <#assign href>${thirdChildItem.externalLink?replace("\"", "")}</#assign>
                                                </#if>
                                                <li><a href="${href}">${thirdChildItem.title?html}</a></li>
                                            </#if>
                                        </#if>
                                    </#list>
                                    </ul>
                                </#list>
                            </ul>
                        </#if>
                    </#list>
                </template>
            </vs-mega-nav>
        </template>
    </vs-header-new>

    <@hst.cmseditmenu menu=menu />
</div>
</#if>