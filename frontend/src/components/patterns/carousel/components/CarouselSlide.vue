<template>
    <SplideSlide>
        <VsStretchedLinkCard
            :link="linkUrl"
            :type="linkType"
            :img-src="imgSrc"
            :img-alt="imgAlt"
            class="vs-carousel-slide"
        >
            <span
                slot="stretchedCardHeader"
                class="vs-carousel-slide__title"
                data-test="vs-carousel-slide__title"
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

/**
* Slide for carousel
*
* @displayName Carousel Slide
*/

export default {
    name: 'VsCarouselSlide',
    status: 'prototype',
    release: '0.0.1',
    components: {
        SplideSlide,
        VsStretchedLinkCard,
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

    .vs-carousel-slide {
        height: 100%;
    }
</style>
