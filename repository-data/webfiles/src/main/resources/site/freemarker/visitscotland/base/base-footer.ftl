<#include "../../include/imports.ftl">
<#include "../../frontend/components/vs-footer.ftl">
<#include "../../frontend/components/vs-footer-nav-list.ftl">
<#include "../../frontend/components/vs-container.ftl">
<#include "../../frontend/components/vs-row.ftl">
<#include "../../frontend/components/vs-col.ftl">

<#include "../macros/modules/footer/footer-accordion-item.ftl">
<#include "../macros/modules/footer/footer-copyright.ftl">
<#include "../macros/modules/footer/footer-social-menu.ftl">

<#-- @ftlvariable name="menu" type="com.visitscotland.brmx.components.navigation.RootMenuItem" -->
<#-- @ftlvariable name="item" type="com.visitscotland.brmx.components.navigation.MenuItem" -->

<vs-footer class="has-edit-button">
    <#if menu??>
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