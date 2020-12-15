<#include "../../../../include/imports.ftl">
<#include "../../global/image-with-caption.ftl">

<#macro singleImage item>
    <#if item.image.cmsImage??>
        <#assign image>
            <@hst.link hippobean=item.image.cmsImage.original/>
        </#assign>
    <#else>
        <#assign image = item.image.externalImage!'' />
    </#if>
    
    <#--TODO: control alignment right/left properly-->
    <#if item.getAlignment()=="right">
        <vs-col cols="12" lg="12" offset-lg="1">
            <vs-heading level="3">${item.innerTitle}</vs-heading>
        </vs-col>
        <vs-col cols="6" lg="6" offset-lg="1">
            <vs-rich-text-wrapper variant="lead">
                <@hst.html hippohtml=item.innerIntroduction/> </br>
            </vs-rich-text-wrapper>
            <ol>
                <#list item.links as megalink>
                    <vs-row>
                        <vs-col cols="4" lg="4" offset-lg="1">
                            <vs-link href="${megalink.link}"> ${megalink.label} - ${megalink.type} <#if megalink.type == "external">(External)</#if></vs-link>

                            </br>
                        </vs-col>

                    </vs-row>
                </#list>
            </ol>
        </vs-col>
        <vs-col cols="4" lg="4">
            <#--FOR SIMPLE IMAGE, THE IMAGE HAS CAPTION-->
            <@imageWithCaption imageSrc=image imageDetails=item.image variant="fullwidth"/>
        </vs-col>
    <#else>
        <vs-col cols="4" lg="4">
            <#--FOR SIMPLE IMAGE, THE IMAGE HAS CAPTION-->
            <@imageWithCaption imageSrc=image imageDetails=item.image variant="fullwidth"/>
        </vs-col>
        <vs-row>
            <vs-col cols="10" lg="10" offset-lg="1">
                <vs-heading level="3">${item.innerTitle}</vs-heading>
            </vs-col>
            <vs-col cols="10" lg="10" offset-lg="1">
                <vs-rich-text-wrapper variant="lead">
                    <@hst.html hippohtml=item.innerIntroduction/> </br>
                </vs-rich-text-wrapper>
                <ol>
                    <#list item.links as megalink>
                        <vs-row>
                            <vs-col cols="4" lg="4" offset-lg="1">
                                <vs-link href="${megalink.link}"> ${megalink.label} <#if megalink.type == "external">(External)</#if></vs-link>
                            </vs-col>

                        </vs-row>
                    </#list>
                </ol>
            </vs-col>
        </vs-row>
    </#if>

    <#if item.cta??>
        <vs-button animate=false background="purple" variant="primary" href="${item.cta.link}" size="md">
            ${item.cta.label}
        </vs-button>
    </#if>


</#macro>