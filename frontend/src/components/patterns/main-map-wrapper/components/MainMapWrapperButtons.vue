<template>
    <div
        class="vs-main-map-wrapper-buttons"
        data-test="vs-main-map-wrapper-buttons"
    >
        <VsButton
            block
            :href="contentData.properties.link"
            data-test="vs-main-map-wrapper-buttons__discover"
        >
            {{ discoverText }} {{ contentData.properties.title }}
        </VsButton>
    </div>
</template>

<script>
import VsButton from '@components/elements/button/Button';

/**
 * Renders link buttons for a map place
 *
 * @displayName Main Map Wrapper Buttons
 */
export default {
    name: 'VsMainMapWrapperButtons',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButton,
    },
    props: {
        /**
         * Data for component content
         */
        contentData: {
            type: Object,
            required: true,
        },
    },
    inject: [
        'discoverText',
    ],
};
</script>

<style lang="scss">
    .vs-main-map-wrapper-buttons {
        position: sticky;
        bottom: 0;
        padding: 1rem 0;
        width: 100%;
        background: white;
        border-top: 4px solid red;
        text-align: center;

        @include media-breakpoint-up(lg) {
            padding: 1rem;
        }
    }
</style>
