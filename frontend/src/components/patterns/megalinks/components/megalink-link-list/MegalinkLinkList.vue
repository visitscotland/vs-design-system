<template>
    <div class="megalink-link-list">
        <VsStretchedLinkCard
            :link="linkUrl"
            :type="linkType"
            :img-src="imgSrc"
            :img-alt="imgAlt"
            class="megalink-link-list__wrapper"
            icon-size="xxs"
        >
            <span
                slot="stretchedCardHeader"
                class="megalink-link-list__title"
                data-test="megalink-link-list__title"
            >
                <!-- @slot Slot to contain heading -->
                <slot name="vsLinkListHeading" />
            </span>

            <VsRichTextWrapper
                slot="stretchedCardContent"
                class="lead megalink-link-list__content"
                data-test="megalink-link-list__content"
            >
                <!-- @slot Slot to contain content -->
                <slot name="vsLinkListContent" />
            </VsRichTextWrapper>
        </VsStretchedLinkCard>
    </div>
</template>

<script>
import VsStretchedLinkCard from '@components/elements/stretched-link-card/StretchedLinkCard';
import VsRichTextWrapper from '@components/elements/rich-text-wrapper/RichTextWrapper';

/**
* Megalink link list cards to be used in the megalinks component
* There is a standard and featured variant.
*
* @displayName Megalinks Link List
*/

export default {
    name: 'VsMegalinkLinkList',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsStretchedLinkCard,
        VsRichTextWrapper,
    },
    props: {
        /**
        * The source of the image used in the component
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
        * Size of the link icon - defaults to 'xs'
        * `xxs, xs, sm, md, lg, xl`)
        */
        iconSize: {
            type: String,
            default: 'xs',
            validator: (value) => value.match(/(xxs|xs|sm|md|lg|xl)/),
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
    .megalink-link-list {
        border: none;
        padding: $spacer-2 0 $spacer-3;
        position: relative;
        height: 100%;

        &:after {
            content: '';
            border-bottom: 1px solid $color-gray-tint-5;
            position: absolute;
            width: calc(100% - 16px);
            left: 8px;
            bottom: 0;
        }

        .megalink-link-list__wrapper.card {
            display: flex;
            flex-direction: row;
            padding: $spacer-2;
            border: none;
            height: 100%;

            .card-body {
                background: none;
                padding: 0;
                align-self: flex-start;
                width: 66%;
            }

            .vs-stretched-link-card__img {
                width: 33%;
                align-self: flex-start;
                margin-right: $spacer-4;
            }

            .megalink-link-list__title {
                letter-spacing: .05rem;
                line-height: $line-height-m;
            }

            .card-title {
                display: flex;
                margin-bottom: 0;

                & > a {
                    line-height: 0;
                }
            }

            .megalink-link-list__content {
                display: none;

                p {
                    display: -webkit-box;
                    -webkit-line-clamp: 3;
                    -webkit-box-orient: vertical;
                    overflow: hidden;
                }
            }
        }

        @include media-breakpoint-up(sm) {
            .megalink-link-list__wrapper.card {
                .megalink-link-list__content {
                    display: block;
                }
            }
        }

        @include media-breakpoint-up(md) {
            .megalink-link-list__wrapper.card {
                .megalink-link-list__title {
                    font-size: $small-font-size;
                }

                .megalink-link-list__content {
                    margin: $spacer-2 0 0;
                    line-height: $line-height-s;
                    display: block;

                    p {
                         display: -webkit-box;
                        -webkit-line-clamp: 3;
                        -webkit-box-orient: vertical;
                        overflow: hidden;
                        font-size: $lead-font-size;
                        margin-bottom: 0;
                    }
                }
            }

            @include media-breakpoint-up(lg) {
                .megalink-link-list__wrapper.card {
                    .megalink-link-list__content p {
                         font-size: $font-size-base;
                    }
                }
            }
        }
    }
</style>

<docs>
    ```js
    <VsMegalinks>
        <VsRow>
            <VsCol
                cols="12"
                md="6"
            >
                <VsMegalinkLinkList
                    imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                    imgAlt="This is the alt text"
                    linkType="internal"
                    linkUrl="www.visitscotland.com"
                >
                    <template slot="vsLinkListHeading">
                        The Edinburgh International Festival and summer festival</template>
                    <template slot="vsLinkListContent">
                        <p>Right across the country, you’ll find amazing places
                        to eat and drink from local markets to renowned
                        restaurants.</p>
                    </template>
                </VsMegalinkLinkList>
            </VsCol>
            <VsCol
                cols="12"
                md="6"
            >
                <VsMegalinkLinkList
                    imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                    imgAlt="This is the alt text 1"
                    linkType="external"
                    linkUrl="www.visitscotland.com"
                >
                    <template slot="vsLinkListHeading">
                        Count 7,000 shining stars in the iconic galloway forest</template>
                    <template slot="vsLinkListContent">
                        <p>Right across the country, you’ll find amazing
                        places to eat and drink from local markets to renowned
                        restaurants. Here are some recomm…</p>
                    </template>
                </VsMegalinkLinkList>
            </VsCol>
            <VsCol
                cols="12"
                md="6"
            >
                <VsMegalinkLinkList
                    imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                    imgAlt="This is the alt text 2"
                    linkType="external"
                    linkUrl="www.visitscotland.com"
                >
                    <template slot="vsLinkListHeading">
                        Count 7,000 shining stars in the iconic galloway forest</template>
                    <template slot="vsLinkListContent">
                        <p>Right across the country, you’ll find amazing places
                        to eat and drink
                        from local markets to renowned restaurants.
                        Here are some recomm…</p>
                    </template>
                </VsMegalinkLinkList>
            </VsCol>
            <VsCol
                cols="12"
                md="6"
            >
                <VsMegalinkLinkList
                    imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                    linkType="download"
                    linkUrl="www.visitscotland.com"
                >
                    <template slot="vsLinkListHeading">
                        Soar through the air on a boat of Falkirk Wheel (PDF 3MB)</template>
                    <template slot="vsLinkListContent">
                        <p>Right across the country, you’ll find amazing
                        places to eat and drink from local markets to renowned
                        restaurants. Here are some recomm…</p>
                    </template>
                </VsMegalinkLinkList>
            </VsCol>
        </VsRow>
    </VsMegalinks>
    ```
</docs>
