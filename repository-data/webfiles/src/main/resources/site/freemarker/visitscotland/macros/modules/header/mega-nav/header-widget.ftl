<#include "../../../../../include/imports.ftl">
<#include "header-featured-item.ftl">

<#macro headerWidget menu mobile=false>
    <#switch menu.type>
        <#case "FeaturedItem">
            <#list menu.links as link>
                <#-- TODO: This if condition might not be necessary -->
                <#if mobile >
                    <@headerFeaturedItem link link?index true />
                <#else>
                    <@headerFeaturedItem link link?index false />
                </#if>
            </#list>
            <#break>
        <#default>
            <!-- Unrecognized Navigation Widget -->
    </#switch>

</#macro>