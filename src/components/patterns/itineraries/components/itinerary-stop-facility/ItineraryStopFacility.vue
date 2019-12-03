<template>
  <li class="vs-itinerary-stop__facility" :class="facility.key">
    <vs-icon v-if="formattedIconName.length" :name="formattedIconName" variant="dark" size="sm" />
    <slot />
  </li>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
/**
 * TODO: Document usage
 */

export default {
  name: "VsItineraryStopFacility",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
  props: {
    facility: {
      type: Object,
    },
  },
  computed: {
    formattedIconName() {
      var formattedIconName = ""
      switch (this.facility.key) {
        case "parking":
          formattedIconName = "toilet"
          break
        case "accessparkdrop":
          formattedIconName = "toilet"
          break
        case "dsblaccess":
          formattedIconName = "accessible"
          break
        case "audioloop":
          formattedIconName = "hearing-loop"
          break
        case "petswelcom":
          formattedIconName = "pet-friendly"
          break
        case "toilet":
          formattedIconName = "toilet"
          break
        default:
          formattedIconName = ""
      }
      return formattedIconName
    },
  },
}
</script>

<style lang="scss" scoped>
.vs-breadcrumb-item {
  color: $color-base-text;
  font-size: $font-size-base;
  font-weight: $font-weight-normal;
}
</style>

<docs>
  ```jsx
  <ul style="list-style-type: none; padding: 0;">
  <vs-itinerary-stop-facility
      v-for="(facility, facilitiesIndex) in itineraries.sampleItinerary.days[0].stops[2].facilities"
      :key="facilitiesIndex"
      :facility="facility"
    >
      {{facility.value}}
    </vs-itinerary-stop-facility>
  </ul>
  ```
</docs>
