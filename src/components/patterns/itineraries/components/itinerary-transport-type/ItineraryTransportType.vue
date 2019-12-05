<template>
  <div class="vs-itinerary-transport-type">
    <vs-icon
      v-if="formattedTransportIconName.length"
      :name="formattedTransportIconName"
      variant="dark"
      size="xs"
    />
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
    v-for="(transport, index) in transport.transport"
    :key="index"
    :transport="transport"
  >
   {{transport.value}}
  </vs-itinerary-transport-type>
  ```
</docs>
