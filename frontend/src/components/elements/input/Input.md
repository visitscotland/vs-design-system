## Usage
Input can take the following HTML5 types: `text`, `password`, `number`,
`email`, `url`, `search` and `tel`.

An input requires a name value to identify the element and tie it to a label.

### Default
Include a `fieldName` prop to identify the element and programmatically tie it to a label.

```jsx
    <label for="default">A default input</label>
    <VsInput
       fieldName="default"
    />
```

### Hint text
Hint text should be used to give a user extra information about the input's purpose.
Avoid placing hint text inside the label text.

```jsx
    <label for="default">Email address</label>
    <VsInput
        id="hint-text-example"
        class="mb-5"
        type="email"
        field-name="hint-text-example"
        hint-text="We'll only use this to email you our newsletter."
    />
```

### Placeholder text
Placeholder text can be used to provide an example of the type of data that is expected.
```jsx
    <label for="placeholder-example">Email address</label>
    <VsInput
        id="placeholder-example"
        placeholder="eg: john.smith@gmail.com"
        class="mb-5"
        type="email"
        field-name="placeholder-example-example"
    />
```

### Optional inputs
Inputs in a form that aren't defined as required in the validation rules should be automatically
appended with '(optional)' in their label.

```jsx
    <label for="optional-example">Last name (optional)</label>
    <VsInput
        id="optional-example"
        class="mb-5"
        field-name="optional-example"
    />
```

### Invalid state
Invalid state is visible when a user exits the field or attempts to submit a form
without meeting validation rules.

```jsx
    <label for="invalid-example">Your name</label>
    <span
        class="error d-block"
    >
        This field is required
    </span>
    <VsInput
        id="invalid-example"
        class="mb-5"
        field-name="invalid-example"
        :invalid="true"
    />
```

### Validation rules and messaging
A set of generic validation messaging can be supplied to the `genericValidation` prop. This will be used
when there isn't a specific corresponding validation message provided to the `validationMessages` prop.

This is to allow a single set of default messaging to be used for a form, removing the need for repeating
the same message and potentially having to provide translations each time.

The example below shows a specific message provided for minimum length errors, but a generic one for
required. Interact with the field to trigger the validation. Validation is triggered on element blur until
the element has been interacted with, after which it is triggered on a change in value.

```jsx
    <label for="validation-example">Please enter your name</label>
    <VsInput
        id="validation-example"
        class="mb-5"
        field-name="validation-example"
        :validation-rules="{'required': true, 'minLength': 3}"
        :generic-validation="{'required': 'This field is required'}"
        :validation-messages="{'minLength': 'Your name must be at least 3 letters long'}"
    />
```

## Accessibility

- Use a label to explain exactly what data is required. Use hint text to provide further information
- A `fieldName` prop is required to ensure that the element is connected to a label
- Use placeholder text where appropriate to show examples of what an input should contain
- Append label text with "(optional)" if it isn't required to notify users
- Ensure that validation message is descriptive enough so users know how to enter a correct value
- Uses `aria-describedby` to ensure that validation messages are linked to the element they refer to