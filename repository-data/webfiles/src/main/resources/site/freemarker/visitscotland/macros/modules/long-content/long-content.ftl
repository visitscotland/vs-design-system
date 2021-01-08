<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-image-with-caption.ftl">
<#include "../../global/image-with-caption.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.beans.mapping.LongContentModule" -->
<#-- @ftlvariable name="section" type="com.visitscotland.brxm.beans.mapping.FlatLongContentSection" -->

<#macro longContent module>
    <@hst.manageContent hippobean=module.hippoBean />
    <#if module.image??>
        <#if module.image.cmsImage??>
            <#assign image>
                <@hst.link hippobean=module.image.cmsImage.original/>
            </#assign>
        <#else>
            <#assign image = module.image.externalImage!'' />
        </#if>
    <#else>
        <#assign image = "" />
    </#if>
    <vs-col cols="12" lg="8" offset-lg="2" style="border: 1px solid grey;">
        <#if image?? && image?has_content>
                <@imageWithCaption imageSrc=image imageDetails=module.image variant="fullwidth"/>
        </#if>
        <vs-col offset-lg="4">
            <vs-heading level="2">
                <#if module.anchor?has_content>
                    <span id="${module.anchor}">${module.title}</span>
                <#else>
                    ${module.title}
                </#if>
            </vs-heading>
        </vs-col>
        <@hst.html hippohtml=module.introduction/>

    <#assign i = 0/>
        </br> </br>
        <#list module.sections as section>
            </br>
            </br>
            <vs-row>
                <#if section.quote?? || section.image??>
                    <#assign i = i+1/>
                    <#if i % 2 != 0 >
                    <vs-row>
                        <vs-col cols="4">
                            <#if section.image??>
                                <#if section.image.cmsImage??>
                                    <#assign media>
                                        <@hst.link hippobean=section.image.cmsImage.original/>
                                    </#assign>
                                <#else>
                                    <#assign media = section.image.externalImage!'' />
                                </#if>
                            <@imageWithCaption imageSrc=media imageDetails=section.image variant="fullwidth"/>
                            </#if>
                            <#if section.quote??>
                                <#if section.quoteImage.cmsImage??>
                                    <#assign imageQuote>
                                        <@hst.link hippobean=section.quoteImage.cmsImage.thumbnail/>
                                    </#assign>
                                <#else>
                                    <#assign imageQuote = section.quoteImage.externalImage!'' />
                                </#if>

                            "<@hst.html hippohtml=section.quote/>"
                            <#if imageQuote?? && imageQuote?has_content>
                                <vs-col cols="3" offset-lg="1">
                                    <vs-img alt="${(section.quoteImage)!'${label("essentials.global", "default.alt-text")}'}"
                                            src="${imageQuote}">
                                    </vs-img>
                                </vs-col>
                            </#if>
                            <vs-heading level="6">${section.quoteAuthorName}</vs-heading>
                            ${section.quoteAuthorTitle}
                            </#if>
                        </vs-col>

                        <vs-col cols="8" >
                            <@hst.html hippohtml=section.copy/>
                        </vs-col>
                    </vs-row>
                    <#else>
                        <vs-row>

                            <vs-col cols="8" >
                                <@hst.html hippohtml=section.copy/>
                            </vs-col>
                            <vs-col cols="4">
                                <#if section.image??>
                                    <#if section.image.cmsImage??>
                                        <#assign media>
                                            <@hst.link hippobean=section.image.cmsImage.original/>
                                        </#assign>
                                    <#else>
                                        <#assign media = section.image.externalImage!'' />
                                    </#if>
                                    <@imageWithCaption imageSrc=media imageDetails=section.image variant="fullwidth"/>
                                </#if>
                                <#if section.quote??>
                                    <#if section.quoteImage.cmsImage??>
                                        <#assign imageQuote>
                                            <@hst.link hippobean=section.quoteImage.cmsImage.thumbnail/>
                                        </#assign>
                                    <#else>
                                        <#assign imageQuote = section.quoteImage.externalImage!'' />
                                    </#if>

                                    "<@hst.html hippohtml=section.quote/>"
                                    <#if imageQuote?? && imageQuote?has_content>
                                        <vs-col cols="3" offset-lg="1">
                                            <vs-img alt="${(section.quoteImage)!'${label("essentials.global", "default.alt-text")}'}"
                                                    src="${imageQuote}">
                                            </vs-img>
                                        </vs-col>
                                    </#if>
                                    <vs-heading level="6">${section.quoteAuthorName}</vs-heading>
                                    ${section.quoteAuthorTitle}
                                </#if>
                            </vs-col>

                        </vs-row>
                    </#if>
                <#else>
                    </br> </br>
                        <@hst.html hippohtml=section.copy/>
                </#if>
            </vs-row>

            </#list>
    </vs-col>
    </br> </br>
</#macro>