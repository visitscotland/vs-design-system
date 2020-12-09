<template>
    <div class="no-js">
        <section
            class="vs-carousel"
            data-test="vs-carousel"
            ref="carousel"
        >
            <VsContainer>
                <VsRow>
                    <VsCol
                        cols="12"
                    >
                        <Splide
                            :options="splideOptions"
                            @splide:moved="carouselMoved"
                            @splide:mounted="initialiseMobilePagination"
                            @splide:updated="resetTabbing"
                        >
                            <!-- @slot default slot to contain slides -->
                            <slot />
                        </Splide>
                        <div class="vs-carousel__mobile-pagination-wrapper">
                            <p
                                class="vs-carousel__mobile-pagination"
                                data-test="vs-carousel__mobile-pagination"
                            >
                                {{ currentSlide }} of {{ totalSlides }}
                            </p>
                        </div>
                    </VsCol>
                </VsRow>
            </VsContainer>
        </section>
    </div>
</template>

<script>
import { Splide } from '@splidejs/vue-splide';
import {
    VsContainer,
    VsRow,
    VsCol,
} from '@components/elements/layout';

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
        VsContainer,
        VsRow,
        VsCol,
    },
    data() {
        return {
            splideOptions: {
                slideFocus: false,
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
            totalSlides: null,
            currentSlide: 1,
        };
    },
    mounted() {
        window.addEventListener('resize', () => {
            this.resetTabbing();
        });
        window.addEventListener('keydown', (e) => {
            if (e.which === 9) {
                this.resetTabbing();
            }
        });
    },
    methods: {
        initialiseMobilePagination(splide) {
            this.totalSlides = splide.length;
        },
        carouselMoved(splide) {
            this.currentSlide = splide.index + 1;
            this.resetTabbing();
        },
        // ensures that the user can't tab into elements
        // that aren't currently shown by the carousel
        resetTabbing() {
            const carouselEl = this.$refs.carousel;
            const carouselLinks = carouselEl.getElementsByTagName('a');
            // timeout to ensure that code has updated from Splide
            setTimeout(() => {
                carouselLinks.forEach((element) => {
                    const link = element;
                    if (element.closest('.is-visible') === null) {
                        link.tabIndex = -1;
                    } else {
                        link.tabIndex = 0;
                    }
                });
            }, 500);
        },
    },
};
</script>

<style lang="scss">
    @import '@splidejs/splide/dist/css/themes/splide-default.min.css';

    .vs-carousel {
        overflow: hidden;

        .splide {
            margin: 0 auto;

            &__arrow[disabled] {
                display: none;
            }

            &__pagination {
                position: relative;
                transform: none;
                width: 100%;
                left: auto;
                bottom: auto;
                display: none;
            }

            &__arrow {
                border-radius: 0;
                background: $color-pink;
                opacity: 1;
                top: 25%;
            }

            &__track {
                overflow: visible;
            }

            &__pagination {
                position: relative;
                transform: none;
                width: 100%;
                left: auto;
                bottom: auto;
                display: none;
            }

            &__arrow {
                border-radius: 0;
                background: $color-pink;
                opacity: 1;
                top: 25%;

                svg {
                    fill: $color-white;
                    width: 1rem;
                    height: 1rem;
                }

                &:hover {
                    background: $color-pink-shade-2;
                }

                &:focus {
                    border: 4px solid $color-pink-tint-5;
                }
            }

            .splide__pagination,
            &__mobile-pagination-wrapper {
                margin-top: $spacer-9;
            }

            &__arrow--prev {
                left: 0;
                transform: translateX(-50%);
            }

            &__arrow--next {
                right: 0;
                transform: translateX(50%);
            }

            &__pagination__page {
                width: 10px;
                height: 10px;
                background: $color-gray-tint-4;

                &.is-active {
                    transform: none;
                    background: $color-black;
                    opacity: 1;
                    width: 14px;
                    height: 14px;
                }

                &:hover {
                    background: $color-pink-tint-5
                }

                &:focus {
                    outline: 1px solid $color-pink;
                }
            }
        }

        .splide__pagination,
        .carousel__mobile-pagination-wrapper {
            margin-top: $spacer-9;
        }

        &__mobile-pagination-wrapper {
            width: 100%;
            display: flex;
            justify-content: center;
        }

        &__mobile-pagination {
            display: inline-block;
            background: $color-gray-tint-7;
            line-height: $line-height-xs;
            padding: $spacer-2;
            font-size: $small-font-size;
            font-weight: $semibold-font-weight;
        }

        @include media-breakpoint-up(sm) {
            .splide__pagination {
                display: inline-flex;
            }

            &__mobile-pagination-wrapper {
                display: none;
            }
        }
    }

    .no-js .vs-carousel {
        .splide__arrows {
            display: none;
        }

        .splide__list {
            width: 100% !important;
            transform: none !important;
            flex-wrap: wrap;
        }

        .splide__slide {
            width: 100% !important;
            margin-bottom: $spacer-3;
            padding-bottom: $spacer-3;

            &::after {
                content: '';
                border-bottom: 1px solid $color-gray-tint-5;
                position: absolute;
                width: calc(100% - 16px);
                left: 8px;
                bottom: 0;
            }
        }

        .splide__pagination {
            display: none;
        }

        .splide__slide .card {
            display: flex;
            flex-direction: row;
            padding: $spacer-2;
            border: none;
            height: 100%;
            transition: box-shadow 800ms;

            &:hover {
                box-shadow: 10px 10px 20px $color-gray-tint-4;

                .stretched-link-card__title {
                    text-decoration: underline;
                }
            }

            .stretched-link {
                text-decoration: none;
            }

            .card-body {
                background: none;
                padding: 0;
                align-self: flex-start;
                width: 66%;
            }

            .stretched-link-card__img {
                width: 33%;
                align-self: flex-start;
                margin-right: $spacer-4;
            }

            .stretched-link-card__title {
                font-size: $small-font-size;
                letter-spacing: .05rem;
                line-height: $line-height-m;
                color: $color-base-text;
                text-decoration: none;
            }

            .stretched-link {
                letter-spacing: 0;
            }

            .card-title {
                display: flex;
                margin-bottom: 0;
            }

            .stretched-link-card__content {
                display: none;

                p {
                    display: -webkit-box;
                    -webkit-line-clamp: 3;
                    -webkit-box-orient: vertical;
                    overflow: hidden;
                }
            }

            .carousel__mobile-pagination-wrapper {
                display: none;
            }
        }

        @include media-breakpoint-up(sm) {
            .stretched-link-card {
                .stretched-link-card__content {
                    display: block;
                }
            }
        }

        @include media-breakpoint-up(lg) {
            .stretched-link-card {
                .stretched-link-card__title {
                    font-size: $small-font-size;
                }

                .stretched-link-card__content {
                    margin: $spacer-2 0 0;
                    line-height: $line-height-s;
                }
            }

            .splide__slide {
                width: calc(50% - 24px) !important;
            }
        }
    }
</style>

<docs>
     ```js
    <VsCarousel>
        <VsCarouselSlide
            link-url="www.visitscotland.com"
            link-type="external"
            img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            img-alt=""
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
    </VsCarousel>
    ```
</docs>
