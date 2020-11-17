<template>
    <section
        class="megalink-single-image"
        :class="alternate ? 'megalink-single-image--alternate' : ''"
    >
        <VsImageWithCaption
            mobile-overlap
            alt-text=""
            :text-align="alternate ? 'left' : 'right'"
            :image-src="imgSrc"
            class="megalink-single-image__image"
        >
            <template slot="caption">
                <!-- @slot Slot for image caption -->
                <slot name="vsSingleImageCaption" />
            </template>
            <template slot="credit">
                <!-- @slot Slot for image credit -->
                <slot name="vsSingleImageCredit" />
            </template>
        </VsImageWithCaption>
        <VsRow>
            <VsCol
                cols="12"
                sm="10"
                lg="6"
                class="offset-sm-1"
                :class="alternate ? 'offset-lg-6' : 'offset-lg-0'"
            >

                <div class="megalink-single-image__content">
                    <VsHeading
                        level="3"
                        v-if="title"
                    >
                        {{ title }}
                    </VsHeading>
                    <VsRichTextWrapper variant="lead">
                        <!-- @slot Slot for content -->
                        <slot name="vsSingleImageContent" />
                    </VsRichTextWrapper>

                    <ul class="megalink-single-image__link-list">
                        <!-- @slot Slot for links list -->
                        <slot name="vsSingleImageLinks" />
                    </ul>

                    <div class="megalink-single-image__button">
                        <VsButton
                            :href="buttonLink"
                            v-if="buttonLink"
                        >
                            <!-- @slot Slot for button text -->
                            <slot name="vsSingleImageButtonText" />
                        </VsButton>
                    </div>
                </div>
            </VsCol>
        </VsRow>
    </section>
</template>

<script>
import VsImageWithCaption from '@components/patterns/image-with-caption/ImageWithCaption';
import VsHeading from '@components/elements/heading/Heading';
import VsRichTextWrapper from '@components/elements/rich-text-wrapper/RichTextWrapper';
import VsButton from '@components/elements/button/Button';
import { VsRow, VsCol } from '@components/elements/layout';

export default {
    name: 'VsMegalinkSingleImage',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsImageWithCaption,
        VsHeading,
        VsRichTextWrapper,
        VsButton,
        VsRow,
        VsCol,
    },
    props: {
        /**
        * Alternate layout with image and text switched sides
        */
        alternate: {
            type: Boolean,
            default: false,
        },
        /**
        * Component title
        */
        title: {
            type: String,
            default: null,
        },
        /**
        * Button url
        */
        buttonLink: {
            type: String,
            default: '',
        },
        /**
        * Image src attribute
        */
        imgSrc: {
            type: String,
            default: '',
        },
    },
};
</script>

<style lang="scss">
    .megalink-single-image {
        min-width: 100%;

        .megalink-single-image__title {
            margin-bottom: 0;
        }

        .megalink-single-image__content {
            position: relative;
            z-index: 10;
            background: $color-white;
            margin: $spacer-0 $spacer-3;
            padding: $spacer-8 $spacer-6;
            clear: both;

            p {
                margin-bottom: 1rem;

                &:first-of-type {
                    margin-top: 1rem;
                }
            }
        }

        .megalink-single-image__link-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .megalink-single-image__link-list-item {
            margin-top: $spacer-4;
            font-size: 1.125rem;

            &:first-of-type {
                margin-bottom: $spacer-0;
            }
        }

        .megalink-single-image__image {
            margin: 0 -12px (-$spacer-8);
        }

        .megalink-single-image__button {
            margin-top: $spacer-7;
        }

        @include media-breakpoint-up(sm) {
            margin: 0;

            .megalink-single-image__image {
                margin-bottom: 0;
            }

            .megalink-single-image__content {
                background: transparent;
            }
        }

        @include media-breakpoint-up(lg) {
            width: 100%;
            margin: 0 0 $spacer-11;
            display: flex;
            flex-direction: column;

            .megalink-single-image__content {
                padding: $spacer-9 $spacer-9 $spacer-9 $spacer-9;
                margin: 0;
                background: $color-white;
            }

            .megalink-single-image__image {
                width: 66%;
                align-self: flex-end;
                margin: 0 0 -200px;
            }

            &--alternate {
                .megalink-single-image__image {
                    align-self: flex-start;
                }

                .megalink-single-image__content {
                    align-self: flex-end;
                }
            }
        }

        @include media-breakpoint-up(xl) {
            .megalink-single-image__content {
                padding: $spacer-9 $spacer-12 $spacer-9 $spacer-9;
            }
        }
    }
</style>

<docs>
    ```js
    <VsMegalinks>
        <vs-col cols="12">
            <vs-megalink-single-image
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
                    <li class="megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                        >
                            This is a link here
                        </VsLink>
                    </li>
                    <li class="megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                            type="external"
                        >
                            This is an external link here
                        </VsLink>
                    </li>
                    <li class="megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                            type="download"
                        >
                            This is a download link here
                        </VsLink>
                    </li>
                </template>
                <template slot="vsSingleImageButtonText">
                    This is the button
                </template>
            </vs-megalink-single-image>
        </vs-col>
        <vs-col cols="12">
            <vs-megalink-single-image
                title="This is the second component heading"
                buttonLink="www.visitscotland.com"
                imgSrc="https://cimg.visitscotland.com/cms-images/attractions/outlander/claire-standing-stones-craigh-na-dun-outlander?size=sm"
                alternate
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
                    <li class="megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                        >
                            This is a link here
                        </VsLink>
                    </li>
                    <li class="megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                        >
                            This is a link here
                        </VsLink>
                    </li>
                    <li class="megalink-single-image__link-list-item">
                        <VsLink
                            href="www.visitscotland.com"
                        >
                            This is a link here
                        </VsLink>
                    </li>
                </template>
                <template slot="vsSingleImageButtonText">
                    This is the button
                </template>
            </vs-megalink-single-image>
        </vs-col>
    </VsMegalinks>
    ```
</docs>
