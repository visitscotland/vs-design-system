<#macro cmsErrors errors editMode>
    <#if errors?? && errors?size gt 0 && editMode>
        <#list errors as error>
            <h1 class="text-danger">${error?upper_case}</h1>
        </#list>
    </#if>
</#macro>