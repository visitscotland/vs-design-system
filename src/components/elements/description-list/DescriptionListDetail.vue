<template>
    <dd class="vs-description-list__detail" :class="{ 'list-inline-item': inline }">
        <slot />
    </dd>
</template>

<script>
/**
 * TODO: Document usage
 */

export default {
    name: "VsDescriptionListDetail",
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

.vs-description-list__detail {
    margin-bottom: 0;
}
</style>
