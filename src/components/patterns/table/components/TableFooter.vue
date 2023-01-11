<template>
    <BTfoot
        v-bind="$attrs"
    >
        <!-- Default slot for table footer content -->
        <slot />
    </BTfoot>
</template>

<script>
import { BTfoot } from 'bootstrap-vue';

/**
 *
 * @displayName Table Footer
 */
export default {
    name: 'VsTableFooter',
    status: 'prototype',
    release: '0.0.1',
    components: {
        BTfoot,
    },
};
</script>

<style lang="scss">
.vs-table{
    .table-sm {
        tfoot{
            td{
                font-size: $font-size-3;
                font-weight: $font-weight-light;
                padding: $spacer-2 $spacer-1;
            }
        }
    }
}

</style>
