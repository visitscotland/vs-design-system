## Usage
Use a select element to shows drop down list of options on interaction. One option can be chosen by the user.

A select element requires a `fieldName` prop value to identify the element and tie it to a label, and an `options` object
containing the values of the list of options.

### Default

```jsx
    <label for="select-example">Please choose a country</label>
    <VsSelect
        :options="[
            { value: 'england', text: 'England' },
            { value: 'northernireland', text: 'Northern Ireland' },
            { value: 'scotland', text: 'Scotland' },
            { value: 'wales', text: 'Wales' }
        ]"
        field-name="select-example"
    />
```

### Hint Text
Hint text should be used to give a user extra information about the input's purpose.
Avoid placing hint text inside the label text.

```jsx
    <label for="hint-text-example">Please choose a country</label>
    <VsSelect
        :options="[
            { value: 'england', text: 'England' },
            { value: 'northernireland', text: 'Northern Ireland' },
            { value: 'scotland', text: 'Scotland' },
            { value: 'wales', text: 'Wales' },
            { value: 'restofworkd', text: 'Rest of the world' }
        ]"
        field-name="hint-text-example"
        hint-text="If you're not based in the UK, please choose 'Rest of the world'"
    />
```

### Optional Inputs
Select elements in a form that aren't defined as required in the validation rules should be
appended with '(optional)' in their label.

```jsx
    <label for="optional-example">How would you like to be contacted? (optional)</label>
     <VsSelect
        :options="[
            { value: 'phone', text: 'Land line' },
            { value: 'mobile', text: 'Mobile' },
            { value: 'email', text: 'Email' }
        ]"
        field-name="optional-example"
    />
```


### Invalid State
Invalid state is visible when a user changes the field or attempts to submit a form
without meeting validation rules.

The checkbox is given a red border and a red validation message is shown to give a visual clue
of the error.

```jsx
<label for="invalid-example">Please choose a country</label>
<span class="error d-block">
    This field is required
</span>
<VsSelect
    field-name="invalid-example"
    :options="[
        { value: 'england', text: 'England' },
        { value: 'northernireland', text: 'Northern Ireland' },
        { value: 'scotland', text: 'Scotland' },
        { value: 'wales', text: 'Wales' },
        { value: 'restofworld', text: 'Rest of the world' }
    ]"
    :invalid="true"
/>
```

### Validation
A set of generic validation messaging can be supplied to the `genericValidation` prop. This will be used
when there isn't a specific corresponding validation message provided to the `validationMessages` prop.

This is to allow a single set of default messaging to be used for a form, removing the need for repeating
the same message and potentially having to provide translations each time.

Validation uses the Vuelidate plugin. [For a full list of validation rules see the Vuelidate documentation](https://vuelidate.js.org/#validators).

The example below shows a `validationMessages` prop overriding a `genericValidation` prop when 'Rest of the world' is selected.

```jsx
    <label for="validation-example">Please enter your name</label>
    <VsSelect
        field-name="invalid-example"
        :options="[
            { value: 'england', text: 'England' },
            { value: 'northernireland', text: 'Northern Ireland' },
            { value: 'scotland', text: 'Scotland' },
            { value: 'wales', text: 'Wales' },
            { value: 'restofworld', text: 'Rest of the world' }
        ]"
        :validation-rules="{'invalidVal': 'restofworld'}"
        :generic-validation="{'noInvalid': 'Please enter a valid choice'}"
        :validation-messages="{'noInvalid': 'To qualify, you must be based in the UK'}"
    />
```

Whereas this example shows a validation message defined by the `genericValidation` prop when 'Rest of the world' is selected,
as a `validationMessages` prop is not supplied

```jsx
    <label for="validation-example">Please enter your name</label>
    <VsSelect
        field-name="invalid-example"
        :options="[
            { value: 'england', text: 'England' },
            { value: 'northernireland', text: 'Northern Ireland' },
            { value: 'scotland', text: 'Scotland' },
            { value: 'wales', text: 'Wales' },
            { value: 'restofworld', text: 'Rest of the world' }
        ]"
        :validation-rules="{'invalidVal': 'restofworld'}"
        :generic-validation="{'noInvalid': 'Please enter a valid choice'}"
    />
```

### Countries List
If the `countries` prop is set to `true`, the `countryListUrl` prop will be used to get
data for populating the options of the element.

This is to ensure that a standard list of countries will be always be used when need, as
well as translations where appropriate.

In this case, any data provided by the `options` prop will be ignored.

```jsx
    <label for="validation-example">Which country do you live in?</label>
    <VsSelect
        field-name="invalid-example"
        :countries="true"
        countryListUrl="https://static.visitscotland.com/forms/common/countries.json"
    />
```


## Accessibility
- Use a label to explain exactly what data is required. Use hint text to provide further information.
- A `fieldName` prop is required to ensure that the element is connected to a label.
- Append label text with "(optional)" if it isn't required to notify users.
- Ensure that validation messages are descriptive enough so users know how to enter a correct value.
- Uses `aria-describedby` to ensure that validation messages are linked to the element they refer to.
- Has a clear focus state when the element is in focus.