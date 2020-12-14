<template>
    <BNavItem
        href="#"
        class="vs-mega-nav-mobile-list-item"
    >
        <slot />
    </BNavItem>
</template>

<script>
import { BNavItem } from 'bootstrap-vue';

/**
 * WORK IN PROGRESS
 *
 * @displayName MegaNav Mobile List Item
 */
export default {
    name: 'VsMegaNavMobileListItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        BNavItem,
    },
};
</script>

<style lang="scss">
.vs-mega-nav-mobile-list-item{

    .nav-link{
        display: block;
        padding: $spacer-3 $spacer-3 $spacer-3 $spacer-2;
        border-bottom: 1px solid #F2F2F2;
        font-size: $display2-size;
        color: $color-base-text!important;
        text-decoration: none;
        border-left: 12px solid transparent;

        &:hover {
            border-left: 12px solid $color-pink;
        }

        &:focus {
            outline: 1px solid $color-pink;
        }
    }
}
</style>

<docs>
  ```jsx

  ```
</docs>
