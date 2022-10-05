<#include "../../../../include/imports.ftl">
<#include "../../global/preview-warning.ftl">


<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.MapsModule" -->
<#-- @ftlvariable name="editMode" type="java.lang.Boolean"-->

<#macro map module>
    <@hst.manageContent hippobean=module.hippoBean />
    <@previewWarning editMode module module.errorMessages />

        ${module.title}

        <@hst.html hippohtml=module.introduction/>

        ${module.tabTitle}
</#macro>