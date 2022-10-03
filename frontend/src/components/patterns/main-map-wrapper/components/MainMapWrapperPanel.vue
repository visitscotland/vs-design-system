<template>
    <div
        data-test="vs-main-map-wrapper-panel"
        class="vs-main-map-wrapper-panel"
    >
        <div class="vs-main-map-wrapper-panel__close">
            <VsButton
                icon-only
                icon="close"
                size="md"
                variant="transparent"
                @click.native="closePanel"
                data-test="vs-main-map-wrapper-panel--btn"
            >
                <span class="sr-only">
                    <slot name="closePanelText" />
                </span>
            </VsButton>
        </div>
    </div>
</template>

<script>
import VsButton from '@components/elements/button/Button/';

/**
 * Renders a side panel for the map wrapper component
 *
 * @displayName Main Map Wrapper Panel
 */

export default {
    name: 'VsMainMapWrapperPanel',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButton,
    },
    methods: {
        /**
         * Emits 'close-panel'
         */
        closePanel() {
            this.$emit('close-panel');
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper-panel {
        padding: $spacer-6;

        &__close {
            display: flex;
            justify-content: flex-end;

            @include media-breakpoint-up(md) {
                display: none;
            }
        }
    }
</style>
