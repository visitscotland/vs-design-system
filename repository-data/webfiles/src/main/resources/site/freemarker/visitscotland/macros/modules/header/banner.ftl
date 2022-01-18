<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-banner.ftl">
<#include "../../../../frontend/components/vs-link.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.BannerModule"-->
<#macro emergencyBanner module>
    <vs-banner>
        <template slot="vsBannerTitle">
            ${module.title} 
        </template>

        <template slot="vsBannerText">
            <@hst.html hippohtml=module.copy />
        </template>

        <template slot="vsBannerCta">
            <vs-link 
                href="${module.ctaLink.link}"
                <#if module.ctaLink.type != "internal">type="${module.ctaLink.type}"</#if>
            >
                ${module.ctaLink.label}
            </VsLink>
        </template>

        <template slot="closeBtnText">
            Close
        </template>
    </vs-banner>
</#macro>
