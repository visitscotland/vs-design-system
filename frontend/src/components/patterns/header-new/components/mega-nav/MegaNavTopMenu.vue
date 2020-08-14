<!-- eslint-disable -->
<template>
    <VsList 
        unstyled
        class="vs-mega-nav-top-menu"
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
    name: 'VsMegaNavTopMenu',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsList,
    },
};
</script>

<style lang="scss">
@import "~bootstrap/scss/type";

.vs-mega-nav-top-menu {
    width: 100%;
    display: flex;
}

</style>

<docs>
  ```jsx

  ```
</docs>
