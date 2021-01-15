<template>
    <section
        class="vs-megalinks"
        :class="`vs-megalinks--${variant}`"
        data-test="megalinks"
    >
        <VsContainer fluid="lg">
            <VsRow>
                <VsCol
                    cols="12"
                    md="8"
                    offset-md="2"
                >
                    <div
                        class="vs-megalinks__intro"
                        v-if="title"
                        data-test="vs-megalinks__intro"
                    >
                        <VsHeading
                            level="2"
                            class="vs-megalinks__heading"
                            data-test="vs-megalinks__heading"
                        >
                            {{ title }}
                        </VsHeading>

                        <div class="vs-megalinks__intro-content lead">
                            <!-- @slot Slot to contain optional intro content -->
                            <slot name="vsMegalinksIntro" />
                        </div>
                    </div>
                </VsCol>
            </VsRow>
            <div class="row vs-megalinks__links-wrapper">
                <!-- @slot Default slot to contain link blocks -->
                <slot />
            </div>
            <VsRow v-if="buttonLink">
                <VsCol cols="12">
                    <div
                        class="vs-megalinks__button"
                        data-test="vs-megalinks__button"
                    >
                        <VsButton :href="buttonLink">
                            <!-- @slot Slot to contain button text -->
                            <slot name="vsMegalinksButton" />
                        </VsButton>
                    </div>
                </VsCol>
            </VsRow>
        </VsContainer>
    </section>
</template>

<script>
import VsHeading from '@components/elements/heading/Heading';
import VsButton from '@components/elements/button/Button';
import {
    VsContainer,
    VsRow,
    VsCol,
} from '@components/elements/layout';

/**
 * Megalinks wrapper used with Megalinks components.
 *
 * @displayName Megalinks
 */
export default {
    name: 'VsMegalinks',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsContainer,
        VsRow,
        VsCol,
        VsHeading,
        VsButton,
    },
    props: {
        /**
        * Title for the megalinks component
        */
        title: {
            type: String,
            required: false,
            default: null,
        },
        /**
        * Component has an optional main link at the bottom
        * this is the href for that
        */
        buttonLink: {
            type: String,
            required: false,
            default: null,
        },
        /**
        * The component variant
        */
        variant: {
            type: String,
            required: false,
            default: null,
        },
    },
};
</script>

<style lang="scss">
    .vs-megalinks {
        padding: 0 0 $spacer-12;
        // make panels in a row equal height
        .row {
            display: flex;
            width: 100%;
        }
        @supports not (-ms-high-contrast: none) {
            .row > [class*='col-'] {
                display: flex;
            }
        }

        .vs-megalinks__intro {
            width: 100%;
            text-align: center;
            margin-bottom: $spacer-9;

            p:first-of-type {
                margin-top: $spacer-6;
            }

            p:last-of-type {
                margin-bottom: 0;
            }
        }

        .vs-megalinks__heading {
            margin-bottom: 0 !important;

            .heading__sub-heading {
                display: none;
            }
        }

        .vs-megalinks__intro-content,
        .vs-megalinks__intro-content .vs-rich-text-wrapper {
            margin-top: $spacer-6;
            font-size: $lead-font-size;
            line-height: $line-height-s;
        }

        .vs-megalinks__button {
            width: 100%;
            text-align: center;
        }

        &--multi-image {
            .vs-megalinks__button {
                margin-top: $spacer-8;
            }
        }

        &--link-list {
            .vs-megalinks__button {
                margin-top: $spacer-8;
            }
        }

        @include media-breakpoint-up(lg) {
            .vs-megalinks__intro {
                text-align: center;
                margin-bottom: $spacer-9;
            }

            .vs-megalinks__intro-content,
            .vs-megalinks__intro-content .vs-rich-text-wrapper {
                font-size: $font-size-lg;
                line-height: $line-height-m;
            }

            &--multi-image {
                .vs-megalinks__button {
                    margin-top:$spacer-10;
                }
            }
        }
    }
</style>

<docs>
    ```js
    <VsMegalinks title="A megalinks multi image component" class="vs-megalinks--multi-image" buttonLink="http://www.visitscotland.com">
        <template slot="vsMegalinksIntro">
            <p>Sed at mauris a est dictum luctus. Nullam viverra
            pellentesque dolor, id elementum neque viverra quis.
            Morbi lacinia est id risus facilisis porttitor ut ac mi.
            Maecenas bibendum sodales nisi eu luctus.</p>
        </template>
        <VsCol
            cols="12"
            md="12"
            lg="10"
            class="offset-lg-1"
        >
            <VsContainer>
                <VsRow>
                    <VsCol
                        cols="12"
                        md="6"
                        lg="12"
                    >
                        <VsMegalinkMultiImage
                            featured
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text"
                            linkType="internal"
                            linkUrl="https://www.visitscotland.com"
                        >
                            <template slot="vsMultiImageHeading">
                                The Edinburgh International Festival
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink
                                from local markets to renowned restaurants.</p>
                            </template>
                        </VsMegalinkMultiImage>
                    </VsCol>
                    <VsCol
                        cols="12"
                        md="6"
                        lg="4"
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text 1"
                            linkType="external"
                            linkUrl="https://www.visitscotland.com"
                        >
                            <template slot="vsMultiImageHeading">
                                Count 7,000 shining stars in the iconic galloway forest
                            </template>
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
                        lg="4"
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text 2"
                            linkType="external"
                            linkUrl="https://www.visitscotland.com"
                        >
                            <template slot="vsMultiImageHeading">
                                Count 7,000 shining stars in the iconic galloway forest
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink
                                from local markets to renowned restaurants.
                                Here are some recomm…</p>
                                <p>Right across the country, you’ll find amazing place
                                to eat and drink
                                from local markets to renowned restaurants. Here are
                                some recomm…</p>
                                <p>Right across the country, you’ll find amazing places
                                to eat and drink
                                from local markets to renowned restaurants. Here are
                                some recomm…</p>
                            </template>
                        </VsMegalinkMultiImage>
                    </VsCol>
                    <VsCol
                        cols="12"
                        md="6"
                        lg="4"
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            linkType="download"
                            linkUrl="https://www.visitscotland.com"
                        >
                            <template slot="vsMultiImageHeading">
                                Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                            </template>
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
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            linkType="download"
                            linkUrl="https://www.visitscotland.com"
                        >
                            <template slot="vsMultiImageHeading">
                                Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                            </template>
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
                    >
                        <VsMegalinkMultiImage
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            linkType="download"
                            linkUrl="https://www.visitscotland.com"
                        >
                            <template slot="vsMultiImageHeading">
                                Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                            </template>
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
                        lg="12"
                    >
                        <VsMegalinkMultiImage
                            featured
                            lastFeatured
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text"
                            linkType="internal"
                            linkUrl="https://www.visitscotland.com"
                        >
                            <template slot="vsMultiImageHeading">
                                The Edinburgh International Festival
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink
                                from local markets to renowned restaurants.</p>
                            </template>
                        </VsMegalinkMultiImage>
                    </VsCol>
                </VsRow>
            </VsContainer>
        </VsCol>
        <template slot="vsMegalinksButton">
            Button Text
        </template>
    </VsMegalinks>

    <VsMegalinks title="A megalinks link list component" variant="link-list" buttonLink="http://www.visitscotland.com">
        <VsCol
            cols="12"
            md="6"
        >
            <VsMegalinkLinkList
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text"
                linkType="internal"
                linkUrl="https://www.visitscotland.com"
            >
                <template slot="vsLinkListHeading">
                    The Edinburgh International Festival and summer festival
                </template>
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
                linkUrl="https://www.visitscotland.com"
            >
                <template slot="vsLinkListHeading">
                    Count 7,000 shining stars in the iconic galloway forest
                </template>
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
                linkUrl="https://www.visitscotland.com"
            >
                <template slot="vsLinkListHeading">
                    Count 7,000 shining stars in the iconic galloway forest
                </template>
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
                linkUrl="https://www.visitscotland.com"
            >
                <template slot="vsLinkListHeading">
                    Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing
                    places to eat and drink from local markets to renowned
                    restaurants. Here are some recomm…</p>
                </template>
            </VsMegalinkLinkList>
        </VsCol>

        <template slot="vsMegalinksButton">
            Button Text
        </template>
    </VsMegalinks>

    <VsMegalinks>
        <VsCol cols="12">
            <VsMegalinkSingleImage
                title="The Component heading"
                buttonLink="www.visitscotland.com"
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
            >
                <template slot="vsSingleImageCaption">An image of Scotland</template>
                <template slot="vsSingleImageCredit">@2020 Credit here</template>
                <template slot="vsSingleImageContent">
                    <p>
                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                        Integer et eros at est dignissim interdum. Fusce nisl metus,
                        pharetra eu feugiat vitae, porttitor eget est. Vivamus
                        condimentum urna vel ante tempor, a eleifend neque ultricies.
                        Morbi convallis, felis id semper vulputate, nisl est porta quam,
                        luctus vehicula sapien orci quis urna. Suspendisse accumsan leo
                        diam, nec faucibus neque pulvinar vitae. Duis non rutrum felis,
                        ut pretium purus. Nullam hendrerit quam vitae ipsum aliquam
                        fermentum. Fusce gravida eu est in convallis.
                    </p>
                </template>
                <template slot="vsSingleImageLinks">
                    <VsLinkListItem
                        href="www.visitscotland.com"
                    >
                        This is a link here
                    </VsLinkListItem>
                    <VsLinkListItem
                        href="www.visitscotland.com"
                        type="external"
                    >
                        This is an external link here
                    </VsLinkListItem>
                    <VsLinkListItem
                        href="www.visitscotland.com"
                        type="download"
                    >
                        This is a download link here
                    </VsLinkListItem>
                </template>
                <template slot="vsSingleImageButtonText">
                    This is the button
                </template>
            </VsMegalinkSingleImage>
        </VsCol>
    </VsMegalinks>
    ```
</docs>
