<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-carousel.ftl">
<#include "../../../../frontend/components/vs-carousel-slide.ftl">

<#macro carousel item>
    <vs-carousel
        next-text="next page"
        prev-text="previous page"
        slides-xs="1"
        slides-sm="2"
        slides-md="3"
        slides-lg="4"
    >
        <#list item.links as carouselItem>
            <#if carouselItem.image.cmsImage??>
                <#assign image>
                    <@hst.link hippobean=carouselItem.image.cmsImage.original/>
                </#assign>
            <#else>
                <#assign image = carouselItem.image.externalImage!'' />
            </#if>
            <vs-carousel-slide
                days-label="${label('itinerary', 'days')}"
                link-url="${carouselItem.link}"
                link-type="${carouselItem.type}"
                img-src="${image}"
                img-alt="${(carouselItem.image.altText)!'${label("essentials.global", "default.alt-text")}'}"
                <#if carouselItem.category??>
                    category="${carouselItem.category}"
                </#if>
                <#if carouselItem.itineraryTransport??>
                    transport="${carouselItem.itineraryTransport}"
                    transport-name="${label('transports', carouselItem.itineraryTransport)}"
                    days="${carouselItem.itineraryDays}"
                </#if>
            >
                <template slot="vsCarouselSlideHeading">
                    ${carouselItem.label}
                </template>
            </vs-carousel-slide>
        </#list>

        <template slot="vsCarouselOf">
            ${label("essentials.pagination", "page.of")}
        </template>
    </vs-carousel>
</#macro>
