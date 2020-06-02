<!doctype html>
<#include "../../include/imports.ftl">

<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <#if hstRequest.requestContext.channelManagerPreviewRequest>
            <link rel="stylesheet" href="<@hst.webfile  path="/css/cms-request.css"/>" type="text/css"/>
        </#if>

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlHeadPreload -->
        <@hst.headContributions categoryIncludes="htmlHeadPreload" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlHeadPreload -->

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlHeadStyles -->
        <@hst.headContributions categoryIncludes="htmlHeadStyles" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlHeadStyles -->

    </head>
    <body>
        <div class="container no-js" data-vue-app-init>
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <@hst.include ref="top"/>
                </div>
            </div>
            <div class="row">
                <div class="col-md-6 col-md-offset-3">
                    <@hst.include ref="menu"/>
                </div>
            </div>
            <main id="main">
                <@hst.include ref="main"/>
            </main>
            
            <@hst.include ref="footer"/>
                
        </div>

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlBodyEndScriptsFirst -->
        <@hst.headContributions categoryIncludes="htmlBodyEndScriptsFirst, scripts" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndScriptsFirst -->

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlBodyEndScripts -->
        <@hst.headContributions categoryIncludes="htmlBodyEndScripts, scripts" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndScripts -->

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlBodyEndAppEntry -->
        <@hst.headContributions categoryIncludes="htmlBodyEndAppEntry" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlBodyEndAppEntry -->
    </body>
</html>