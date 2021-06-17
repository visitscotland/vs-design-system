<#include "../../../../../include/imports.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-top-menu-item.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-list.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-list-item.ftl">
<#include "header-widget.ftl">

<#macro headerDesktopNav menu=menu>
    <#list menu.siteMenuItems as item>
        <#list item.childMenuItems as childItem>
            <#if childItem.widget??>
                <#assign featuredItem = headerWidget(childItem.widget) />
            </#if>
        </#list>
        <#if item.title?has_content>
            <vs-mega-nav-top-menu-item
                    href="${getUrl(item)}"
                    cta-text="<#if item.cta??>${item.cta}<#else></#if>"
            >
                <template slot="buttonContent">
                    ${item.title}
                </template>

                <template slot="dropdownContent">
                    <#list item.childMenuItems as childItem>
                        <#if childItem.widget??>
                            <@headerWidget childItem.widget />
                        <#elseif childItem.title??>
                            <vs-mega-nav-list>
                                <vs-mega-nav-list-item slot="navListHeading">
                                    ${childItem.title}
                                </vs-mega-nav-list-item>

                                <#list childItem.childMenuItems as thirdChildItem>
                                    <#if thirdChildItem.title??>
                                        <vs-mega-nav-list-item
                                            slot="navListItems"
                                            href="${getUrl(thirdChildItem)}"
                                        >
                                            ${thirdChildItem.title}
                                        </vs-mega-nav-list-item>
                                    </#if>
                                </#list>
                                <#if childItem.cta?? && childItem.hstLink??>
                                    <vs-mega-nav-list-item
                                        href="${getUrl(childItem)}"
                                        subheading-link
                                        slot="navHeadingCtaLink"
                                    >
                                        ${childItem.cta}
                                    </vs-mega-nav-list-item>
                                </#if>
                            </vs-mega-nav-list>
                        </#if>
                    </#list>
                </template>

                <template slot="navFeaturedItem">
                    <!-- featured item component -->
                    <!-- event item component -->
                </template>
            </vs-mega-nav-top-menu-item>
        </#if>
    </#list>

</#macro>