<#include "../../../../../include/imports.ftl">
<#include "../../../../../frontend/components/vs-megalink-multi-image.ftl">
<#include "./multi-image-featured.ftl">
<#include "./multi-image-two-items.ftl">
<#include "./multi-image-three-items.ftl">

<#macro intro>

</#macro>

<#macro multiImage item>
    <vs-row>
        <vs-col
            cols="10"
            class="offset-1"
        >
                <vs-row>
                    <#-- if there's at least one featured link -->
                    <#if item.featuredLinks?size gt 0>
                        <@multiImageFeatured lastFeatured='false' feature=item.featuredLinks[0] />
                    </#if>

                    <#list item.links as megalink>
                        <#-- 2 and 4 links will be displayed in 2 columns -->
                        <#if item.links?size == 2 || item.links?size == 4>
                            <@multiImageTwoItems megalink=megalink />
                        </#if>
                        <#-- 3 and 6 links will be displayed in 3 columns -->
                        <#if item.links?size == 3 || item.links?size == 6>
                            <@multiImageThreeItems megalink=megalink />
                        </#if>
                        <#-- 5 links will be displayed in a combintation of 2 and 3 columns -->
                        <#if item.links?size == 5>
                            <#if megalink?index lt 3>
                                <@multiImageThreeItems megalink=megalink />
                            <#else>
                                <@multiImageTwoItems megalink=megalink />
                            </#if>
                        </#if>
                    </#list>

                    <#-- if there's a second featured link -->
                    <#if item.featuredLinks?size gt 1>
                        <@multiImageFeatured  lastFeatured='true' feature=item.featuredLinks[1] />
                    </#if>
                </vs-row>
        </vs-col>
    </vs-row>

    <#--  <#list item.featuredLinks as feature>
        <#if feature.image.cmsImage??>
            <#assign image>
                <@hst.link hippobean=feature.image.cmsImage.original/>
            </#assign>
        <#else>
            <#assign image>
                ${feature.image.externalImage}
            </#assign>
        </#if>

        <vs-row>
            <vs-link href="${feature.link}">
                <vs-col cols="6" lg="6" offset-lg="1">
                    <vs-img alt="${(feature.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                            src="${image}">
                    </vs-img>
                </vs-col>
                <vs-col cols="3" lg="3" offset-lg="1">
                    <vs-heading level="3">${feature.label}</vs-heading>
                    <#if item.teaserVisible == true >
                        ${feature.teaser}
                    </#if>
                    </br>
                </vs-col>
            </vs-link> </br>
        </vs-row>
    </#list>

    <vs-row>
        <#list item.links as megalink>
            <#if megalink.image.cmsImage??>
                <#assign image>
                    <@hst.link hippobean=megalink.image.cmsImage.original/>
                </#assign>
            <#else>
                <#assign image>
                    ${megalink.image.externalImage}
                </#assign>
            </#if>

            <vs-col cols="4" lg="4">
                    <vs-megalink linkType="external" url="ajfasd" featured>
                        <slot name="iamge">
                            <vs-img alt="${(feature.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                            src="${image}">
                            </vs-img>
                        </slot>

                        <slot name="heading">
                            Heading here
                        </slot>



                    <vs-img alt="${(feature.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                            src="${image}">
                    </vs-img>
                    <vs-link class="ml-6 mb-2" external href="https://www.visitscotland.com">
                        <vs-heading class="megalink__heading" level="2">${megalink.label}</vs-heading>
                    </vs-link>
                    <#if item.teaserVisible == true >
                        ${megalink.teaser}
                    </#if>
                    </br>
            </vs-col>

        </#list>
        <#if item.cta??>
            <vs-button animate=false background="purple" variant="primary" href="${item.cta.link}" size="md">
                ${item.cta.label}
            </vs-button>
        </#if>
    </vs-row>  -->

</#macro>