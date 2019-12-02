<template>
  <figure class="d-flex flex-column">
    <div class="vs-itinerary-stop-image__image-wrapper">
      <slot name="image" />
      <vs-button
        variant="transparent"
        class="d-lg-none position-absolute vs-itinerary-stop-image__toggle-caption"
        v-if="showToggle"
        :animate="false"
        @click.native="toggleCaption"
      >
        <vs-icon name="information" variant="light" size="sm" />
        <span class="sr-only">{{ this.toggleButtonText }}</span>
      </vs-button>
    </div>

    <vs-container
      class="position-relative vs-itinerary-stop-image__caption-wrapper"
      :class="[showCaption ? 'd-flex' : 'd-none d-lg-flex']"
    >
      <figcaption ref="figcaption">
        <vs-row>
          <vs-col>
            <div class="p-4" v-if="this.showCaptionData">
              <p class="vs-itinerary-stop-image__image-description" v-if="this.description">
                {{ this.description }}
              </p>
              <p class="vs-itinerary-stop-image__image-credit m-0" v-if="this.credit">
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
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"
import { VsContainer, VsRow, VsCol } from "@components/elements/layout"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"

/**
 * Itinerary Stop image element
 */
export default {
  name: "VsItineraryStopImage",
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
     * The description for the image
     */
    description: {
      type: String,
    },

    /**
     * The image ID in the Digital Media Library
     */
    dmlId: {
      type: String,
    },

    /**
     * The source URL for the image
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
      return this.description.length || this.credit.length ? true : false
    },
    showToggle() {
      // only show the image detail toggle button if there's a map or caption data
      return this.showMap || this.showCaptionData ? true : false
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

.vs-itinerary-stop-image__caption-wrapper {
  @include media-breakpoint-down(lg) {
    max-width: 100%;
    padding: 0;
  }
}

.vs-itinerary-stop-image__toggle-caption {
  bottom: 0;
  padding: 0.325rem;
  right: 0;
}

figure {
  position: relative;
}

img {
  width: 100%;
  height: auto;
}

.vs-itinerary-stop-image__image-wrapper {
  position: relative;
}

figcaption {
  background-color: $color-white;
  color: $color-base-text;
  width: 100%;
}

.vs-itinerary-stop-image__image-description {
  font-size: 0.875rem;
  font-weight: 500;
  line-height: 1rem;
}

.vs-itinerary-stop-image__image-credit {
  font-size: 0.875rem;
  font-weight: $font-weight-light;
  line-height: 1rem;
}
</style>

<docs>
  
  ```jsx
    <vs-itinerary-stop-image
      v-for="(item, index) in itineraries.sampleItinerary.days[0].stops"
      :altText="item.image.altText"
      :credit="item.image.credit"
      :description="item.image.description"
      :image-src="item.image.imageSrc"
      :key="index"
      :latitude="item.image.latitude"
      :longitude="item.image.longitude"
    >
    <img 
      class="lazyload" 
      :src="item.image.imageSrc"
      srcset="data:image/gif;base64,R0lGODlhAQABAAAAACH5BAEKAAEALAAAAAABAAEAAAICTAEAOw=="
      :data-srcset="item.image.imageSrc" 
      :alt="item.image.altText"
      data-sizes="auto"
      slot="image" />
      <noscript>
        <img class="img-fluid" :src="item.image.imageSrc" alt="item.image.altText" />
      </noscript>
    </vs-itinerary-stop-image>
  ```
</docs>
