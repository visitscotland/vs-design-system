<template>
    <div
        class="vs-icon-list"
        data-test="vs-icon-list"
    >
        <div
            class="vs-icon-list__title"
            v-if="title"
        >
            {{ title }}
        </div>
        <ul class="vs-icon-list__list mb-0">
            <slot />
        </ul>
    </div>
</template>

<script>
/**
 * An icon list can be used where there is a list icons with a caption with optional heading.
 * An example use is to create a list of key facilities for a product.
 *
 * @displayName Icon List
 */

export default {
    name: 'VsIconList',
    status: 'prototype',
    release: '0.0.1',
    props: {
        /**
         * The title for the icon list
         */
        title: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
.vs-icon-list {
    text-align: center;

    .vs-icon-list__title {
        font-size: $font-size-4;
        font-weight: $font-weight-normal;
        margin-bottom: $spacer-5;
    }

    .vs-icon-list__list {
        display: inline-block;
        margin: 0 auto;
        padding: 0;
        text-align: left;
    }
}
</style>
