<#include "../../include/imports.ftl">
<#include "../../frontend/components/vs-footer.ftl">
<#include "../../frontend/components/vs-footer-nav-list.ftl">
<#include "../../frontend/components/vs-container.ftl">
<#include "../../frontend/components/vs-row.ftl">
<#include "../../frontend/components/vs-col.ftl">

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