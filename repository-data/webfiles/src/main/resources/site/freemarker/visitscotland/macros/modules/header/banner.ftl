<#include "../../../../include/imports.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.BannerModule"-->
<#macro emergencyBanner module>
    <h1> ${module.title} </h1>
    <@hst.html hippohtml=module.copy />
    <vs-link
        href="${module.ctaLink.link}"
         <#if module.ctaLink.type != "internal">type="${module.ctaLink.type}"</#if>>
        ${module.ctaLink.label}
    </vs-link>
</#macro>
