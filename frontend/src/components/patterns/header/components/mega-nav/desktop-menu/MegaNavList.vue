<template>
    <ul
        unstyled
        class="mega-nav-list"
        data-test="mega-nav-list"
    >
        <!-- @slot Slot for nav list heading  -->
        <slot name="navListHeading" />

        <!-- @slot Slot for nav list items  -->
        <slot name="navListItems" />
    </ul>
</template>

<script>
export default {
    name: 'VsMegaNavList',
    status: 'prototype',
    release: '0.1.0',
};
</script>

<style lang="scss">
    .mega-nav-list{
        // background: grey;
        padding: 0;
        overflow: hidden;
        border-radius: 5px;
        margin: 0 0 8px 8px;
        color: white;
        position: relative;
    }
</style>

<docs>
  ```jsx

  ```
</docs>
