<#include "../../../../include/imports.ftl">

<#macro multiImage item>

    <#list item.featuredLinks as feature>
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
        </br>
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
                <vs-link href="${megalink.link}">
                    <vs-img alt="${(feature.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                            src="${image}">
                    </vs-img>
                    <vs-heading level="3">${megalink.label}</vs-heading>
                    <#if item.teaserVisible == true >
                        ${megalink.teaser}
                    </#if>
                    </br>
                </vs-link>
            </vs-col>

        </#list>
        <#if item.cta??>
            <vs-button animate=false background="purple" variant="primary" href="${item.cta.link}" size="md">
                ${item.cta.label}
            </vs-button>
        </#if>
    </vs-row>

</#macro>