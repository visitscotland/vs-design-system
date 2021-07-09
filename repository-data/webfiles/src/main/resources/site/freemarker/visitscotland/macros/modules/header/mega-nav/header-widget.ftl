<#include "../../../../../include/imports.ftl">
<#include "header-featured-item.ftl">

<#macro headerWidget menu mobile=false>
    <#switch menu.type>
        <#case "FeaturedItem">
            <#list menu.links as link>
                <@headerFeaturedItem link link?index mobile />
            </#list>
            <#break>
        <#default>
    </#switch>
</#macro>