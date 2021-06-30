<#include "../../../../../include/imports.ftl">
<#include "header-featured-item.ftl">

<#macro headerWidget menu mobile=false>
    <#switch menu.type>
        <#case "FeaturedItem">
            <#list menu.links as link>
                <#if mobile >
                    <@headerFeaturedItem link link?index true />
                <#else>
                    <@headerFeaturedItem link link?index false />
                </#if>
            </#list>
            <#break>
        <#default>
    </#switch>
</#macro>