<#include "../../include/imports.ftl">
<@hst.setBundle basename="navigation"/>

<#-- @ftlvariable name="breadcrumb" type="org.onehippo.forge.breadcrumb.om.Breadcrumb" -->

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

<#if breadcrumb?? && breadcrumb.items??>
    <@hst.headContribution category="seo">

        <script type="application/ld+json">
        {
            "@context": "https://schema.org",
            "@type": "BreadcrumbList",
            "itemListElement": [{
                "@type": "ListItem",
                "position": 1,
                "item": {
                    "@id": "<@hst.link siteMapItemRefId="root" fullyQualified=true/>",
                    "name": "<@fmt.message key="home"/>"
                }
        <#assign count = 1>
        <#list breadcrumb.items as item>
            <#-- Avoid duplicated Home element (Document based pages skip this element)-->
            <#if item.link?? &&  item.link.hstSiteMapItem.id?? && item.link.hstSiteMapItem.id != "root">
                <@hst.link var="link" link=item.link fullyQualified=true/>
                <#assign count = count + 1>
            <#-- Keep the indentation -->
            },{
                "@type": "ListItem",
                "position": ${count},
                "item": {
                    "@id": "${link}",
                    "name": "${item.title?html}"
                }
            </#if>
        </#list>
            }]
        }
        </script>
    </@hst.headContribution>
</#if>