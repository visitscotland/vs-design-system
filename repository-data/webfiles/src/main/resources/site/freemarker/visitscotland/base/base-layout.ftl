<!doctype html>
<#include "../../include/imports.ftl">
<#include "../macros/global/gtm.ftl">
<#include "headerContributions.ftl">
<#include "footerContributions.ftl">
<#include "../../frontend/components/vs-cookie-checker.ftl">

<html data-version="${version}" lang="en">
    <head>
        <#if hstRequest.requestContext.channelManagerPreviewRequest>
            <link rel="stylesheet" href="<@hst.webfile  path="/assets/css/cms-request.css"/>" type="text/css"/>
        </#if>
        
        <!-- Google Tag Manager -->
        <script>(function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
        new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
        j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
        'https://www.googletagmanager.com/gtm.js?id='+i+dl+ '&gtm_auth=YWMt6gew5Au1PAnzyzMADQ&gtm_preview=env-3&gtm_cookies_win=x';f.parentNode.insertBefore(j,f);
        })(window,document,'script','dataLayer','GTM-WWKWV5K');</script>
        <!-- End Google Tag Manager -->

        <@headContributions />
    </head>
    <body>
        <@gtm noscript=true />
        <div class="no-js" data-vue-app-init>
            <@hst.include ref="top"/>

            <@hst.include ref="menu"/>

            <main id="main">
                <@hst.include ref="main"/>
            </main>

            <@hst.include ref="footer"/>
        </div>

        <@footerContributions />
    </body>
</html>