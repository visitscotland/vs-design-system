<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/footer-nav-list.ftl">
<#include "../../vs-dotcom-ds/components/footer-nav-list-item.ftl">
<#include "../../vs-dotcom-ds/components/footer.ftl">
<#include "../../vs-dotcom-ds/components/icon.ftl">
<#include "../../vs-dotcom-ds/components/accordion-item.ftl">
<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">
<#include "../../vs-dotcom-ds/components/list.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->

<vs-footer class="has-edit-button">
    <#if enhancedMenu??>

            <vs-footer-nav-list :responsive="true" break-point="md">
                <vs-row>
                    <#list enhancedMenu as item>
                        <vs-col cols="12" md="4" lg="3">
                            <vs-accordion-item 
                                :visible="false" 
                                variant="dark" 
                                index="${item?index}" 
                                class="<#if item?index == 0>border-left-0 pl-md-0</#if>"
                            >
                                <span slot="title">
                                    <#if !item.hstLink?? && !item.externalLink??>
                                        ${item.title?html}
                                    <#else>
                                        <#if item.hstLink??>
                                            <#assign href><@hst.link link=item.hstLink/></#assign>
                                        <#elseif item.externalLink??>
                                            <#assign href>${item.externalLink?replace("\"", "")}</#assign>
                                        </#if>
                                    </#if>
                                </span>

                                <span slot="icon-open">
                                    <vs-icon name="chevron-up" variant="light" size="xs" />
                                </span>

                                <span slot="icon-closed">
                                    <vs-icon name="chevron-right" variant="light" size="xs" />
                                </span>

                                <vs-list unstyled class="pb-2">
                                    <#list item.childMenuItems as children>
                                        <#assign href = "">
                                        <#assign external = false>

                                        <#if children.hstLink??>
                                            <#assign href><@hst.link link=children.hstLink/></#assign>
                                        <#elseif children.externalLink??>
                                            <#assign href>${children.externalLink}</#assign>
                                            <#assign external = true>
                                        </#if>

                                        <vs-footer-nav-list-item
                                            href="${href}"
                                            link-text="${children.title}"
                                            :external="<#if external>true<#else>false</#if>"
                                        ></vs-footer-nav-list-item>
                                    </#list>
                                </vs-list>
                            </vs-accordion-item>
                        </vs-col>
                    </#list>
                </vs-row>
            </vs-footer-nav-list>
        <@hst.cmseditmenu menu=menu/>
        <#--  <@hst.include ref="utility"/>  -->
    </#if>
</vs-footer>