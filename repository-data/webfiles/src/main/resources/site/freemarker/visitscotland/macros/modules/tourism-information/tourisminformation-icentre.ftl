<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-icentre.ftl">
<#include "../../../../frontend/components/vs-quote.ftl">
<#include "../../../../frontend/components/vs-img.ftl">
<#include "../../global/image-with-caption.ftl">
<#include "../../global/quote.ftl">

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

    <#if module.quoteLink??>
        <#assign quoteLink=module.quoteLink />
    </#if>

    <vs-container slot="upper" class="py-lg-4 vs-icentre__container" >
        <vs-icentre>
            <template slot="icentreHeading">
                <vs-heading level="2">${module.title}</vs-heading>
            </template>

            <template slot="icentreImageWithCaption">
                <@imageWithCaption imageSrc=image imageDetails=module.image variant="fullwidth"/>
            </template>

            <template slot="icentreLinks">
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
            </template>

            <#if module.quote??>
                <template slot="icentreQuote">
                    <@quote authorImage=imageQuote content=module.quote authorName=module.quoteAuthorName authorTitle=module.quoteAuthorTitle link=quoteLink />
                </template>
            </#if>
        </vs-icentre>
    </vs-container>

</#macro>
