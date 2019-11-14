<#include "../include/imports.ftl">

<#-- @ftlvariable name="breadcrumb" type="org.onehippo.forge.breadcrumb.om.Breadcrumb" -->
<#if breadcrumb?? && breadcrumb.items??>
    <a href="/site">Home</a>
        <#list breadcrumb.items as item>

           <#if item.title!="home" >
                <@hst.link var="link" link=item.link/>
                  ${breadcrumb.separator}&nbsp; <a href="${link}">${item.title?html}</a><#sep>
            </#if>
        </#list>

</#if>

