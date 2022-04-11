## Usage
The reCaptcha should be used when validating user inputs that will be submitted
to a database. This gets a response from Google although further verification should
be made via API Request to check the reponse.

### Default
```jsx
    <vs-recaptcha
        site-key="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
    />
```

### Invalid state
If a form is attempted to be submitted without the reCaptcha being checked and verified,
it will appear in its invalid state.
```jsx
    <vs-recaptcha
        :invalid="true"
        site-key="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
    />
```
