<#compress>
    <#include "../../../include/imports.ftl">
    <#include "../headerContributions.ftl">
    <#include "../base-footer.ftl">
    <#include "../base-top-menu.ftl">
    <#include "../utility-footer.ftl">

    <#if legacy?? && (legacy != "false")>
        <link rel="stylesheet" href="<@hst.link fullyQualified=fullyQualifiedURLs path='/webfiles/static/third-party/legacy-1.0.css' />" />
    </#if>

    <@headContributions />
    <!-- end include -->
</#compress>