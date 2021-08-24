<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-iknow-community.ftl">
<#include "../../../../frontend/components/vs-tag.ftl">
<#include "../../../../frontend/components/vs-link.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.CannedSearchModule" -->

<#macro cannedSearch module themeName="">
    <vs-module-wrapper class="theme-${themeName}">
        <template slot="vsModuleWrapperHeading">
            ${module.title}
            <@hst.html hippohtml=module.description/>

            <vs-button  href="${module.viewAllLink.link}">
                ${module.viewAllLink.label}
            </vs-button>


            ${module.cannedSearchEndpoint}

            ${module.credit}
        </template>

    </vs-module-wrapper>
</#macro>