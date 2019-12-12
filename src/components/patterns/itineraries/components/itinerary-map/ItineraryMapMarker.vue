<template>
  <vs-button
    class="vs-itinerary-map-marker"
    variant="transparent"
    :animate="false"
    @click.native="handleClick()"
    @mouseenter.native="handleMouseEnter()"
    @mouseleave.native="handleMouseLeave()"
    @focus.native="handleMouseEnter()"
  >
    <vs-icon
      name="map-marker-filled"
      variant="secondary-teal"
      :size="isActiveStop || isHighlighted ? 'lg' : 'md'"
      :padding="0"
    />
    <span class="vs-itinerary-map-marker__count" :class="{ active: isActiveStop }"
      ><span class="sr-only">Stop</span>{{ this.stopCount }}</span
    >
  </vs-button>
</template>

<script>
import itinerariesStore from "@components/patterns/itineraries/itineraries.store"
import VsIcon from "@components/elements/icon/Icon"
import VsButton from "@components/elements/button/Button"

/**
 * TODO: Document usage
 */

export default {
  name: "VsItineraryMapMarker",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsButton,
    VsIcon,
  },
  data() {
    return {
      isActiveStop: false,
      isHighlighted: false,
    }
  },
  props: {
    stopCount: {
      type: Number,
      required: true,
    },
  },
  itinerariesStore,
  watch: {
    currentActiveStop(newValue, oldValue) {
      if (newValue) {
        this.toggleActive()
      }
    },
    currentHighlightedStop(newValue, oldValue) {
      this.toggleHighlighted()
    },
  },
  computed: {
    currentActiveStop() {
      return itinerariesStore.getters["itineraries/getActiveStop"]
    },
    currentHighlightedStop() {
      return itinerariesStore.getters["itineraries/getHighlightedStop"]
    },
  },
  methods: {
    handleClick() {
      return itinerariesStore.dispatch("itineraries/setStopActive", this.stopCount)
    },
    handleMouseEnter() {
      return itinerariesStore.dispatch("itineraries/setStopHighlighted", this.stopCount)
    },
    handleMouseLeave() {
      return itinerariesStore.dispatch(
        "itineraries/setStopUnhighlighted",
        this.isActiveStop ? this.stopCount : null
      )
    },
    toggleActive() {
      this.isActiveStop = this.currentActiveStop === this.stopCount ? true : false
    },
    toggleHighlighted() {
      this.isHighlighted = this.currentHighlightedStop === this.stopCount ? true : false
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-itinerary-map-marker {
  font-weight: $font-weight-bold;
  padding: 0;
}

.vs-itinerary-map-marker > svg {
  position: relative;
}

.vs-itinerary-map-marker__count {
  color: $color-white;
  position: absolute;
  top: 4px;

  &.active {
    font-size: $font-size-lg;
    z-index: 2;
  }
}
</style>

<docs>
  ```jsx

    <vs-itinerary-map-marker :stopCount=1 />
    <vs-itinerary-map-marker :stopCount=2 />
    <vs-itinerary-map-marker :stopCount=3 />
  ``` 
</docs>
