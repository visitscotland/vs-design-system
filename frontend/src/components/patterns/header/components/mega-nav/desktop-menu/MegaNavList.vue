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

        <!-- @slot Slot for nav list heading cta link at bottom of the menu group  -->
        <slot name="navHeadingCtaLink" />
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
        position: relative;
        padding: 0;
        margin: 0 $spacer-6 $spacer-4 0;
        width: 23.1%;
        color: $color-white;

        @include media-breakpoint-up(xl) {
            margin-right: $spacer-9;
            width: 21.8%;
        }

        @include media-breakpoint-up(xxl) {
            margin-right: $spacer-10;
            width: 21.4%;
        }
    }
</style>

<docs>
  ```jsx

  ```
</docs>
