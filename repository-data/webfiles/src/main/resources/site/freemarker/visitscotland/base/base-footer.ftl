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
        <template slot="accordion-items">
            <@footerAccordionItem footerMenuItems=enhancedMenu />
        </template>

        <template slot="social-menu">      
           <@footerSocialMenu />
        </template>
        
        <@hst.cmseditmenu menu=menu/>

        <@hst.include ref="utility"/>
    </#if>
</vs-footer>