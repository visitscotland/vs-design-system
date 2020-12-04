<template>
    <SplideSlide>
        <VsStretchedLinkCard
            :link="linkUrl"
            :type="linkType"
            :img-src="imgSrc"
            :img-alt="imgAlt"
            class="carousel-slide"
        >
            <VsStretchedLinkPanels
                v-if="days && transport"
                :days="days"
                :transport="transport"
                slot="stretchedCardPanels"
            />

            <template slot="stretchedCardCategory">
                {{ category }}
            </template>

            <span
                slot="stretchedCardHeader"
                class="carousel-slide__title"
                data-test="carousel-slide__title"
            >
                <!-- @slot Slot to contain heading -->
                <slot name="vsCarouselSlideHeading" />
            </span>
        </VsStretchedLinkCard>
    </SplideSlide>
</template>

<script>
import { SplideSlide } from '@splidejs/vue-splide';
import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';
import VsStretchedLinkPanels from '@components/elements/stretched-link-card/components/StretchedLinkPanels';

/**
* Slide for carousel
*/

export default {
    name: 'VsCarouselSlide',
    status: 'prototype',
    release: '0.0.1',
    components: {
        SplideSlide,
        VsStretchedLinkCard,
        VsStretchedLinkPanels,
    },
    props: {
        /**
        * The image to use in the component
        */
        imgSrc: {
            required: true,
            type: String,
        },
        /**
        * The image alt text to use in the component
        */
        imgAlt: {
            type: String,
            default: '',
        },
        /**
        * The type of link. This will set the icon.
        * `external, internal, download`
        */
        linkType: {
            type: String,
            required: true,
            validator: (value) => value.match(/(external|internal|download)/),
        },
        /**
        * The link destination
        */
        linkUrl: {
            type: String,
            required: true,
        },
        /**
        * The category of the content
        */
        category: {
            type: String,
            default: null,
        },
        /**
        * Optional prop for number of days
        */
        days: {
            type: String,
            default: '',
        },
        /**
        * Optional prop for transport type (will show a the transport icon if used)
        */
        transport: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
    .splide__slide {
        opacity: 0.5;
        transition: opacity 0.2s ease;

        &.is-visible {
            opacity: 1;
        }
    }

    .carousel-slide {
        height: 100%;
        padding: $spacer-2;

        .card-title {
            margin-bottom: $spacer-0;
        }

        .card-body {
            padding: $spacer-3 0;
        }
    }
</style>
