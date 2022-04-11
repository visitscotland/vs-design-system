## Usage
The select component can be used with or without a form element to capture data input from
a user. Optional validation rules and messages are supplied via objects in the props. The list of
options are passed in using a required object.

### Default

```jsx
    <label for="select-example">A select element</label>
    <VsSelect
        :options="[
          { value: null, text: 'Please select an option', selected: 'true' },
          { value: 'a', text: 'This is First option' },
          { value: 'b', text: 'Selected Option' },
          { value: { C: '3PO' }, text: 'This is an option with object value' },
          { value: 'd', text: 'This one is disabled', disabled: true }
        ]"
        field-name="select-example"
        class="mb-6"
    />
```

### Hint text
Hint text can be added to give extra information to the user.

```jsx
    <label for="hint-text-example">A hint text example</label>
    <VsSelect
        :options="[
            { value: null, text: 'Please select an option', selected: 'true' },
            { value: 'a', text: 'This is First option' },
            { value: 'b', text: 'Selected Option' },
            { value: { C: '3PO' }, text: 'This is an option with object value' },
            { value: 'd', text: 'This one is disabled', disabled: true }
        ]"
        field-name="hint-text-example"
        hint-text="This is some hint text"
    />
```

### Invalid state
Invalid state is visible when a user changes the field or attempts to submit a form
without meeting validation rules.

```jsx
<label for="select-invalid">An invalid select element</label>
<VsSelect
    field-name="select-invalid"
    :options="[
        { value: null, text: 'Please select an option', selected: 'true' },
        { value: 'a', text: 'This is First option' },
        { value: 'b', text: 'Selected Option' },
        { value: { C: '3PO' }, text: 'This is an option with object value' },
        { value: 'd', text: 'This one is disabled', disabled: true }
    ]"
    :invalid="true"
    size="lg"
/>
