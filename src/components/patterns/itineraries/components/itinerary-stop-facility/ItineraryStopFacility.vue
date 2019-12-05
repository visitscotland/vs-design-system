<template>
  <dd class="itinerary-stop__facilities__dd">
    <div class="icon-wrapper">
      <vs-icon v-if="formattedIconName.length" :name="formattedIconName" variant="dark" size="xs" />
    </div>
    <slot />
  </dd>
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
          formattedIconName = "facility-park"
          break
        case "accessparkdrop":
          formattedIconName = "facility-accessparkdrop"
          break
        case "dsblaccess":
          formattedIconName = "facility-dsblacce"
          break
        case "audioloop":
          formattedIconName = "facility-audioloop"
          break
        case "petswelcom":
          formattedIconName = "facility-petswelcom"
          break
        case "toilet":
          formattedIconName = "toilet"
          break
        case "wifi":
          formattedIconName = "facility-wifi"
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
.itinerary-stop__facilities__dd {
  display: inline-table;
  text-align: center;
  width: 90px;
  position: relative;

  .icon-wrapper {
    display: block;
    width: 100%;
  }
}
</style>

<docs>
  ```jsx
    <vs-itinerary-stop-facility
        v-for="(facility, facilitiesIndex) in facilities.facilities"
        :key="facilitiesIndex"
        :facility="facility"
      >
      {{facility.value}}
    </vs-itinerary-stop-facility>
  ```
</docs>
