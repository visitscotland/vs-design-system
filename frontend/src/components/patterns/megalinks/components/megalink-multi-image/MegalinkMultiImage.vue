<template>
    <VsStretchedLinkCard
        :link="linkUrl"
        :type="linkType"
        class="megalink-multi-image p-2"
        :class="{
            'megalink-multi-image--featured' : featured,
            'megalink-multi-image--featured-last' : lastFeatured,
        }"
        data-test="megalink-multi-image-featured"
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
import VsImg from '@components/elements/img/Img';

/**
* Megalink multi-image cards to be used in the megalinks component
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
        border: none;
        position: relative;
        transition: box-shadow 800ms;

        .stretched-link {
            color: $color-base-text;
            text-decoration: none;
            letter-spacing: 0;

            &:hover {
                .megalink-multi-image__title {
                    text-decoration: underline;
                }
            }

            &:focus {
                outline: 2px solid $color-theme-primary;
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
            font-size: $font-size-sm;
            line-height: $line-height-s;
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
            line-height: $line-height-s;

            p:last-of-type {
                margin-bottom: 0;
            }
        }
    };
    @include media-breakpoint-up(xl) {
        .megalink-multi-image.card {
            margin-bottom: $spacer-11;

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
            margin-top: 0;

            .megalink-multi-image__title {
                font-size: $h3-font-size;
                letter-spacing: 0.125rem;
            }

            .vs-link__icon {
                height: 16px;
                width: 16px;
            }

            .megalink-multi-image__img {
                width: calc(50% - 20px);
            }

            .megalink-multi-image__content {
                font-size: $lead-font-size;
                margin-top: $spacer-8;
                line-height: $line-height-m;
            }

            .card-body {
                max-width: 50%;
                padding: $spacer-9 5% $spacer-5;
            }

            &.megalink-multi-image--featured-last {
                flex-direction: row-reverse;
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
                    xl="12"
                >
                    <vs-megalink-multi-image
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
                    </vs-megalink-multi-image>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <vs-megalink-multi-image
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
                    </vs-megalink-multi-image>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <vs-megalink-multi-image
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
                    </vs-megalink-multi-image>
                </VsCol>
                <VsCol
                    cols="12"
                    md="6"
                    xl="4"
                >
                    <vs-megalink-multi-image
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
                    </vs-megalink-multi-image>
                </VsCol>
            </VsRow>
        </VsContainer>
    </VsMegalinks>
    ```
</docs>
