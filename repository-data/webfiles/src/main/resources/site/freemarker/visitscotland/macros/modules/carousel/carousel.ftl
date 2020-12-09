<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-carousel.ftl">
<#include "../../../../frontend/components/vs-carousel-slide.ftl">

<#macro carousel item>
    <vs-carousel>
        <#list item.links as carouselItem>
            <#if carouselItem.image.cmsImage??>
                <#assign image>
                    <@hst.link hippobean=carouselItem.image.cmsImage.original/>
                </#assign>
            <#else>
                <#assign image = carouselItem.image.externalImage!'' />
            </#if>
            <vs-carousel-slide
                link-url="${carouselItem.link}"
                link-type="${carouselItem.type}"
                img-src="${image}"
                img-alt="${(carouselItem.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                <#if carouselItem.category??>
                    category="${carouselItem.category}"
                </#if>
                <#if carouselItem.itineraryTransport??>
                    transport="${carouselItem.itineraryTransport}"
                    days="${carouselItem.itineraryDays}"
                </#if>
            >
                <template slot="vsCarouselSlideHeading">
                    ${carouselItem.label}
                </template>

                <#--  <vs-col cols="12" lg="3">
                    <vs-link href="${carouselItem.link}">
                        <vs-row>
                            <vs-col >
                                <vs-img alt=""
                                        src="">
                                </vs-img>

                                <#if carouselItem.itineraryTransport??>
                                    <vs-heading level="7">Transport: ${carouselItem.itineraryTransport}  <vs-icon name="${carouselItem.itineraryTransport}" variant="dark" size="lg"></vs-icon>  </vs-heading>
                                    <vs-heading level="7">Days: ${carouselItem.itineraryDays} days</vs-heading>
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
