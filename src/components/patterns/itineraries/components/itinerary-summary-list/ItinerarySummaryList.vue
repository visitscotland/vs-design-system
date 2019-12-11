<template>
  <vs-row tag="ul" class="itinerary-summary-box__list list-unstyled">
    <slot />
  </vs-row>
</template>

<script>
/**
 * TODO: Document usage
 */

export default {
  name: "VsItinerarySummaryList",
  status: "prototype",
  release: "0.0.1",
}
</script>

<style lang="scss" scoped>
.itinerary-summary-box__list {
  background-color: $color-yellow;
  margin: 0 0 1rem 0;
}
</style>

<docs>
  ```jsx
<vs-itinerary-summary-list>
  <vs-itinerary-summary-list-item>
    <strong>Days</strong>
    <span>{{itineraries.sampleItinerary.totalDays}}</span>
  </vs-itinerary-summary-list-item>
  <vs-itinerary-summary-list-item>
    <strong>Distance <br /><abbr title="miles">mi</abbr>/<abbr title="kilometres">km</abbr></strong>
    <span>{{itineraries.sampleItinerary.totalMiles}}<span class="divider">/</span>{{itineraries.sampleItinerary.totalKM}}</span>
  </vs-itinerary-summary-list-item>
  <vs-itinerary-summary-list-item>
    <strong>Transport</strong>
    <div class="icon-wrapper">
      <vs-icon :name="itineraries.sampleItinerary.transport.key" variant="dark" size="sm" />
      {{itineraries.sampleItinerary.transport.value}}
    </div>
  </vs-itinerary-summary-list-item>
  <vs-itinerary-summary-list-item>
    <strong>Main theme</strong>
    <div class="icon-wrapper">
      <vs-icon :name="itineraries.sampleItinerary.theme.key" variant="dark" size="sm" />
      {{itineraries.sampleItinerary.theme.value}}
    </div>
  </vs-itinerary-summary-list-item>
</vs-itinerary-summary-list>
  ```
</docs>
