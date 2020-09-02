<template>
    <VsRow>
        <VsCol cols="12">
            <BNavbarNav class="vs-mega-nav__mobile-list">
                <slot />
            </BNavbarNav>
        </VsCol>
    </VsRow>
</template>

<script>

/**
 * WORK IN PROGRESS
 */
import { VsRow, VsCol } from '@components/elements/layout';
import { BNavbarNav } from 'bootstrap-vue';

export default {
    name: 'VsMegaNavMobileList',
    status: 'prototype',
    release: '0.1.0',
    components: {
        BNavbarNav,
        VsRow,
        VsCol,
    },
};
</script>

<style lang="scss">
@import "~bootstrap/scss/type";

.vs-mega-nav__mobile-list {
    margin-top: $spacer-2;
    padding: 0 0;
}

</style>

<docs>
  ```jsx

  ```
</docs>
