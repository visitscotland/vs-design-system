<!-- eslint-disable -->
<template>
    <BNavItem href="#">
        <slot />
    </BNavItem>
</template>

<script>
import { BNavItem } from 'bootstrap-vue';

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

</style>

<docs>
  ```jsx

  ```
</docs>
