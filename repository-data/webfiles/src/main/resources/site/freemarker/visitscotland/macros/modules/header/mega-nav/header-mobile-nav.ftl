<#include "../../../../../include/imports.ftl">
<#include "../../../../../frontend/components/vs-accordion.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-accordion-item.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-list.ftl">
<#include "../../../../../frontend/components/vs-mega-nav-list-item.ftl">

<#macro headerMobileNav menu=menu>
    <vs-accordion>
        <#list menu.siteMenuItems as item>
            <#if item.title?has_content>
                <vs-mega-nav-accordion-item
                    title="${item.title}"
                    level="1"
                    control-id="${item?index}"
                    :is-last="<#if item?is_last>true<#else>false</#if>"
                >
                    <#if item.cta?? && item.hstLink??>
                        <vs-mega-nav-list>
                            <vs-mega-nav-list-item
                                slot="navListHeading"
                                href="${getUrl(item)}"
                                cta-link
                            >
                                ${item.cta}
                            </vs-mega-nav-list-item>
                        </vs-mega-nav-list>
                    </#if>

                    <#list item.childMenuItems as childItem>
                        <#if childItem.title??>
                            <vs-mega-nav-accordion-item
                                title="${childItem.title}"
                                level="2"
                                control-id="${childItem?index}"
                                :is-last="<#if childItem?is_last>true<#else>false</#if>"
                            >
                                <vs-mega-nav-list>
                                    <#list childItem.childMenuItems as thirdChildItem>
                                        <#if thirdChildItem.title??>
                                            <vs-mega-nav-list-item
                                                slot="navListItems"
                                                href="${getUrl(thirdChildItem)}"
                                                :is-last="<#if thirdChildItem?is_last && !childItem.cta?? && !childItem.hstLink??>true<#else>false</#if>"
                                            >   
                                                ${thirdChildItem.title}
                                            </vs-mega-nav-list-item>
                                        </#if>
                                    </#list>

                                    <#if childItem.cta?? && childItem.hstLink??>
                                        <vs-mega-nav-list-item
                                            href="${getUrl(childItem)}"
                                            subheading-link
                                            :is-last="true"
                                            slot="navHeadingCtaLink"
                                        >
                                            ${childItem.cta}
                                        </vs-mega-nav-list-item>
                                    </#if>
                                </vs-mega-nav-list>
                            </vs-mega-nav-accordion-item>
                        </#if>
                    </#list>  
                </vs-mega-nav-accordion-item>
            </#if>
        </#list>
    </vs-accordion>
</#macro>