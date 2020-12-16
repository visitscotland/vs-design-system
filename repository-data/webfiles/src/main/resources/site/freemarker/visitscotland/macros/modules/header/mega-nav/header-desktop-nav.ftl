<#include "../../../../../include/imports.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-top-menu-item.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-list.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-list-item.ftl">

<#macro headerDesktopNav menu=menu>
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
                        <#if childItem.title??>
                            <#assign subheadingUrl = "#">
                            <#if childItem.hstLink??>
                                <#assign subheadingUrl><@hst.link link=childItem.hstLink/></#assign>
                            <#elseif item.externalLink??>
                                <#assign subheadingUrl>${childItem.externalLink?replace("\"", "")}</#assign>
                            </#if>

                            <vs-mega-nav-list>
                                <vs-mega-nav-list-item slot="navListHeading">
                                    ${childItem.title}
                                </vs-mega-nav-list-item>

                                <#list childItem.childMenuItems as thirdChildItem>
                                    <#if thirdChildItem.title??>
                                        <#assign navItemUrl = "#">
                                        <#if thirdChildItem.hstLink??>
                                            <#assign navItemUrl><@hst.link link=thirdChildItem.hstLink/></#assign>
                                        <#elseif item.externalLink??>
                                            <#assign navItemUrl>${thirdChildItem.externalLink?replace("\"", "")}</#assign>
                                        </#if>
                                        <vs-mega-nav-list-item
                                            slot="navListItems"
                                            href="${navItemUrl}"
                                        >
                                            ${thirdChildItem.title}
                                        </vs-mega-nav-list-item>
                                    </#if>
                                </#list>

                                <vs-mega-nav-list-item
                                    href="${subheadingUrl}"
                                    subheading-link
                                    slot="navHeadingCtaLink"
                                >
                                    ${childItem.cta}
                                </vs-mega-nav-list-item>
                            </vs-mega-nav-list>
                        </#if>
                    </#list>
                </template>
            </vs-mega-nav-top-menu-item>
        </#if>
    </#list>

</#macro>