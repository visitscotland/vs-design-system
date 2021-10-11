<template>
    <div
        class="vs-psr"
        id="inline-search-container"
    />
</template>

<script>
/**
 * Prototypes are wrappers used to visually communicate parts
 * of the product or actions that are still unavailable.
 *
 * This is a development only tool to help visualise
 * components/functionalities to be finished.
 *
 * @displayName Prototype
 */

export default {
    name: 'VsPsr',
    status: 'prototype',
    release: '0.0.1',
    mounted() {
        const recaptchaScript = document.createElement('script');
        recaptchaScript.setAttribute('src', 'https://develop.visitscotland.com/api/dev/ui/product-search/static/js/bundle.js');
        document.head.appendChild(recaptchaScript);
    },
};
</script>

<style lang="scss">

</style>

<docs>
```jsx
    <h4>Originals</h4>
```
</docs>
