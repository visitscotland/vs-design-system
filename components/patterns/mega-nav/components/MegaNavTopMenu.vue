<template>
    <VsList
        unstyled
        class="vs-mega-nav-top-menu"
        data-test="vs-mega-nav-top-menu"
    >
        <!-- @slot Default slot for top menu items -->
        <slot />
    </VsList>
</template>

<script>
/**
 *  Mega nav top level menu list with a slot for top level menu items
 *
 * @displayName MegaNav Top Menu
 */
export default {
    name: 'VsMegaNavTopMenu',
    status: 'prototype',
    release: '0.1.0',
};
</script>

<style lang="scss">

.vs-mega-nav-top-menu {
    &.vs-list--unstyled{
        width: 100%;
        display: flex;
        margin-top: $spacer-3;
    }
}

 @include no-js {
    .vs-mega-nav-top-menu {
        &.vs-list--unstyled{
            display: block;
        }
    }
}

</style>

<docs>
    ```[import](../__examples__/meganav.example.vue)
    ```
</docs>
