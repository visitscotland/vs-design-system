<#include "../../../../../include/imports.ftl">

<#macro headerFeaturedItem item>
    <@hst.link var="imageSrc" hippobean=item.image.cmsImage.original/>
    <!-- A featured item would go here -->
    <!-- Image src = ${imageSrc} -->
    <!-- Card title=  ${item.label} -->
    <!-- Card href ${item.link} -->
    <!-- Card teaser ${item.teaser} -->
    <!-- CTA Link: ${item.cta} -->


    <!-- The following fields are available but they are not required -->
    <!-- Card link icon: ${item.type} -->
    <!-- Card Itinerary : ${item.itineraryTransport}, ${item.itineraryDays} -->

</#macro>