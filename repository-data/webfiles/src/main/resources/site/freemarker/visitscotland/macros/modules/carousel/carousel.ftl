<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-carousel.ftl">
<#include "../../../../frontend/components/vs-carousel-slide.ftl">

<#macro carousel item>
    <vs-carousel>
        <#list item.links as megalink>
            <#if megalink.image.cmsImage??>
                <#assign image>
                    <@hst.link hippobean=megalink.image.cmsImage.original/>
                </#assign>
            <#else>
                <#assign image = megalink.image.externalImage!'' />
            </#if>
            <vs-carousel-slide
                link-url="${megalink.link}"
                link-type="${megalink.type}"
                img-src="${image}"
                img-alt="${(megalink.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                <#if megalink.category??>
                    category="${megalink.category}"
                </#if>
                <#if megalink.itineraryTransport??>
                    transport="${megalink.itineraryTransport}"
                    days="${megalink.itineraryDays}"
                </#if>
            >
                <template slot="vsCarouselSlideHeading">
                    ${megalink.label}
                </template>

                <#--  <vs-col cols="12" lg="3">
                    <vs-link href="${megalink.link}">
                        <vs-row>
                            <vs-col >
                                <vs-img alt=""
                                        src="">
                                </vs-img>

                                <#if megalink.itineraryTransport??>
                                    <vs-heading level="7">Transport: ${megalink.itineraryTransport}  <vs-icon name="${megalink.itineraryTransport}" variant="dark" size="lg"></vs-icon>  </vs-heading>
                                    <vs-heading level="7">Days: ${megalink.itineraryDays} days</vs-heading>
                                </#if>
                                <vs-heading level="3"></vs-heading>

                            </vs-col>
                            </br></br>
                        </vs-row>
                    </vs-link>
                    <vs-heading level="5"></vs-heading>
                </vs-col>  -->
            </vs-carousel-slide>
        </#list>
    </vs-carousel>
</#macro>
