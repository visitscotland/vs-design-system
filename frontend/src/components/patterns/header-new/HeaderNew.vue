<!-- eslint-disable -->
<template>
    <header>
        <!-- @slot Global menu bar slot  -->
        <slot name="global-navbar" />
        
        <!-- @slot Main navigation bar slot  -->
        <slot name="mega-nav" />
    </header>
</template>

<script>
export default {
    name: 'VsHeaderNew',
    status: 'prototype',
    release: '0.1.0',
};
</script>

<style lang="scss"></style>

<docs>
    ```[import](./header.new.example.vue)
    ```
</docs>
