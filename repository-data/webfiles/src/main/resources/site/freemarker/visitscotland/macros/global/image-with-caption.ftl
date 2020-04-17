<#include "../../../vs-dotcom-ds/components/svg.ftl">
<#include "../../../vs-dotcom-ds/components/social-credit-link.ftl">
<#include "../../../vs-dotcom-ds/components/image-with-caption.ftl">

<#macro imageWithCaption imageSrc imageDetails variant>
    <vs-image-with-caption
        alt-text="${(imageDetails.altText)!'${label("essentials.global", "default.alt-text")}'}"
        image-src="${imageSrc}"
        latitude="${(imageDetails.coordinates.latitude)!''}"
        longitude="${(imageDetails.coordinates.longitude)!''}"
        variant="${variant}"
    >
        <span slot="caption">
            ${(imageDetails.description)!''}
        </span>

        <#if imageDetails.source?has_content>
            <vs-svg slot="toggle-icon" path="${imageDetails.source + '-bg'}" height="24" width="24"></vs-svg>

            <vs-social-credit-link
                slot="social-link"
                credit="${(imageDetails.credit)!'No credit'}"
                social-post-url="${imageDetails.postUrl}"
                source="${imageDetails.source}"
            ></vs-social-credit-link>
        <#else>
            <#if imageDetails.credit?has_content>
                <span slot="credit">
                    &copy; ${imageDetails.credit}
                </span>
            </#if>
        </#if>
    </vs-image-with-caption>
</#macro>