<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-megalinks.ftl">
<#include "./multi-image/megalinks-multi-image.ftl">

<#macro megalinks item type>
    <#if item.cta??>
        <#assign ctaExists = "true" />
    <#else>
        <#assign ctaExists = "false" />
    </#if>

    
    <vs-megalinks <#if ctaExists == "true">button-link="${item.cta.link}"</#if>>
        <@hst.manageContent hippobean=item.megalinkItem />

        <template slot="vsMegalinksHeading">
            ${item.title}
        </template>
        <vs-rich-text-wrapper
            variant="lead"
            slot="vsMegalinksIntro"
            class="mt-6"
        >
            <@hst.html hippohtml=item.introduction/>
        </vs-rich-text-wrapper>
        <#if type == "MultiImageLinksModule">
            <@multiImage item=item /> 
        </#if>

        <#if type == "SingleImageLinksModule">
            <@multiImage item=item /> 
        </#if>

        <#if ctaExists == "true">
            <template slot="vsMegalinksButton">
                ${item.cta.label}
            </template>
        </#if>
    </vs-megalinks>
</#macro>
