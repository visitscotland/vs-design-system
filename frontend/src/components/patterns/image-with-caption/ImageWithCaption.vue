<template>
    <figure class="vs-image-with-caption">
        <div class="vs-image-with-caption__image-wrapper position-relative">
            <slot />

            <vs-button
                variant="outline-transparent"
                class="position-absolute vs-image-with-caption__toggle-caption-btn"
                :animate="false"
                :aria-controls="'image_' + imageSrc"
                :aria-expanded="showCaption ? 'true' : 'false'"
                @click.native="toggleCaption"
            >
                <vs-svg path="image-toggle" height="24" width="24" />
            </vs-button>
        </div>

        <vs-container
            :class="{ 'd-block': this.showCaption }"
            class="vs-image-with-caption__caption-wrapper position-relative"
            :id="'image_' + imageSrc"
        >
            <figcaption ref="figcaption" v-if="showCaptionData">
                <vs-row>
                    <vs-col>
                        <div class="p-4">
                            <p class="vs-image-with-caption__image-caption" v-if="this.caption">
                                {{ this.caption }}
                            </p>
                            <p class="vs-image-with-caption__image-credit m-0" v-if="this.credit">
                                &copy; {{ this.credit }}
                            </p>
                        </div>
                    </vs-col>
                    <vs-col cols="auto" class="pl-0" v-if="showMap">
                        <div class="map__wrapper">
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
        </vs-container>
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

.map__wrapper {
    max-width: 80px;
    width: 80px;
}

.vs-image-with-caption__toggle-caption-btn {
    bottom: 0.5rem;
    padding: 0;
    right: 0.5rem;
    border-radius: 50%;
    display: block;

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
        background-color: $color-theme-dark;
        color: $color-white;
        width: 100%;        

        @include media-breakpoint-up(sm) {
            bottom: 0;
            max-width: 400px;
            position: absolute;
            right: 1rem;
            width: auto;
            z-index: 2;
        }
    }

    .vs-image-with-caption__image-caption {
        font-size: 0.875rem;
        font-weight: 500;
        line-height: 1rem;
    }

    .vs-image-with-caption__image-credit {
        font-size: 0.875rem;
        font-weight: $font-weight-light;
        line-height: 1rem;
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
