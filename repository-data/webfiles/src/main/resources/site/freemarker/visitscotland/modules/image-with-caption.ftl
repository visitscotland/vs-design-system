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
            <vs-icon
                slot="toggle-icon"
                name="${imageDetails.source}"
                variant="light"
                size="sm"
            ></vs-icon>

            <vs-social-credit-link
                slot="social-link"
                credit="${(imageDetails.credit)!'No credit'}"
                social-post-url="${imageDetails.postUrl}"
                source="${imageDetails.source}"
            ></vs-social-credit-link>
        <#else>
            <span slot="credit">
                &copy; ${imageDetails.credit}
            </span>
        </#if>
    </vs-image-with-caption>
</#macro>