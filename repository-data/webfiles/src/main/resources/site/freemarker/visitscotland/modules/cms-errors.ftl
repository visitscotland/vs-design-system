<#macro cmsErrors errors>
    <#list errors as error>
        <h1 class="text-danger">${error?upper_case}</h1>
    </#list>
</#macro>