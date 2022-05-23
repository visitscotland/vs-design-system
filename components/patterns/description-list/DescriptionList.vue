<template>
    <VsRow
        tag="dl"
        class="vs-description-list"
        :class="{ 'list-inline': inline }"
    >
        <!-- @slot The description content goes here -->
        <slot />
    </VsRow>
</template>

<script>
import { VsRow } from '@components/elements/grid';
/**
 * A description list can be used where there is a list of terms and descriptions.
 *
 * The default list has 2 columns that collapse to stacked lists on smaller
 * screens. There is also an Inline option that can be passed in for a different style.
 *
 * @displayName Description List
 */

export default {
    name: 'VsDescriptionList',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * Option to create inline list
         */
        inline: {
            type: Boolean,
            default: false,
        },
    },
    /**
     * Provides inline prop to be injected to child components
     */
    provide() {
        return {
            inline: this.inline,
        };
    },
};
</script>

<style lang="scss">
.vs-description-list {

    // turns offset column off for first item after a term to avoid layout errors
    .vs-description-list__term+.vs-description-list__detail {
        @include make-col-offset(0);
    }
}
</style>
