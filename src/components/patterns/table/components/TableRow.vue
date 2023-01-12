<template>
    <BTr
        v-bind="$attrs"
    >
        <!-- Default slot for table row content -->
        <slot />
    </BTr>
</template>

<script>
import { BTr } from 'bootstrap-vue';

/**
 *
 * @displayName Table Row
 */
export default {
    name: 'VsTableRow',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BTr,
    },
};
</script>
