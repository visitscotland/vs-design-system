<template>
    <div
        class="vs-main-map-wrapper-detail"
        data-test="vs-main-map-wrapper-detail"
    >
        <VsImg
            v-if="typeof contentData.properties.image !== 'undefined'"
            :src="contentData.properties.image"
            class="vs-main-map-wrapper-detail__image mb-5 mb-lg-3"
        />

        <VsHeading
            v-if="showTitle"
            :level="headingLevel"
            override-style-level="4"
            class="vs-main-map-wrapper-detail__heading mt-0"
            :class="(typeof contentData.properties.title !== 'undefined' ? 'd-lg-none' : '')"
            data-test="vs-main-map-wrapper-detail__heading"
        >
            <template v-if="typeof contentData.properties.placeTitle !== 'undefined'">
                {{ contentData.properties.placeTitle }}
            </template>

            <template v-else>
                {{ contentData.properties.title }}
            </template>
        </VsHeading>

        <p
            v-if="typeof contentData.properties.address !== 'undefined'"
            data-test="vs-main-map-wrapper-detail__address"
            class="vs-main-map-wrapper-detail__address"
        >
            {{ contentData.properties.address.shortAddress }}
        </p>

        <!-- eslint-disable vue/no-v-html -->
        <p
            data-test="vs-main-map-wrapper-detail__description"
            class="vs-main-map-wrapper-detail__description"
            v-html="contentData.properties.description"
        />
        <!-- eslint-enable vue/no-v-html -->
    </div>
</template>

<script>
import VsImg from '@components/elements/img/Img';
import VsHeading from '@components/elements/heading/Heading';

/**
 * Renders a details of a place in the map component.
 * Content is received by a data object
 *
 * @displayName Main Map Wrapper Detail
 */
export default {
    name: 'VsMainMapWrapperDetail',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsImg,
        VsHeading,
    },
    props: {
        /**
         * Data for component content
         */
        contentData: {
            type: Object,
            required: true,
        },
        /**
         * Heading level - to allow sequential heading
         */
        headingLevel: {
            type: String,
            default: '3',
        },
    },
    computed: {
        showTitle() {
            if (typeof this.contentData.properties.title !== 'undefined'
                || typeof this.contentData.properties.placeTitle !== 'undefined') {
                return true;
            }

            return false;
        },
    },
};
</script>

<style lang="scss">
    .vs-main-map-wrapper-detail {
        text-align: left;

        &__image {
            max-width: 100%;
        }

        &__heading.vs-heading--style-level-4.vs-heading,
        &__address {
            margin-bottom: 0;
        }

        @include media-breakpoint-up(lg) {
            font-size: $font-size-4;
            margin: 0 $spacer-2;
        }

        &__description {
            margin-top: 1rem;
            text-align: left;
        }
    }
</style>
