<#include "../../../../../frontend/components/vs-mega-nav-featured-item.ftl">
<#include "../../../../../include/imports.ftl">

<#macro headerFeaturedItem item index>
    <@hst.link var="imageSrc" hippobean=item.image.cmsImage.original/>

    <#if index = 0>
        <template slot="navFeaturedItem">
    <#else>
        <template slot="navFeaturedItemLeft">
    </#if>

        <vs-mega-nav-featured-item
            link="${item.link}"
            img-url="${imageSrc}"
            align=""
        >

            <!-- THINGS WE NEED
                - alt text
                - index
            -->

            <template slot="vsFeaturedItemHeader">
                ${item.label}
            </template>

            <template slot="vsFeaturedItemContent">
                ${item.teaser}
            </template>

            <template slot="vsFeaturedItemLink">
                ${item.cta}
            </template>

        </vs-mega-nav-featured-item>
        <!-- A featured item would go here -->
        <!-- Image src = ${imageSrc} -->
        <!-- Card title=  ${item.label} -->
        <!-- Card href ${item.link} -->
        <!-- Card teaser ${item.teaser} -->
        <!-- CTA Link: ${item.cta} -->
    </template>
</#macro>