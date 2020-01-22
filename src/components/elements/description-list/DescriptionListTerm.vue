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
    props: {
        /**
         * Option to create inline list
         */
        inline: {
            type: Boolean,
        },
    },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/type";

.vs-description-list__term {
    &.vs-description-list__term--styled {
        font-family: $headings-font-family;
        border-bottom: 1px solid $color-gray-tint-5;
        margin-bottom: $spacer-3;
        padding: $spacer-1 0;
    }

    &.list-inline-item {
        &::after {
            content: ": ";
        }
    }
}
</style>
