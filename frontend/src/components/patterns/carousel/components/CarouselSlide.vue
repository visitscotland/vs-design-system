<template>
    <VsCol
        :cols="slideCols.xs"
        :sm="slideCols.sm"
        :lg="slideCols.md"
        :xl="slideCols.lg"
    >
        <div class="vs-carousel-slide">
            <VsStretchedLinkCard
                :link="linkUrl"
                :type="linkType"
                :img-src="imgSrc"
                :img-alt="imgAlt"
                class="vs-carousel-slide__card"
            >
                <VsStretchedLinkPanels
                    v-if="days && transport"
                    :days="days"
                    :transport="transport"
                    slot="stretchedCardPanels"
                    :days-label="daysLabel"
                />

                <template slot="stretchedCardCategory">
                    {{ category }}
                </template>

                <span
                    slot="stretchedCardHeader"
                    class="vs-carousel-slide__title"
                    data-test="vs-carousel-slide__title"
                >
                    <!-- @slot Slot to contain heading -->
                    <slot name="vsCarouselSlideHeading" />
                </span>
            </VsStretchedLinkCard>
        </div>
    </VsCol>
</template>

<script>
import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';
import VsStretchedLinkPanels from '@components/elements/stretched-link-card/components/StretchedLinkPanels';
import { VsCol } from '@components/elements/layout';

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
        VsStretchedLinkCard,
        VsStretchedLinkPanels,
        VsCol,
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
        * Label for days - too allow translation in CMS
        */
        daysLabel: {
            type: String,
            default: 'days',
        },
        /**
        * Optional prop for transport type (will show a the transport icon if used)
        */
        transport: {
            type: String,
            default: '',
        },
    },
    data() {
        return {
            parentData: this.$parent.slidesPerPage,
        };
    },
    inject: ['slideCols'],
};
</script>

<style lang="scss">
    .vs-carousel-slide {
        max-width: 100%;
        flex-shrink: 0;

        .vs-carousel-slide__card {
            opacity: 0.5;
            transition: opacity 0.3s ease;
            transition-delay: 0.6s;
            padding: $spacer-2;
            max-width: 100%;

            &--active {
                opacity: 1;
            }
        }

        .card-title {
            margin-bottom: $spacer-0;
        }

        .card-body {
            padding: $spacer-3 0 0;
            text-align: left;;

            .vs-heading__sub-heading {
                display: none;
            }
        }
    }

    .no-js .vs-carousel-slide {
        margin-bottom: $spacer-2;

        &::after {
            content: '';
            position: absolute;
            left: $spacer-3;
            bottom: $spacer-1;
            height: 1px;
            width: calc(100% - 24px);
            background: $color-gray-tint-5;

            @include media-breakpoint-up(sm) {
                width: calc(100% - 40px);
                left: $spacer-5;
            }
        }

        .vs-stretched-link-card {
            opacity: 1;
            display: flex;
            flex-direction: row;
            padding: $spacer-2;
            border: none;
            height: 100%;
            transition: box-shadow 800ms;

            &:hover {
                box-shadow: 10px 10px 20px $color-gray-tint-4;

                .vs-stretched-link-card__title {
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
                text-align: left;
            }

            &__img {
                width: 33%;
                align-self: flex-start;
                margin-right: $spacer-4;
            }

            &__title {
                font-size: $font-size-sm;
                letter-spacing: .05rem;
                line-height: $line-height-m;
                color: $color-base-text;
                text-decoration: none;
            }

            &__category {
                margin-bottom: $spacer-2;
            }

            .stretched-link {
                letter-spacing: 0;
            }

            .card-title {
                display: flex;
                margin-bottom: 0;
            }

            &__content {
                display: none;

                p {
                    display: -webkit-box;
                    -webkit-line-clamp: 3;
                    -webkit-box-orient: vertical;
                    overflow: hidden;
                }
            }

            .vs-stretched-link-panels {
                right: auto;
                left: calc(33% - 4px);
                top: $spacer-4;
                transform: translateX(-100%);

                &__panel {
                    display: none;
                    width: 36px;
                    height: 36px;

                    &--days {
                        display: flex;
                    }
                }

                 &__days {
                     font-size: $font-size-base;
                     margin-bottom: 0;
                 }
            }
        }

        @include media-breakpoint-up(sm) {
            .vs-stretched-link-card {
                &__content {
                    display: block;
                }
            }
        }

        @include media-breakpoint-up(lg) {
            .vs-stretched-link-card {
                &__title {
                    font-size: $small-font-size;
                }

                &__content {
                    margin: $spacer-2 0 0;
                    line-height: $line-height-s;
                }
            }

            .vs-carousel-slide {
                width: calc(50% - 24px) !important;
            }
        }
    }
</style>
