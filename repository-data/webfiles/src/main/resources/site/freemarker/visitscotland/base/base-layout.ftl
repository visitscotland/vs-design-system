<<<<<<< HEAD
<!doctype html>
<#include "../../include/imports.ftl">

<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <#if hstRequest.requestContext.channelManagerPreviewRequest>
            <link rel="stylesheet" href="<@hst.webfile  path="/css/cms-request.css"/>" type="text/css"/>
        </#if>

        <!-- BEGIN HEAD CONTRIBUTIONS: seo -->
        <@hst.headContributions categoryIncludes="seo" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: seo -->

        <!-- BEGIN HEAD CONTRIBUTIONS: opengraph -->
        <@hst.headContributions categoryIncludes="opengraph" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: opengraph -->

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlHeadPreload -->
        <@hst.headContributions categoryIncludes="htmlHeadPreload" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlHeadPreload -->

        <!-- BEGIN HEAD CONTRIBUTIONS: htmlHeadStyles -->
        <@hst.headContributions categoryIncludes="htmlHeadStyles" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: htmlHeadStyles -->

        <!-- BEGIN HEAD CONTRIBUTIONS: general -->
        <@hst.headContributions categoryExcludes="htmlHeadPreload,htmlHeadStyles,htmlBodyEndScriptsFirst,htmlBodyEndScripts,htmlBodyEndAppEntry,seo,opengraph" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: general -->

    </head>
    <body>
        <div class="no-js container" data-vue-app-init>
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
=======
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

        <!-- BEGIN HEAD CONTRIBUTIONS: general -->
        <@hst.headContributions categoryExcludes="htmlHeadPreload,htmlHeadStyles,htmlBodyEndScriptsFirst,htmlBodyEndScripts,htmlBodyEndAppInit" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: general -->

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
>>>>>>> b7a2d3bd18c66e8365e7c45ce79357d26e31f56b
</html>