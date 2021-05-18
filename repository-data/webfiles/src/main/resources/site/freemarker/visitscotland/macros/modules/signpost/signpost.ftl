<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-image-with-caption.ftl">
<#include "../../global/image-with-caption.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.SignpostModule" -->
<#macro signpost module>
    <h1>${module.title}</h1>
    <@hst.html hippohtml=module.copy/>
    <vs-link href="${module.cta.link}">${module.cta.label}</vs-link> <br />
</#macro>