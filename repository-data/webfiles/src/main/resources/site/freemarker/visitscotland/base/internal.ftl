<!doctype html>
<#include "../../include/imports.ftl">

<html lang="en">
<head>
    <#compress>
        <internal-css-header>
            <meta charset="utf-8"/>
            <meta name="viewport" content="width=device-width, initial-scale=1.0">

            <@hst.headContributions categoryIncludes="seo" xhtml=true/>
            <@hst.headContributions categoryIncludes="opengraph" xhtml=true/>
            <@hst.headContributions categoryIncludes="htmlHeadPreload" xhtml=true/>
            <@hst.headContributions categoryIncludes="htmlHeadStyles" xhtml=true/>
            <@hst.headContributions categoryExcludes="htmlHeadPreload,htmlHeadStyles,htmlBodyEndScriptsFirst,htmlBodyEndScripts,htmlBodyEndAppInit,seo,opengraph" xhtml=true/>

        </internal-css-header>
    </#compress>
</head>
<body>
<#compress>
    <internal-header>
        <div class="no-js" data-vue-app-init>
            <@hst.include ref="top"/>

            <@hst.include ref="menu"/>

            <main id="main">
                <@hst.include ref="main" />
            </main>

    </internal-header>
</#compress>
<#compress>
    <internal-footer>
            <@hst.include ref="footer"/>
        </div>
    </internal-footer>
</#compress>
<#compress>
    <internal-scripts-footer>
        <@hst.headContributions categoryIncludes="htmlBodyEndScriptsFirst" xhtml=true/>
        <@hst.headContributions categoryIncludes="htmlBodyEndScripts" xhtml=true/>
        <@hst.headContributions categoryIncludes="htmlBodyEndAppInit" xhtml=true/>
    </internal-scripts-footer>
</#compress>
</body>
</html>
