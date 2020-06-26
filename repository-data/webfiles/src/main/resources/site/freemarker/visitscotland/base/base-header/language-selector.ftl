<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/header-dropdown.ftl">



<#-- @ftlvariable name="hstRequest" type="org.hippoecm.hst.core.component.HstRequest"-->
<@hst.setBundle basename="universal"/>

<#assign currentLocale=hstRequest.requestContext.resolvedMount.mount.locale?replace("_","-")?lower_case>
<#assign languages = ["en-gb","de-de","es-es","fr-fr","it-it","nl-nl"]>


<vs-header-drawer-toggle
     section="top"
     class="d-lg-none"
     content-key="language-list"
     drawer-key="header-top"
>
    <@fmt.message key="${currentLocale}" />
</vs-header-drawer-toggle>

<vs-header-dropdown text="<@fmt.message key="${currentLocale}" />" right class="d-none d-lg-flex" section="top">

    <#list languages as language>
        <#assign langToken =language?replace("_","-")?lower_case>
        <vs-dropdown-item
            key="${langToken}"
            active="${(currentLocale == lang)?c}"
            tracking-id="lang.${langToken}"
            href="/site/${langToken}"
        >
            <@fmt.message key="${langToken}" />
        </vs-dropdown-item>
    </#list>

    <#--v-for="(lang, i) in header.languages"-->
    <#--:key="i"-->
    <#--:href="lang.href"-->
    <#--:active="lang.isActive"-->
    <#--:tracking-id="lang.trackingId"-->
    <#-->-->
    <#--{{ lang.title }}-->
    <#--</vs-dropdown-item>-->
</vs-header-dropdown>