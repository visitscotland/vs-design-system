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
            </vs-carousel-slide>
        </#list>
    </vs-carousel>
</#macro>
