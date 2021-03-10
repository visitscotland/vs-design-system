<template>
    <section
        class="vs-carousel"
        data-test="vs-carousel"
        ref="carousel"
    >
        <VsContainer>
            <VsRow>
                <VsCol cols="12">
                    <div
                        class="slider"
                        ref="slideContainer"
                    >
                        <VsRow
                            class="vs-carousel__track"
                        >
                            <!-- @slot default slot to contain slides -->
                            <slot />
                        </VsRow>
                        <button
                            v-if="!prevDisabled"
                            class="vs-carousel__control vs-carousel__control--prev"
                            @click="sliderNavigate('prev')"
                            @keyup.enter="sliderNavigate('prev')"
                        >
                            <VsIcon
                                name="internal-link"
                                size="xs"
                                orientation="down"
                                variant="light"
                            />
                            <span class="sr-only">{{ prevText }}</span>
                        </button>
                        <button
                            v-if="!nextDisabled"
                            class="vs-carousel__control vs-carousel__control--next"
                            @click="sliderNavigate('next')"
                            @keyup.enter="sliderNavigate('next')"
                        >
                            <VsIcon
                                name="internal-link"
                                size="xs"
                                variant="light"
                            />
                            <span class="sr-only">{{ nextText }}</span>
                        </button>

                        <ul class="vs-carousel__navigation">
                            <li
                                v-for="index in maxPages"
                                :key="index"
                                class="vs-carousel__navigation-item"
                                :class="index === currentPage + 1 ?
                                    'vs-carousel__navigation-item--active' : ''"
                                @click="sliderNavigate(index - 1)"
                                @keyup.enter="sliderNavigate(index - 1)"
                                tabindex="0"
                            >
                                <span class="sr-only">Navigate to page {{ index }}</span>
                            </li>
                        </ul>
                    </div>

                    <div class="vs-carousel__mobile-pagination-wrapper">
                        <p
                            class="vs-carousel__mobile-pagination"
                            data-test="vs-carousel__mobile-pagination"
                        >
                            {{ currentPage + 1 }}
                            <!-- @slot slot for word 'of' to allow translation -->
                            <slot name="vsCarouselOf" />
                            {{ totalSlides }}
                        </p>
                    </div>
                </VsCol>
            </VsRow>
        </VsContainer>
    </section>
</template>

<script>
import VsIcon from '@components/elements/icon';
import {
    VsContainer,
    VsRow,
    VsCol,
} from '@components/elements/layout';

/**
* Multi purpose carousel component to use
* for carousel and slider content
*
* @displayName Carousel
*/

export default {
    name: 'VsCarousel',
    status: 'protolink-type',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsIcon,
    },
    props: {
        /**
        * Accessible text for previous
        * carousel control
        */
        prevText: {
            type: String,
            default: 'Previous slide',
        },
        /**
        * Accessible text for next
        * carousel control
        */
        nextText: {
            type: String,
            default: 'Next slide',
        },
        /**
        *  Number of slides to appear
        *  at 'xs' screen size
        */
        slidesXs: {
            type: String,
            default: '1',
        },
        /**
        *  Number of slides to appear
        *  at 'sm' screen size
        */
        slidesSm: {
            type: String,
            default: '2',
        },
        /**
        *  Number of slides to appear
        *  at 'md' screen size
        */
        slidesMd: {
            type: String,
            default: '3',
        },
        /**
        *  Number of slides to appear
        *  at 'lg' screen size
        */
        slidesLg: {
            type: String,
            default: '4',
        },
    },
    data() {
        return {
            totalSlides: null,
            currentPage: 0,
            maxPages: 1,
            nextDisabled: false,
            prevDisabled: true,
            currentWidth: 'lg',
        };
    },
    computed: {
        slidesPerPage() {
            const slidesPerPage = {

            };
            slidesPerPage.lg = this.slidesLg;
            slidesPerPage.md = this.slidesMd;
            slidesPerPage.sm = this.slidesSm;
            slidesPerPage.xs = this.slidesXs;

            return slidesPerPage;
        },
    },
    provide() {
        const slideCols = {
        };

        Object.keys(this.slidesPerPage).forEach((key) => {
            const colSpan = 12 / this.slidesPerPage[key];
            slideCols[key] = colSpan;
        });

        return {
            slideCols,
        };
    },
    mounted() {
        window.addEventListener('resize', () => {
            const oldSize = this.currentPage;
            this.setActivePage(oldSize);

            this.defineActiveSlides(window.innerWidth);
        });
        this.defineActiveSlides();
    },
    methods: {
        defineActiveSlides() {
            this.calcViewport();
            const allSlides = this.$refs.slideContainer
                .getElementsByClassName('vs-carousel-slide__card');
            let slideCount = 0;

            allSlides.forEach((slide, index) => {
                const activeSlideStart = this.currentPage * this.slidesPerPage[this.currentWidth];
                const activeSlideEnd = parseInt(
                    this.currentPage * this.slidesPerPage[this.currentWidth], 10
                ) + parseInt(this.slidesPerPage[this.currentWidth], 10);
                if (index >= activeSlideStart && index < activeSlideEnd) {
                    slide.classList.add('vs-carousel-slide__card--active');
                    slide.getElementsByTagName('a')[0].setAttribute('tabindex', '0');
                } else {
                    slide.classList.remove('vs-carousel-slide__card--active');
                    slide.getElementsByTagName('a')[0].setAttribute('tabindex', '-1');
                }

                slideCount += 1;
            });

            this.totalSlides = slideCount;

            this.maxPages = Math.ceil(slideCount / this.slidesPerPage[this.currentWidth]);
        },
        calcViewport() {
            if (window.matchMedia('(min-width: 1200px)').matches) {
                this.currentWidth = 'lg';
            } else if (window.matchMedia('(min-width: 992px)').matches) {
                this.currentWidth = 'md';
            } else if (window.matchMedia('(min-width: 576px)').matches) {
                this.currentWidth = 'sm';
            } else {
                this.currentWidth = 'xs';
            }
        },
        setActivePage() {
            let firstActiveItem;

            if (this.currentWidth === 'xs' && this.slidesXs === '1') {
                firstActiveItem = this.currentPage + 1;
            } else {
                firstActiveItem = (this.currentPage * this.slidesPerPage[this.currentWidth]) + 1;
            }

            this.calcViewport();

            let newFirstPage;
            if (this.currentWidth === 'xs' && this.slidesXs === '1') {
                newFirstPage = firstActiveItem;
            } else {
                newFirstPage = Math.ceil(
                    firstActiveItem / this.slidesPerPage[this.currentWidth]
                );
            }

            this.currentPage = newFirstPage - 1;

            this.sliderNavigate(this.currentPage);
        },
        sliderNavigate(direction) {
            if (direction === 'next') {
                this.currentPage += 1;
            } else if (direction === 'prev') {
                this.currentPage -= 1;
            } else {
                this.currentPage = direction;
            }

            this.initNavigation();

            const track = this.$refs.slideContainer.getElementsByClassName('row')[0];

            track.style.transform = `translateX(-${this.currentPage * 100}%)`;

            this.defineActiveSlides();
        },
        initNavigation() {
            if (this.currentPage + 1
                >= this.totalSlides / this.slidesPerPage[this.currentWidth]) {
                this.nextDisabled = true;
            } else {
                this.nextDisabled = false;
            }

            if (this.currentPage > 0) {
                this.prevDisabled = false;
            } else {
                this.prevDisabled = true;
            }
        },
    },
};
</script>

<style lang="scss">
    .vs-carousel {
        overflow: hidden;

        &__track {
            flex-wrap: nowrap;
            transition: transform 0.6s ease-out;

            & > [class^="col-"] {
                display: flex;
            }
        }

        &__control {
            position: absolute;
            top: 25%;
            border: none;
            background: $color-theme-primary;
            z-index: 20;
            width: 35px;
            height: 35px;
            border-radius: 0;
            display: flex;
            align-items: center;
            justify-content: center;

            &--next {
                right: 0;
            }

            &--prev {
                left: 0;
            }

            &:focus,
            &:hover {
                border: 2px solid $color-purple-tint-4;
                outline: none;
            }
        }

        &__navigation {
            display: none;
            justify-content: center;

            @include media-breakpoint-up(sm) {
                display: flex;
            }
        }

        &__navigation-item {
            display: inline-block;
            width: 10px;
            height: 10px;
            border-radius: 5px;
            background: $color-gray-tint-4;
            transform: translateY(2px);
            margin: $spacer-9 2px 0;
            cursor: pointer;

            &--active {
                width: 14px;
                height: 14px;
                border-radius: 7px;
                background: $color-black;
                transform: none;
                cursor: default;

                &:hover {
                    background: $color-black;
                }
            }

            &:hover {
                background: $color-pink-tint-5;
            }

            &:focus {
                outline: 1px solid $color-purple;
            }

            @media (hover: none) {
                &:hover {
                    background: $color-gray-tint-4;
                }

                &--active {
                    &:hover {
                        background: $color-black;
                    }
                }
            }
        }

        &__mobile-pagination-wrapper {
            display: flex;
            justify-content: center;
            margin-top: $spacer-9;

            @include media-breakpoint-up(sm) {
                display: none;
            }
        }

        &__mobile-pagination {
            background: $color-gray-tint-7;
            padding: $spacer-1 $spacer-2;
            color: $color-black;
            font-size: $font-size-sm;
            font-weight: bold;
            margin: 0;
        }
    }

    .no-js .vs-carousel {
        &__control {
            display: none;
        }

        &__navigation,
        &__mobile-pagination-wrapper {
            display: none;
        }

        &__track {
            flex-wrap: wrap;

            & > [class^="col-"] {
                width: 100%;
                max-width: 100%;
                flex: 0 0 100%;

                @include media-breakpoint-up(md) {
                    width: 50%;
                    max-width: 50%;
                    flex: 0 0 50%;
                }
            }
        }
    }
</style>

<docs>
     ```js
    <VsCarousel
        next-text="next page"
        prev-text="previous page"
        slides-xs="1"
        slides-sm="2"
        slides-md="3"
        slides-lg="4"
    >
        <VsCarouselSlide
            link-url="www.visitscotland.com"
            link-type="external"
            img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                1 Count 7,000 shining stars in the iconic
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
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                2 Count 7,000 shining stars in the iconic
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
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                3 Count 7,000 shining stars in the iconic
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
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                4 Count 7,000 shining stars in the iconic
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
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                5 Count 7,000 shining stars in the iconic
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
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                6 Count 7,000 shining stars in the iconic
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
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                7 Count 7,000 shining stars in the iconic
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
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                8 Count 7,000 shining stars in the iconic
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
            img-alt=""
            category="Category"
            days="15"
            transport="bus"
            transport-name="Bus"
        >
            <template slot="vsCarouselSlideHeading">
                9 Count 7,000 shining stars in the iconic
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

        <template slot="vsCarouselOf">
            of
        </template>
    </VsCarousel>
    ```
</docs>
