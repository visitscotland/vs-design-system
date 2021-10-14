<template>
    <figure
        class="vs-image-with-caption"
        data-test="vs-image-with-caption"
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
                    {{ toggleButtonText }}
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
            default: '',
        },
    },
    data() {
        return {
            showCaption: false,
        };
    },
    computed: {
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

            .vs-caption{
                &--large {
                    position: absolute;
                    bottom: -48px;
                    right: 0;
                    z-index: 2;
                }

                @include media-breakpoint-down(xs) {
                    &--large,
                    &--fullwidth {
                        position: absolute;
                        top: 0;
                        width: 100%;
                        height: 100%;
                        display: flex;
                        text-align: center;
                    }
                }
            }

            @include media-breakpoint-up(sm) {
                display: block;

                .vs-image-with-caption--closed-default & {
                    display: none;

                    .vs-caption--fullwidth {
                        display: flex;
                        position: absolute;
                        top: 0;
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

                    .vs-caption--large{
                        position: relative;
                        top: 0;

                        @include media-breakpoint-down(xs) {
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

                            .vs-caption{
                                &__map-wrapper {
                                    padding-top: 0!important;
                                }

                                &__image-caption {
                                    margin-bottom: $spacer-5;
                                }

                                &__caption-info{
                                    padding: $spacer-3 $spacer-2;
                                }
                            }
                        }

                        @include media-breakpoint-between(sm, md) {
                            width: 100%;
                            height: auto;
                            text-align: left;
                        }

                        @include media-breakpoint-up(lg) {
                            bottom: 200px;
                            top: auto;
                            position: absolute;
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
                    .vs-caption--fullwidth{
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
    <BsWrapper style="max-width:700px">
        <h3>Large Caption Style</h3>
        <VsImageWithCaption
            v-for="(item, index) in imageWithCaption.imageExamples.large"
            :altText="item.altText"
            :image-src="item.imageSrc"
            :key="`large-${index}`"
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
                    slot="socialLink"
                    :credit="item.credit"
                    :socialPostUrl="item.socialPostUrl"
                    :source="item.source"
                >
                </VsSocialCreditLink>
            </VsCaption>
        </VsImageWithCaption>
    </BsWrapper>

  ```
</docs>
