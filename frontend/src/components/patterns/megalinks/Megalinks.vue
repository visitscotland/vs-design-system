<template>
    <section class="vs-megalinks">
        <VsContainer>
            <VsRow>
                <VsCol
                    cols="12"
                    md="8"
                    offset-md="2"
                >
                    <div class="vs-megalinks__intro">
                        <VsHeading
                            level="2"
                            class="vs-megalinks__heading"
                        >
                            <!-- @slot Slot to contain optional title -->
                            <slot name="vs-megalinks-title" />
                        </VsHeading>

                        <!-- @slot Slot to contain optional intro content -->
                        <slot name="vs-megalinks-intro" />
                    </div>
                </VsCol>
            </VsRow>
            <!-- @slot Default slot to contain link blocks -->
            <slot />
        </VsContainer>
    </section>
</template>

<script>
import VsHeading from '@components/elements/heading/Heading';
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
    },
};
</script>

<style lang="scss">
    .vs-megalinks {
        // make panels in a row equal height
        .row {
            display: flex;
        }

        .row > [class*='col-'] {
            display: flex;
        }

        .vs-megalinks__intro {
            text-align: center;
            margin-bottom: $spacer-4;
        }

        .vs-megalinks__heading {
            margin-bottom: 0 !important;
        }

        .vs-megalinks__intro-content {
            margin-top: $spacer-6;
        }

        @include media-breakpoint-up(lg) {
            .vs-megalinks__intro {
                text-align: center;
                margin-bottom: $spacer-10;
            }
        }
    }
</style>

<docs>
    ```js
    <VsMegalinks>
        <template slot="vs-megalinks-title">
            Find your experiences in the highest village of Scotland
        </template>
        <VsRichTextWrapper
            variant="lead"
            slot="vs-megalinks-intro"
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
                    md="6"
                    lg="12"
                >
                    <vs-megalink-multi-image
                        featured
                        imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                        imgAlt="This is the alt text"
                        linkType="internal"
                    >
                        <template slot="vs-multi-image-heading">
                            The Edinburgh International Festival and summer festival
                        </template>
                        <template slot="vs-multi-image-content">
                            <p>Right across the country, you’ll find amazing places to eat and drink
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
                        <template slot="vs-multi-image-heading">
                            Count 7,000 shining stars in the iconic galloway forest
                        </template>
                        <template slot="vs-multi-image-content">
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
                        <template slot="vs-multi-image-heading">
                            Count 7,000 shining stars in the iconic galloway forest
                        </template>
                        <template slot="vs-multi-image-content">
                            <p>Right across the country, you’ll find amazing places to eat and drink
                            from local markets to renowned restaurants. Here are some recomm…</p>
                            <p>Right across the country, you’ll find amazing places to eat and drink
                            from local markets to renowned restaurants. Here are some recomm…</p>
                            <p>Right across the country, you’ll find amazing places to eat and drink
                            from local markets to renowned restaurants. Here are some recomm…</p>
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
                        <template slot="vs-multi-image-heading">
                            Soar through the air on a boat of Falkirk Wheel (PDF 3MB)
                        </template>
                        <template slot="vs-multi-image-content">
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
