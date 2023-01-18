<template>
    <BTbody
        v-bind="$attrs"
    >
        <!-- Default slot for table body content -->
        <slot />
    </BTbody>
</template>

<script>
import { BTbody } from 'bootstrap-vue';

/**
 *
 * @displayName Table Body
 */
export default {
    name: 'VsTableBody',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BTbody,
    },
};
</script>
