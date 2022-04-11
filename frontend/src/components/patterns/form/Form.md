## Usage
The Form component provides details of the Marketo instances for submission, as well
as data for the form structure and validation details.

```jsx
<VsForm
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
</VsForm>
```
