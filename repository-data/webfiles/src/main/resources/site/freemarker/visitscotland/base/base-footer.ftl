<#include "../../include/imports.ftl">
<#include "../../frontend/components/vs-footer.ftl">
<#include "../../frontend/components/vs-footer-nav-list.ftl">
<#include "../../frontend/components/vs-container.ftl">
<#include "../../frontend/components/vs-row.ftl">
<#include "../../frontend/components/vs-col.ftl">

<#include "../macros/modules/footer/footer-accordion-item.ftl">
<#include "../macros/modules/footer/footer-copyright.ftl">
<#include "../macros/modules/footer/footer-social-menu.ftl">

<#-- @ftlvariable name="menu" type="org.hippoecm.hst.core.sitemenu.HstSiteMenu" -->
<#-- @ftlvariable name="enhancedMenu" type="java.util.List" -->
<#-- @ftlvariable name="item" type=""com.visitscotland.www.components.navigation.VsHstSiteMenuItemImpl" -->

<vs-footer class="has-edit-button">
    <#if enhancedMenu??>
        <template slot="accordion-items">
            <@footerAccordionItem footerMenuItems=menu.siteMenuItems />
        </template>

        <template slot="social-menu">      
           <@footerSocialMenu />
        </template>
        
        <@hst.cmseditmenu menu=menu/>

        <@hst.include ref="utility"/>
    </#if>

    <@footerCopyright />
</vs-footer>