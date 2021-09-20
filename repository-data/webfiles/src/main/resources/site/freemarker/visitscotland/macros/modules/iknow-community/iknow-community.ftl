<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-iknow-community.ftl">
<#include "../../../../frontend/components/vs-tag.ftl">
<#include "../../../../frontend/components/vs-link.ftl">
<#include "../../../../frontend/components/vs-module-wrapper.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.IKnowCommunityModule" -->

<#macro iknowCommunity module>
    <vs-module-wrapper theme="<#if themeName?has_content>${themeName}<#else>light</#if>">
        <template slot="vsModuleWrapperHeading">
            ${module.title}
        </template>
        <vs-iknow-community>
            <template slot="iknowCommunityText">
                <@hst.html hippohtml=module.copy/>
            </template>
            <template slot="iknowCommunityLink">
                <vs-link
                    href="${module.link.link}"
                    <#if module.link.type != "internal">type="${module.link.type}"</#if>
                >
                    ${module.link.label}
                </vs-link> <br />
            </template>
            <template slot="iknowCommunityTags">
                <#list module.tags as tag>
                    <vs-tag
                        href="${tag.link}"
                    >
                        ${tag.label}
                    </vs-tag>
                </#list>
            </template>
        </vs-iknow-community>
    </vs-module-wrapper>
</#macro>