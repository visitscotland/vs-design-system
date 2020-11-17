<#include "../../../../../include/imports.ftl">
<#include "../../../../../frontend/components/vs-megalink-single-image.ftl">
<#include "../../../../../frontend/components/vs-link.ftl">

<#macro singleImage item>
    <#if item.image.cmsImage??>
        <#assign image>
            <@hst.link hippobean=item.image.cmsImage.original/>
        </#assign>
    <#else>
        <#assign image = item.image.externalImage!'' />
    </#if>
    <vs-col cols="12">
        <vs-megalink-single-image 
            title="${item.innerTitle}"
            img-src="${image}"
            <#if item.cta.link??>button-link="${item.cta.link}"</#if>
            <#if item.alignment == 'left'>alternate</#if>
        >
            <template slot="vsSingleImageCaption">${item.image.description}</template>
            <template slot="vsSingleImageCredit">${item.image.credit}</template>
            <template slot="vsSingleImageContent">
                <@hst.html hippohtml=item.innerIntroduction/>
            </template>
            <template slot="vsSingleImageLinks">
                <#list item.links as listItem>
                    <li class="megalink-single-image__link-list-item">
                        <vs-link
                            href="${listItem.link}"
                            <#if listItem.type != "internal">type="${listItem.type}"</#if>
                        >
                            ${listItem.label}
                        </vs-link>
                    </li>
                </#list>
            </template>
            
            <template slot="vsSingleImageButtonText">
                ${item.cta.label}
            </template>
            
        </vs-megalink-single-image>
    </vs-col>
    
    <#--TODO: control alignment right/left properly-->
    <#--  <#if item.getAlignment()=="right">
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
            <@imageWithCaption imageSrc=image imageDetails=item.image variant="fullwidth"/>
        </vs-col>
    <#else>
        <vs-col cols="4" lg="4">
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
    </#if>  -->
</#macro>