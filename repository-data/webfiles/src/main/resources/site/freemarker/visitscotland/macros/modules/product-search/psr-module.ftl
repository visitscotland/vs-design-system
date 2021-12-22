<#include "../../../../include/imports.ftl">

<#include "../../../../frontend/components/vs-psr-module.ftl">


<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.CannedSearchModule" -->

<#macro productSearchWidget module language >
    <#--  search results link = ${module.searchUrl}parameters  -->
    <#assign isPolygon = (module.location.type == "POLYGON")>
    <vs-psr-module
            :config-arr="[
            {'subSearchType': '${module.category.productTypes}'},
            {'type': '${module.location.type}'},
            {'${isPolygon?then('locpoly', 'locplace')}': '${module.location.key}'},
            <#if module.location??>
                <#--  {'loc': '${module.location.name}'},  -->
            </#if>
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