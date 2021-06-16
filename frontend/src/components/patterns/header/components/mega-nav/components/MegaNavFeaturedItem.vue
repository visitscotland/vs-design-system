<template>
    <VsStretchedLinkCard
        :link="link"
        type="internal"
        :img-src="imgUrl"
        :img-alt="imgAlt"
        class="vs-mega-nav-featured-item"
        data-test="vs-mega-nav-featured-item"
    >
        <template slot="stretchedCardHeader">
            <!-- @slot Featured Item header text  -->
            <slot name="vsFeaturedItemHeader" />
        </template>

        <template slot="stretchedCardContent">
            <!-- @slot Featured Item body content  -->
            <slot name="vsFeaturedItemContent" />
        </template>

        <template slot="stretchedCardLink">
            <!-- @slot Featured Item link text  -->
            <slot name="vsFeaturedItemLink" />
        </template>
    </VsStretchedLinkCard>
</template>

<script>
import VsStretchedLinkCard from '@components/patterns/stretched-link-card/StretchedLinkCard';

/**
 *  This component is used to show a featured item in the meganav
 *
 * @displayName MegaNav Featured Item
 */

export default {
    name: 'VsMegaNavFeaturedItem',
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
    },
};
</script>

<style lang="scss">
    .card.vs-mega-nav-featured-item {
        border-top: 1px solid #e0e0e0;

        &:hover {
            box-shadow: inset 0px 0px 3px 6px rgba(0,0,41,0.05)
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
            padding: $spacer-5 $spacer-3;
        }

        .card-title {
            font-size: $h5-font-size;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .vs-stretched-link-card__content {
            display: none;
            -webkit-line-clamp: 4;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }

        .vs-stretched-link-card__link {
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
                padding: $spacer-8;
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
            padding: 12px;
            flex-direction: column;
            border-top: none;

            .card-body {
                padding: $spacer-5 $spacer-3;
            }

            .vs-stretched-link-card__img {
                width: 100%;
            }

            .card-title {
                font-size: $h5-font-size;
            }

            .vs-stretched-link-card__content {
                display: -webkit-box;
                margin: $spacer-0;
                color: $color-base-text;
                font-size: $small-font-size;
            }

            .vs-stretched-link-card__link {
                align-self: flex-end;
                padding: $spacer-2;
            }
        }
    }
</style>

<docs>
    ```jsx
        <VsContainer>
            <VsRow>
                <VsCol cols="12" lg="4">
                    <VsMegaNavFeaturedItem
                        link="www.visitscotland.com"
                        img-url="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        img-alt="Alt text"
                    >
                        <template slot="vsFeaturedItemHeader">
                            From our home to yours – see Scotland virtually
                        </template>

                        <template slot="vsFeaturedItemContent">
                            <p>The content for the card goes here</p>
                            <p>A second line of content</p>
                        </template>

                        <template slot="vsFeaturedItemLink">
                            A link to a page
                        </template>
                    </VsMegaNavFeaturedItem>
                </VsCol>
            </VsRow>
        </VsContainer>
    ```
</docs>
