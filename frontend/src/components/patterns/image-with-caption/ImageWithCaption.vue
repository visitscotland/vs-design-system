<template>
    <figure class="vs-image-with-caption position-relative">
        <div class="vs-image-with-caption__image-wrapper">
            <slot />

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
                <vs-icon v-if="showCaption" name="close-circle" variant="light" size="sm" :padding="0"/>   
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
                v-if="showCaptionData"
            >
                <vs-row class="justify-content-center justify-content-sm-start">
                    <vs-col 
                    class="order-2 order-sm-1" 
                    :class="[!showMap ? 'align-self-center' : '']">
                        <div class="p-4">
                            <p class="vs-image-with-caption__image-caption" v-if="this.caption">
                                {{ this.caption }}
                            </p>
                            <p class="vs-image-with-caption__image-credit" v-if="this.credit">
                                &copy; {{ this.credit }}
                            </p>
                        </div>
                    </vs-col>
                    <vs-col class="col-12 col-sm-auto order-1 order-sm-2 pl-sm-0 align-self-end align-self-sm-start" v-if="showMap">
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
         * The image credit
         */
        credit: {
            type: String,
            required: false,
        },

        /**
         * The caption for the image
         */
        caption: {
            type: String,
            required: false,
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

        /**
         * Chooses to show caption open by default or not: used when images are smaller than 300px
         */
        closedDefaultCaption: {
            type: Boolean,
            default: false,
        }
    },
    computed: {
        showCaptionData() {
            return this.caption || this.credit ? true : false
        },
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
            font-size: 0.875rem;
            line-height: 1.2;
        }

        .vs-image-with-caption__image-caption {
            font-weight: 500;
        }

        .vs-image-with-caption__image-credit {
            font-weight: 100;
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
                bottom: 0;
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
                    max-width: 80px;
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
    <vs-image-with-caption
        v-for="(item, index) in imageWithCaption.imageExamples"
        :altText="item.altText"
        :credit="item.credit"
        :caption="item.caption"
        :image-src="item.imageSrc"
        :key="index"
        :latitude="item.latitude"
        :longitude="item.longitude"
        style="max-width:500px"
        :variant="item.variant"
        :closedDefaultCaption="item.isSmall"
    >
        <vs-img 
            class="lazyload" 
            :src="item.imageSrc"
            srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
            :data-srcset="item.imageSrc" 
            :alt="item.altText"
            data-sizes="auto">
            
        </vs-img>
    </vs-image-with-caption>

  ```
</docs>
