## Usage
An input which shows drop down list of options on interaction. One option can be chosen by the user.

An select element requires a name value to identify the element and tie it to a label, and an `options` object
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

### Hint text
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

### Optional inputs
Select elements in a form that aren't defined as required in the validation rules should be automatically
appended with '(optional)' in their label.

```jsx
    <label for="optional-example">Last name (optional)</label>
     <VsSelect
        :options="[
            { value: 'england', text: 'England' },
            { value: 'northernireland', text: 'Northern Ireland' },
            { value: 'scotland', text: 'Scotland' },
            { value: 'wales', text: 'Wales' },
            { value: 'restofworkd', text: 'Rest of the world' }
        ]"
        field-name="optional-example"
    />
```


### Invalid state
Invalid state is visible when a user changes the field or attempts to submit a form
without meeting validation rules.

```jsx
<label for="invalid-example">An invalid select element</label>
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

### Validation rules and messaging
A set of generic validation messaging can be supplied to the `genericValidation` prop. This will be used
when there isn't a specific corresponding validation message provided to the `validationMessages` prop.

This is to allow a single set of default messaging to be used for a form, removing the need for repeating
the same message and potentially having to provide translations each time.

The example below shows a specific validation message overriding a generic one  when 'Rest of the world' is selected.

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

Whereas this example shows a generic validation message when 'Rest of the world' is selected, as a specific message is not supplied

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
        :validation-rules="{'required': true, 'minLength': 3}"
        :generic-validation="{'required': 'This field is required'}"
        :validation-messages="{'minLength': 'Your name must be at least 3 letters long'}"
    />
```


## Accessibility
