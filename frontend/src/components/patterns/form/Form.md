## Usage
The Form component provides details of the Marketo instances for submission, as well
as data for the form structure and validation details.

```jsx
<VsForm
    dataUrl="https://static.visitscotland.com/forms/vs-3331/simpleForm.json"
    messagingUrl="http://172.28.74.124:5555/messaging.json"
    countryListUrl="http://172.28.74.124:5555/countries.json"
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
