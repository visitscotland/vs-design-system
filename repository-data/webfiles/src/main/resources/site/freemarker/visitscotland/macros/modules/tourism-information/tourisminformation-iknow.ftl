<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-link.ftl">
<#include "../../../../frontend/components/vs-heading.ftl">
<#include "../../../../frontend/components/vs-iknow-partner-item.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">

<#macro iknow module themeName="">
    <vs-module-wrapper theme="<#if themeName?has_content>${themeName}<#else>light</#if>">
        <span slot="vsModuleWrapperHeading">
            ${module.title}
        </span>
        <vs-container>
            <vs-iknow-partner-item>
                <span slot="iknowText">
                    <@hst.html hippohtml=module.description/>
                </span>
                <span slot="iknowCta">
                    <vs-link
                        href="${module.link.link}"
                        <#if module.link.type != "internal">type="${module.link.type}"</#if>
                    >
                        ${module.link.label}
                    </vs-link>
                </span>
            </vs-iknow-partner-item>
        </vs-container>
    </vs-module-wrapper>

</#macro>