<#include "../../../../include/imports.ftl">

<#macro list item>
  <ol>
    <vs-row>
        <#list item.links as megalink>
            <#if megalink.image.cmsImage??>
                <#assign image>
                    <@hst.link hippobean=megalink.image.cmsImage.original/>
                </#assign>
            <#else>
                <#assign image = megalink.image.externalImage!'' />
            </#if>
            <vs-col cols="5" lg="5">
                <vs-link href="${megalink.link}">
                    <vs-row>
                        <vs-col >
                            <vs-img alt="${(megalink.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                                    src="${image}">
                            </vs-img>
                        <vs-heading level="3">${megalink.label}</vs-heading>
                        <#if item.teaserVisible == true >
                            ${megalink.teaser}
                        </#if>
                        </vs-col>
                        </br></br>
                    </vs-row>
                </vs-link> </br>
            </vs-col>
        </#list>

    </vs-row>
    <#if item.cta??>
        <vs-button animate=false background="purple" variant="primary" href="${item.cta.link}" size="md">
            ${item.cta.label}
        </vs-button>
    </#if>
</ol>

</#macro>