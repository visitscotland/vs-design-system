<template>
    <BTh
        data-test="vs-table-header-cell"
        :stacked-heading="stackedHeading"
        v-bind="$attrs"
    >
        <!-- Default slot for table header cell content -->
        <slot />
    </BTh>
</template>

<script>
import { BTh } from 'bootstrap-vue';

/**
 *
 * @displayName Table Header Cell
 */
export default {
    name: 'VsTableHeaderCell',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BTh,
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

<style lang="scss">
.vs-table{
    .table {
        th{
            border-top:0;
        }
        thead th{
            border-bottom: 0;
        }
    }
}
</style>
