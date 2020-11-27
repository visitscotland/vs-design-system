<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-image-with-caption.ftl">
<#include "../../global/image-with-caption.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brmx.beans.mapping.LongContentModule" -->
<#-- @ftlvariable name="section" type="com.visitscotland.brmx.beans.mapping.FlatLongContentSection" -->

<#macro longContent module>

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

    <#list module.sections as section>

        Long Content Module
        <@hst.html hippohtml=section.copy/>

        <#if section.quote??>
            <vs-row>
                <vs-col cols="6" lg="6" >
                    <@imageWithCaption imageSrc=image imageDetails=section.image variant="fullwidth"/>
                </vs-col>

                <vs-col cols="6" lg="5">
                    <vs-row>
                        <vs-col cols="3" lg="3">
                            <vs-img alt="${(section.quoteImage)!'${label("essentials.global", "default.alt-text")}'}"
                                    src="${imageQuote}">
                            </vs-img>
                        </vs-col>
                        <vs-col cols="6" lg="6">
                            "<@hst.html hippohtml=section.quote/>"
                        </vs-col>
                    </vs-row>

                    <vs-heading level="6">${section.quoteAuthorName}</vs-heading>
                    ${section.quoteAuthorTitle}
                    </br> </br>
                    <#if section.quoteLink??>
                        <vs-button animate=false background="purple" variant="primary" href="${section.quoteLink.link}" size="md">
                            ${section.quoteLink.label}
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
    </#list>





</#macro>