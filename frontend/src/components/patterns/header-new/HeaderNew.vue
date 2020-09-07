<template>
    <header class="vs-header">
        <!-- @slot Global menu bar slot  -->
        <slot name="globalNav" />

        <!-- @slot Main navigation bar slot  -->
        <slot name="megaNav" />
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
