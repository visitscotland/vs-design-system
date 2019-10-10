<!doctype html>
<#include "../include/imports.ftl">
<#include "../include/vs-dotcom-ds/components/container.ftl">
<#include "../include/vs-dotcom-ds/components/row.ftl">
<#include "../include/vs-dotcom-ds/components/col.ftl">

<html lang="en">
  <head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" href="<@hst.webfile  path="/design-system/components/globalStyles.css"/>" type="text/css"/>
    <#if hstRequest.requestContext.cmsRequest>
      <link rel="stylesheet" href="<@hst.webfile  path="/css/cms-request.css"/>" type="text/css"/>
    </#if>
    <@hst.headContributions categoryExcludes="htmlBodyEnd, scripts, htmlAppInit" xhtml=true/>
  </head>
  <body>
    <div data-vue-app-init>
      <vs-container>
        <vs-row>
          <vs-col>
            <@hst.include ref="top"/>
          </vs-col>
        </vs-row>
      </vs-container>
      <@hst.include ref="menu"/>
      <@hst.include ref="main"/>
      <@hst.include ref="footer"/>
    </div>
    <@hst.headContributions categoryIncludes="htmlBodyEnd, scripts" xhtml=true/>
    <@hst.headContributions categoryIncludes="htmlAppInit" xhtml=true/>
  </body>
</html>