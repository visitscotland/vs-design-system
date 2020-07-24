<template>
    <VsCol
        tag="dt"
        :class="[
            $style.root,
            {
                [$style.inline]: inline,
                [$style.styled]: !inline
            }
        ]"
    >
        <slot />
    </VsCol>
</template>

<script>
import { VsCol } from '@components/elements/layout';
/**
 * Renders a description list - dl element
 */

export default {
    name: 'VsDescriptionListTerm',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsCol,
    },
    /**
     * Injects inline prop provided by DescriptionList
     */
    inject: ['inline'],
};
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";
</style>

<style lang="scss" module>
.root{
    // Responsive columns to achieve default 2 column list layout
    &.styled {
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
    &.inline {
        &::after {
            content: ": ";
        }
    }
}
</style>
