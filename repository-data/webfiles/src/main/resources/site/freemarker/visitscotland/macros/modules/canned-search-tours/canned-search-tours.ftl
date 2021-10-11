<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-tag.ftl">
<#include "../../../../frontend/components/vs-link.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">
<#include "../../../../frontend/components/vs-button.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.CannedSearchToursModule" -->

<#macro cannedSearchTours module themeName="">
    <h1>${module.title}</h1>
    <p><@hst.html hippohtml=module.copy /></p>
    <p>DMS tours call = ${module.dmsApiUrl}</p>
    <p>productType = ${module.productType}</p>

    <vs-button
            href="${module.viewAllLink.link}">
        ${module.viewAllLink.label}
    </vs-button>

</#macro>