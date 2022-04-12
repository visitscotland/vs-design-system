<#include "../../../../../include/imports.ftl">
<#include "header-featured-item.ftl">

<#include "../../../global/preview-warning.ftl">

<#macro headerWidget menu accordion=false>
    <#switch menu.type>
        <#case "FeaturedItem">
            <#list menu.links as link>
                <#if link.cta??>
                         <@headerFeaturedItem link link?index accordion />
                <#else>
                    <@previewWarning editMode menu menu.errorMessages/>
                </#if>
            </#list>
            <#break>
        <#default>
    </#switch>
</#macro>