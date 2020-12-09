<!doctype html>
<#include "../../include/imports.ftl">
<#include "headerContributions.ftl">
<#include "footerContributions.ftl">

<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <#if hstRequest.requestContext.channelManagerPreviewRequest>
            <link rel="stylesheet" href="<@hst.webfile  path="/css/cms-request.css"/>" type="text/css"/>
        </#if>

        <@headContributions>

    </head>
    <body>
        <div class="no-js" data-vue-app-init>
            <@hst.include ref="top"/>

            <@hst.include ref="menu"/>

            <main id="main">
                <@hst.include ref="main"/>
            </main>

            <@hst.include ref="footer"/>
        </div>

        <#--
         IMPORTANT!

         Please consider to update internal.ftl when making changes in the head contributions, so third Parties can
         benefit from the same configuration.
        -->

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlBodyEndScriptsFirst -->
        <@hst.headContributions categoryIncludes="htmlBodyEndScriptsFirst" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndScriptsFirst -->

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlBodyEndScripts -->
        <@hst.headContributions categoryIncludes="htmlBodyEndScripts" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndScripts -->

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlBodyEndAppInit -->
        <@hst.headContributions categoryIncludes="htmlBodyEndAppInit" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndAppInit -->
    </body>
</html>