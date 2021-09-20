<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-iknow-community.ftl">
<#include "../../../../frontend/components/vs-tag.ftl">
<#include "../../../../frontend/components/vs-link.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">
<#include "../../../../frontend/components/vs-canned-search.ftl">
<#include "../../../../frontend/components/vs-button.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.CannedSearchModule" -->

<#macro cannedSearch module themeName="">
    <vs-module-wrapper theme="<#if themeName?has_content>${themeName}<#else>light</#if>">
        <template slot="vsModuleWrapperHeading">
            ${module.title}
        </template>

        <template slot="vsModuleWrapperIntro">
            <@hst.html hippohtml=module.description/>
        </template>

        <vs-canned-search
            api-url="${module.cannedSearchEndpoint}"
        >
            <template slot="vsCannedSearchButtons">
                <vs-button
                    href="${module.viewAllLink.link}">
                    ${module.viewAllLink.label}
                </vs-button>
            </template>

        </vs-canned-search>
    </vs-module-wrapper>
</#macro>