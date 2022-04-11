## Usage

```jsx
<BsWrapper>
    <legend>Checkbox legend</legend>
    <VsCheckbox
        field-name="checkbox-example"
        value="accepted"
        id="checkbox-example"
        label="I accept the terms and conditions"
        hint-text="Checkbox hint text"
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
</BsWrapper>
```

### Invalid state

```jsx
<BsWrapper>
    <VsCheckbox
        field-name="checkbox-example"
        value="accepted"
        id="checkbox-invalid-example"
        label="I accept the terms and conditions"
        :invalid="true"
    />
</BsWrapper>
```