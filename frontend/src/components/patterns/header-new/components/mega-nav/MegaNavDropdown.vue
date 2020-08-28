<!-- eslint-disable -->
<template>
    <VsList 
        unstyled
        class="vs-mega-nav__dropdown"
        :class="{
            'vs-mega-nav__dropdown--open': isOpen,
        }"
    >
        <slot />
    </VsList>
</template>

<script>

/**
 *  Mega nav top level menu list
 */

import VsList from '@components/elements/list/List';

export default {
    name: 'VsMegaNavDropdown',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsList,
    },
    props: {
        isOpen: {
            type: Boolean,
            default: false,
        },
    },
};
</script>

<style lang="scss">
@import "~bootstrap/scss/type";

.vs-mega-nav__dropdown {
    z-index: 1;
    background: #f5f6fa;
    width: 100%;
    padding: 24px 0;
    position: absolute;
    top: 55px;
    left: 0;
    min-height: 200px;
    box-shadow: 0 3px 7px rgba(0,0,0,.16);
    &--open{
        display: block;
    }
}

</style>

<docs>
  ```jsx

  ```
</docs>
