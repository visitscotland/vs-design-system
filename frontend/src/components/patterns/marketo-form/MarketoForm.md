## Usage
The Form component provides details of the Marketo instances for submission, as well
as data for the form structure and validation details.

The form is created programatically from a group of JSON data files which provide data for
input types, validation rules, validation messaging, recaptcha, submit button and details of
where the form should be submitted to.

Please note this form will not successfully submit to Marketo as the necessary
third party scripts aren't included on this page.

```jsx
<VsMarketoForm
    dataUrl="https://static.visitscotland.com/forms/newsletter-sign-up/newsletter-sign-up.json"
    messagingUrl="https://static.visitscotland.com/forms/common/messaging.json"
    countryListUrl="https://static.visitscotland.com/forms/common/countries.json"
    recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
    marketo-instance="//app-lon10.marketo.com"
    munchkin-id="830-QYE-256"
    language="en"
    :is-prod="false"
>
    <template slot="submitError">
        We're sorry there's been a problem, please try again later.
    </template>

    <template slot="submitting">
        We're just submitting your form
    </template>
</VsMarketoForm>
```

### Conditional Inputs
An input can be given a prop of 'conditional', meaning that it will only appear when another element's value
meets a certain criteria.

This is achieved by supplying...