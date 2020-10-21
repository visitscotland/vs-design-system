<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-megalinks.ftl">
<#include "../module-intro/module-intro.ftl">
<#include "./multi-image/megalinks-multi-image.ftl">

<#macro megalinks item type>
    <#--  <div class="has-edit-button" style="background-color:${style}">
        <@hst.manageContent hippobean=item.megalinkItem />
            <vs-row>
                <vs-col cols="10" lg="8" offset-lg="1">
                    <vs-heading level="1">${item.title}</vs-heading>
                </vs-col>
            </vs-row>
            <vs-row class="mb-6">
                <vs-col cols="12" lg="8" offset-lg="1">
                    <vs-rich-text-wrapper variant="lead">
                        <@hst.html hippohtml=item.introduction/>
                    </vs-rich-text-wrapper>
                </vs-col>
            </vs-row>
    </div>  -->
    <div class="has-edit-button">
        <vs-megalinks>
            <@hst.manageContent hippobean=item.megalinkItem />
            <#-- TO DO: move the intro to a macro -->
            <template slot="vs-megalinks-heading">
                ${item.title}
            </template>
            <vs-rich-text-wrapper
                variant="lead"
                slot="vs-megalinks-intro"
                class="mt-6"
            >
                <@hst.html hippohtml=item.introduction/>
            </vs-rich-text-wrapper>
            <#if type== "MultiImageLinksModule">
                <@multiImage item=item /> 
            </#if>
        </vs-megalinks>
    </div>
</#macro>
