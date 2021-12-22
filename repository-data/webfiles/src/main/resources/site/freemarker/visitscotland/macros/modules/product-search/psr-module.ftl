<#include "../../../../include/imports.ftl">

<#include "../../../../frontend/components/vs-psr-module.ftl">
<#include "../../../../frontend/components/vs-psr-embed.ftl">


<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.CannedSearchModule" -->

<#macro productSearchWidget module language >
    <br>
    search results link = ${module.searchUrl}parameters
    <br>
    :configArr="[
    {'subSearchType': '${module.category.productTypes}'},
    <#if module.location??>
    <#--- MODE 1: -->
        <#if module.location.type == "POLYGON">
            {'locpoly': '${module.location.key}'}
        <#else >
            {'loc': '${module.location.key}'},
        </#if>

    <#--- MODE 2: -->
        <#assign isPolygon = (module.location.type == "POLYGON")>
        {'${isPolygon?then('locpoly', 'loc')}': '${module.location.key}'},

    <#--- MODE 3: -->
        {'location': '${module.location.id}'},

    <#--- display name for the combo (Probably not necessary if we select the right option) but it is the current functionality in production -->
        {'displayName': '${module.location.name}'},
    </#if>
    {'lang':'${language}'},
    ]"


    <vs-psr-module
            :config-arr="[
            {'subSearchType': '${module.category.productTypes}'},
            <#if module.location??>
                {'loc': '${module.location.name}'},
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