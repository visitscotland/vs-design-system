<#include "../../../../include/imports.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.MarketoFormModule" -->
<#macro marketo form>
    <h1>${form.title}</h1>
    <@hst.html hippohtml=form.copy/>
    <p>${form.noJavaScriptMessage}</p>
    <p>${form.jsonUrl}</p>
</#macro>