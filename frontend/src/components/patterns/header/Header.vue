<template>
    <header
        class="vs-header"
        data-test="vs-header"
    >
        <!-- @slot Global menu bar slot  -->
        <slot name="globalMenu" />

        <!-- @slot Main navigation bar slot  -->
        <slot name="megaNav" />
    </header>
</template>

<script>
export default {
    name: 'VsHeader',
    status: 'prototype',
    release: '0.1.0',
};
</script>

<style lang="scss">

.vs-header{
    position: relative;
    z-index: 10;
}

</style>

<docs>
    ```[import](./header.example.vue)
    ```
</docs>
