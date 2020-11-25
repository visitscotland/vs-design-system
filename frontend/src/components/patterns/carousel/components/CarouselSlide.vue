<template>
    <SplideSlide>
        <VsStretchedLinkCard
            :link="linkUrl"
            :type="linkType"
            class="carousel-slide"
            data-test="carousel-slide-featured"
        >
            <VsImg
                slot="stretchedCardImage"
                :src="imgSrc"
                :alt="imgAlt"
                class="carousel-slide__img"
            />
            <span
                slot="stretchedCardHeader"
                class="carousel-slide__title"
                data-test="carousel-slide__title"
            ><!-- @slot Slot to contain heading --><slot name="vsCarouselSlideHeading" />
            </span>
        </VsStretchedLinkCard>
    </SplideSlide>
</template>

<script>
import { SplideSlide } from '@splidejs/vue-splide';
import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';

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
            required: false,
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
</style>
