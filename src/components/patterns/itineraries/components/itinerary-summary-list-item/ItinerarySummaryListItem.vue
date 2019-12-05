<template>
  <vs-col tag="li" cols="6" class="itinerary-summary-list-item p-2">
    <div
      class="itinerary-summary-list-item__wrapper d-flex flex-column-reverse justify-content-between pt-5"
    >
      <slot />
    </div>
  </vs-col>
</template>

<script>
/**
 * TODO: Document usage
 */

export default {
  name: "VsItinerarySummaryListItem",
  status: "prototype",
  release: "0.0.1",
}
</script>

<style lang="scss" scoped>
.itinerary-summary-list-item__wrapper ::v-deep {
  border: 1px solid $color-base-text;
  width: 100%;
  height: 100%;
  min-height: 100px;
  font-size: 0.875rem;

  span {
    font-size: 1.625rem;
    font-family: $headings-font-family;

    &.divider {
      font-family: $headings-font-family-thin;
    }
  }
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
