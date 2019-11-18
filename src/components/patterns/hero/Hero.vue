<template>
  <figure class="d-flex flex-column">
    <div class="vs-hero__image-wrapper">
      <img :src="this.imageSrc" :alt="this.altText" :data-dml-id="this.dmlId" />

      <vs-button
        variant="transparent"
        class="d-lg-none position-absolute vs-hero__toggle-caption"
        :animate="false"
        @click.native="toggleCaption"
      >
        <vs-icon name="information" variant="light" size="sm" />
        <span class="sr-only">{{ this.toggleButtonText }}</span>
      </vs-button>
    </div>
    <vs-container
      class="position-relative d-none d-lg-flex vs-hero__caption-wrapper"
      :class="{ 'd-flex': showCaption }"
    >
      <figcaption ref="figcaption">
        <div class="d-flex justify-content-between">
          <div>
            <p class="vs-hero__image-description">{{ this.description }}</p>
            <p class="vs-hero__image-credit m-0">&copy; {{ this.credit }}</p>
          </div>
          <div class="map__wrapper" v-if="showMap">
            <vs-image-location-map
              :latitude="this.latitude"
              :longitude="this.longitude"
            ></vs-image-location-map>
          </div>
        </div>
      </figcaption>
    </vs-container>
  </figure>
</template>

<script>
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
     * The description for the hero's image
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
  width: 100px;
}

.vs-hero__caption-wrapper {
  @include media-breakpoint-down(lg) {
    padding: 0;
    max-width: 100%;
  }
}

.vs-hero__toggle-caption {
  bottom: 0;
  right: 0;
  padding: 0.325rem;

  &:focus {
    box-shadow: none;
  }
}

figure {
  position: relative;
}

.vs-hero__image-wrapper {
  object-fit: cover;
  position: relative;
  overflow: hidden;

  @include media-breakpoint-up(sm) {
    height: 200px;
  }

  @include media-breakpoint-up(md) {
    height: 300px;
  }

  @include media-breakpoint-up(lg) {
    height: 400px;
  }

  @include media-breakpoint-up(xl) {
    height: 500px;
  }
}

img {
  width: 100%;

  @include media-breakpoint-up(sm) {
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
  width: 100%;

  @include media-breakpoint-up(lg) {
    bottom: -1rem;
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
      :dmlId="item.dmlId"
    >
    </vs-hero>
  </div>
  ```
</docs>
