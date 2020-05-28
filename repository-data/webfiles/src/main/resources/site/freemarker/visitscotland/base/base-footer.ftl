<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/footer.ftl">
<#include "../../vs-dotcom-ds/components/footer-nav-list.ftl">
<#include "../../vs-dotcom-ds/components/container.ftl">
<#include "../../vs-dotcom-ds/components/row.ftl">
<#include "../../vs-dotcom-ds/components/col.ftl">

<#include "../macros/modules/footer/footer-accordion-item.ftl">
<#include "../macros/modules/footer/footer-social-menu.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->

<vs-footer class="has-edit-button">
    <#if enhancedMenu??>
        <vs-footer-nav-list break-point="md">
            <@footerAccordionItem footerMenuItems=enhancedMenu />
               
            <vs-col cols="12" lg="3" xl="2" class="d-none d-lg-block">
                <@footerSocialMenu />
            </vs-col>
        </vs-footer-nav-list>

        <div class="border-top border-secondary-light">
            <vs-container class="vs-footer-social-menu__wrapper">
                <vs-row>
                    <vs-col cols="12" class="d-block d-lg-none">
                        <@footerSocialMenu />
                    </vs-col>
                </vs-row>
            </vs-container>
        </div>
        <@hst.cmseditmenu menu=menu/>
        <#--  <@hst.include ref="utility"/>  -->
    </#if>
</vs-footer>