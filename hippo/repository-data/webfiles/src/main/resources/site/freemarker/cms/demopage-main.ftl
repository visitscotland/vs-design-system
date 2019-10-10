<#include "../include/imports.ftl">
<#include "../include/vs-dotcom-ds/components/button.ftl">
<#include "../include/vs-dotcom-ds/components/heading.ftl">
<#include "../include/vs-dotcom-ds/components/container.ftl">
<#include "../include/vs-dotcom-ds/components/row.ftl">
<#include "../include/vs-dotcom-ds/components/col.ftl">

<vs-container>
  <vs-row>
    <vs-col>
        <vs-heading sub="The bestest">Demo page</vs-heading>

        <p>This demo page can be used to demo any design system component. In order to do so, edit the template at:</p>

        <pre>/repository-data/webfiles/src/main/resources/site/freemarker/cms/demopage-main.ftl</pre>

        <p>Make sure to add the relevant component include to the top of the page</p>

        <p>
            <vs-button>This is a button</vs-button>
        </p>
    </vs-col>
  </vs-row>
</vs-container>
