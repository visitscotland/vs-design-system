<template>
  <div class="vs-itinerary-map-popup">
    <transition name="vs-map-popup--fade">
      <div v-if="isVisible" class="vs-itinerary-map-popup__panel">
        <img
          class="vs-itinerary__map-popup-image"
          :src="feature.properties.imageSrc"
          :alt="feature.properties.altText"
        />
        <h4 class="vs-itinerary__map-popup-heading" v-html="feature.properties.title"></h4>
      </div>
    </transition>
  </div>
</template>

<script>
import itinerariesStore from "@components/patterns/itineraries/itineraries.store"

/**
 * TODO: Document usage.
 */

export default {
  name: "VsItineraryMapPopup",
  status: "prototype",
  release: "0.0.1",
  data() {
    return {
      isVisible: false,
    }
  },
  itinerariesStore,
  watch: {
    feature() {
      this.isVisible = this.feature ? true : false
    },
  },
  computed: {
    feature() {
      return itinerariesStore.getters["itineraries/getHighlightedStop"]
    },
  },
}
</script>

<style lang="scss">
.vs-itinerary-map-popup {
  overflow: visible;
  pointer-events: none;
  z-index: 3 !important;
}

.vs-itinerary__map-popup-heading {
  font-family: $font-family-base;
  font-size: $font-size-base;
  font-weight: $font-weight-bold;
}

.vs-itinerary__map-popup-image {
  margin-right: 10px;
  width: 105px;
}

.vs-itinerary-map-popup__panel {
  background: $color-white;
  border-radius: 3px;
  box-shadow: 0 0 12px rgba(black, 0.2);
  display: flex;
  font-size: 13px;
  line-height: 1.2;
  margin-bottom: 110px;
  padding: 5px;
  position: relative;
  max-width: 300px;

  &::after {
    border-color: #fff transparent transparent transparent;
    border-style: solid;
    border-width: 12px 8px 0 8px;
    bottom: -12px;
    content: " ";
    height: 0;
    left: 50%;
    margin-left: -8px;
    position: absolute;
    width: 0;
    z-index: 1;
  }
}

/**
 * Transitions
 */
.vs-map-popup--fade-enter-active,
.vs-map-popup--fade-leave-active {
  transition: all 350ms;
  transform: scale3d(1, 1, 1) translateY(0px);
}
.vs-map-popup--fade-enter,
.vs-map-popup--fade-leave-to {
  opacity: 0;
  transform: scale3d(0.8, 0.8, 0.8) translateY(20px);
  // transition-delay: 125ms;
}
</style>

<docs>
  ```jsx
    const sampleGeojsonData = require("../../../../../assets/fixtures/itineraries/sampleItineraryGeojson.json")
    <vs-itinerary-map-popup />
  ``` 
</docs>
