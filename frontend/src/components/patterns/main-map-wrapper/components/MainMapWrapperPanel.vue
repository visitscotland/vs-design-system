<template>
    <section
        data-test="vs-main-map-wrapper-panel"
        class="vs-main-map-wrapper-panel"
    >
        <div
            class="vs-main-map-wrapper-panel__back"
            v-if="currentStage > 0"
        >
            <VsButton
                icon-only
                icon="internal-link"
                icon-orientation="down"
                size="md"
                variant="secondary"
                @click.native="stageBack"
                data-test="vs-main-map-wrapper-panel--btn-back"
            >
                <span class="sr-only">
                    <!-- @slot Text for panel back button  -->
                    <slot name="backBtnText" />
                </span>
            </VsButton>
        </div>

        <div class="vs-main-map-wrapper-panel__close">
            <VsButton
                icon-only
                icon="close"
                size="md"
                variant="secondary"
                @click.native="closePanel"
                data-test="vs-main-map-wrapper-panel--btn-close"
            >
                <span class="sr-only">
                    <!-- @slot Text for panel close button  -->
                    <slot name="closeSidePanelText" />
                </span>
            </VsButton>
        </div>

        <VsHeading
            level="4"
            class="vs-main-map-wrapper__heading text-center mt-0"
            v-if="currentHeading !== ''"
            data-test="vs-main-map-categories__heading"
        >
            {{ currentHeading }}
        </VsHeading>

        <template v-if="currentStage === 0">
            <div
                v-for="filter in filters"
                :key="filter.id"
            >
                <VsMainMapWrapperCategory
                    :category-name="filter.label"
                    :type="filter.id"
                    @category-selected="setCategory(filter.id)"
                />
            </div>
        </template>
        <template v-if="currentStage === 1">
            <div>
                Stage 2 - {{ selectedCategory }}
            </div>
        </template>
    </section>
</template>

<script>
import VsButton from '@components/elements/button/Button/';
import VsHeading from '@components/elements/heading/Heading';
import VsMainMapWrapperCategory from './MainMapWrapperCategory';

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
        VsMainMapWrapperCategory,
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
            currentStage: 0,
            selectedCategory: '',
            filterCategories: this.filters,
        };
    },
    inject: ['filters'],
    computed: {
        currentHeading() {
            let headingText = '';

            switch (this.currentStage) {
            case 0:
                headingText = this.categoryHeading;
                break;
            case 1:
                headingText = this.currentFilter.label;
                break;
            default:
                break;
            }

            return headingText;
        },
        currentFilter() {
            let currentFilter = '';
            this.filters.forEach((filter) => {
                if (filter.id === this.selectedCategory) {
                    currentFilter = filter;
                }
            });

            return currentFilter;
        },
    },
    methods: {
        /**
         * Emits 'close-panel'
         */
        closePanel() {
            this.$emit('close-panel');
        },
        /**
         * Sets the currently chosen category
         */
        setCategory(cat) {
            this.selectedCategory = cat;
            this.currentStage += 1;
        },
        /**
         * Moves one stage back
         */
        stageBack() {
            this.currentStage -= 1;
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

        &__close,
        &__back {
            position: absolute;
            top: $spacer-3;
        }

        &__back {
            left: $spacer-3;
        }

        &__close {
            right: $spacer-3;
        }

        .vs-main-wrapper-category:last-of-type {
            &::before {
                display: none;

                @include media-breakpoint-up(lg) {
                    display: block;
                }
            }
        }

        @include media-breakpoint-up(lg) {
            padding: $spacer-8;
            border-right: none;

            &__close,
            &__back {
                display: none;
            }
        }
    }
</style>
