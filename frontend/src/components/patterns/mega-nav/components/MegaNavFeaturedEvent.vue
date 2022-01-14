<template>
    <VsStretchedLinkCard
        :link="link"
        type="internal"
        :img-src="imgUrl"
        :img-alt="imgAlt"
        class="vs-mega-nav-featured-event"
        data-test="vs-mega-nav-featured-event"
    >
        <template slot="stretchedCardContent">
            <p class="vs-mega-nav-featured-event__date-range">
                {{ datesLabel }}&nbsp;
                <span class="vs-mega-nav-featured-event__date-detail">{{ dates }}</span>
            </p>
            <p class="vs-mega-nav-featured-event__title text-truncate text-truncate--2">
                {{ title }}
            </p>
            <p class="vs-mega-nav-featured-event__location text-truncate text-truncate--1">
                {{ location }}
            </p>
        </template>

        <template slot="stretchedCardLink">
            <!-- @slot Featured Event link text  -->
            <slot name="vsFeaturedEventLink" />
        </template>
    </VsStretchedLinkCard>
</template>

<script>
import VsStretchedLinkCard from '@components/patterns/stretched-link-card/StretchedLinkCard';

/**
 *  This component is used to show a featured event in the meganav
 *
 * @displayName MegaNav Featured Event
 */

export default {
    name: 'VsMegaNavFeaturedEvent',
    status: 'prototype',
    release: '0.1.0',
    components: {
        VsStretchedLinkCard,
    },
    props: {
        /**
        * Link URL for the featured item
        */
        link: {
            type: String,
            required: true,
        },
        /**
        * Image URL for the featured item
        */
        imgUrl: {
            type: String,
            required: true,
        },
        /**
        * Image alt text for the featured item
        */
        imgAlt: {
            type: String,
            default: '',
        },
        datesLabel: {
            type: String,
            default: '',
        },
        dates: {
            type: String,
            default: '',
        },
        title: {
            type: String,
            default: '',
        },
        location: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
    .card.vs-mega-nav-featured-event {
        border-top: 1px solid #e0e0e0;

        &:hover {
            box-shadow: -7px 12px 27px -13px rgba(0, 0, 0, 0.4);
        }

        &:focus {
            .vs-stretched-link-card__link {
                outline: 2px;
            }
        }

        &--bottom {
            bottom: 0;
            top: auto;
        }

        .card-body {
            position: absolute;
            width: 80%;
            bottom: 0;
            left: 0;
            background: $color-white;
            padding: $spacer-0;
        }

        .card-title {
            display: none;
        }

        .vs-stretched-link-card__content {
            padding: $spacer-0 $spacer-5;
            border-left: $spacer-1 solid $color-pink;
        }

        .vs-stretched-link-card__link {
            font-size: $small-font-size;
        }

        .vs-mega-nav-featured-event__date-range {
            color: $color-pink;
            margin-bottom: $spacer-2;
            font-size: $small-font-size;
        }

        .vs-mega-nav-featured-event__date-detail {
            font-weight: $font-weight-bold;
        }

        .vs-mega-nav-featured-event__title {
            font-size: $small-font-size;
            font-weight: $font-weight-bold;
            margin-bottom: $spacer-0;
        }

        .vs-mega-nav-featured-event__location {
            font-size: $small-font-size;
        }

        @include media-breakpoint-up(sm) {
            display: flex;
            flex-direction: row-reverse;

            .card-title {
                font-size: $font-size-base;
            }

            .card-body {
                position: initial;
                width: auto;
                max-width: 100%;
                display: flex;
                flex-direction: column;
                justify-content: space-between;

                // IE11 fix as it doesn't like position: initial
                @media screen and (-ms-high-contrast: active), screen and
                    (-ms-high-contrast: none) {
                    position: relative;
                }
            }

            .vs-stretched-link-card__img {
                width: 245px;
                height: auto;
            }
        }

        @include media-breakpoint-up(lg) {
            padding: $spacer-3;
            flex-direction: column;
            border-top: none;

            .vs-stretched-link-card__img {
                width: 100%;
            }

            .vs-stretched-link-card__link {
                align-self: flex-end;
                padding: $spacer-2;
            }
        }
    }

    @include no-js {
        .card.vs-mega-nav-featured-event {
            display: none;
        }
    }
</style>

<docs>
    ```jsx
        <VsContainer>
            <VsRow>
                <VsCol cols="12" lg="4">
                    <MegaNavFeaturedEvent
                        link="www.visitscotland.com"
                        img-url="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        img-alt="Alt text"
                        title="Autism Friendly Ancient Egyptian Family Workshop Day"
                        location="South Nutshill Industrial Park"
                        dates-label="Dates from"
                        dates="4 Dec 2020"
                    >

                        <template slot="vsFeaturedEventLink">
                            View event details
                        </template>
                    </MegaNavFeaturedEvent>
                </VsCol>
            </VsRow>
        </VsContainer>
    ```
</docs>
