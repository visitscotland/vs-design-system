<template>
    <VueRecaptcha
        :sitekey="siteKey"
        @verify="verified"
        class="vs-recaptcha"
        :class="invalid ? 'vs-recaptcha--invalid' :''"
        :language="language"
    />
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
        /**
         * recaptcha site key string
         */
        siteKey: {
            type: String,
            required: true,
        },
        /**
         * whether or not the form is invalid - this should
         * also only be true when an attempt at submission has
         * been made
         */
        invalid: {
            type: Boolean,
            default: false,
        },
        /**
         * language to show text in
         */
        language: {
            type: String,
            default: 'en',
        },
    },
    methods: {
        verified(response) {
            this.$emit('verified', response);
        },
    },
};
</script>

<style type="scss">
    .vs-recaptcha--invalid {
        border: red 3px solid !important;
    }
</style>

<docs>
```jsx
    <vs-recaptcha
        sitekey="6LfqqfcZAAAAACbkbPaHRZTIFpKZGAPZBDkwBKhe"
        language="de"
    />
```
</docs>
