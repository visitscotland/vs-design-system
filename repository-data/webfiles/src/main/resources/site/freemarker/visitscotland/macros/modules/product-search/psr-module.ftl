<#include "../../../../include/imports.ftl">

<#include "../../../../frontend/components/vs-psr-module.ftl">

<#include "../../shared/theme-calculator.ftl">


<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.PSModule" -->

<#macro productSearchWidget module="" isAboveContent=true >
    <#if module?has_content>

        <vs-psr-module
                :config-arr="[
                {'subSearchType': '${module.category.productTypes}'},
                <#if module.location??>
                    <#assign isPolygon = (module.location.type == "POLYGON")>
                    {'type': '${module.location.type}'}, <#-- TODO: Is this parameter required -->
                    {'${isPolygon?then('locpoly', 'locplace')}': '${module.location.key}'},
                </#if>
                {'domain' : '${module.domain}'},
                {'lang':'${locale[0..1]}'},
            ]"
        >
            <template slot="vsModuleHeading">
                ${module.title}
            </template>

            <template slot="vsModuleIntro">
                ${module.description}
            </template>
        </vs-psr-module>
    </#if>

    <#if isAboveContent>
        <#if themeCalculator(introTheme) != "light">
            <br>SPACE<br>
        </#if>
    </#if>
</#macro>