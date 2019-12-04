<template>
  <div class="d-flex flex-column-reverse vs-itinerary-summary__item">
    <slot />
  </div>
</template>

<script>
import VsIcon from "@components/elements/icon/Icon"
/**
 * TODO: Document usage
 */

export default {
  name: "VsItineraryTransportType",
  status: "prototype",
  release: "0.0.1",
  components: { VsIcon },
  props: {
    transport: {
      type: Object,
    },
  },
  computed: {
    formattedTransportIconName() {
      var formattedTransportIconName = ""
      switch (this.transport.key) {
        case "car":
          formattedTransportIconName = "car"
          break
        case "walk":
          formattedTransportIconName = "walk"
          break
        case "bus":
          formattedTransportIconName = "bus-icon"
          break
        case "ferry":
          formattedTransportIconName = "ferry"
          break
        case "train":
          formattedTransportIconName = "train"
          break
        case "sail":
          formattedTransportIconName = "sail"
          break
        case "bike":
          formattedTransportIconName = "bike"
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
  <vs-itinerary-transport-type>
   
  </vs-itinerary-transport-type>
  ```
</docs>
