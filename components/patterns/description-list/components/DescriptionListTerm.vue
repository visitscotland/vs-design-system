<template>
    <VsCol
        tag="dt"
        class="vs-description-list-term"
        :class="{
            'list-inline-item': inline,
            'vs-description-list-term--styled': !inline
        }"
    >
        <slot />
    </VsCol>
</template>

<script>
/**
 * TODO: Document usage
 *
 * @displayName Description List Term
 */

export default {
    name: 'VsDescriptionListTerm',
    status: 'prototype',
    release: '0.0.1',
<<<<<<< HEAD
    components: {
        VsCol,
    },
=======
>>>>>>> d528b0ebf615aaee3e3cd0c677defd6c34d85709
    /**
     * Injects inline prop provided by DescriptionList
     */
    inject: ['inline'],
};
</script>

<style lang="scss">
.vs-description-list-term {
    // Responsive columns to achieve default 2 column list layout
    &.vs-description-list-term--styled {
        font-family: $headings-font-family;
        @include make-col(4);

        @include media-breakpoint-down(md) {
            @include make-col(6);
        }

        @include media-breakpoint-down(sm) {
            border-bottom: 1px solid $color-gray-tint-5;
            margin-bottom: $spacer-2;
            @include make-col(12);
        }
    }

    // Adds colon separator after inline term for consistency
    &.list-inline-item {
        &::after {
            content: ": ";
        }
    }
}
</style>
