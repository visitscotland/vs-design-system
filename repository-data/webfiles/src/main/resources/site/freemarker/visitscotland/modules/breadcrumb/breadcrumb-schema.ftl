<#include "../../../include/imports.ftl">
<@hst.setBundle basename="navigation"/>

<#-- @ftlvariable name="breadcrumb" type="org.onehippo.forge.breadcrumb.om.Breadcrumb" -->
<#-- @ftlvariable name="requestedURI" type="java.lang.String" -->
<#-- @ftlvariable name="isHome" type="java.lang.Boolean" -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Page" -->

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