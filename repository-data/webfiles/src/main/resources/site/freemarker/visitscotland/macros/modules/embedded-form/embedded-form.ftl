<#include "../../../../include/imports.ftl">
<#include "../../../../frontend/components/vs-form.ftl">

<#-- Reference: https://developers.marketo.com/javascript-api/forms/ -->
<@hst.headContribution category="htmlBodyEndScripts">
    <script src="//e.visitscotland.com/js/forms2/js/forms2.min.js"></script>
</@hst.headContribution>
<@hst.headContribution category="htmlBodyEndScripts">
    <script src='https://www.google.com/recaptcha/api.js'></script>
</@hst.headContribution> 

<#macro embeddedForm>
    <!-- data-url="https://static.visitscotland.com/forms/vs-3331/simpleForm.json"
    messaging-url="https://static.visitscotland.com/forms/messaging.json" -->
    <vs-form
        required-text="required"
        recaptcha-key="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
        submit-text="Submit the form"
        :is-prod="false"
        messaging-url="http://172.28.74.113:5050/messaging.json"
        data-url="http://172.28.74.113:5050/simpleForm.json"
        marketo-instance="//app-lon10.marketo.com"
        munchkin-id="830-QYE-256"
    >
        <template slot="invalid">
            You have invalid fields - please check the form.
        </template>

        <template slot="submitError">
            We're sorry there's been a problem, please try again later.
        </template>

        <template slot="submitting">
            We're just submitting your form
        </template>

        <template slot="submitted">
            Thank you for your details, your form has been submitted
        </template>
    </vs-form>
</#macro>
