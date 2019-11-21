<#include "../../include/imports.ftl">
<#include "../../vs-dotcom-ds/components/header-dropdown.ftl">



<#-- @ftlvariable name="hstRequest" type="org.hippoecm.hst.core.component.HstRequest"-->
<@hst.setBundle basename="universal"/>

<#assign currentLocale=hstRequest.requestContext.resolvedMount.mount.locale>
<#assign languages = ["en_GB","de_DE","es_ES","fr_FR","it_IT","nl_NL"]>


<vs-header-dropdown text="EN" right class="d-none d-lg-flex" section="top">

    <#list languages as language>
        <#assign langToken =language?replace("_","-")?lower_case>
        <vs-dropdown-item
            key="${langToken}"
            active="${(currentLocale == language)?c}"
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