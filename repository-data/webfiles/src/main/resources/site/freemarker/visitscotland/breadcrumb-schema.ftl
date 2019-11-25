<#include "../include/imports.ftl">

<#if breadcrumb?? && breadcrumb.items??>
    <@hst.headContribution category="htmlHead">
        <script type="application/ld+json">
        {
            "@context": "https://schema.org",
            "@type": "BreadcrumbList",
            "itemListElement": [{
                "@type": "ListItem",
                "position": 1,
                "name": "Home",
                "item": "<@hst.link siteMapItemRefId="root"/>"
            }
            <#assign count = 1>
            <#list breadcrumb.items as item>
                <#if item.title!="home">
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