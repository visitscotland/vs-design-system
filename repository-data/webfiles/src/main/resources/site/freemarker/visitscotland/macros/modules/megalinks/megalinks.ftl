<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-megalinks.ftl">
<#include "../module-intro/module-intro.ftl">
<#include "./multi-image/megalinks-multi-image.ftl">

<#macro megalinks item type>
    <div class="has-edit-button">
        <vs-megalinks button-link="${item.cta.link}">
            <@hst.manageContent hippobean=item.megalinkItem />
            <#-- TO DO: move the intro to a macro -->
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

            <template slot="vsMegalinksButton">
                 ${item.cta.label}
            </template>
        </vs-megalinks>
    </div>
</#macro>
