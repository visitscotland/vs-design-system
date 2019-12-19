<template>
  <component :is="type" class="vs-summary-box__list list-unstyled" tag="ul">
    <slot />
  </component>
</template>

<script>
import { VsRow } from "@components/elements/layout"
/**
 * Itinerary Stop image element
 */
export default {
  name: "VsSummaryBoxList",
  status: "prototype",
  release: "0.0.1",
  components: {
    VsRow,
  },
  props: {
    /**
     * The image alt text for screen readers
     */
    type: {
      type: String,
      default: "vs-row",
    },
  },
}
</script>

<style lang="scss" scoped></style>

<docs>
  
  ```jsx
  <vs-summary-box-list>
    <vs-summary-box-list-item>
      <strong>Days</strong>
      <span>{{itineraries.sampleItinerary.totalDays}}</span>
    </vs-summary-box-list-item>
    <vs-summary-box-list-item>
      <strong>Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></strong>
      <span>{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</span>
    </vs-summary-box-list-item>
    <vs-summary-box-list-item>
      <strong>Transport</strong>
      <div class="icon-wrapper">
        <vs-icon :name="itineraries.sampleItinerary.transport.key" variant="dark" size="sm" />
        {{itineraries.sampleItinerary.transport.value}}
      </div>
    </vs-summary-box-list-item>
    <vs-summary-box-list-item>
      <strong>Main theme</strong>
      <div class="icon-wrapper">
        <vs-icon :name="itineraries.sampleItinerary.theme.key" variant="dark" size="sm" />
        {{itineraries.sampleItinerary.theme.value}}
      </div>
    </vs-summary-box-list-item>
  </vs-summary-box-list>
  ```
</docs>
