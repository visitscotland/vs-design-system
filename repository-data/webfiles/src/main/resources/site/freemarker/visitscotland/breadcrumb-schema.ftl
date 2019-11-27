<#include "../include/imports.ftl">

<@hst.setBundle basename="navigation"/>

<#-- @ftlvariable name="breadcrumb" type="org.onehippo.forge.breadcrumb.om.Breadcrumb" -->

<#if breadcrumb?? && breadcrumb.items??>
    <@hst.headContribution category="htmlHead">
        <script type="application/ld+json">
        {
            "@context": "https://schema.org",
            "@type": "BreadcrumbList",
            "itemListElement": [{
                "@type": "ListItem",
                "position": 1,
                {
                    "@id": "<@hst.link siteMapItemRefId="root"/>",
                    "name": "Home"
                }
            }
        <#assign count = 1>
        <#list breadcrumb.items as item>
            <#-- Avoid duplicated Home element (Document based pages skip this element)-->
            <#if item.link?? &&  item.link.hstSiteMapItem.id?? && item.link.hstSiteMapItem.id != "root">

                <#if item.link??>
                    <@hst.link var="link" link=item.link/>
                <#else>
                    <#-- This assignation removes the link from the breadcrumb -->
                    <#assign link = requestedURI>
                </#if>


            <#--1(${link}) != 2(${requestedURI})-->
                <#assign count = count + 1>
                ,{
                    "@type": "ListItem",
                    "position": ${count},
                    {
                        "@id": "${link}",
                        "name": "${item.title?html}"
                    }
                }
            </#if>
        </#list>
            ]
        }

        </script>
    </@hst.headContribution>
</#if>