<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-megalinks.ftl">
<#include "./multi-image/megalinks-multi-image.ftl">
<#include "./link-list/megalinks-link-list.ftl">

<#macro megalinks item type>
    <#if item.cta??>
        <#assign ctaExists = "true" />
    <#else>
        <#assign ctaExists = "false" />
    </#if>

    <#if type=="MultiImageLinksModule">
        <#assign variant = "multi-image">
    <#elseif type=="ListLinksModule">
        <#assign variant = "link-list">
    <#elseif type=="SingleImageLinksModule">
        <#assign variant = "single-image">
    </#if>
    <#assign showTeaser = item.teaserVisible?string('true', 'false') />
    
    <vs-megalinks variant="${variant}" title="${item.title}" <#if ctaExists == "true">button-link="${item.cta.link}"</#if>>
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
            <@multiImage item=item showTeaser=showTeaser/>

        <#elseif type == "ListLinksModule">
            <@linkList item=item showTeaser=showTeaser />

        <#--  <#elseif type == "SingleImageLinksModule">
            <@multiImage item=item />   -->
        </#if>

       <#if ctaExists == "true">                
            <template slot="vsMegalinksButton">
                ${item.cta.label}
            </template>
        </#if>
         
    </vs-megalinks>
</#macro>
