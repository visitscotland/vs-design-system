<#compress>
<#include "../../../include/imports.ftl">
<#include "../../../vs-dotcom-ds/components/breadcrumb.ftl">
<#include "../../../vs-dotcom-ds/components/breadcrumb-item.ftl">


<#include "content-driven-seo.ftl">
<#include "breadcrumb-schema.ftl">

<@hst.setBundle basename="navigation"/>

<#-- @ftlvariable name="breadcrumb" type="org.onehippo.forge.breadcrumb.om.Breadcrumb" -->
<#-- @ftlvariable name="requestedURI" type="java.lang.String" -->
<#-- @ftlvariable name="isHome" type="java.lang.Boolean" -->
<#-- @ftlvariable name="document" type="com.visitscotland.brmx.beans.Page" -->

</#compress>
<#if breadcrumb?? && breadcrumb.items??>
    <vs-breadcrumb>
        <vs-breadcrumb-item
                key="home"
                href="<@hst.link siteMapItemRefId="root"/>"
                <#if isHome>active</#if>
                text="<@fmt.message key="home" />"
        >
        </vs-breadcrumb-item>

        <#list breadcrumb.items as item>
            <#-- Avoid duplicated Home element (Document based pages skip this element)-->
            <#if item.link?? &&  item.link.hstSiteMapItem.id?? && item.link.hstSiteMapItem.id != "root">
                <#if item.link??>
                    <@hst.link var="link" link=item.link/>
                <#else>
                    <#-- This asignation removes the link from the breadcrumb -->
                    <#assign link = requestedURI>
                </#if>
                <vs-breadcrumb-item
                        key="${item.title}"
                        href="${link}"
                        <#if link?? && link == requestedURI >active</#if>
                        text="${item.title?html}"
                >
                </vs-breadcrumb-item>
            </#if>
        </#list>

    </vs-breadcrumb>
</#if>


