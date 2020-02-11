<#include "../include/imports.ftl">
<#include "../include/vs-dotcom-ds/components/container.ftl">
<#include "../include/vs-dotcom-ds/components/row.ftl">
<#include "../include/vs-dotcom-ds/components/col.ftl">
<@hst.setBundle basename="essentials.global"/>

<vs-container>
  <vs-row>
    <@hst.include ref="container"/>
  </vs-row>
  <vs-row>
    <vs-col>
      <hr/>
      <div class="text-center">
        <sub><@fmt.message key="footer.text" var="footer"/>${footer?html}</sub>
      </div>
    </vs-col>
  </vs-row>
</vs-container>
