<template>
  <figure class="d-flex flex-column">
    <div class="vs-hero__image-wrapper">
      <img :src="this.imageSrc" :alt="this.altText" />

      <vs-button
        class="d-lg-none position-absolute vs-hero__toggle-caption"
        @click="toggleCaption()"
      >
        <span class="sr-only">{{ this.toggleButtonText }}</span>
      </vs-button>
    </div>

    <figcaption ref="figcaption" class="d-none d-lg-flex" :class="{ 'd-flex': showCaption }">
      <vs-container class="d-flex">
        <div>
          <p class="vs-hero__image-description">{{ this.description }}</p>
          <p class="vs-hero__image-credit m-0">&copy; {{ this.credit }}</p>
        </div>
        <div class="map__wrapper">
          <vs-image-location-map :latitude="this.latitude" :longitude="this.longitude">
          </vs-image-location-map>
        </div>
      </vs-container>
    </figcaption>
  </figure>
</template>

<script>
import { VsButton } from "../../elements/button"
import { VsContainer, VsRow, VsCol } from "../../elements/layout"
import VsImageLocationMap from "@components/patterns/image-location-map/ImageLocationMap"

/**
 * Hero image element
 */
export default {
  name: "VsHero",
  status: "prototype",
  release: "0.0.1",
  components: { VsContainer, VsRow, VsCol, VsImageLocationMap },
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
     * The source URL for the hero's image
     */
    imageSrc: {
      type: String,
    },

    /**
     * The description for the hero's image
     */
    description: {
      type: String,
    },

    /**
     * The image credit
     */
    credit: {
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
  methods: {
    toggleCaption() {
      console.log("test")
      return this.showCaption !== this.showCaption
    },
  },
}
</script>

<style lang="scss" scoped>
@import "~bootstrap/scss/utilities/flex";
@import "~bootstrap/scss/utilities/position";
@import "~bootstrap/scss/utilities/spacing";

.map__wrapper {
  width: 100px;
}

.vs-hero__toggle-caption {
  bottom: 0;
  right: 0;
  z-index: 2;
}

figure {
  border: 1px solid red;
}

.vs-hero__image-wrapper {
  object-fit: cover;
  position: relative;
  overflow: hidden;

  @include media-breakpoint-up(lg) {
    height: 400px;
  }
}

img {
  width: 100%;

  @include media-breakpoint-up(lg) {
    position: absolute;
    left: 50%;
    top: 50%;

    -webkit-transform: translate(-50%, -50%);
    -moz-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    -o-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
  }
}

figcaption {
  background-color: $color-gray-shade-6;
  color: $color-white;
  padding: $spacer-4;

  @include media-breakpoint-up(lg) {
    bottom: 0;
    max-width: 400px;
    position: absolute;
    right: 0;
    width: auto;
    z-index: 2;
  }
}

.vs-hero__image-description {
  font-size: 0.875rem;
  font-weight: 500;
}

.vs-hero__image-credit {
  font-size: 0.875rem;
  font-weight: $font-weight-normal;
}
</style>

<docs>
  ```jsx
  <div>
    <vs-hero
      v-for="(item, index) in hero.imageExamples"
      :key="index"
      :image-src="item.imageSrc"
      :title="item.title"
      :description="item.description"
      :credit="item.credit"
      :latitude="item.latitude"
      :longitude="item.longitude"
      :altText="item.altText"
    >
    </vs-hero>
  </div>
  ```
</docs>
