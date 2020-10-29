<#include "../../../../include/imports.ftl">

<#-- Reference: https://developers.marketo.com/javascript-api/forms/ -->

<#macro embeddedForm marketoId>
    <p>Newsletter</p>
    <form id="mktoForm_${marketoId}"></form>

    <@hst.headContribution category="htmlBodyEndScripts">
        <script src="//app-sjqe.marketo.com/js/forms2/js/forms2.js"></script>
    </@hst.headContribution>

    <@hst.headContribution category="htmlBodyEndScripts">
        <script type="text/javascript">
            <#-- TODO ${label("configuration", "marketo.munchkinId")} -->
            <#-- TODO Do we need to proxy app-sjqe.marketo.com? -->
            MktoForms2.loadForm("//app-sjqe.marketo.com", "718-GIV-198", ${marketoId});
        </script>
    </@hst.headContribution>
</#macro>