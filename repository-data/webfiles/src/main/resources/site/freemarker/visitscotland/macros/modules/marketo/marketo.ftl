<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-form.ftl">
<#include "../../../../frontend/components/vs-container.ftl">
<#include "../../../../frontend/components/vs-row.ftl">
<#include "../../../../frontend/components/vs-col.ftl">

<#-- @ftlvariable name="module" type="com.visitscotland.brxm.model.MarketoFormModule" -->
<#macro marketo form>
    <vs-container class="mb-10">
        <vs-row>
            <vs-col
                cols="12"
                md="10"
                lg="7"
                xl="7"
                class="col-xxl-6"
            >
                <#--  <h1>${form.title}</h1>  -->
                <#--  <@hst.html hippohtml=form.copy/>  -->

                <vs-form
                    data-url="${form.jsonUrl}"
                    messaging-url="http://127.0.0.1:5555/messaging.json"
                    country-list-url="http://127.0.0.1:5555/countries.json"
                    recaptcha-key="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
                    marketo-instance="//app-lon10.marketo.com"
                    munchkin-id="830-QYE-256"
                    language="en"
                    :is-prod="false"
                />
                <#--  
                <p>${form.noJavaScriptMessage}</p>
                <p>${form.jsonUrl}</p>  -->
            </vs-col>
        </vs-row>
    </vs-container>
</#macro>