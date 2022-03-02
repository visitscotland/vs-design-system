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
    <vs-form
        required-text="required"
        data-url="https://static.visitscotland.com/forms/vs-3331/simpleForm.json"
        recaptcha-key="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
        submit-text="Submit the form"
        form-id="90"
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
