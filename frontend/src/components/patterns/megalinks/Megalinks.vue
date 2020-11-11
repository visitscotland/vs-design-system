<template>
    <section
        class="vs-megalinks"
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
                        data-test="vs-megalinks__intro"
                    >
                        <VsHeading
                            level="2"
                            class="vs-megalinks__heading"
                            data-test="vs-megalinks__heading"
                        >
                            <!-- @slot Slot to contain optional title -->
                            <slot name="vsMegalinksHeading" />
                        </VsHeading>

                        <!-- @slot Slot to contain optional intro content -->
                        <slot name="vsMegalinksIntro" />
                    </div>
                </VsCol>
            </VsRow>
            <!-- @slot Default slot to contain link blocks -->
            <slot />
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
        * Component has an optional main link at the bottom
        * this is the href for that
        */
        buttonLink: {
            type: String,
            required: false,
            default: null,
        },
    },
};
</script>

<style lang="scss">
    .vs-megalinks {
        padding: $spacer-11 0;
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

            P:last-of-type {
                margin-bottom: 0;
            }
        }

        .vs-megalinks__heading {
            margin-bottom: 0 !important;
        }

        .vs-megalinks__intro-content {
            margin-top: $spacer-6;
        }

        .vs-megalinks__button {
            width: 100%;
            margin-top: $spacer-8;
            text-align: center;
        }

        @include media-breakpoint-up(lg) {
            .vs-megalinks__intro {
                text-align: center;
                // margin-bottom: $spacer-10;
            }

            .vs-megalinks__button {
                margin-top: $spacer-10;
            }
        }
    }
</style>

<docs>
    ```js
    <VsMegalinks buttonLink="http://www.visitscotland.com">
        <template slot="vsMegalinksHeading">
            Find your experiences in the highest village of Scotland
        </template>
        <VsRichTextWrapper
            variant="lead"
            slot="vsMegalinksIntro"
            class="mt-6"
        >
            Lorem ipsum dolor sit amet consectetur adipisicing elit.
            Quae, in quidem doloribus iure, fuga laboriosam libero voluptatem
            earum, rerum eveniet cumque molestiae dolores. Fugiat eligendi
            inventore quidem, nesciunt explicabo repudiandae.
        </VsRichTextWrapper>
        <VsContainer>
            <VsRow>
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
            </VsRow>
        </VsContainer>
        <template slot="vsMegalinksButton">
            Button Text
        </template>
    </VsMegalinks>
    ```
</docs>
