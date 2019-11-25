<#include "../include/imports.ftl">
<#include "../vs-dotcom-ds/components/breadcrumb.ftl">
<#include "../vs-dotcom-ds/components/breadcrumb-item.ftl">

<#-- @ftlvariable name="breadcrumb" type="org.onehippo.forge.breadcrumb.om.Breadcrumb" -->

<#if breadcrumb?? && breadcrumb.items??>

    <vs-breadcrumb>
        <vs-breadcrumb-item
                key="home"
                href="/site/"
            ${(isHome)?string("active","")}
        <#--active=false--><#-- TODO: active ("/site/" != requestedURI) -->
                text="Home"
        >

        </vs-breadcrumb-item>

        <#list breadcrumb.items as item>
            <#if item.title!="home">

                <#if item.link??>
                    <@hst.link var="link" link=item.link/>
                <#else>
                    <#-- This asignation removes the link from the breadcrumb -->
                    <#assign link = requestedURI>
                </#if>


            <#--1(${link}) != 2(${requestedURI})-->
                <vs-breadcrumb-item
                        key="${item.title}"
                        href="${link}"
                    ${(link?? && link == requestedURI)?string("active","")}
                        text="${item.title?html}"
                >
                </vs-breadcrumb-item>

            </#if>
        </#list>

    </vs-breadcrumb>


</#if>

<#if breadcrumb?? && breadcrumb.items??>
    <#assign count = 1>
    <@hst.headContribution category="htmlHead">
<script type="application/ld+json">
{
    "@context": "https://schema.org",
    "@type": "BreadcrumbList",
    "itemListElement": [{
        "@type": "ListItem",
        "position": ${count},
        "name": "Home",
        "item": "<@hst.link siteMapItemRefId="root"/>"
    }
    <#list breadcrumb.items as item>
        <#if item.title!="Home">
            <#if item.link?? && item.link.notFound && (breadcrumb.linkNotFoundMode == 'hide' || breadcrumb.linkNotFoundMode == 'unlink')>
                <#if breadcrumb.linkNotFoundMode == 'unlink'>
                </#if>
            <#else>
                <#assign count = count + 1>
                <@hst.link var="link" link=item.link/>
                ,{
                    "@type": "ListItem",
                    "position": ${count},
                    "name": "${item.title?html}",
                    "item": "${link}"
                }
            </#if>
        </#if>
    </#list>
    ]
}

</script>
    </@hst.headContribution>
</#if>
