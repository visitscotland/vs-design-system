<!-- eslint-disable -->
<template>
    <VsList 
        unstyled
        class="vs-mega-nav__dropdown d-none"
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
};
</script>

<style lang="scss">
@import "~bootstrap/scss/type";

.vs-mega-nav__dropdown {
    background: pink;
}

</style>

<docs>
  ```jsx

  ```
</docs>
