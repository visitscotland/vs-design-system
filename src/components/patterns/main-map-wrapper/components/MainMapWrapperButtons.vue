<template>
    <div
        class="vs-main-map-wrapper-buttons"
        :class="filterCount !== null ? 'vs-main-map-wrapper-buttons--filters' : ''"
        data-test="vs-main-map-wrapper-buttons"
    >
        <template v-if="filterCount !== null">
            <VsButton
                class="vs-main-map-wrapper-buttons__clear-filters"
                data-test="vs-main-map-wrapper-buttons__clear-filters"
                variant="secondary"
                size="sm"
                @click.native="clearFiltersClick"
            >
                {{ clearFiltersText }}
            </VsButton>

            <VsButton
                class="vs-main-map-wrapper-buttons__filters"
                data-test="vs-main-map-wrapper-buttons__filters"
                variant="secondary"
                icon="filters"
                size="sm"
                @click.native="filtersClick"
            >
                {{ filterCount }} {{ filtersAppliedText }}
            </VsButton>
        </template>

        <template v-else>
            <VsButton
                block
                :href="contentData.properties.link.link"
                data-test="vs-main-map-wrapper-buttons__discover"
            >
                {{ contentData.properties.link.label }}
            </VsButton>

            <VsButton
                v-if="websiteDataExists"
                :href="contentData.properties.website.link"
                block
                target="_blank"
                data-test="vs-main-map-wrapper-buttons__website"
            >
                {{ contentData.properties.website.label }}
            </VsButton>
        </template>
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
    inject: [
        'filtersAppliedText',
        'clearFiltersText',
    ],
    props: {
        /**
         * Data for component content
         */
        contentData: {
            type: Object,
            required: true,
        },
        /**
         * If content is filtered by subcategory
         */
        filterCount: {
            type: Number,
            default: null,
        },
    },
    computed: {
        websiteDataExists() {
            if (typeof this.contentData.properties.website !== 'undefined'
                && this.contentData.properties.website.link
                    !== this.contentData.properties.link.link) {
                return true;
            }

            return false;
        },
    },
    methods: {
        filtersClick() {
            this.$parent.$emit('set-stage', 0);
        },
        clearFiltersClick() {
            this.$emit('clear-filters');
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper-buttons {
        position: sticky;
        bottom: 0;
        padding: $spacer-4 0;
        width: 100%;
        background: $color-white;
        text-align: center;
        box-shadow: 0px -12px 10px -10px #c69fbc;

        @include media-breakpoint-up(lg) {
            padding: $spacer-4;
        }

        &--filters {
            display: flex;
            justify-content: space-between;

            .vs-button:first-of-type {
                margin-right: $spacer-4;
            }
        }
    }
</style>
