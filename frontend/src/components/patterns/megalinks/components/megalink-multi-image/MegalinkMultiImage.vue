<template>
    <VsStretchedLinkCard
        :link="linkUrl"
        :type="linkType"
        :img-src="imgSrc"
        :img-alt="imgAlt"
        class="megalink-multi-image p-2"
        :class="{
            'megalink-multi-image--featured' : featured,
            'megalink-multi-image--featured-last' : lastFeatured,
        }"
        :data-test="featured ? 'megalink-multi-image-featured' : 'megalink-multi-image-card'"
    >
        <span
            slot="stretchedCardHeader"
            class="megalink-multi-image__title"
            data-test="megalink-multi-image__title"
        ><!-- @slot Slot to contain heading --><slot name="vsMultiImageHeading" /></span>

        <VsRichTextWrapper
            slot="stretchedCardContent"
            class="lead megalink-multi-image__content"
            data-test="megalink-multi-image__content"
        >
            <!-- @slot Slot to contain content -->
            <slot name="vsMultiImageContent" />
        </VsRichTextWrapper>
    </VsStretchedLinkCard>
</template>

<script>
import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';
import VsRichTextWrapper from '@components/elements/rich-text-wrapper/RichTextWrapper';

/**
* Megalink cards to be used in the megalinks component
* There is a standard and featured variant.
 *
 * @displayName Megalinks Multi-Image
*/

export default {
    name: 'VsMegalinkMultiImage',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsStretchedLinkCard,
        VsRichTextWrapper,
    },
    props: {
        /**
        * If the component should be the featured variant
        */
        featured: {
            required: false,
            type: Boolean,
            default: false,
        },
        /**
        * If the featured component is the last one
        */
        lastFeatured: {
            required: false,
            type: Boolean,
            default: false,
        },
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
    .megalink-multi-image.card {
        margin-top: $spacer-7;

        .card-body {
            padding: $spacer-4 0 $spacer-2;
            width: 100%;
        }

        &:hover {
            box-shadow: 10px 10px 20px $color-gray-tint-4;
        }

        .megalink-multi-image__title {
            font-size: $font-size-sm;
            line-height: $line-height-s;
            letter-spacing: 0.0875rem;
        }

        .card-title {
            margin-bottom: 0;
        }
    };

    @include media-breakpoint-up(xl) {
        .megalink-multi-image.card {
            margin-top: $spacer-12;

            .megalink-multi-image__title {
                font-size: $h6-font-size;
                line-height: $line-height-s;
            }

            .card-body {
                padding-bottom: $spacer-5;
            }
        }
        .megalink-multi-image--featured.card {
            display: flex;
            flex-direction: row;
            justify-content: flex-start;

            .megalink-multi-image__title {
                font-size: $h3-font-size;
                letter-spacing: 0.125rem;
            }

            .vs-link__icon {
                height: 16px;
                width: 16px;
            }

            .stretched-link-card__img {
                width: calc(50% - 20px);
            }

            .megalink-multi-image__content {
                margin-top: $spacer-4;

                p {
                    line-height: $line-height-m;
                    font-size: $lead-font-size;
                }
            }

            .card-body {
                max-width: calc(50% + 20px);
                padding: $spacer-6 5% $spacer-5;
            }

            &.megalink-multi-image--featured-last {
                flex-direction: row-reverse;
                margin-top: $spacer-12;
            }
        }

        @include media-breakpoint-up(xl) {
            .megalink-multi-image--featured.card {
                .card-body {
                    padding: $spacer-9 5% $spacer-5;
                }
                .megalink-multi-image__content {
                    margin-top: $spacer-8;
                }
            }
        }
    }
</style>

<docs>
    ```js
    <VsMegalinks>
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    lg="6"
                    xl="12"
                >
                    <VsMegalinkMultiImage
                        featured
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text"
                        linkType="internal"
                        linkUrl="www.visitscotland.com"
                    >
                        <template slot="vsMultiImageHeading">
                            The Edinburgh International Festival and summer festival</template>
                        <template slot="vsMultiImageContent">
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink from local markets to renowned
                            restaurants.</p>
                        </template>
                    </VsMegalinkMultiImage>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <VsMegalinkMultiImage
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text 1"
                        linkType="external"
                        linkUrl="www.visitscotland.com"
                    >
                        <template slot="vsMultiImageHeading">
                            Count 7,000 shining stars in the iconic
                            galloway forest</template>
                        <template slot="vsMultiImageContent">
                            <p>Right across the country, you’ll find amazing
                            places to eat and drink from local markets to renowned
                            restaurants. Here are some recomm…</p>
                        </template>
                    </VsMegalinkMultiImage>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <VsMegalinkMultiImage
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text 2"
                        linkType="external"
                        linkUrl="www.visitscotland.com"
                    >
                        <template slot="vsMultiImageHeading">
                            Count 7,000 shining stars in the iconic galloway forest</template>
                        <template slot="vsMultiImageContent">
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink
                            from local markets to renowned restaurants.
                            Here are some recomm…</p>
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink
                            from local markets to renowned restaurants.
                            Here are some recomm…</p>
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink
                            from local markets to renowned restaurants.
                            Here are some recomm…</p>
                        </template>
                    </VsMegalinkMultiImage>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <VsMegalinkMultiImage
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        linkType="download"
                        linkUrl="www.visitscotland.com"
                    >
                        <template slot="vsMultiImageHeading">
                            Soar through the air on a boat of Falkirk Wheel (PDF 3MB)</template>
                        <template slot="vsMultiImageContent">
                            <p>Right across the country, you’ll find amazing
                            places to eat and drink from local markets to renowned
                            restaurants. Here are some recomm…</p>
                        </template>
                    </VsMegalinkMultiImage>
                </VsCol>
            </VsRow>
        </VsContainer>
    </VsMegalinks>
    ```
</docs>
