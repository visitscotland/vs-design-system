<template>
    <figure class="vs-image-with-caption position-relative">
        <div class="vs-image-with-caption__image-wrapper">
            <slot>
                <vs-img
                    v-if="imageSrc"
                    class="lazyload"
                    :src="imageSrc"
                    srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
                    :data-srcset="imageSrc"
                    :alt="altText"
                    data-sizes="auto"
                >
                </vs-img>
            </slot>

            <vs-button
                variant="outline-transparent"
                class="position-absolute vs-image-with-caption__toggle-caption-btn"
                :class="{ 'd-block': this.closedDefaultCaption }"
                :animate="false"
                :aria-controls="'image_' + imageSrc"
                :aria-expanded="showCaption ? 'true' : 'false'"
                @click.native="toggleCaption"
            >
                <vs-svg v-if="!showCaption" path="image-toggle" height="24" width="24" />
                <vs-icon v-if="showCaption" name="close-circle" variant="light" size="sm" :padding="0" />
            </vs-button>
        </div>

        <div
            :class="{ 'd-block': this.showCaption, 'd-none': this.closedDefaultCaption }"
            class="vs-image-with-caption__caption-wrapper"
            :id="'image_' + imageSrc"
        >
            <figcaption
                ref="figcaption"
                :class="
                    this.variant == 'large'
                        ? 'vs-image-with-caption__large-caption'
                        : 'vs-image-with-caption__fullwidth-caption'
                "
                class="d-flex d-sm-block"
            >
                <vs-row class="justify-content-center justify-content-sm-start">
                    <vs-col
                        class="order-2 order-sm-1"
                        :class="[!showMap ? 'align-self-center' : '']"
                    >
                        <div :class="this.variant == 'large' ? 'p-4' : 'p-3 pr-8'">
                            <p class="vs-image-with-caption__image-caption">
                                <slot name="caption" />
                            </p>

                            <p class="vs-image-with-caption__image-credit">
                                <slot name="credit" />
                            </p>

                            <slot name="social-link" />
                        </div>
                    </vs-col>
                    <vs-col
                        class="col-12 col-sm-auto order-1 order-sm-2 pl-sm-0 align-self-end align-self-sm-start"
                        v-if="showMap && variant != 'fullwidth'"
                    >
                        <div class="map-wrapper pt-3 pt-sm-2 pb-sm-2 pr-sm-4 mx-auto">
                            <vs-image-location-map
                                :latitude="this.latitude"
                                :longitude="this.longitude"
                                map-outline-color="#ffffff"
                                map-marker-color="#7CC9CC"
                            ></vs-image-location-map>
                        </div>
                    </vs-col>
                </vs-row>
            </figcaption>
        </div>
    </figure>
</template>

<script>
import { lazysizes } from "lazysizes"
import VsSvg from "@components/elements/svg/Svg"
import VsButton from "@components/elements/button/Button"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"

/**
 * Image with toggle to open a caption and image location map
 */
export default {
    name: "VsImageWithCaption",
    status: "prototype",
    release: "0.0.1",
    components: { VsContainer, VsRow, VsCol, VsImageLocationMap, VsButton, VsSvg },
    data() {
        return {
            showCaption: false,
        }
    },
    props: {
        /**
         * The image alt text for screen readers
         */
        altText: {
            type: String,
            required: false,
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
            required: false,
        },

        /**
         * The image latitude
         */
        latitude: {
            type: String,
            required: false,
        },

        /**
         * The image longitude
         */
        longitude: {
            type: String,
            required: false,
        },

        /**
         * The screenreader text for the toggle button
         */
        toggleButtonText: {
            type: String,
            default: "Toggle Caption",
        },

        /**
         * Option to choose which variant to show
         */
        variant: {
            type: String,
            default: "fullwidth",
            validator: value => {
                return value.match(/(fullwidth|large)/)
            },
        },
    },
    computed: {
        showMap() {
            return this.longitude && this.latitude ? true : false
        },
    },
    methods: {
        toggleCaption() {
            return (this.showCaption = !this.showCaption)
        },
    },
}
</script>

<style lang="scss" scoped>
img {
    width: 100%;
    height: auto;
}

.vs-image-with-caption__toggle-caption-btn {
    bottom: 0.5rem;
    padding: 0;
    right: 0.5rem;
    border-radius: 50%;
    display: block;
    line-height: 1;
    z-index: 3;

    @include media-breakpoint-up(sm) {
        display: none;
    }
}

.vs-image-with-caption__caption-wrapper {
    display: none;

    @include media-breakpoint-up(sm) {
        display: block;
    }

    @include media-breakpoint-down(lg) {
        max-width: 100%;
        padding: 0;
    }

    figcaption {
        background-color: $color-gray-shade-6;
        color: $color-white;

        .vs-image-with-caption__image-caption,
        .vs-image-with-caption__image-credit {
            font-size: $small-font-size;
            line-height: 1.2;
        }

        .vs-image-with-caption__image-caption {
            font-weight: 500;
        }

        .vs-image-with-caption__image-credit {
            font-weight: $font-weight-light;
            margin-bottom: $spacer-0;
        }

        &.vs-image-with-caption__large-caption {
            position: absolute;
            top: 0;
            right: 0;
            width: 100%;
            height: 100%;
            z-index: 2;
            text-align: center;

            > .row {
                margin: 0 auto;
            }

            .vs-image-with-caption__image-caption {
                margin-bottom: $spacer-2;
            }

            .map-wrapper {
                max-width: 60px;
            }

            @include media-breakpoint-up(sm) {
                bottom: -48px;
                right: 1rem;
                top: auto;
                width: 330px;
                height: auto;
                text-align: left;

                > .row {
                    margin: 0 -16px;
                }

                .vs-image-with-caption__image-caption {
                    margin-bottom: $spacer-8;
                }

                .map-wrapper {
                    max-width: 74px;
                }
            }
        }

        &.vs-image-with-caption__fullwidth-caption {
            width: 100%;

            .vs-image-with-caption__image-caption {
                margin-bottom: $spacer-2;
            }
        }
    }
}
</style>

<docs>
  
  ```jsx

    <h3>Large Caption Style</h3>
    <vs-image-with-caption
        v-for="(item, index) in imageWithCaption.imageExamples.large"
        :altText="item.altText"
        :image-src="item.imageSrc"
        :key="`large-${index}`"
        :latitude="item.latitude"
        :longitude="item.longitude"
        variant="large"
        style="max-width:700px; margin-bottom: 5rem;"
    >
        <vs-img 
            class="lazyload" 
            :src="item.imageSrc"
            srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
            :data-srcset="item.imageSrc" 
            :alt="item.altText"
            data-sizes="auto">
        </vs-img>

        <span slot="caption">
            {{ item.caption }}
        </span>

        <span slot="credit">
            &copy; {{ item.credit }}
        </span>
    </vs-image-with-caption>

    <h3 class="mt-9">Fullwidth Caption Style</h3>
    <vs-image-with-caption
        v-for="(item, index) in imageWithCaption.imageExamples.fullwidth"
        :altText="item.altText"
        :closedDefaultCaption="item.isSmall"
        :image-src="item.imageSrc"
        :key="`fullwidth-${index}`"
        variant="fullwidth"
        style="max-width:700px"
    >
        <vs-img 
            class="lazyload" 
            :src="item.imageSrc"
            srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
            :data-srcset="item.imageSrc" 
            :alt="item.altText"
            data-sizes="auto">
        </vs-img>

        <span slot="caption">
            {{ item.caption }}
        </span>

        <span slot="credit">
            &copy; {{ item.credit }}
        </span>
    </vs-image-with-caption>

    <h3 class="mt-9">Social images</h3>
    <vs-image-with-caption
        v-for="(item, index) in imageWithCaption.imageExamples.social"
        :altText="item.altText"
        :image-src="item.imageSrc"
        :key="`social-${index}`"
        :latitude="item.latitude"
        :longitude="item.longitude"
        :variant="item.variant"
        style="max-width:700px; margin-bottom: 4rem;"
    >
        <vs-img 
            class="lazyload" 
            :src="item.imageSrc"
            srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
            :data-srcset="item.imageSrc" 
            :alt="item.altText"
            data-sizes="auto">
        </vs-img>

        <span slot="caption">
            {{ item.caption }}
        </span>

        <VsSocialCreditLink 
            slot="social-link"
            :credit="item.credit"
            :socialPostUrl="item.socialPostUrl"
            :source="item.source" 
        >
        </VsSocialCreditLink>
    </vs-image-with-caption>

  ```
</docs>
