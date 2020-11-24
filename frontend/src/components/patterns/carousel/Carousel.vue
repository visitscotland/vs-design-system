<template>
    <Splide :options="splideOptions">
        <SplideSlide>
            <img
                alt=""
                src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            >
        </SplideSlide>
        <SplideSlide>
            <img
                alt=""
                src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            >
        </SplideSlide>
        <SplideSlide>
            <img
                alt=""
                src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            >
        </SplideSlide>
    </splide>
</template>

<script>
// import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';
import { Splide, SplideSlide } from '@splidejs/vue-splide';

/**
* Multi purpose carousel component to use
* for carousel and slider content
*/

export default {
    name: 'VsCarousel',
    status: 'prototype',
    release: '0.0.1',
    components: {
        // VsStretchedLinkCard,
        Splide,
        SplideSlide,
    },
    data() {
        return {
            splideOptions: {
                perPage: 2,
            },
        };
    },
};
</script>

<style lang="scss">
    @import '@splidejs/splide/dist/css/themes/splide-default.min.css';
</style>

<docs>
     ```js
    <VsCarousel>
    </VsCarousel>
    ```
</docs>
