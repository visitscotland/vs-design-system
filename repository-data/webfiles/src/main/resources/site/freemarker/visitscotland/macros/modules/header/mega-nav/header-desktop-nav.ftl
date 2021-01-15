<#include "../../../../../include/imports.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-top-menu-item.ftl">

<#macro headerDesktopNav menu=menu>
    <#list menu.siteMenuItems as item>
        <#if item.title?has_content>

            <#assign href="#">
            <#if item.hstLink??>
                <#assign href><@hst.link fullyQualified=fullyQualifiedURLs link=item.hstLink/></#assign>
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
                        <#list item.childMenuItems as childItem>
                            <#if childItem.title??>
                                <#if !childItem.hstLink?? && !childItem.externalLink??>
                                    <li>${childItem.title?html}</li>
                                <#else>
                                    <#if childItem.hstLink??>
                                        <#assign href><@hst.link fullyQualified=fullyQualifiedURLs link=childItem.hstLink/></#assign>
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
                                                <#assign href><@hst.link fullyQualified=fullyQualifiedURLs link=thirdChildItem.hstLink/></#assign>
                                            <#elseif item.externalLink??>
                                                <#assign href>${thirdChildItem.externalLink?replace("\"", "")}</#assign>
                                            </#if>
                                            <li><a href="${href}">${thirdChildItem.title?html}</a></li>
                                        </#if>
                                    </#if>
                                </#list>
                            </ul>
                        </#list>
                    </#list>
                </template>
            </vs-mega-nav-top-menu-item>
        </#if>
    </#list>

</#macro>