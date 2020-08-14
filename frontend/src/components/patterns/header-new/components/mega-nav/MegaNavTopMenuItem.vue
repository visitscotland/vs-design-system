<!-- eslint-disable -->
<template>
    <li class="vs-mega-nav__item">
        <VsButton
            class="vs-mega-nav__button"
            variant="transparent"
        >
            <slot />
        </VsButton>
    </li>
</template>

<script>

/**
 *  Mega nav top level menu button
 */

import VsButton from '@components/elements/button/Button';

export default {
    name: 'VsMegaNavTopMenuItem',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsButton,
    },
};
</script>

<style lang="scss">
@import "~bootstrap/scss/type";

.vs-mega-nav__button{
    text-transform: none;
    position: relative;
}
</style>

<docs>
  ```jsx
  ```
</docs>
