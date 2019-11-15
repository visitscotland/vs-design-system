<#include "../include/imports.ftl">

<#-- @ftlvariable name="breadcrumb" type="org.onehippo.forge.breadcrumb.om.Breadcrumb" -->
<#if breadcrumb?? && breadcrumb.items??>
    <#if "/site/" != requestedURI>
        <a href="/site/">Home</a>
    <#else>
        Home
    </#if>
        <#list breadcrumb.items as item>


           <#if item.title!="home">
               <@hst.link var="link" link=item.link/>
               ${breadcrumb.separator}&nbsp;
               <#if link != requestedURI>
                   <a href="${link}">${item.title?html}</a>
               <#else>
                   ${item.title?html}
               </#if>
            </#if>
        </#list>

</#if>

