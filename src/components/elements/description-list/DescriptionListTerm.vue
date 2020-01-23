<template>
    <dt
        class="vs-description-list__term"
        :class="{ 'list-inline-item': inline, 'vs-description-list__term--styled': !inline }"
    >
        <slot />
    </dt>
</template>

<script>
/**
 * TODO: Document usage
 */

export default {
    name: "VsDescriptionListTerm",
    status: "prototype",
    release: "0.0.1",
    /**
     * Injects inline prop provided by DescriptionList
     */
    inject: ["inline"],
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-description-list__term {
    // Responsive columns to achieve default 2 column list layout
    &.vs-description-list__term--styled {
        font-family: $headings-font-family;
        @include make-col(4);

        @include media-breakpoint-down(md) {
            @include make-col(6);
        }

        @include media-breakpoint-down(sm) {
            border-bottom: 1px solid $color-gray-tint-5;
            margin-bottom: $spacer-2;
            padding: $spacer-1 0;
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
