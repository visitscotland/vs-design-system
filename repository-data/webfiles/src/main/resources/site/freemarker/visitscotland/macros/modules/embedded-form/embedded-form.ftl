<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-form.ftl">

<#-- Reference: https://developers.marketo.com/javascript-api/forms/ -->

<#macro embeddedForm marketoId>
    <section class="vs-embedded-form">
        <vs-container>
            <vs-row>
                <vs-col cols="12">
                    <p>Newsletter form:</p>

                    <vs-form form-id="${marketoId}" />

                    <@hst.headContribution category="htmlBodyEndScripts">
                        <script src="//e.visitscotland.com/js/forms2/js/forms2.min.js"></script>
                    </@hst.headContribution>

                    <@hst.headContribution category="htmlBodyEndScripts">
                        <script type="text/javascript">
                            <#-- TODO ${label("configuration", "marketo.munchkinId")} -->
                            <#-- TODO Do we need to proxy app-sjqe.marketo.com? -->
                        </script>
                    </@hst.headContribution>
                </vs-col>
            </vs-row>
        </vs-container>
    </section>
</#macro>
