<#include "../../../../include/imports.ftl">

<#macro icentre module>
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
            For more friendly advice visit one of our tourist information iCentres in the area:
            <#list module.iCentreList as iCentre>
                <vs-link href="${iCentre.link}">${iCentre.label}</vs-link>,
            </#list>
        </vs-col>
    </vs-row>

</#macro>