<template>
    <figure class="d-flex flex-column">
        <div class="vs-image-with-caption__image-wrapper">
            <slot />
            <vs-button
                variant="outline-transparent"
                class="position-absolute vs-image-with-caption__toggle-caption"
                v-if="showToggle"
                :animate="false"
                :aria-expanded="showCaption ? 'true' : 'false'"
                :aria-controls="'image_' + imageSrc"
                @click.native="toggleCaption"
            >
                <vs-svg path="image-toggle" height="24" width="24" />
            </vs-button>
        </div>

        <vs-container
            class="position-relative vs-image-with-caption__caption-wrapper"
            :class="[showCaption ? 'd-flex' : 'd-none']"
            :id="'image_' + imageSrc"
        >
            <figcaption ref="figcaption">
                <vs-row>
                    <vs-col>
                        <div class="p-4" v-if="this.showCaptionData">
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
                                map-outline-color="#191919"
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
    },
    computed: {
        backgroundSet() {
            // TODO: finish computed property to build a whole data-bgset once
            // the JAVA image scaling solution is finished.
            return "data-bgset='" + this.imageSrc + " 320w [(max-width: 360px)]')"
        },
        backgroundStyle() {
            return "background-image: url('" + this.imageSrc + "');"
        },
        showCaptionData() {
            return this.caption.length || this.credit.length ? true : false
        },
        showToggle() {
            // only show the image detail toggle button if there's a map or caption data
            return this.showMap || this.showCaptionData ? true : false
        },
        showMap() {
            // only show the map if longitude and latitude props are both set
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
.map__wrapper {
    max-width: 80px;
    width: 80px;
}

.vs-image-with-caption__caption-wrapper {
    @include media-breakpoint-down(lg) {
        max-width: 100%;
        padding: 0;
    }
}

.vs-image-with-caption__toggle-caption {
    bottom: 0.5rem;
    padding: 0;
    right: 0.5rem;
    border-radius: 50%;
}

figure {
    position: relative;
}

img {
    width: 100%;
    height: auto;
}

.vs-image-with-caption__image-wrapper {
    position: relative;
}

figcaption {
    background-color: $color-white;
    color: $color-base-text;
    width: 100%;
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
</style>

<docs>
  
  ```jsx
    <vs-image-with-caption
        v-for="(item, index) in itineraries.sampleItinerary.days[0].stops"
        :altText="item.image.altText"
        :credit="item.image.credit"
        :caption="item.image.caption"
        :image-src="item.image.imageSrc"
        :key="index"
        :latitude="item.image.latitude"
        :longitude="item.image.longitude"
    >
        <vs-img 
            class="lazyload" 
            :src="item.image.imageSrc"
            srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
            :data-srcset="item.image.imageSrc" 
            :alt="item.image.altText"
            data-sizes="auto">
        </vs-img>
    </vs-image-with-caption>
  ```
</docs>
