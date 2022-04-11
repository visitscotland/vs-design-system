## Usage

A checkbox list should be used when a user needs to select one or multiple options from a list or toggle a single option on and off.

Checkboxes should work independently from each other and each must have a label and unique field name provided.

If only one item needs to be selected from a list, use a radio button.

### Single checkbox
```jsx
    <VsCheckbox
        field-name="checkbox-examplee"
        value="accepted"
        id="checkbox-example"
        label="I accept the terms and conditions"
        class="mb-6"
    />
```

### Checkbox group

```jsx
    <VsCheckbox
        field-name="checkbox-example-2"
        value="example-2"
        id="checkbox-2"
        label="I accept the terms and conditions"
        class="mb-6"
    />

    <VsCheckbox
        field-name="checkbox-example-3"
        value="example-3"
        id="checkbox-3"
        label="By ticking this box you are indicating your consent for VisitScotland
        to use your email address to send you our e-newsletter on a regular basis.
        You can unsubscribe at any time via the link in the email. We will process
        your details in accordance with our privacy policy"
        class="mb-6"
    />
```
### Hint text
Hint text should only be used if a legend is also used.

```jsx
    <fieldset>
        <legend>A checkbox with hint text</legend>
        <VsCheckbox
            field-name="checkbox-example-4"
            value="example-4"
            id="checkbox-4"
            label="I accept the terms and conditions"
            hint-text="Checkbox hint text"
            class="mb-6"
        />
    </fieldset>
```

### Invalid state

```jsx
    <span
        class="error d-block"
    >
        This field is required
    </span>
    <VsCheckbox
        field-name="checkbox-example-invalid"
        value="example-5"
        id="checkbox-invalid"
        label="I accept the terms and conditions"
        :invalid="true"
    />
```