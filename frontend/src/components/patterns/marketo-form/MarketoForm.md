## Usage
The Marketo Form component is used to submit user inputted data to a Marketo instance.

The form is created programatically from a group of JSON data files which provide data for
input types, labels, validation rules, validation messaging, recaptcha, submit button and details of
where the form should be submitted to.

*Please note this form will not successfully submit to Marketo as the necessary third party scripts aren't included on this page.*

```jsx
<VsMarketoForm
    dataUrl="../../../fixtures/marketo-forms/form-example.json"
    messagingUrl="https://static.visitscotland.com/forms/common/messaging.json"
    countryListUrl="https://static.visitscotland.com/forms/common/countries.json"
    recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
    marketo-instance="marketo-instance-code"
    munchkin-id="munchkin-id-code"
    language="en"
    :is-prod="false"
>
    <template slot="submitError">
        We're sorry there's been a problem, please try again later. You can also ask to subscribe by sending an email to <a href='mailto:info@visitscotland.com'>info@visitscotland.com</a>.
    </template>

    <template slot="submitting">
        We're just submitting your form
    </template>
</VsMarketoForm>
```

### Language
The Marketo Form is designed to support multiple languages. By using the `language` prop
the form can request translated content from the form data JSON and the messaging data JSON.

*Please note that due to the Recaptcha already being used in English on this page, a translated version isn't shown below. This will be automatically translated into the language when used in isolation.*

```jsx
<VsMarketoForm
    dataUrl="../../../fixtures/marketo-forms/form-example.json"
    messagingUrl="https://static.visitscotland.com/forms/common/messaging.json"
    countryListUrl="https://static.visitscotland.com/forms/common/countries.json"
    recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
    marketo-instance="marketo-instance-code"
    munchkin-id="munchkin-id-code"
    language="de"
    :is-prod="false"
>
    <template slot="submitError">
        We're sorry there's been a problem, please try again later. You can also ask to subscribe by sending an email to <a href='mailto:info@visitscotland.com'>info@visitscotland.com</a>.
    </template>

    <template slot="submitting">
        We're just submitting your form
    </template>
</VsMarketoForm>
```

### Optional Inputs
It's good accessibility practice to highlight that a form element is an optional field (rather that required). The
Marketo Form component automatically appends '(optional)' to an element if the validation rules supplied don't
define it as a required element.

In the example below, the 'Last name' field is not defined as required.

```jsx
<VsMarketoForm
    dataUrl="../../../fixtures/marketo-forms/optional-field.json"
    messagingUrl="https://static.visitscotland.com/forms/common/messaging.json"
    countryListUrl="https://static.visitscotland.com/forms/common/countries.json"
    recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
    marketo-instance="marketo-instance-code"
    munchkin-id="munchkin-id-code"
    language="en"
    :is-prod="false"
>
    <template slot="submitError">
        We're sorry there's been a problem, please try again later. You can also ask to subscribe by sending an email to <a href='mailto:info@visitscotland.com'>info@visitscotland.com</a>.
    </template>

    <template slot="submitting">
        We're just submitting your form
    </template>
</VsMarketoForm>
```

### Conditional Inputs
An input can be given a value of 'conditional' in the data supplied, along with conditions it needs to meet to appear.
This means it will only appear once another element's value meets a certain criteria. More than one value can be
used to show the element under a range of values.

In the example below the conditional field will only shown if 'Yes' is chosen in the initial select element.

```jsx
<VsMarketoForm
    dataUrl="../../../fixtures/marketo-forms/conditional-field.json"
    messagingUrl="https://static.visitscotland.com/forms/common/messaging.json"
    countryListUrl="https://static.visitscotland.com/forms/common/countries.json"
    recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
    marketo-instance="marketo-instance-code"
    munchkin-id="munchkin-id-code"
    language="en"
    :is-prod="false"
>
    <template slot="submitError">
        We're sorry there's been a problem, please try again later. You can also ask to subscribe by sending an email to <a href='mailto:info@visitscotland.com'>info@visitscotland.com</a>.
    </template>

    <template slot="submitting">
        We're just submitting your form
    </template>
</VsMarketoForm>
```

### Status Messages
The Marketo Form component will automatically update when a form is submitting, has been submitted or if there was
an error with the submission.

These messages should clearly inform the user to the status of the form and, if something has gone wrong, offer alternatives
or assistance.

Due to the form not being able to submit in this environment, status messaging is not able to be shown.


### No-JS Message
As the form requires Javascript to pull in data and to submit, it will not function if Javascript is disabled.

A no-JS message is defined in the form's JSON data as specific instructions may be needed to allow people
to complete the form's intended functionality in another way.

```jsx
<div class="no-js">
    <VsMarketoForm
        dataUrl="../../../fixtures/marketo-forms/conditional-field.json"
        messagingUrl="https://static.visitscotland.com/forms/common/messaging.json"
        countryListUrl="https://static.visitscotland.com/forms/common/countries.json"
        recaptchaKey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
        marketo-instance="marketo-instance-code"
        munchkin-id="munchkin-id-code"
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
</div>
```

## Accessibility
- Use placeholder text where appropriate to show examples of what an input should contain.
- Ensure that validation message is descriptive enough so users know how to enter a correct value.
- Provide clear and informative status messages. If something has gone wrong, give a user an alternative to completing the form.
- Provide a non-Javascript message that allows the same outcome to be achieved using an alternative method.
- Form elements are programmatically created, ensuring that accessibility rules are adhered to.
