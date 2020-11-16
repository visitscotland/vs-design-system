<template>
    <section
        class="vs-megalinks"
        :class="`vs-megalinks--${variant}`"
        data-test="megalinks"
    >
        <VsContainer>
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

                        <div class="vs-megalinks__intro-content">
                            <!-- @slot Slot to contain optional intro content -->
                            <slot name="vsMegalinksIntro" />
                        </div>
                    </div>
                </VsCol>
            </VsRow>
            <VsRow class="vs-megalinks__links-wrapper">
                <!-- @slot Default slot to contain link blocks -->
                <slot />
            </VsRow>
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
            .vs-megalinks__links-wrapper {
                margin-bottom: (-$spacer-8);
            }

            .vs-megalinks__button {
                margin-top: $spacer-8;
            }
        }

        &--link-list {
            .vs-megalinks__button {
                margin-top: $spacer-9;
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
                .vs-megalinks__links-wrapper {
                    margin-bottom: (-$spacer-12);
                }

                .vs-megalinks__button {
                    margin-top: $spacer-12;
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
            Maecenas bibendum sodales nisi eu luctus.</p>.
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
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink
                                from local markets to renowned restaurants.</p>
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
                    <VsCol
                        cols="12"
                        md="6"
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
                    <VsCol
                        cols="12"
                        md="6"
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
                    <VsCol
                        cols="12"
                        md="6"
                        lg="12"
                    >
                        <vs-megalink-multi-image
                            featured
                            lastFeatured
                            imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                            imgAlt="This is the alt text"
                            linkType="internal"
                        >
                            <template slot="vsMultiImageHeading">
                                The Edinburgh International Festival and summer festival
                            </template>
                            <template slot="vsMultiImageContent">
                                <p>Right across the country, you’ll find amazing
                                places to eat and drink
                                from local markets to renowned restaurants.</p>
                            </template>
                        </vs-megalink-multi-image>
                    </VsCol>
                </VsRow>
            </VsContainer>
        </VsCol>
        <template slot="vsMegalinksButton">
            Button Text
        </template>
    </VsMegalinks>

    <VsMegalinks title="A megalinks link list component" class="vs-megalinks--multi-image" buttonLink="http://www.visitscotland.com">
        <VsCol
            cols="12"
            md="6"
        >
            <vs-megalink-link-list
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text"
                linkType="internal"
            >
                <template slot="vsLinkListHeading">
                    The Edinburgh International Festival and summer festival
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing places
                    to eat and drink from local markets to renowned
                    restaurants.</p>
                </template>
            </vs-megalink-link-list>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <vs-megalink-link-list
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text 1"
                linkType="external"
            >
                <template slot="vsLinkListHeading">
                    Count 7,000 shining stars in the iconic galloway forest
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing
                    places to eat and drink from local markets to renowned
                    restaurants. Here are some recomm…</p>
                </template>
            </vs-megalink-link-list>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <vs-megalink-link-list
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                imgAlt="This is the alt text 2"
                linkType="external"
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
            </vs-megalink-link-list>
        </VsCol>
        <VsCol
            cols="12"
            md="6"
        >
            <vs-megalink-link-list
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                linkType="download"
            >
                <template slot="vsLinkListHeading">
                    Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                </template>
                <template slot="vsLinkListContent">
                    <p>Right across the country, you’ll find amazing
                    places to eat and drink from local markets to renowned
                    restaurants. Here are some recomm…</p>
                </template>
            </vs-megalink-link-list>
        </VsCol>
        <template slot="vsMegalinksButton">
            Button Text
        </template>
    </VsMegalinks>
    ```
</docs>
