<template>
  <component
    :is="type"
    class="vs-itinerary-map-marker"
    :class="isActiveStop || isHighlighted ? 'active' : ''"
    @click="handleClick()"
    @mouseenter="handleMouseEnter()"
    @mouseleave="handleMouseLeave()"
    @focus="handleMouseEnter()"
  >
    <div class="map-marker__wrapper">
      <vs-icon
        name="map-marker-filled"
        :class="isActiveStop || isHighlighted ? 'active' : ''"
        :variant="isActiveStop || isHighlighted ? 'dark' : 'secondary-teal'"
        :size="isActiveStop || isHighlighted ? 'lg' : 'md'"
        :padding="0"
      />
      <span
        class="vs-itinerary-map-marker__count"
        :class="isActiveStop || isHighlighted ? 'active' : ''"
        ><span class="sr-only">Stop</span>{{ this.feature.properties.stopCount }}</span
      >
    </div>
  </component>
</template>

<script>
import itinerariesStore from "@components/patterns/itineraries/itineraries.store"
import VsIcon from "@components/elements/icon/Icon"

/**
 * TODO: Document usage
 */

export default {
  name: "VsItineraryMapMarker",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsIcon,
  },
  data() {
    return {
      isActiveStop: false,
      isHighlighted: false,
    }
  },
  props: {
    type: {
      type: String,
      default: "button",
    },
    feature: {
      type: Object,
      required: true,
    },
  },
  itinerariesStore,
  watch: {
    activeStop(newValue) {
      if (newValue) {
        this.toggleActive()
      }
    },
    highlightedStop() {
      this.toggleHighlighted()
    },
  },
  computed: {
    activeStop() {
      return itinerariesStore.getters["itineraries/getActiveStop"]
    },
    highlightedStop() {
      return itinerariesStore.getters["itineraries/getHighlightedStop"]
    },
  },
  methods: {
    handleClick() {
      var element = document.querySelector(
        "[data-stop='" + this.feature.properties.stopCount + "']"
      )
      if (element !== null) {
        element.scrollIntoView({ block: "start", behavior: "smooth" })
      }

      return itinerariesStore.dispatch(
        "itineraries/setStopActive",
        this.feature.properties.stopCount
      )
    },
    handleMouseEnter() {
      return itinerariesStore.dispatch("itineraries/setStopHighlighted", this.feature)
    },
    handleMouseLeave() {
      return itinerariesStore.dispatch("itineraries/setStopHighlighted", null)
    },
    toggleActive() {
      this.isActiveStop = this.activeStop === this.feature.properties.stopCount ? true : false
    },
    toggleHighlighted() {
      this.isHighlighted = this.highlightedStop === this.feature ? true : false
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-itinerary-map-marker {
  background: transparent;
  border: none;
  display: block;
  font-weight: $font-weight-bold;
  padding: 0;
  position: absolute;

  &:hover,
  &:focus,
  &.active {
    z-index: 1 !important;
  }
}

svg {
  transition: all 250ms ease;
}

svg.active {
  transform: scale(1.2, 1.2) translateY(-10px);
  fill: $color-secondary-teal-shade-3 !important;
}

.vs-itinerary-map-marker__count {
  color: $color-white;
  display: block;
  font-size: 1rem;
  position: absolute;
  top: 4px;
  left: 0;
  transition: all 250ms ease;
  text-align: center;
  width: 100%;

  &.active {
    font-size: 1.5rem;
    transform: scale(1.2, 1.2) translateY(-5px);
  }
}
</style>

<docs>
  ```jsx
    const sampleGeojsonData = require("../../../../../assets/fixtures/itineraries/sampleItineraryGeojson.json")
    <vs-row class="py-5">
      <vs-col>
        <vs-itinerary-map-marker :feature="sampleGeojsonData.features[0]" />
      </vs-col>
      <vs-col>
        <vs-itinerary-map-marker :feature="sampleGeojsonData.features[1]" />
      </vs-col>
      <vs-col>
        <vs-itinerary-map-marker :feature="sampleGeojsonData.features[2]" />
      </vs-col>
    </vs-row>
  ``` 
</docs>
