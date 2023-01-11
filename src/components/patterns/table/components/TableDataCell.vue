<template>
    <BTd
        data-test="vs-table-data-cell"
        :stacked-heading="stackedHeading"
        v-bind="$attrs"
    >
        <!-- Default slot for table data cell content -->
        <slot />
    </BTd>
</template>

<script>
import { BTd } from 'bootstrap-vue';

/**
 *
 * @displayName Table Data Cell
 */
export default {
    name: 'VsTableDataCell',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BTd,
    },
    props: {
        /**
         * The heading to read when the table is in stacked mode
         */
        stackedHeading: {
            type: String,
            default: '',
        },
    },
};
</script>
