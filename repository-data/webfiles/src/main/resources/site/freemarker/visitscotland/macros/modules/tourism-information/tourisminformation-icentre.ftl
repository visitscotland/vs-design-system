<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-image-with-caption.ftl">
<#include "../../global/image-with-caption.ftl">

<#macro icentre module>
    <@hst.manageContent hippobean=module.hippoBean />
    <#if module.image.cmsImage??>
        <#assign image>
            <@hst.link hippobean=module.image.cmsImage.original/>
        </#assign>
    <#else>
        <#assign image = module.image.externalImage!'' />
    </#if>

    <#if module.quoteImage??>
        <#assign imageQuote>
            <@hst.link hippobean=module.quoteImage.cmsImage.original/>
        </#assign>
    </#if>
    <#if !module.quote??>
        <vs-row class="mb-6">
            <vs-col cols="12" lg="8" offset-lg="1">
                <vs-rich-text-wrapper variant="lead">
                    ${module.description}
                    <#list module.links as iCentre>
                        <vs-link href="${iCentre.link}">${iCentre.label}</vs-link>,
                    </#list>
                </vs-rich-text-wrapper>
            </vs-col>
        </vs-row>
        <vs-row>
            <vs-col cols="12" lg="6" offset-lg="3">
                <@imageWithCaption imageSrc=image imageDetails=module.image variant="fullwidth"/>
            </vs-col>
        </vs-row>
    <#else>
        <vs-row>
            <vs-col cols="6" lg="6" >
                <@imageWithCaption imageSrc=image imageDetails=module.image variant="fullwidth"/>
            </vs-col>

            <vs-col cols="6" lg="5">
                <vs-row>
                    <vs-col cols="3" lg="3">
                        <vs-img alt="${(module.quoteImage)!'${label("essentials.global", "default.alt-text")}'}"
                                src="${imageQuote}">
                        </vs-img>
                    </vs-col>
                    <vs-col cols="6" lg="6">
                        "<@hst.html hippohtml=module.quote/>"
                    </vs-col>
                </vs-row>

                <vs-heading level="6">${module.quoteAuthorName}</vs-heading>
                ${module.quoteAuthorTitle}
                </br> </br>
                <#if module.quoteLink??>
                    <vs-button animate=false background="purple" variant="primary" href="${module.quoteLink.link}" size="md">
                        ${module.quoteLink.label}
                    </vs-button>
                </#if>
            </vs-col>

            </br>
            <vs-col cols="8" lg="7" offset-lg="6">
                ${module.description}
                <#list module.links as iCentre>
                    <vs-link href="${iCentre.link}">${iCentre.label}</vs-link>,
                </#list>
            </vs-col>
        </vs-row>
    </#if>



</#macro>