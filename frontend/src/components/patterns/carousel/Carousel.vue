<template>
    <VsContainer>
        <VsRow>
            <VsCol
                cols="12"
            >
                <section class="carousel">
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
                </section>
            </VsCol>
        </VsRow>
    </VsContainer>
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
                arrowPath: 'M20.527,73.811,22.3,72.043a1.072,1.072,0,0,0,0-1.515L8.444,56.677H38.929A1.071,1.071,0,0,0,40,55.606v-2.5a1.071,1.071,0,0,0-1.071-1.071H8.444L22.3,38.183a1.072,1.072,0,0,0,0-1.515L20.527,34.9a1.072,1.072,0,0,0-1.515,0L.314,53.6a1.072,1.072,0,0,0,0,1.515l18.7,18.7A1.071,1.071,0,0,0,20.527,73.811Z',
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
                transform: translateY(-28%);
                fill: $color-white;
                height: 56px;
            }
        }

        .splide__arrow--prev {
            left: 0;
            transform: translateX(-50%);
        }

        .splide__arrow--next {
            right: 0;
            transform: translateX(50%);

            svg {
                transform: translateY(-28%) scaleX(-1);
            }
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
