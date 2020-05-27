<!doctype html>
<#include "../../include/imports.ftl">
<#--  <#include "../../vs-dotcom-ds/vue-app-init.ftl">  -->

<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#if hstRequest.requestContext.channelManagerPreviewRequest>
            <link rel="stylesheet" href="<@hst.webfile  path="/css/cms-request.css"/>" type="text/css"/>
        </#if>
        <!-- HEAD CONTRIBUTIONS: htmlHeadPreload -->
        <@hst.headContributions categoryIncludes="htmlHeadPreload" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlHeadPreload -->

        <!-- HEAD CONTRIBUTIONS: htmlHeadStyles -->
        <@hst.headContributions categoryIncludes="htmlHeadStyles" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlHeadStyles -->
    </head>
    <body>
        <div data-vue-app-init class="no-js">
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <@hst.include ref="menu"/>
                </div>
            </div>
            <main id="main">
                <@hst.include ref="main"/>
            </main>
            <div class="row">
                <@hst.include ref="footer"/>
            </div>
        </div>

        <!-- HEAD CONTRIBUTIONS: htmlBodyEndScriptsFirst -->
        <@hst.headContributions categoryIncludes="htmlBodyEndScriptsFirst, scripts" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndScriptsFirst -->

        <!-- HEAD CONTRIBUTIONS: htmlBodyEndScripts -->
        <@hst.headContributions categoryIncludes="htmlBodyEndScripts, scripts" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndScripts -->

        <!-- HEAD CONTRIBUTIONS: htmlBodyEndAppEntry -->
        <@hst.headContributions categoryIncludes="htmlBodyEndAppEntry" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndAppEntry -->
    </body>
</html>