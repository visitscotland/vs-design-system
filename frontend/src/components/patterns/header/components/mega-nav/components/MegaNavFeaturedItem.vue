<template>
    <VsStretchedLinkCard
        :link="link"
        type="internal"
        img-src="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
        img-alt="This is the alt text"
        class="vs-mega-nav-featured-item"
    >
        <template slot="stretchedCardHeader">
            <slot name="vs-featured-item-header" />
        </template>

        <template slot="stretchedCardContent">
            <slot name="vs-featured-item-content" />
        </template>

        <template slot="stretchedCardLink">
            <slot name="vs-featured-item-link" />
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
    },
};
</script>

<style lang="scss">
    .card.vs-mega-nav-featured-item {

        &:hover {
            box-shadow: 0px 6px 6px 5px rgba(0,0,41,0.16)
        }

        &:focus {
            .vs-stretched-link-card__link {
                outline: 2px;
            }
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
        }

        .vs-stretched-link-card__link {
            font-size: $small-font-size;
        }

        @include media-breakpoint-up(md) {
            display: flex;
            flex-direction: row-reverse;

            .card-title {
                font-size: $font-size-base;
            }

            .card-body {
                position: initial;
                width: auto;
                padding: $spacer-8;
                display: flex;
                flex-direction: column;
                justify-content: space-between;
            }

            .vs-stretched-link-card__img {
                width: 245px;
                height: auto;
            }
        }

        @include media-breakpoint-up(lg) {
            flex-direction: column;

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
                display: block;
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
                    <VsMegaNavFeaturedItem link="www.visitscotland.com">
                        <template slot="vs-featured-item-header">
                            From our home to yours – see Scotland virtually
                        </template>

                        <template slot="vs-featured-item-content">
                            <p>The content for the card goes here</p>
                            <p>A second line of content</p>
                        </template>

                        <template slot="vs-featured-item-link">
                            A link to a page
                        </template>
                    </VsMegaNavFeaturedItem>
                </VsCol>
            </VsRow>
        </VsContainer>
    ```
</docs>
