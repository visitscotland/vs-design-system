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
    <vs-col cols="12" lg="8" offset-lg="2" style="border: 1px solid grey;">
        <#if image?? && image?has_content>
                <@imageWithCaption imageSrc=image imageDetails=module.image variant="fullwidth"/>
        </#if>
        <vs-col offset-lg="4">
                <vs-heading level="2">${module.title}</vs-heading>
        </vs-col>
        <@hst.html hippohtml=module.introduction/>


        </br> </br>
        <#list module.sections as section>
            <vs-row>
                <#if section.quote??>
                    <#if section.quoteImage.cmsImage??>
                        <#assign imageQuote>
                            <@hst.link hippobean=section.quoteImage.cmsImage.thumbnail/>
                        </#assign>
                    <#else>
                        <#assign imageQuote = section.quoteImage.externalImage!'' />
                    </#if>
                    <vs-row>
                        <vs-col cols="4">
                            "<@hst.html hippohtml=section.quote/>"
                            <#if imageQuote?? && imageQuote?has_content>
                                <vs-col cols="3" offset-lg="1">
                                    <vs-img alt="${(section.quoteImage)!'${label("essentials.global", "default.alt-text")}'}"
                                            src="${imageQuote}">
                                    </vs-img>
                                   <#-- <@imageWithCaption imageSrc=imageQuote imageDetails=section.quoteImage variant="fullwidth"/>-->
                                </vs-col>
                            </#if>
                            <vs-heading level="6">${section.quoteAuthorName}</vs-heading>
                            ${section.quoteAuthorTitle}
                        </vs-col>

                        <vs-col cols="8" >
                            <@hst.html hippohtml=section.copy/>
                        </vs-col>
                    </vs-row>
                <#else>
                    </br> </br>
                        <@hst.html hippohtml=section.copy/>
                </#if>
            </vs-row>

            </#list>
    </vs-col>
    </br> </br>
</#macro>