<!-- eslint-disable -->
<template>
    <ul
        class="vs-mega-nav__list list-unstyled"
    >
        <slot />
    </ul>
</template>

<script>

/**
 * The nearest ancestor with position relative
 */
export default {
    name: 'VsMegaNavList',
    status: 'prototype',
    release: '0.1.0',
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
