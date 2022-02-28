<template>
    <div>
        <h1>Recaptcha</h1>
        this is it:
        <VueRecaptcha
            :sitekey="siteKey"
            @verify="verified"
        />
    </div>
</template>

<script>
import { VueRecaptcha } from 'vue-recaptcha';

/**
 * Google Recaptcha widget. Returns a response to be verified
 * by back end.
 *
 * @displayName Recaptcha
 */

export default {
    name: 'VsRecaptcha',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VueRecaptcha,
    },
    props: {
        siteKey: {
            type: String,
            required: true,
        },
    },
    methods: {
        verified(response) {
            console.log(response);

            this.$emit('verified', response);
        },
    },
};
</script>

<docs>
```jsx
    <vs-recaptcha
        sitekey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
    />
```
</docs>
