<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-image-with-caption.ftl">
<#include "../../global/image-with-caption.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.SignpostModule" -->
<#macro signpost module themeName="">
    <vs-module-wrapper class="theme-${themeName}">
        <span slot="vsModuleWrapperHeading">
            ${module.title}
        </span>
        <vs-container>
            <vs-iknow-partner-item>
                <span slot="iknowText">
                    <@hst.html hippohtml=module.copy/>
                </span>
                <span slot="iknowCta">
                    <vs-link href="${module.cta.link}">${module.cta.label}</vs-link>
                </span>
            </vs-iknow-partner-item>
        </vs-container>
    </vs-module-wrapper>
</#macro>