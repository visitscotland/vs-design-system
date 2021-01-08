<#macro quote authorImage content authorName authorTitle link="">
    <vs-quote>
        <#if authorImage??>
            <vs-img
                alt="${(authorImage)!'${label("essentials.global", "default.alt-text")}'}"
                src="${authorImage}"
                slot="quoteImage">
            </vs-img>
        </#if>
        <div slot="quoteContent">
            <@hst.html hippohtml=content/>
        </div>
        <span slot="quoteAuthorName">${authorName}</span>
        <span slot="quoteAuthorTitle">${authorTitle}</span>
        <#if link?has_content>
            <vs-button
                href="${link.link}"
                slot="quoteLink">
                ${link.label}
            </vs-button>
        </#if>
    </vs-quote>
</#macro>