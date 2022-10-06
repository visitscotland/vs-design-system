<template>
    <section
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

        <VsHeading
            level="4"
            class="vs-main-map-categories__heading text-center mt-0"
            v-if="currentHeading !== ''"
            data-test="vs-main-map-categories__heading"
        >
            {{ currentHeading }}
        </VsHeading>

        <VsMainMapWrapperCategories />
    </section>
</template>

<script>
import VsButton from '@components/elements/button/Button/';
import VsHeading from '@components/elements/heading/Heading';
import VsMainMapWrapperCategories from './MainMapWrapperCategories';

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
        VsMainMapWrapperCategories,
        VsHeading,
    },
    props: {
        /**
         * Heading for the categories view
         */
        categoryHeading: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            currentStage: 'category',
        };
    },
    computed: {
        currentHeading() {
            let headingText = '';

            switch (this.currentStage) {
            case 'category':
                headingText = this.categoryHeading;
                break;
            default:
                break;
            }

            return headingText;
        },
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
        position: relative;
        padding: $spacer-11 $spacer-6 $spacer-6;
        border: 1px solid $color-gray;
        height: 100%;

        h4.vs-heading {
            margin-bottom: $spacer-8;
        }

        &__close {
            position: absolute;
            top: $spacer-3;
            right: $spacer-3;
        }

        @include media-breakpoint-up(lg) {
            padding: $spacer-8;
            border-right: none;

            &__close {
                display: none;
            }
        }
    }
</style>
