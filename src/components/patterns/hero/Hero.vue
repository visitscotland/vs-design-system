<template>
    <figure class="d-flex flex-column">
        <div class="vs-hero__image-wrapper">
            <slot />

            <vs-button
                variant="transparent"
                class="d-lg-none position-absolute vs-hero__toggle-caption"
                v-if="showToggle"
                :animate="false"
                @click.native="toggleCaption"
            >
                <vs-icon name="information" variant="light" size="sm" />
                <span class="sr-only">{{ this.toggleButtonText }}</span>
            </vs-button>
        </div>

        <vs-container
            class="position-relative vs-hero__caption-wrapper"
            :class="[showCaption ? 'd-flex' : 'd-none d-lg-flex']"
        >
            <figcaption ref="figcaption">
                <vs-row>
                    <vs-col>
                        <div class="p-4" v-if="this.showCaptionData">
                            <p class="vs-hero__image-caption" v-if="this.caption">
                                {{ this.caption }}
                            </p>
                            <p class="vs-hero__image-credit m-0" v-if="this.credit">
                                &copy; {{ this.credit }}
                            </p>
                        </div>
                    </vs-col>
                    <vs-col cols="auto" class="pl-0" v-if="showMap">
                        <div class="map__wrapper">
                            <vs-image-location-map
                                :latitude="this.latitude"
                                :longitude="this.longitude"
                                map-outline-color="#FFFFFF"
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
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"

/**
 * Hero image element
 */
export default {
    name: "VsHero",
    status: "prototype",
    release: "0.0.1",
    components: { VsContainer, VsRow, VsCol, VsImageLocationMap, VsButton, VsIcon },
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
        },

        /**
         * The image credit
         */
        credit: {
            type: String,
        },

        /**
         * The caption for the hero's image
         */
        caption: {
            type: String,
        },

        /**
         * The image ID in the Digital Media Library
         */
        dmlId: {
            type: String,
        },

        /**
         * The source URL for the hero's image
         */
        imageSrc: {
            type: String,
        },

        /**
         * The image latitude
         */
        latitude: {
            type: String,
        },

        /**
         * The image longitude
         */
        longitude: {
            type: String,
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
            return this.caption.length || this.credit.length ? true : false
        },
        showMap() {
            // only show the map if longitude and latitude are both set
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

.vs-hero__caption-wrapper {
    @include media-breakpoint-down(lg) {
        padding: 0;
    }
}

.vs-hero__toggle-caption {
    bottom: 0;
    padding: 0.325rem;
    right: 0;
}

figure {
    position: relative;
    background-color: $color-theme-dark;
}

img {
    width: 100%;
    height: auto;
}

.vs-hero__image-wrapper {
    position: relative;
    max-height: 100vh;
    overflow: hidden;

    @include media-breakpoint-up(xl) {
        display: flex;
        flex-direction: column;
        justify-content: center; /* Centering y-axis */
        align-items: center;
    }
}

.vs-hero__overlay-text {
    font-family: $headings-font-family;
    font-size: $display1-size;
    left: 50%;
    max-width: 100%;
    position: absolute;
    text-shadow: 0 0 10px rgba(0, 0, 0, 0.5);
    top: 50%;
    transform: translate(-50%, -50%);

    @include media-breakpoint-up(sm) {
        font-size: $display1-size * 1.5;
    }

    @include media-breakpoint-up(md) {
        font-size: $display1-size * 1.75;
    }

    @include media-breakpoint-up(lg) {
        font-size: $display1-size * 2;
    }
}

figcaption {
    background-color: $color-theme-dark;
    color: $color-white;
    width: 100%;

    @include media-breakpoint-up(lg) {
        bottom: 0;
        max-width: 400px;
        position: absolute;
        right: 1rem;
        width: auto;
        z-index: 2;
    }
}

.vs-hero__image-caption {
    font-size: 0.875rem;
    font-weight: 500;
    line-height: 1rem;
}

.vs-hero__image-credit {
    font-size: 0.875rem;
    font-weight: $font-weight-light;
    line-height: 1rem;
}
</style>

<docs>
  ```jsx
    <vs-hero
      v-for="(item, index) in hero.imageExamples"
      :altText="item.altText"
      :credit="item.credit"
      :caption="item.caption"
      :image-src="item.imageSrc"
      :key="index"
      :latitude="item.latitude"
      :longitude="item.longitude"
    >
    <img 
      class="lazyload" 
      :src="item.imageSrc"
      srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
      :data-srcset="item.imageSrc" 
      :alt="item.altText"
      data-sizes="auto"
       />
      <span class="vs-hero__overlay-text text-light">Scotland</span>
    </vs-hero>
  ```
</docs>
