## Usage
The input component can be used with or without a form element to capture data input from
a user. Optional validation rules and messages are supplied via objects in the props.

### Default
Include a 'fieldName' prop to identify the element and tie it to a label.

```jsx
    <label for="default">A default input</label>
    <VsInput
       fieldName="default"
    />
```

### Sizing
Use the 'size' prop to change the size of the input.

```jsx
    <label for="small">Small</label>
    <VsInput
        id="small"
        placeholder="Enter your name"
        class="mb-5"
        size="sm"
        field-name="input1"
    />
    <label for="medium">Medium (default)</label>
    <VsInput
        id="medium"
        placeholder="Enter your name"
        class="mb-5"
        size="md"
        field-name="input1"
    />
    <label for="large">Large</label>
    <VsInput
        id="large" placeholder="Enter your name" class="mb-5" size="lg" field-name="input3"
    />
```

### Hint text
Hint text can be added to give extra information to the user.

```jsx
    <label for="default">A hint text example</label>
    <VsInput
        id="medium"
        placeholder="Enter your name"
        class="mb-5"
        size="md"
        field-name="input1"
        hint-text="This is some hint text"
    />
```

### Invalid state
Invalid state is visible when a user exits the field or attempts to submit a form
without meeting validation rules.

```jsx
    <label for="default">An invalid input</label>
    <VsInput
        id="medium"
        placeholder="Enter your name"
        class="mb-5"
        size="md"
        field-name="input1"
        :invalid="true"
    />
```
