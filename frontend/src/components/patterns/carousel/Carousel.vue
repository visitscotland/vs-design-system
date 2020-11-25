<template>
    <section class="carousel">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                >
                    <Splide
                        :options="splideOptions"
                    >
                        <VsCarouselSlide
                            link-url="www.visitscotland.com"
                            link-type="external"
                            img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        >
                            <template slot="vsCarouselSlideHeading">
                                Count 7,000 shining stars in the iconic
                                galloway forest
                            </template>
                            <template slot="vsCarouselSlideContent">
                                <p>
                                    Right across the country, you’ll find amazing
                                    places to eat and drink from local markets to renowned
                                    restaurants. Here are some recomm…
                                </p>
                            </template>
                        </VsCarouselSlide>

                        <VsCarouselSlide
                            link-url="www.visitscotland.com"
                            link-type="external"
                            img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        >
                            <template slot="vsCarouselSlideHeading">
                                Count 7,000 shining stars in the iconic
                                galloway forest
                            </template>
                            <template slot="vsCarouselSlideContent">
                                <p>
                                    Right across the country, you’ll find amazing
                                    places to eat and drink from local markets to renowned
                                    restaurants. Here are some recomm…
                                </p>
                            </template>
                        </VsCarouselSlide>

                        <VsCarouselSlide
                            link-url="www.visitscotland.com"
                            link-type="external"
                            img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        >
                            <template slot="vsCarouselSlideHeading">
                                Count 7,000 shining stars in the iconic
                                galloway forest
                            </template>
                            <template slot="vsCarouselSlideContent">
                                <p>
                                    Right across the country, you’ll find amazing
                                    places to eat and drink from local markets to renowned
                                    restaurants. Here are some recomm…
                                </p>
                            </template>
                        </VsCarouselSlide>

                        <VsCarouselSlide
                            link-url="www.visitscotland.com"
                            link-type="external"
                            img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        >
                            <template slot="vsCarouselSlideHeading">
                                Count 7,000 shining stars in the iconic
                                galloway forest
                            </template>
                            <template slot="vsCarouselSlideContent">
                                <p>
                                    Right across the country, you’ll find amazing
                                    places to eat and drink from local markets to renowned
                                    restaurants. Here are some recomm…
                                </p>
                            </template>
                        </VsCarouselSlide>

                        <VsCarouselSlide
                            link-url="www.visitscotland.com"
                            link-type="external"
                            img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        >
                            <template slot="vsCarouselSlideHeading">
                                Count 7,000 shining stars in the iconic
                                galloway forest
                            </template>
                            <template slot="vsCarouselSlideContent">
                                <p>
                                    Right across the country, you’ll find amazing
                                    places to eat and drink from local markets to renowned
                                    restaurants. Here are some recomm…
                                </p>
                            </template>
                        </VsCarouselSlide>

                        <VsCarouselSlide
                            link-url="www.visitscotland.com"
                            link-type="external"
                            img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        >
                            <template slot="vsCarouselSlideHeading">
                                Count 7,000 shining stars in the iconic
                                galloway forest
                            </template>
                            <template slot="vsCarouselSlideContent">
                                <p>
                                    Right across the country, you’ll find amazing
                                    places to eat and drink from local markets to renowned
                                    restaurants. Here are some recomm…
                                </p>
                            </template>
                        </VsCarouselSlide>
                    </Splide>
                </VsCol>
            </VsRow>
        </VsContainer>
    </section>
</template>

<script>
import { Splide } from '@splidejs/vue-splide';
import VsCarouselSlide from '@components/patterns/carousel/components/CarouselSlide';
// import leftArrow from '../../../assets/svg/icons/family.svg';

/**
* Multi purpose carousel component to use
* for carousel and slider content
*/

export default {
    name: 'VsCarousel',
    status: 'protolink-type',
    release: '0.0.1',
    components: {
        Splide,
        VsCarouselSlide,
    },
    data() {
        return {
            splideOptions: {
                perPage: 4,
                gap: '24px',
                arrowPath: 'M19.47.55,17.7,2.31a1.07,1.07,0,0,0,0,1.52L31.56,17.68H1.07A1.07,1.07,0,0,0,0,18.75v2.5a1.07,1.07,0,0,0,1.07,1.07H31.56L17.7,36.17a1.07,1.07,0,0,0,0,1.52l1.77,1.77a1.09,1.09,0,0,0,1.52,0l18.7-18.7a1.09,1.09,0,0,0,0-1.52L21,.54A1.08,1.08,0,0,0,19.47.55Z',
                breakpoints: {
                    992: {
                        perPage: 3,
                    },
                    768: {
                        perPage: 2,
                    },
                    576: {
                        perPage: 1,
                        width: '80%',
                    },
                },
            },
        };
    },
};
</script>

<style lang="scss">
    @import '@splidejs/splide/dist/css/themes/splide-default.min.css';

    .carousel {
        overflow: hidden;

        .splide {
            margin: 0 auto;
        }

        .splide__arrow[disabled] {
            display: none;
        }

        .splide__track {
            overflow: visible;
        }

        .splide__pagination {
            position: relative;
            transform: none;
            width: 100%;
            left: auto;
            bottom: auto;
        }

        .splide__arrow {
            border-radius: 0;
            background: $color-pink;
            opacity: 1;
            top: 40%;

            svg {
                // transform: translateY(-28%);
                fill: $color-white;
                // height: 56px;
            }
        }

        .splide__arrow--prev {
            left: 0;
            transform: translateX(-50%);
        }

        .splide__arrow--next {
            right: 0;
            transform: translateX(50%);
        }

        .splide__pagination {
            margin-top: $spacer-9;
        }

        .splide__pagination__page {
            width: 10px;
            height: 10px;
            color: $color-gray-tint-4;

            &.is-active {
                transform: none;
                background: $color-black;
                opacity: 1;
                width: 14px;
                height: 14px;
            }
        }
    }
</style>

<docs>
     ```js
    <VsCarousel>
    </VsCarousel>
    ```
</docs>
