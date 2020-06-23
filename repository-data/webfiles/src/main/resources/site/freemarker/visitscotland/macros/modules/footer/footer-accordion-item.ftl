<#include "../../../../include/imports.ftl">
<#include "../../../../vs-dotcom-ds/components/footer-accordion-item.ftl">
<#include "../../../../vs-dotcom-ds/components/footer-nav-list-item.ftl">
<#include "../../../../vs-dotcom-ds/components/icon.ftl">
<#include "../../../../vs-dotcom-ds/components/list.ftl">

<#macro footerAccordionItem footerMenuItems>
    <#list footerMenuItems as menuItem>
        <vs-col cols="12" md="4" lg="3">
            <vs-footer-accordion-item
                :open-by-default="false" 
                variant="dark" 
                control-id="footer_accordion_item_${menuItem?index}" 
                class="<#if menuItem?is_first>border-left-0 pl-md-0</#if> <#if menuItem?is_last>border-bottom-0</#if>"
            >
                <span slot="title">
                    <#if menuItem.title?has_content>
                        <#if !menuItem.hstLink?? && !menuItem.externalLink??>
                            ${menuItem.title?html}
                        <#else>
                            <#if menuItem.hstLink??>
                                <#assign href><@hst.link link=menuItem.hstLink/></#assign>
                            <#elseif menuItem.externalLink??>
                                <#assign href>${menuItem.externalLink?replace("\"", "")}</#assign>
                            </#if>
                        </#if>
                    </#if>
                </span>

                <span slot="icon-open">
                    <vs-icon name="chevron-up" variant="light" size="xs"></vs-icon>
                </span>

                <span slot="icon-closed">
                    <vs-icon name="chevron-right" variant="light" size="xs"></vs-icon>
                </span>

                <vs-list unstyled>
                    <#list menuItem.childMenuItems as childItem>
                        <#assign href = "">
                        <#assign external = false>

                        <#if childItem.title?has_content>
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
                </vs-list>
            </vs-footer-accordion-item>
         </vs-col>
    </#list>
</#macro>