## Usage

```jsx
    <VsCheckbox
        field-name="checkbox-example"
        value="accepted"
        id="checkbox-example"
        label="I accept the terms and conditions"
        class="mb-6"
    />

    <VsCheckbox
        field-name="checkbox-example-2"
        value="second"
        id="checkbox-example-2"
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
            field-name="checkbox-example-3"
            value="accepted"
            id="checkbox-example-3"
            label="I accept the terms and conditions"
            hint-text="Checkbox hint text"
            class="mb-6"
        />
    </fieldset>
```

### Invalid state

```jsx
    <VsCheckbox
        field-name="checkbox-example"
        value="accepted"
        id="checkbox-invalid-example"
        label="I accept the terms and conditions"
        :invalid="true"
    />
```