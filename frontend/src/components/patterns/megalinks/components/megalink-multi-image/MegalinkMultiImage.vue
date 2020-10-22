<template>
    <VsStretchedLinkCard
        link="https://visitscotland.com"
        :type="linkType"
        class="megalink-multi-image p-2"
        :class="{
            'megalink-multi-image--featured' : featured,
            'megalink-multi-image--featured-last' : lastFeatured,
        }"
    >
        <VsImg
            slot="stretchedCardImage"
            :src="imgSrc"
            :alt="imgAlt"
            class="megalink-multi-image__img"
        />
        <span
            slot="stretchedCardHeader"
            class="megalink-multi-image__title"
        ><!-- @slot Slot to contain heading --><slot name="vsMultiImageHeading" /></span>

        <VsRichTextWrapper
            slot="stretchedCardContent"
            class="lead megalink-multi-image__content"
        >
            <!-- @slot Slot to contain content -->
            <slot name="vsMultiImageContent" />
        </VsRichTextWrapper>
    </VsStretchedLinkCard>
</template>

<script>
import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';
import VsRichTextWrapper from '@components/elements/rich-text-wrapper/RichTextWrapper';
import VsImg from '@components/elements/img/Img';

/**
* Megalink cards to be used in the megalinks component
* There is a standard and featured variant.
*/

export default {
    name: 'VsMegalinkMultiImage',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsStretchedLinkCard,
        VsRichTextWrapper,
        VsImg,
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
    },
};
</script>

<style lang="scss">
    .megalink-multi-image.card {
        border: none;
        margin-top: $spacer-7;

        .stretched-link {
            color: $color-base-text;
            text-decoration: none;

            &:hover {
                .megalink-multi-image__title {
                    text-decoration: underline;
                }
            }

            &:focus {
                outline: 1px solid $color-theme-primary;
            }
        }

        .card-body {
            padding: $spacer-4 0 $spacer-2;
        }

        &:hover {
            box-shadow: 10px 10px 20px $color-gray-tint-4;
        }

        .megalink-multi-image__img {
            max-width: 100%;
        }

        .megalink-multi-image__title {
            font-size: 0.875rem;
            letter-spacing: 0.0875rem;
        }

        .card-title {
            margin-bottom: 0;
        }

        .vs-link__icon {
            height: 12px;
            width: 12px;
        }

        .megalink-multi-image__content {
            margin-top: $spacer-2;

            p:last-of-type {
                margin-bottom: 0;
            }
        }
    };
    @include media-breakpoint-up(lg) {
        .megalink-multi-image.card {
            margin-top: $spacer-12;

            .card-body {
                padding-bottom: $spacer-5;
            }
        }
        .megalink-multi-image--featured.card {
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            margin-top: 0;

            .megalink-multi-image__title {
                font-size: 1.25rem;
                letter-spacing: 0.125rem;
            }

            .vs-link__icon {
                height: 16px;
                width: 16px;
            }

            .megalink-multi-image__img {
                width: 50%;
            }

            .megalink-multi-image__content {
                font-size: 1.125rem;
                margin-top: $spacer-8;
            }

            .card-body {
                max-width: 45%;
                padding: $spacer-9 5% $spacer-5 0;
            }

            &.megalink-multi-image--featured-last {
                flex-direction: row-reverse;
                 margin-top: $spacer-12;

                .card-body {
                    padding: $spacer-9 0 $spacer-5 5%;
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
                    md="6"
                    lg="12"
                >
                    <vs-megalink-multi-image
                        featured
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text"
                        linkType="internal"
                    >
                        <template slot="vsMultiImageHeading">
                            The Edinburgh International Festival and summer festival
                        </template>
                        <template slot="vsMultiImageContent">
                            <p>Right across the country, you’ll find amazing places
                            to eat and drink from local markets to renowned
                            restaurants.</p>
                        </template>
                    </vs-megalink-multi-image>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    lg="4"
                >
                    <vs-megalink-multi-image
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text 1"
                        linkType="external"
                    >
                        <template slot="vsMultiImageHeading">
                            Count 7,000 shining stars in the iconic galloway forest
                        </template>
                        <template slot="vsMultiImageContent">
                            <p>Right across the country, you’ll find amazing
                            places to eat and drink from local markets to renowned
                            restaurants. Here are some recomm…</p>
                        </template>
                    </vs-megalink-multi-image>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    lg="4"
                >
                    <vs-megalink-multi-image
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text 2"
                        linkType="external"
                    >
                        <template slot="vsMultiImageHeading">
                            Count 7,000 shining stars in the iconic galloway forest
                        </template>
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
                    </vs-megalink-multi-image>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    lg="4"
                >
                    <vs-megalink-multi-image
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        linkType="download"
                    >
                        <template slot="vsMultiImageHeading">
                            Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                        </template>
                        <template slot="vsMultiImageContent">
                            <p>Right across the country, you’ll find amazing
                            places to eat and drink from local markets to renowned
                            restaurants. Here are some recomm…</p>
                        </template>
                    </vs-megalink-multi-image>
                </VsCol>
            </VsRow>
        </VsContainer>
    </VsMegalinks>
    ```
</docs>
