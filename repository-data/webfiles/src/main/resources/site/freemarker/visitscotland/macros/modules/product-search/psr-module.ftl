<#include "../../../../include/imports.ftl">

<#include "../../../../frontend/components/vs-psr-module.ftl">


<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.CannedSearchModule" -->

<#macro productSearchWidget module language >
    <#assign isPolygon = (module.location.type == "POLYGON")>
    <vs-psr-module
            :config-arr="[
            {'subSearchType': '${module.category.productTypes}'},
            {'type': '${module.location.type}'},
            <#if module.location??>
                {'${isPolygon?then('locpoly', 'locplace')}': '${module.location.key}'},
            </#if>
            {'searchUrl' : '${module.searchUrl}'},
            {'urls': [
            <#list module.supportingURLs as type, url>
                {'${type}' : '${url}'},
            </#list>
            ]},
            {'lang':'${language}'},
        ]"
    >
        <template slot="vsModuleHeading">
            ${module.title}
        </template>

        <template slot="vsModuleIntro">
            ${module.description}
        </template>
    </vs-psr-module>
</#macro>