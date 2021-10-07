<template>
    <figure
        class="vs-image-with-caption"
        :class="imageWithCaptionClasses"
    >
        <div
            class="vs-image-with-caption__image-wrapper"
            :class="mobileOverlap ? 'vs-image-with-caption--overlapped' : ''"
        >
            <!-- @slot Default slot for image -->
            <slot>
                <VsImg
                    v-if="imageSrc"
                    class="lazyload"
                    :src="imageSrc"
                    :data-srcset="imageSrc"
                    :alt="altText"
                    data-sizes="auto"
                />
            </slot>

            <VsButton
                variant="outline-transparent"
                class="vs-image-with-caption__toggle-caption-btn"
                aria-label="Expand caption"
                :animate="false"
                :aria-controls="'image_' + imageSrc"
                :aria-expanded="showCaption ? 'true' : 'false'"
                @click.native="toggleCaption"
            >
                <span class="sr-only">
                    {{ captionButtonText }}
                </span>

                <VsIcon
                    v-if="showCaption"
                    name="close-circle"
                    variant="light"
                    size="md"
                />

                <!-- @slot Slot for custom toggle icon - used for social images -->
                <slot
                    v-else
                    name="toggle-icon"
                >
                    <VsSvg
                        path="info-toggle"
                        height="24"
                        width="24"
                    />
                </slot>
            </VsButton>
        </div>

        <div
            class="vs-image-with-caption__caption-wrapper"
            :class="captionWrapperClasses"
            :id="'image_' + imageSrc"
        >
            <!-- @slot Slot for image caption component -->
            <slot name="img-caption" />
        </div>
    </figure>
</template>

<script>

// eslint-disable-next-line no-unused-vars
import { lazysizes } from 'lazysizes';
import VsSvg from '@components/elements/svg/Svg';
import VsImg from '@components/elements/img/Img';
import VsIcon from '@components/elements/icon/Icon';
import VsButton from '@components/elements/button/Button';

/**
 * Image with toggle to open a caption and image location map
 *
 * @displayName Image With Caption
 */
export default {
    name: 'VsImageWithCaption',
    status: 'prototype',
    release: '0.0.1',
    components: {
        VsButton,
        VsSvg,
        VsImg,
        VsIcon,
    },
    props: {
        /**
         * The image alt text for screen readers
         */
        altText: {
            type: String,
            default: '',
        },

        /**
         * Chooses to show caption open by default or not: used when images are smaller than 300px
         */
        closedDefaultCaption: {
            type: Boolean,
            default: false,
        },

        /**
         * The source URL for the image
         */
        imageSrc: {
            type: String,
            default: '',
        },

        /**
         * Option for a large Hero image at top of a page
         */
        isHeroImage: {
            type: Boolean,
            default: false,
        },

        /**
         * Option if the mobile view is overlapped at the bottom
        */
        mobileOverlap: {
            type: Boolean,
            default: false,
        },

        /**
         * The screenreader text for the toggle button
         */
        toggleButtonText: {
            type: String,
            default: 'Toggle Caption',
        },
    },
    data() {
        return {
            showCaption: false,
        };
    },
    computed: {
        captionButtonText() {
            return this.showCaption ? 'Close image caption' : 'Open image caption';
        },
        imageWithCaptionClasses() {
            return {
                'vs-image-with-caption--closed-default': this.closedDefaultCaption,
                'vs-image-with-caption--hero': this.isHeroImage,
            };
        },
        captionWrapperClasses() {
            return {
                'd-block': this.showCaption,
                container: this.isHeroImage,
            };
        },
    },
    methods: {
        toggleCaption() {
            this.showCaption = !this.showCaption;
        },
    },
};
</script>

<style lang="scss">
    .vs-image-with-caption{
        position: relative;

        &__image-wrapper {
            position: relative;

            img {
                width: 100%;
                height: auto;
            }

            .vs-image-with-caption__toggle-caption-btn {
                position: absolute;
                bottom: $spacer-2;
                padding: 0;
                right: $spacer-2;
                line-height: $line_height_xs;
                z-index: 3;
                display: block;

                .vs-icon{
                    margin-top: 0;
                }

                @include media-breakpoint-up(sm) {
                    display: none;

                    .vs-image-with-caption--closed-default & {
                        display: block;
                    }
                }
            }
        }

        &__caption-wrapper {
            display: none;
            padding: 0;

            @include media-breakpoint-up(sm) {
                display: block;

                .vs-image-with-caption--closed-default & {
                    display: none;

                    .vs-caption.fullwidth-caption-wrapper {
                        display: flex;
                        position: absolute;
                        height: 100%;
                        text-align: center;
                    }
                }
            }
        }

        &--overlapped {
            .vs-image-with-caption__toggle-caption-btn {
                bottom: $spacer-9;
                right: $spacer-4;
            }
        }

        &--hero{
            margin-bottom: 0;

            .vs-image-with-caption{
                &__image-wrapper {
                    .vs-image-with-caption__toggle-caption-btn {
                        display: block;

                        @include media-breakpoint-up(lg) {
                            display: none;
                        }
                    }
                }

                &__caption-wrapper {
                    display: none;

                    @include media-breakpoint-up(sm) {
                        position: relative;
                    }

                    @include media-breakpoint-up(lg) {
                        display: block;
                    }

                    .vs-caption.large-caption-wrapper{
                        @include media-breakpoint-down(xs) {
                            position: relative;
                            top: 0;
                            width: 100%;
                            height: auto;
                            text-align: left;
                            display: block;

                            .order-2 {
                                order: 1;
                            }

                            .order-1 {
                                order: 2;
                                flex: 0 0 auto;
                                width: auto;
                                max-width: 100%;
                                align-self: auto!important;
                            }

                            .map-wrapper {
                                padding-top: 0!important;
                            }

                            .image-caption {
                                margin-bottom: $spacer-5;
                            }

                            .caption-info{
                                padding: $spacer-3 $spacer-2;
                            }
                        }

                        @include media-breakpoint-between(sm, md) {
                            position: relative;
                            top: 0;
                            width: 100%;
                            height: auto;
                            text-align: left;
                        }

                        @include media-breakpoint-up(lg) {
                            bottom: 200px;
                        }
                    }
                }
            }
        }
    }

    @include no-js {
        .vs-image-with-caption{
            &__image-wrapper {
                .vs-image-with-caption__toggle-caption-btn {
                    display: none;
                }
            }

            &__caption-wrapper {
                display: block;
            }
        }

        .vs-image-with-caption--closed-default{
            .vs-image-with-caption{
                &__image-wrapper {
                    .vs-image-with-caption__toggle-caption-btn {
                        display: none;
                    }
                }

                &__caption-wrapper{
                    .vs-caption.fullwidth-caption-wrapper{
                        position: relative;
                        display: block;
                        text-align: left;
                    }
                }
            }
        }
    }
</style>

<docs>

  ```jsx

    <h3>Large Caption Style</h3>
    <VsImageWithCaption
        v-for="(item, index) in imageWithCaption.imageExamples.large"
        :altText="item.altText"
        :image-src="item.imageSrc"
        :key="`large-${index}`"
        style="max-width:700px"
        class="mb-11"
    >
        <VsCaption
            slot="img-caption"
            :latitude="item.latitude"
            :longitude="item.longitude"
            variant="large"
        >
            <span slot="caption" v-if="item.caption">
                {{ item.caption }}
            </span>

            <span slot="credit" v-if="item.credit">
                {{ item.credit }}
            </span>
        </VsCaption>
    </VsImageWithCaption>

    <h3 style="margin-top: 7rem;">Fullwidth Caption Style</h3>
    <VsImageWithCaption
        v-for="(item, index) in imageWithCaption.imageExamples.fullwidth"
        :altText="item.altText"
        :closedDefaultCaption="item.isSmall"
        :image-src="item.imageSrc"
        :key="`fullwidth1-${index}`"
        style="max-width:700px"
    >
        <VsCaption
            slot="img-caption"
            variant="fullwidth"
        >
            <span slot="caption" v-if="item.caption">
                {{ item.caption }}
            </span>

            <span slot="credit" v-if="item.credit">
                {{ item.credit }}
            </span>
        </VsCaption>
    </VsImageWithCaption>

    <VsImageWithCaption
        v-for="(item, index) in imageWithCaption.imageExamples.small"
        :altText="item.altText"
        :closedDefaultCaption="item.isSmall"
        :image-src="item.imageSrc"
        :key="`fullwidth2-${index}`"
        style="max-width:300px"
    >
        <VsImg
            class="lazyload"
            :src="item.imageSrc"
            :data-srcset="item.imageSrc"
            :alt="item.altText"
            data-sizes="auto">
        </VsImg>

        <VsCaption
            slot="img-caption"
            variant="fullwidth"
        >
            <span slot="caption" v-if="item.caption">
                {{ item.caption }}
            </span>

            <span slot="credit" v-if="item.credit">
                {{ item.credit }}
            </span>
        </VsCaption>
    </VsImageWithCaption>

    <h3 style="margin-top: 5rem;">Social images</h3>
    <VsImageWithCaption
        v-for="(item, index) in imageWithCaption.imageExamples.social"
        :altText="item.altText"
        :image-src="item.imageSrc"
        :key="`social-${index}`"
        style="max-width:700px"
    >
        <VsImg
            class="lazyload"
            :src="item.imageSrc"
            :data-srcset="item.imageSrc"
            :alt="item.altText"
            data-sizes="auto">
        </VsImg>

        <VsSvg slot="toggle-icon" path="instagram-bg" height="24" width="24" />

        <VsCaption
            slot="img-caption"
            :latitude="item.latitude"
            :longitude="item.longitude"
            :variant="item.variant"
        >
            <span slot="caption" v-if="item.caption">
                {{ item.caption }}
            </span>

            <VsSocialCreditLink
                slot="social-link"
                :credit="item.credit"
                :socialPostUrl="item.socialPostUrl"
                :source="item.source"
            >
            </VsSocialCreditLink>
        </VsCaption>
    </VsImageWithCaption>

  ```
</docs>
