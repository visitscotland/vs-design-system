<!-- eslint-disable -->
<template>
    <BNavbarNav>
       <slot />
    </BNavbarNav>
</template>

<script>

/**
 * The nearest ancestor with position relative
 */
import { BNavbarNav } from 'bootstrap-vue';

export default {
    name: 'VsMegaNavMobileList',
    status: 'prototype',
    release: '0.1.0',
    components: {
        BNavbarNav,
    },
};
</script>

<style lang="scss">
@import "~bootstrap/scss/type";

.vs-mega-nav__list {
    width: 100%;
    display: flex;
}

</style>

<docs>
  ```jsx

  ```
</docs>
