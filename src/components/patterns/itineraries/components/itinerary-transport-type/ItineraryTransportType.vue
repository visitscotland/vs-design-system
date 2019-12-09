<template>
  <span
    class="vs-itinerary-transport-type position-relative py-1"
    v-b-tooltip.hover
    :title="this.transportType.value"
  >
    <vs-icon
      v-if="formattedTransportIconName.length"
      :name="formattedTransportIconName"
      variant="dark"
      size="sm"
    />
    <slot />
  </span>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
import { VBTooltip } from "bootstrap-vue"
/**
 * TODO: Document usage
 */

export default {
  name: "VsItineraryTransportType",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
  directives: { "b-tooltip": VBTooltip },
  props: {
    transportType: {
      type: Object,
    },
  },
  computed: {
    formattedTransportIconName() {
      var formattedTransportIconName = ""
      switch (this.transportType.key) {
        case "car":
          formattedTransportIconName = "car"
          break
        case "walk":
          formattedTransportIconName = "walk"
          break
        case "bus":
          formattedTransportIconName = "bus"
          break
        case "ferry":
          formattedTransportIconName = "ferry"
          break
        case "train":
          formattedTransportIconName = "train"
          break
        case "sail":
          formattedTransportIconName = "boat"
          break
        case "bike":
          formattedTransportIconName = "cycle"
          break
        default:
          formattedTransportIconName = ""
      }
      return formattedTransportIconName
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  ```jsx
  <vs-itinerary-transport-type
    v-for="(transportType, index) in transportTypes.transportTypes"
    :key="index"
    :transportType="transportType"
  >
   <span class="sr-only">{{transportType.value}}</span>
  </vs-itinerary-transport-type>
  ```
</docs>
