<template>
    <BThead
        v-bind="$attrs"
    >
        <!-- Default slot for table header content -->
        <slot />
    </BThead>
</template>

<script>
import { BThead } from 'bootstrap-vue';

/**
 *
 * @displayName Table Head
 */
export default {
    name: 'VsTableHead',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BThead,
    },
};
</script>
