<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-image-with-caption.ftl">
<#include "../../global/image-with-caption.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.SignpostModule" -->
<#macro signpost module>
    ${module.title} </br>
    <@hst.html hippohtml=module.copy/></br>
    <vs-link href="${module.cta.link}">${module.cta.label}</vs-link></br>

</#macro>