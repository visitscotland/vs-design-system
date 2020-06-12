<!doctype html>
<#include "../../include/imports.ftl">
<#--<#include "../vs-dotcom-ds/components/button.ftl">-->

<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="<@hst.webfile  path="/design-system/components/core.css"/>" type="text/css"/>
        <#if hstRequest.requestContext.channelManagerPreviewRequest>
            <link rel="stylesheet" href="<@hst.webfile  path="/css/cms-request.css"/>" type="text/css"/>
        </#if>
        <@hst.headContributions categoryExcludes="htmlBodyEnd, scripts, htmlAppInit" xhtml=true/>
    </head>
    <body>
        <div data-vue-app-init>
            <div class="container" >
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
            </div>
            
            <@hst.include ref="footer"/>
                
        </div>

        <script type="text/javascript" src="<@hst.webfile  path='design-system/components/core.js'/>"></script>
        <@hst.headContributions categoryIncludes="htmlBodyEnd, scripts" xhtml=true/>
        <@hst.headContributions categoryIncludes="htmlAppInit" xhtml=true/>
    </body>
</html>