<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-icentre.ftl">
<#include "../../../../frontend/components/vs-quote.ftl">
<#include "../../../../frontend/components/vs-img.ftl">
<#include "../../global/image-with-caption.ftl">

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

    <vs-container slot="upper" class="py-lg-4" >
        <vs-icentre>
            <span slot="icentreHeading">
                <vs-heading level="2">${module.title}</vs-heading>
            </span>

            <span slot="icentreImageWithCaption">
                <@imageWithCaption imageSrc=image imageDetails=module.image variant="fullwidth"/>
            </span>

            <span slot="icentreLinks">
                ${module.description}
                <#list module.links as iCentre>
                    <vs-link href="${iCentre.link}">
                        ${iCentre.label}
                    </vs-link>
                    <#if iCentre?counter == (module.links?size - 1)>
                        &nbsp;and&nbsp;
                    <#else>
                        <#if iCentre?has_next>,&nbsp;</#if>
                    </#if>
                </#list>
                .
            </span>

            <#if module.quote??>
                <vs-quote slot="icentreQuote">
                    <#if module.quoteImage??>
                        <vs-img
                            alt="${(module.quoteImage)!'${label("essentials.global", "default.alt-text")}'}"
                            src="${imageQuote}"
                            slot="quoteImage">
                        </vs-img>
                    </#if>
                    <div slot="quoteContent">
                        <@hst.html hippohtml=module.quote/>
                    </div>
                    <span slot="quoteAuthorName">
                        ${module.quoteAuthorName}
                    </span>
                    <span slot="quoteAuthorTitle">
                        ${module.quoteAuthorTitle}
                    </span>
                    <#if module.quoteLink??>
                        <vs-button
                            href="${module.quoteLink.link}"
                            slot="quoteLink">
                            ${module.quoteLink.label}
                        </vs-button>
                    </#if>
                </vs-quote>
            </#if>
        </vs-icentre>
    </vs-container>

</#macro>
