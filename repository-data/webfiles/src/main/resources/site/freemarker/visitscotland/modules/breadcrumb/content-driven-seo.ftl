<#include "../../../include/imports.ftl">

<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Page" -->

<#-- TODO REFACTOR -->
<@hst.headContribution category="seo">
<#-- canonical tag has -->
    <link rel="canonical" href="<@hst.link hippobean=document canonical=true fullyQualified=true/>" />
</@hst.headContribution>

<#-- TODO REFACTOR -->
<#if document.availableTranslations.translations?size gt 1>
    <#list document.availableTranslations.translations as translation>
        <@hst.headContribution category="seo">
            <link rel="alternate" href="<@hst.link hippobean=translation fullyQualified=true/>"  hreflang="${translation.localeString}"/>
        </@hst.headContribution>
    </#list>
</#if>

<#-- TODO REFACTOR -->
<@hst.headContribution category="seo">
    <meta name="description" content="${document.seoDescription}" />
</@hst.headContribution>
<@hst.headContribution category="seo">
    <title>${document.title}</title>
</@hst.headContribution>

<#-- TODO REFACTOR. Note seo fields are not available from the base layout-->
<@hst.link var="ogImage" hippobean=document.heroImage.original fullyQualified=true/>
<@hst.headContribution category="opengraph">
    <meta property="og:title" content="${document.seoTitle}" />
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta property="og:description" content="${document.seoDescription}" />
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta property="og:type" content="article" />
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta property="og:url" content="<@hst.link hippobean=document canonical=true fullyQualified=true/>" />
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta property="fb:pages" content="334250391467"/><#-- TODO: lablel -->
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta property="og:image" content="${ogImage}" />
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta name="twitter:card" content="summary_large_image" />
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta name="twitter:site" content="@VisitScotland" /><#-- TODO: lablel -->
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta name="twitter:title" content="${document.seoTitle}" />
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta name="twitter:description" content="${document.seoDescription}" />
</@hst.headContribution>
<@hst.headContribution category="opengraph">
    <meta name="twitter:image" content="${ogImage}" />
</@hst.headContribution>