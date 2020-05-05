<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/footer-nav-list.ftl">
<#include "../../vs-dotcom-ds/components/icon.ftl">
<#include "../../vs-dotcom-ds/components/accordion-item.ftl">
<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->


<#if enhancedMenu??>
    <div class="has-edit-button">
        <vs-container>
            <vs-row>
                <#list enhancedMenu as item>
                    <vs-col cols="12" md="4" lg="3">
                        <vs-footer-nav-list>
                            <vs-accordion-item :visible="false" variant="dark" index="1">
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
                                    <vs-icon name="chevron-down" variant="light" size="xs" />
                                </span>

                                <span slot="icon-closed">
                                    <vs-icon name="chevron-up" variant="light" size="xs" />
                                </span>

                                <ul class="py-3">
                                    <#list item.childMenuItems as children>
                                        <#if children.hstLink?? && children.title?has_content>
                                            <#assign href><@hst.link link=children.hstLink/></#assign>
                                            
                                            <li>
                                                <vs-link  href="${href}">
                                                    ${children.title?html}
                                                </vs-link>
                                            </li>
                                        <#elseif children.externalLink??>
                                            <#assign href>${children.externalLink?replace("\"", "")}</#assign>
                                            <#assign external>true</#assign>
                                            <li>
                                                <vs-link external href="${href}">
                                                    ${children.title?html}
                                                </vs-link>
                                            </li>
                                        </#if>
                                    </#list>
                                </ul>
                            </vs-accordion-item>
                        </vs-footer-nav-list>
                    </vs-col>
                </#list>


            </vs-row>
        </vs-container>
    </div>

    <@hst.cmseditmenu menu=menu/>
    <@hst.include ref="utility"/>
</#if>