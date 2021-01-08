<!doctype html>
<#include "../../include/imports.ftl">
<#include "../macros/modules/embedded-form/embedded-form.ftl">

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
        <@hst.headContributions categoryExcludes="htmlHeadPreload,htmlHeadStyles,htmlBodyEndScriptsFirst,htmlBodyEndScripts,htmlBodyEndAppInit,seo,opengraph" xhtml=true/>
        <!-- END HEAD CONTRIBUTIONS: general -->

    </head>
    <body>
        <div class="no-js" data-vue-app-init>
            <@hst.include ref="top"/>

            <@hst.include ref="menu"/>

            <main id="main">
                <@hst.include ref="main"/>

                <@embeddedForm label("channel", "marketo.newsletter.formId") />
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
</html>