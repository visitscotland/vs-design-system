<template>
    <BTableSimple
        class="vs-table"
        data-test="vs-table"
        small
        caption-top
        :responsive="responsive"
        :stacked="stacked"
    >
        <caption class="sr-only">
            {{ tableCaption }}
        </caption>

        <!-- Default slot for table data and elements -->
        <slot />
    </BTableSimple>
</template>

<script>
import { BTableSimple } from 'bootstrap-vue';

/**
 * The table component organises and displays information to help a user read and scan it easily.
 *
 * @displayName Table
 */
export default {
    name: 'VsTable',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BTableSimple,
    },
    props: {
        /**
         * The table caption for screen readers
         */
        tableCaption: {
            type: String,
            required: true,
        },
        /**
         * The type of table to display
         * `responsive|stacked`
         */
        tableType: {
            type: String,
            default: 'responsive',
            validator: (value) => value.match(
                /(responsive|stacked)/,
            ),
        },
    },
    computed: {
        responsive() {
            return this.tableType === 'responsive';
        },
        stacked() {
            return this.tableType === 'stacked';
        },
    },
};
</script>

<style lang="scss">
.vs-table{
    .table-sm th, .table-sm td{
        font-size: $font-size-4;
        padding: $spacer-1;
    }

    &.table.b-table{
        &.b-table-stacked{
            > tbody > tr > [data-label]::before{
                text-align: left;
            }
        }
    }
}
</style>
